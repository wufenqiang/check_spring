package com.weather.bigdata.it.app.check_spring_server.service

import java.util

trait SendHelper {
  def beat(heartbeatTitle: String): Boolean
  def text(access_tokens: util.Set[String], title: String, text: String, outurl: String, pushtype: Integer): Boolean
  def text(token: String, title: String, text: String, outurl: String, pushtype: Integer): Boolean
  def text(token: String, title: String, text: String, pushtype: Integer): Boolean
}
