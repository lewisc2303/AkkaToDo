import scalikejdbc.{DB, _}
import scalikejdbc.config.DBs

object dbtest extends App {

  Class.forName("com.mysql.jdbc.Driver")

  // DBs.setup/DBs.setupAll loads specified JDBC driver classes.
  DBs.setupAll()

  println("[DATABASE] Opening DB connection")

  val memberIds = DB readOnly { implicit session =>
    sql"select id from ToDos".map(_.long(1)).list.apply()
  }
  println(memberIds)

  println("[DATABASE] Closing connection...")

  // wipes out ConnectionPool
  DBs.closeAll()
}
