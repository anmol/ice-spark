package org.apg

import com.google.inject.{Inject, Singleton}
import com.typesafe.config.Config
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

@Singleton
class Executor @Inject()(spark: SparkSession){

  def execute() = {
    println("Hello world!")
    val df = spark.table("cdc_apg.public_user")
    df.show(false)
  }

}
