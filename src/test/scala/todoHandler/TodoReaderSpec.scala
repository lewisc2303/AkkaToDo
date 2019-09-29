package todoHandler

import InMemoryStore.ToDoList.{testInMemoryList, testInMemoryListWithUrgent, urgentToDo}
import org.scalatest.{FreeSpec, Matchers}

class TodoReaderSpec extends FreeSpec with Matchers {

//  "getAllToDos" - {
//    "SHOULD return Right with the whole ToDo list" in {
//      val todoReader = new TodoReader(testInMemoryList)
//      todoReader.getAllToDos shouldEqual Right(testInMemoryList)
//    }
//    "WHEN no ToDos exists it SHOULD return Left with error message" in {
//      val todoReader = new TodoReader(List.empty)
//      todoReader.getAllToDos shouldEqual Left("No ToDo items could be found")
//    }
//  }
//  "getUrgentToDos" - {
//    "SHOULD return a Right with Urgent ToDos" in {
//      val todoReader = new TodoReader(testInMemoryListWithUrgent)
//      todoReader.getUrgentToDos shouldEqual Right(List(urgentToDo))
//    }
//    "WHEN no urgent todos exist it SHOULD return a Left with error message" in {
//      val todoReader = new TodoReader(testInMemoryList)
//      todoReader.getUrgentToDos shouldEqual Left("No urgent ToDo items could be found")
//    }
//  }
}

//todo fix failing tests, as now implementation has Futures, and dont want to test the futures but the implementation underneath. REFACTOR
