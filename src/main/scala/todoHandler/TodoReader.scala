package todoHandler

import definitions.ToDoTypes.{ToDo, Urgent}

class TodoReader(toDos: List[ToDo]) {
  def getAllToDos: Either[String, List[ToDo]] = toDos match {
    case Nil => Left("No ToDo items could be found")
    case _   => Right(toDos)
  }

  def getUrgentToDos(): List[ToDo] = {
    toDos.filter(todo => todo.urgency == Urgent)
  }
}
