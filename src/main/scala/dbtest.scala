import dbClient.MySqlConnector.toUrgency
import definitions.ToDoTypes.{Item, ToDo}
import scalikejdbc.{DB, _}
import scalikejdbc.config.DBs

object dbtest extends App {

  // DBs.setup/DBs.setupAll loads specified JDBC driver classes.
  DBs.setupAll()

  println("[DATABASE] Opening DB connection")

  val memberIds = DB readOnly { implicit session =>
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
  println(memberIds)

  println("[DATABASE] Closing connection...")

  // wipes out ConnectionPool
  DBs.closeAll()
}
