package com.weather.bigdata.it.app.check_spring_server.utils.judge

import java.io.{BufferedReader, InputStreamReader}
import java.net.URL

object ReadURL {
  def urlAliveGet(url: String): (JudgeType, String) = {
    try{
      val sb = new StringBuilder
      val metauri = new URL(url)
      val conn = metauri.openConnection

      val is = conn.getInputStream

      val isr = new InputStreamReader(is)
      val reader = new BufferedReader(isr)
      var c = reader.readLine
      while (null != c) {
        sb.append(c)
        c = reader.readLine
      }
      reader.close()
      Tuple2.apply(JudgeType.Normal, sb.toString())
    }catch {
      case e:Exception=>{
        Tuple2.apply(JudgeType.CallAbnormity, url+";"+e.getMessage)
      }
    }

  }
}
