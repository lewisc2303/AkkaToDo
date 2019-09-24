package todoHandler

import InMemoryStore.ToDoList.testInMemoryList
import org.scalatest.{FreeSpec, Matchers}

class TodoReaderSpec extends FreeSpec with Matchers {

  "getAllToDos" - {
    "should return Right with the whole ToDo list" in {
      val todoReader = new TodoReader(testInMemoryList)
      todoReader.getAllToDos shouldEqual Right(testInMemoryList)
    }
    "should return Left with error message when no ToDos exist" in {
      val todoReader = new TodoReader(List.empty)
      todoReader.getAllToDos shouldEqual Left("No ToDo items could be found")
    }
  }
}
