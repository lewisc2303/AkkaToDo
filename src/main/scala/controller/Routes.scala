package controller

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import definitions.ToDoTypes.ToDo
import todoHandler.TodoReader

class Routes(todoReader: TodoReader) extends Directives with ImplicitJsonConv {

  lazy val routes: Route =
    path("health") {
      get {
        complete("pong")
      }
    } ~
      path("todos" / "all") {
        get {
          complete(
            Handler.handle(todoReader.getAllToDos)
          )
        }
      } ~
      path("todos" / "urgent") {
        get {
          complete(
            Handler.handle(todoReader.getUrgentToDos)
          )
        }
      } ~
      path("todos" / "create") {
        post {
          entity(implicitly[FromRequestUnmarshaller[ToDo]]) { todo =>
            println(todo)
            complete(StatusCodes.OK)
          }
        }
      }
}
