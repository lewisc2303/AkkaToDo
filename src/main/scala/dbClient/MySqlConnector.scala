package dbClient

import java.sql.Date
import java.time.LocalDate

import definitions.ToDoTypes.{MaybeLater, NotImportant, Urgent, _}
import scalikejdbc.{DB, _}

import scala.concurrent.{ExecutionContext, Future}

object MySqlConnector {

  def toUrgency(urgency: String) = urgency match {
    case "maybeLater"   => MaybeLater
    case "urgent"       => Urgent
    case "notImportant" => NotImportant
  }
  def fromUrgency(urgency: Urgency) = urgency match {
    case MaybeLater   => "maybeLater"
    case Urgent       => "urgent"
    case NotImportant => "notImportant"
  }

  def getAllToDos(implicit ec: ExecutionContext): Future[List[ToDo]] = {

    concurrent.Future {
      DB readOnly { implicit session =>
        sql"""SELECT name as name,
            description as description,
            dateCreated as dateCreated,
            urgency as urgency,
            deadline as deadline
            FROM ToDos
        """
          .map { result =>
            val name        = result.string("name")
            val description = result.string("description")
            val dateCreated = result.localDate("dateCreated")
            val urgency     = result.string("urgency")
            val deadline    = result.localDate("deadline")

            ToDo(Item(name, description, deadline), dateCreated, toUrgency(urgency))
          }
          .list()
          .apply
      }
    }
  }

  def persistToDo(toDo: ToDo) = DB localTx { implicit session =>
    def toDate(localDate: LocalDate): Date =
      new Date(localDate.toEpochDay) //todo fix epoch date conversion
    val sqlQuery =
      sql"""INSERT INTO ToDos (name, description, dateCreated, urgency, deadline)
          VALUES (${toDo.item.title},
          ${toDo.item.description},
          ${toDate(toDo.dateCreated)},
          ${fromUrgency(toDo.urgency)},
          ${toDate(toDo.item.deadline)})
      """
    sqlQuery.updateAndReturnGeneratedKey.apply()
  }
}
