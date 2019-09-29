import InMemoryStore.ToDoList
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import controller.Routes
import todoHandler.TodoReader

import scala.util.{Failure, Success}

object Boot extends App {

  implicit val system           = ActorSystem("myToDos")
  implicit val materializer     = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  println("Binding...")
  val toDoSource = ToDoList.testInMemoryList
  val todoReader = new TodoReader(toDoSource)
  val router     = new Routes(todoReader)
  val routes     = router.routes
  val binding    = Http().bindAndHandle(routes, "0.0.0.0", 8080)

  binding.onComplete {
    case Success(bound) => println(s"Bound to: " + bound.localAddress)
    case Failure(e)     => println("Failed to bind http service", e)
  }
}
