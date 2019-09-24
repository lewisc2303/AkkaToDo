package InMemoryStore

import java.time.LocalDate

import definitions.ToDoTypes.{Item, MaybeLater, ToDo, Urgent}

object ToDoList {
  val testInMemoryList: List[ToDo] = List(
    ToDo(
      Item("Go Shopping", "get some potatoes", LocalDate.now()),
      LocalDate.now(),
      MaybeLater
    )
  )

  val urgentToDo = ToDo(
    Item("The house is on fire", "Leave but take your wallet, laptop and phone", LocalDate.now()),
    LocalDate.now(),
    Urgent
  )

  val testInMemoryListWithUrgent = testInMemoryList :+ urgentToDo
}
