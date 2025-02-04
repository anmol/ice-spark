package org.apg.provider

import com.google.inject.Provider
import org.apache.spark.SparkConf
import org.apg.config.Environment
import org.apg.config.Environment.Environment

import javax.inject.Inject
import org.apg.config.AppConfig
import org.apache.spark.sql.SparkSession

class SparkSessionProvider @Inject()(environment: Environment, appConfig: AppConfig) extends Provider[SparkSession]{

  override def get(): SparkSession = {
    val sparkConf = new SparkConf()
      .setAppName(appConfig.appName)

    SparkSession.builder
      .master(if(environment.equals(Environment.LOCAL)) "local[*]" else "yarn")
      .config(sparkConf)
      .getOrCreate()
  }
}
