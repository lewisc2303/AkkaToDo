package controller

import akka.http.scaladsl.marshalling.ToResponseMarshallable
import definitions.ToDoTypes.{Item, ToDo, Urgency}
import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

trait ImplicitJsonConv {
  implicit val toDoDecoder: Decoder[ToDo]       = deriveDecoder[ToDo]
  implicit val toDoEncoder: Encoder[ToDo]       = deriveEncoder[ToDo]
  implicit val itemDecoder: Decoder[Item]       = deriveDecoder[Item]
  implicit val itemEncoder: Encoder[Item]       = deriveEncoder[Item]
  implicit val urgencyDecoder: Decoder[Urgency] = deriveDecoder[Urgency]
  implicit val urgencyEncoder: Encoder[Urgency] = deriveEncoder[Urgency]
}

object Handler extends ImplicitJsonConv {

  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  def handle[T](f: Either[String, T]): ToResponseMarshallable = {
    f match {
      case Right(value: List[ToDo]) => value
      case Left(error: String)      => error
    }
  }
}
