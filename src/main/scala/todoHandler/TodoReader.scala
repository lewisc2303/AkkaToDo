package todoHandler

import definitions.ToDoTypes.ToDo

import scala.concurrent.{ExecutionContext, Future}

class TodoReader(toDos: Future[List[ToDo]], implicit val ec: ExecutionContext) {

  def getAllToDos =
    toDos.map {
      case Nil           => Left("No ToDo items could be found")
      case nonEmptyToDos => Right(nonEmptyToDos)
    }
}
