package definitions

import java.time.LocalDate

object ToDoTypes {
  case class ToDo(item: Item, dateCreated: LocalDate, urgency: Urgency)
  case class Item(title: String, description: String, deadline: LocalDate)

  sealed trait Urgency
  case object Urgent       extends Urgency
  case object MaybeLater   extends Urgency
  case object NotImportant extends Urgency
}
