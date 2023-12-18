val TinyScalaUtils_Repository =
   "TinyScalaUtils" at "https://charpov.github.io/TinyScalaUtils/maven/"

ThisBuild / resolvers ++= Seq(TinyScalaUtils_Repository, Resolver.mavenLocal)

val Parallel           = "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
val Async              = "com.github.rssh"        %% "dotty-cps-async"            % "0.9.17"
val Actors             = "com.typesafe.akka"      %% "akka-actor-typed"           % "2.8.0"
val SLF4J              = "org.slf4j"               % "slf4j-simple"               % "2.0.5"
val Reactor            = "io.projectreactor"       % "reactor-core"               % "3.5.4"
val TinyScalaUtils     = "com.github.charpov"     %% "tiny-scala-utils"           % "1.1.0"
val TinyScalaUtilsTest = "com.github.charpov"     %% "tiny-scala-utils-test"      % "1.1.0" % Test

ThisBuild / scalaVersion := "3.3.2-RC1"

ThisBuild / scalacOptions ++= Seq(
  "-deprecation",   // Emit warning and location for usages of deprecated APIs.
  "-feature",       // Emit warning for usages of features that should be imported explicitly.
  "-unchecked",     // Enable detailed unchecked (erasure) warnings.
  "-new-syntax",    // Require `then` and `do` in control expressions.
  "-source:future", // source version.
  "-language:noAutoTupling", // Disable auto-tupling
  "-Wunused:imports",        // check unused imports.
)

ThisBuild / javacOptions ++= Seq("--source", "21", "-deprecation", "-Xlint")

lazy val fcp = (project in file(".")).settings(
  version := "1.1.1",
  Compile / unmanagedSourceDirectories := Seq(
    baseDirectory.value / "Scala" / "main",
    baseDirectory.value / "Java" / "main"
  ),
  Test / unmanagedSourceDirectories := Seq(baseDirectory.value / "Scala" / "test"),
  fork                              := true,
  Test / parallelExecution          := false,
  Test / run / outputStrategy       := Some(StdoutOutput),
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
