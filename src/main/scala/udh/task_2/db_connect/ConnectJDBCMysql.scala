package udh.task_2.db_connect

import java.sql.{Connection, DriverManager, ResultSet}

object ConnectJDBCMysql {

  val driver = "com.mysql.jdbc.Driver"
  val url = "jdbc:mysql://localhost:3306/udh_keys"
  val password = "...."
  val username = "...."

  var connection: Connection = _
  var resultSet: ResultSet = _
  Class.forName(driver)

  def connectionInitializeAndGetQuery(query: String): ResultSet ={
    try {
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement()
      resultSet = statement.executeQuery(query)
    } catch {
      case e: Throwable => e.printStackTrace()
    }
    connection.close()
    resultSet
  }

  def saveDocumentInfoToTable(uuid: String, key: String): Unit ={
    val query = s"INSERT INTO uuid_key VALUES($uuid, $key)"
    connectionInitializeAndGetQuery(query)
  }

  def getSomeDocumentInfo(uuid: String): String ={
    val query = s"SELECT doc_key FROM uuid_key WHERE uuid = $uuid"
    val resultSet = connectionInitializeAndGetQuery(query)
    resultSet.toString
  }
}
