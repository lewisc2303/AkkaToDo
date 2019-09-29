package controller

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import definitions.ToDoTypes.ToDo
import todoHandler.TodoReader

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
            todoReader.getAllToDos.map(x => Handler.handle(x))
          )
        }
      } ~
      path("todos" / "create") {
        post {
          entity(implicitly[FromRequestUnmarshaller[ToDo]]) { todo =>
            println(todo)
            complete(StatusCodes.OK) //todo add validations for post request
          }
        }
      }
}
