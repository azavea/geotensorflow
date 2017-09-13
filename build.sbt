lazy val commonSettings = Seq(
  organization := "com.azavea",
  version := "0.1.0-SNAPHOST",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-Yinline-warnings",
    "-language:implicitConversions",
    "-language:reflectiveCalls",
    "-language:higherKinds",
    "-language:postfixOps",
    "-language:existentials",
    "-feature"),
  outputStrategy := Some(StdoutOutput),
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  fork := true,
  fork in Test := true,
  parallelExecution in Test := false,
  javaOptions ++= Seq(s"-Djava.library.path=${Environment.ldLibraryPath}", "-Xmx10G"),
  test in assembly := {},
  libraryDependencies += { scalaVersion ("org.scala-lang" % "scala-reflect" % _) }.value,
  addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3"),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
  ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) },
  resolvers ++=
    Seq(
      "geosolutions" at "http://maven.geo-solutions.it/",
      "osgeo" at "http://download.osgeo.org/webdav/geotools/",
      "LocationTech GeoTrellis Snapshots" at "https://repo.locationtech.org/content/repositories/geotrellis-snapshots"
    ),
  assemblyMergeStrategy in assembly := {
    case m if m.toLowerCase.endsWith("manifest.mf") => MergeStrategy.discard
    case m if m.toLowerCase.matches("meta-inf.*\\.sf$") => MergeStrategy.discard
    case "reference.conf" | "application.conf" => MergeStrategy.concat
    case _ => MergeStrategy.first
  }
)

lazy val root =
  Project("root", file("."))
    .aggregate(predict, `predict-test`)
    .settings(commonSettings: _*)

lazy val predict =
  (project in file("predict"))
    .settings(commonSettings: _*)

lazy val `predict-test` =
  (project in file("predict-test"))
    .settings(commonSettings: _*)
