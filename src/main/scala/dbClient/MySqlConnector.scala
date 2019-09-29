package dbClient

import definitions.ToDoTypes._
import scalikejdbc.{DB, _}

import scala.concurrent.{ExecutionContext, Future}

object MySqlConnector {

  def toUrgency(urgency: String) = urgency match {
    case "maybeLater"   => MaybeLater
    case "urgent"       => Urgent
    case "notImportant" => NotImportant
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
}
