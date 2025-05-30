package org.apg.executors

import com.google.inject.{Inject, Singleton}
import org.apache.spark.sql.SparkSession

@Singleton
class IcebergExecutor @Inject()(spark: SparkSession){

  def execute() = {
    println("Hello world!")
    val df = spark.table("cdc_apg.public_user")
    df.show(false)
  }

}
