import Dependencies.commonDependencies
import sbtassembly.AssemblyPlugin.autoImport.assemblyOption

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.17"

// Usage of ThisBuild is needed - as per SBT
// ThisBuild, which means the “entire build”, so a setting applies to the entire build rather than a single project
// https://www.scala-sbt.org/1.0/docs/Scopes.html
ThisBuild / Test / fork := true
// In order to support spark in local mode by bumping the memory
ThisBuild / javaOptions ++= Seq("-Xms1024m", "-Xmx4g", "-XX:MaxPermSize=4g", "-XX:+CMSClassUnloadingEnabled", "-XX:+UseConcMarkSweepGC")
ThisBuild / scalacOptions ++= Seq("-target:jvm-1.8")

ThisBuild / useCoursier := false

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-parser-combinators" % VersionScheme.Always

// No parallel execution for spark context testing scenariosRef: https://github.com/holdenk/spark-testing-base
ThisBuild / Test / parallelExecution := false

// According to https://github.com/sbt/sbt-assembly, suggest to turn caching off. This should reduce memory usage
ThisBuild / assemblyCacheOutput := false

// to avoid conflict in EMR runtime - https://29022131.atlassian.net/browse/PHOENIX-9097
assembly / assemblyShadeRules := Seq(
  ShadeRule.rename("okhttp3.**" -> "shadedecidtplokhttp3.@1").inAll,
  ShadeRule.rename("okio.**" -> "shadedecidtplokio.@1").inAll,
  ShadeRule.rename("com.google.common.**" -> "shadedgooglecommon.@1").inAll
)

lazy val root = (project in file("."))
  .settings(
    name := "ice-spark",
    organization := "org.apg"
  )
  .settings(
    libraryDependencies ++= commonDependencies
  )
