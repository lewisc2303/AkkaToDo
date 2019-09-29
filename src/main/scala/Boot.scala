import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import controller.Routes
import dbClient.MySqlConnector._
import scalikejdbc.config.DBs
import todoHandler.TodoReader

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

object Boot extends App {

  implicit val system           = ActorSystem("myToDos")
  implicit val materializer     = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  println("connecting to Database")
  DBs.setupAll()

  println("Binding...")
  val toDoSource = getAllToDos
  val todoReader = new TodoReader(toDoSource, global)
  val router     = new Routes(todoReader, global)
  val routes     = router.routes
  val binding    = Http().bindAndHandle(routes, "0.0.0.0", 8080)

  binding.onComplete {
    case Success(bound) =>
      println(s"Bound to: " + bound.localAddress)
      val shutdown = Future(gracefulShutdown(binding = bound))
      Await.result(shutdown, 10 seconds)
    case Failure(e) => println("Failed to bind http service", e)
  }

  def gracefulShutdown(time: FiniteDuration = 10.seconds, binding: Http.ServerBinding) =
    sys.addShutdownHook {
      for {
        _ <- Future {
          println(s"shuting down in $time milliSeconds")
        }
        _ <- Future(DBs.closeAll()) //shutdown mySql connection
        _ <- binding.unbind() // unbind server, all connections should be drained
        _ <- Http().shutdownAllConnectionPools() // shutdown connection pools
        _ <- system.terminate()
      } yield ()
    }
}
//todo figure out why not shutting down correctly with correct delay
