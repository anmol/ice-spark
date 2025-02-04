package org.apg

import com.google.inject.Guice
import org.apg.module.IceSparkModule

object Main {
  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector(new IceSparkModule)
    val executor = injector.getInstance(classOf[Executor])

    executor.execute()

  }
}