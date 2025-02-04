package org.apg.module

import org.apg.LoggerSupport
import org.apg.config.Environment
import org.apg.config.Environment.Environment
import org.apg.provider.SparkSessionProvider
import com.google.inject.AbstractModule
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession

import scala.util.Properties

class IceSparkModule extends AbstractModule with LoggerSupport {
  override def configure(): Unit = {
    val environment = Environment.withName(Properties.propOrElse("environment", "local"))
    logger.info(s"Running in $environment environment")

    bind(classOf[Environment]).toInstance(environment)
    bind(classOf[SparkSession]).toProvider(classOf[SparkSessionProvider]).asEagerSingleton()

    val config = {
      val iceSparkConfigFileName = "ice-spark"
      ConfigFactory.load(s"$iceSparkConfigFileName.$environment")
        .withFallback(ConfigFactory.load(iceSparkConfigFileName))
    }

    bind(classOf[Config]).toInstance(config)
  }

}
