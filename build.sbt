name := "AkkaToDo"

version:= "1.1.3"

scalaVersion := "2.12.8"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalacOptions ++= Seq("-target:jvm-1.8", "-unchecked", "-deprecation", "-feature")

//addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.0.4")

mainClass in Compile := Some("Boot")

libraryDependencies ++= {
  Seq(
    "org.scalatest" %% "scalatest" % "3.0.8" % Test
  )
}
