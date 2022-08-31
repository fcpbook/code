val TinyScalaUtils_Repository =
   "TinyScalaUtils" at "https://charpov.github.io/TinyScalaUtils/maven/"

ThisBuild / resolvers ++= Seq(TinyScalaUtils_Repository, Resolver.mavenLocal)

val Parallel           = "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
val Async              = "com.github.rssh"        %% "dotty-cps-async"            % "0.9.11"
val Actors             = "com.typesafe.akka"      %% "akka-actor-typed"           % "2.7.0"
val SLF4J              = "org.slf4j"               % "slf4j-simple"               % "2.0.3"
val Reactor            = "io.projectreactor"       % "reactor-core"               % "3.4.24"
val TinyScalaUtils     = "com.github.charpov"     %% "tiny-scala-utils"           % "1.0.0"
val TinyScalaUtilsTest = "com.github.charpov"     %% "tiny-scala-utils-test"      % "1.0.0" % Test

ThisBuild / scalaVersion := "3.2.0"

ThisBuild / scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-feature",     // Emit warning for usages of features that should be imported explicitly.
  "-unchecked",   // Enable detailed unchecked (erasure) warnings.
)

ThisBuild / javacOptions ++= Seq("--source", "17", "-deprecation", "-Xlint")

lazy val fcp = (project in file(".")).settings(
  version := "1.1.0",
  Compile / unmanagedSourceDirectories := Seq(
    baseDirectory.value / "Scala" / "main",
    baseDirectory.value / "Java" / "main"
  ),
  Test / unmanagedSourceDirectories := Seq(baseDirectory.value / "Scala" / "test"),
  fork                        := true,
  Test / parallelExecution    := false,
  Test / run / outputStrategy := Some(StdoutOutput),
  Test / javaOptions += "-Xmx8G",
  libraryDependencies ++= Seq(
    Parallel,
    TinyScalaUtils,
    TinyScalaUtilsTest,
  ),
  libraryDependencies ++= Seq( // for Chapter 27 only
    Async,
    Reactor,
    Actors.cross(CrossVersion.for3Use2_13),
    SLF4J,
  ),
)
