name := "UDH"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.3",
  "com.typesafe.akka" %% "akka-stream" % "2.5.14",
  "com.typesafe.akka" %% "akka-actor" % "2.5.14",
  "mysql" % "mysql-connector-java" % "6.0.6",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.3",
  "org.hibernate" % "hibernate-core" % "5.2.1.Final",
  "org.hibernate" % "hibernate-entitymanager" % "5.2.1.Final"
)

        