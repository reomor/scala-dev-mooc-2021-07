import sbt.Keys.libraryDependencies

import Dependencies._


lazy val root = (project in file("."))
  .settings(
    name := "scala-dev-mooc-2021-07",
    version := "0.1",
    scalaVersion := "2.13.3",
    libraryDependencies ++= zio
  )

libraryDependencies += "org.scalatest" %% "scalatest-flatspec" % "3.2.9" % "test"
