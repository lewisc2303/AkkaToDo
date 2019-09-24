package todoHandler

import definitions.ToDoTypes.{ToDo, Urgent}

class TodoReader(toDos: List[ToDo]) {
  def getAllToDos: Either[String, List[ToDo]] = toDos match {
    case Nil           => Left("No ToDo items could be found")
    case nonEmptyToDos => Right(nonEmptyToDos)
  }

  def getUrgentToDos: Either[String, List[ToDo]] = {
    val urgentToDos = toDos.filter(todo => todo.urgency == Urgent)
    urgentToDos match {
      case Nil           => Left("No urgent ToDo items could be found")
      case nonEmptyToDos => Right(nonEmptyToDos)
    }
  }
}
