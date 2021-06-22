package com.github.renardeinside.demos

import java.sql.DriverManager
import com.typesafe.scalalogging.LazyLogging

object SampleApp extends App with LazyLogging {

  val driverClassName = "com.simba.spark.jdbc.Driver"

  def safeGet(variableName: String): String = {
    val value = sys.env.get(variableName)
    value match {
      case Some(value) => value
      case None => throw new RuntimeException(s"Environment variable $variableName is not defined")
    }
  }

  def connectionUrl: String = {
    val databricksHost = safeGet("DATABRICKS_HOST")
    val databricksToken = safeGet("DATABRICKS_TOKEN")
    val databricksHttpPath = safeGet("DATABRICKS_HTTP_PATH")

    val url = s"jdbc:spark://$databricksHost:443/default;transportMode=http;ssl=1;httpPath=$databricksHttpPath;AuthMech=3;UID=token;PWD=$databricksToken"
    url
  }

  logger.info("Checking driver class")
  Class.forName(driverClassName)
  logger.info("Checking driver class - done")

  logger.info("Connecting to the SQL endpoint")
  val connection = DriverManager.getConnection(connectionUrl)
  logger.info("Connecting to the SQL endpoint - done")

  try {
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SHOW TABLES")

    while (resultSet.next()) {
      val dbName = resultSet.getString(1)
      val tableName = resultSet.getString(2)
      println(f"| $dbName%10s | $tableName%40s |")
    }
  }
  catch {
    case e: Throwable => e.printStackTrace()
  }
  connection.close()
  logger.info("Connection closed gracefully")
}