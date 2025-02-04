import sbt._

object Dependencies {

  private lazy val typesafeVersion = "1.3.2"
  private val configDependencies: Seq[ModuleID] = Seq(
    "com.typesafe" % "config" % typesafeVersion
  )
  // Application dependencies
  private lazy val guiceVersion = "5.1.0"
  private val guiceDependency = Seq(
    "com.google.inject" % "guice" % guiceVersion
  )

  // Logging dependencies
  private val loggingDependencies: Seq[ModuleID] = Seq(
    // logstash 4.9 uses same Jackson version (2.6) with Spark 2.4 Be careful when upgrading because it breaks compatibility
    // For conditional Logback configs
    "org.codehaus.janino" % "janino" % "2.7.8",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "net.logstash.logback" % "logstash-logback-encoder" % "4.9",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
  )

  // Overriding the snappy transitive dependency to support M1 chips
  private lazy val snappyVersion = "1.1.8.4"
  private val snappyDependency = Seq(
    "org.xerial.snappy" % "snappy-java" % snappyVersion
  ).map(_ % "runtime")

  // Spark related dependencies, marked as provided as EMR runtime environment include them
  // https://docs.aws.amazon.com/emr/latest/ReleaseGuide/emr-hadoop.html
  private lazy val hadoopVersion = "3.3.6"
  private lazy val sparkVersion = "3.5.1"
  private val sparkDependencies = Seq(
    // Spark related libraries are included in EMR container
    // https://docs.aws.amazon.com/emr/latest/ReleaseGuide/Hadoop-release-history.html
    // to check on the version compatibility with EMR version
    "org.apache.spark" %% "spark-sql" % sparkVersion,
    "org.apache.spark" %% "spark-core" % sparkVersion
  ).map(_ % "provided" excludeAll ExclusionRule("org.apache.hadoop")) ++ snappyDependency

  private val hadoopDependencies = Seq(
    "org.apache.hadoop" % "hadoop-client" % hadoopVersion,
    "org.apache.hadoop" % "hadoop-aws" % hadoopVersion,
  ).map(_ % "provided")

  private val awsDependencies = Seq(
    "org.apache.iceberg" % "iceberg-aws-bundle" % "1.6.0"
  ).map(_ % "provided")

  private val icebergDependencies = Seq(
    "org.apache.iceberg" % "iceberg-spark-runtime-3.5_2.12" % "1.6.0"
  ).map(_ % "provided")

  val commonDependencies: Seq[ModuleID] = configDependencies ++ loggingDependencies ++ guiceDependency ++ sparkDependencies ++ hadoopDependencies ++ awsDependencies ++ icebergDependencies
}
