package controller

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.{Directives, Route}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import todoHandler.TodoReader

class Routes(todoReader: TodoReader) extends Directives {

  lazy val routes: Route =
    path("health") {
      get {
        complete("pong")
      }
    } ~
      path("todos") {
        get {
          todoReader.getAllToDos match {
            case Right(value) => complete(value)
            case Left(error)  => complete(HttpResponse(InternalServerError, entity = error))
          }
        }
      }
}
