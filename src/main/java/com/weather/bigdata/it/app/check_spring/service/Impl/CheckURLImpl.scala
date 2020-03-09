package com.weather.bigdata.it.app.check_spring.service.Impl

import java.util.{Date, Properties}

import com.alibaba.fastjson.{JSON, JSONObject}
import com.weather.bigdata.it.app.check_spring.prop.{CheckProp, SettingProp}
import com.weather.bigdata.it.app.check_spring.service.{CheckURL, CollectionMsg, Judge}
import com.weather.bigdata.it.app.check_spring.utils.status.ObjStatus
import com.weather.bigdata.it.utils.formatUtil.DateFormatUtil
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConversions._

@Service
class CheckURLImpl extends CheckURL{
  private val log:Logger=LoggerFactory.getLogger(this.getClass)
  @Autowired private var settingProp:SettingProp = _
  @Autowired private var checkProp:CheckProp =_
  @Autowired private var collectionMsg:CollectionMsg=_
  @Autowired private var judge:Judge=_

  private val resultKey = "result"
  private val statusKey = "status"
  private val errorMsgKey = "errorMsg"
  private val timeKey="time"
  private val uptimeKey="uptime"

  override def anaURL(): Unit = {
    val urls=checkProp.getUrls
    urls.foreach(url=>{
      this.anaURL(url)
    })
  }

  override def anaURL(url: String): Unit = {
    val open_check_url=settingProp.open_check_url
    if(open_check_url){

      val (judgeType,msg)=judge.urlAliveGet(url)
      val urlsProp:Properties=checkProp.UrlsProperties

      val (force, tokens, tokens_force, data_timeSD, data_dtime:java.lang.Long, update_timeSD, update_dtime:java.lang.Long, title)=checkProp.anaUrlProp(url)
      if(judgeType.isNormal){
        val json:JSONObject=JSON.parseObject(msg).getJSONObject(this.resultKey)

        val data_time:Date=DateFormatUtil.YYYYMMDDHHMM(json.getString(this.timeKey))
        val update_time:Date={
          if(json.containsKey(this.uptimeKey)){
            DateFormatUtil.YYYYMMDDHHMM(json.getString(this.uptimeKey))
          }else{
            null
          }
        }


        val (judgeType0,text:String)=judge.checkTime(data_time,data_dtime,update_time,update_dtime)

        val os=new ObjStatus(judgeType0,force,title,text,url,tokens,tokens_force)

        val flag:Boolean=collectionMsg.add(url,os)

      }else{
        val os=new ObjStatus(judgeType,force,title,judgeType.getDesc,url,tokens,tokens_force)
        val flag:Boolean=collectionMsg.add(url,os)
      }
    }else{
      log.warn("open_check_url={},url检测关闭",open_check_url)
    }
  }
}
