package controller

import akka.http.scaladsl.server.{Directives, Route}

class Routes extends Directives {

  lazy val routes: Route =
    path("health") {
      get {
        extractExecutionContext { implicit executor =>
          complete("pong")
        }
      }
    }
}
