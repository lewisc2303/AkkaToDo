package controller

import akka.http.scaladsl.server.{Directives, Route}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import todoHandler.TodoReader

class Routes(todoReader: TodoReader) extends Directives {

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
      }
}
