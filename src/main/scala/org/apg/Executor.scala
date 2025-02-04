package org.apg

import com.google.inject.{Inject, Singleton}
import com.typesafe.config.Config
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

@Singleton
class Executor @Inject()(){

  def execute() = {
    println("Hello world!")
  }

}
