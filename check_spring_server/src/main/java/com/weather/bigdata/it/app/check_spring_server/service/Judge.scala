package com.weather.bigdata.it.app.check_spring_server.service

import java.util.Date

import com.weather.bigdata.it.app.check_spring_server.utils.judge.JudgeType

trait Judge {
  def checkTime(data_time: Date, dt: Long, update_time: Date, dupt: Long): (JudgeType,String)
  def urlAliveGet(url: String): (JudgeType, String)
}
