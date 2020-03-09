package com.weather.bigdata.it.app.check_spring.utils.scalaUtils

import java.util

import scala.collection.JavaConversions._

object unionUtil {
  def tokenSetUnion(token0: util.Set[String], token1: util.Set[String]): util.Set[String] = {
    token0.union(token1).filter(p=>(!p.equals("")))
  }
}
