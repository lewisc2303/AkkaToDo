
name := "AkkaToDo"

version:= "1.1.3"

scalaVersion := "2.13"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalacOptions ++= Seq("-target:jvm-1.8", "-unchecked", "-deprecation", "-feature")
