name := """mock-piq-v2"""
organization := "com.titus"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.8"


libraryDependencies += "org.apache.logging.log4j" % "log4j-api" % "2.17.1"
libraryDependencies += "org.apache.logging.log4j" % "log4j-core" % "2.17.1"


libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.titus.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.titus.binders._"
