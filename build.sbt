import sbt.Keys.libraryDependencies


lazy val root = (project in file("."))
  .settings(
    name := "scala-dev-mooc-2021-07",
    version := "0.1",
    scalaVersion := "2.13.3"
  )