name := "akka-test"

organization := "akka-test"

version := "0.0.1"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
  "com.typesafe.akka" %% "akka-cluster" % "2.3.6",
  "com.typesafe.akka" %% "akka-slf4j" % "2.3.6",
  "ch.qos.logback" % "logback-classic" % "1.0.7"
)

resolvers ++= Seq(
  "maven" at "http://maven.oschina.net/content/groups/public/",
  "Twitter Maven Repo" at "http://maven.twttr.com/",
  "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"
)


initialCommands := "import scala-test1.scalatest1._"

