package InMemoryStore

import java.time.LocalDate

import definitions.ToDoTypes.{Item, MaybeLater, ToDo}

object ToDoList {
  val testInMemoryList = List(
    ToDo(
      Item("Go Shopping", "get some potatoes", LocalDate.now()),
      LocalDate.now(),
      MaybeLater
    )
  )
}
