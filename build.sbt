name := "AkkaToDo"

version:= "1.2.7"

scalaVersion := "2.12.8"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalacOptions ++= Seq("-target:jvm-1.8", "-unchecked", "-deprecation", "-feature")

mainClass in Compile := Some("Boot")

mainClass in assembly := Some("Boot")

enablePlugins(DockerPlugin)


val akkaVersion = "2.5.23"
val akkaHttpVersion = "10.1.8"
val circeVersion = "0.11.1"

libraryDependencies ++= scalaTest ++ akka ++ akkaHttpCirce ++ circe

lazy val scalaTest = Seq(
  "org.scalatest" %% "scalatest" % "3.0.8" % Test
)

lazy val akka = Seq(
  "com.typesafe.akka"     %% "akka-slf4j"           % akkaVersion,
  "com.typesafe.akka"     %% "akka-stream"          % akkaVersion,
  "com.typesafe.akka"     %% "akka-http"            % akkaHttpVersion,
  "com.typesafe.akka"     %% "akka-http-testkit"    % akkaHttpVersion % Test,
  "com.typesafe.akka"     %% "akka-stream-testkit"  % akkaVersion % Test
)

lazy val akkaHttpCirce = Seq(
  "de.heikoseeberger" %% "akka-http-circe" % "1.25.2"
)

lazy val circe = Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic"% circeVersion,
  "io.circe" %% "circe-refined"% circeVersion,
  "io.circe" %% "circe-parser"% circeVersion
)

dockerfile in docker := {
  // The assembly task generates a fat JAR file
  val artifact: File = assembly.value
  val artifactTargetPath = s"/app/${artifact.name}"

  new Dockerfile {
    from("openjdk:8-jre")
    add(artifact, artifactTargetPath)
    entryPoint("java", "-jar", artifactTargetPath)
    expose(7070)
  }
}