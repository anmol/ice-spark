package org.apg.config

import com.typesafe.config.Config

import javax.inject.{Inject, Singleton}

@Singleton
class AppConfig @Inject()(config: Config) {
  val appName = config.getString("app.name")
}
