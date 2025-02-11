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
      .set("spark.sql.catalog.my_catalog", "org.apache.iceberg.spark.SparkCatalog")
      .set("spark.sql.catalog.my_catalog.warehouse", appConfig.icebergCatalogWarehouse)
      .set("spark.sql.catalog.my_catalog.type", appConfig.icebergCatalogType)
      .set("spark.sql.extensions", "org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions")
      .set("spark.sql.catalog.my_catalog.lf.managed", appConfig.icebergCatalogLfEnabled)
      .set("spark.sql.catalog.my_catalog.client.assume-role.region", appConfig.icebergCatalogAwsRegion)
      .set("spark.sql.catalog.my_catalog.glue.account-id", appConfig.icebergCatalogAwsAccountId)
      .set("spark.sql.catalog.my_catalog.glue.id", appConfig.icebergCatalogAwsAccountId)
      .set("spark.sql.defaultCatalog", "my_catalog")

    SparkSession.builder
      .master(if(environment.equals(Environment.LOCAL)) "local[*]" else "yarn")
      .config(sparkConf)
      .getOrCreate()
  }
}
