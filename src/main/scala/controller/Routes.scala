package controller

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import controller.Handler.handle
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import definitions.ToDoTypes.ToDo
import todoHandler.TodoReader
import dbClient.MySqlConnector.persistToDo

import scala.concurrent.ExecutionContext

class Routes(todoReader: TodoReader, implicit val ec: ExecutionContext)
    extends Directives
    with ImplicitJsonConv {

  lazy val routes: Route =
    path("health") {
      get {
        complete("pong")
      }
    } ~
      path("todos" / "all") {
        get {
          complete(
            todoReader.getAllToDos.map(x => handle(x))
          )
        }
      } ~
      path("todos" / "create") {
        post {
          entity(implicitly[FromRequestUnmarshaller[ToDo]]) { todo =>
            val createToDo = persistToDo(todo)
            println("[DATABASE] persisted with generatted key : " + createToDo.toString)
            complete(StatusCodes.OK) //todo add validations for post request
          }
        }
      }
}
