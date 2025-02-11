package org.apg.config

import com.typesafe.config.Config

import javax.inject.{Inject, Singleton}

@Singleton
class AppConfig @Inject()(config: Config) {
  val appName = config.getString("app.name")
  val icebergCatalogWarehouse = config.getString("iceberg.catalog.warehouse")
  val icebergCatalogType = config.getString("iceberg.catalog.type")
  val icebergCatalogLfEnabled = config.getString("iceberg.catalog.lf-enabled")
  val icebergCatalogAwsRegion = config.getString("iceberg.catalog.aws-region")
  val icebergCatalogAwsAccountId = config.getString("iceberg.catalog.aws-account-id")
}
