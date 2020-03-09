package com.weather.bigdata.it.app.check_spring.service.Impl

import java.util

import com.weather.bigdata.it.app.check_spring.prop.SettingProp
import com.weather.bigdata.it.app.check_spring.service.{CollectionMsg, SendHelper}
import com.weather.bigdata.it.app.check_spring.utils.cache.cache
import com.weather.bigdata.it.app.check_spring.utils.status.ObjStatus
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConversions._

@Service
class CollectionMsgImpl extends CollectionMsg{

  private val log=LoggerFactory.getLogger(this.getClass)

  @Autowired private var sendHelper:SendHelper=_
  @Autowired private var settingProp:SettingProp=_


  override def add(watchObject: String, os: ObjStatus): java.lang.Boolean = {
    val flag:Boolean={
      if(cache.collectStatus.containsKey(watchObject)){
        val before: ObjStatus =cache.collectStatus.get(watchObject)
        if(before.getJt.equals(os.getJt)){
          false
        }else{
          if(!settingProp.open_remind){
            val flag=this.sendcollection(os)
          }
          true
        }
      }else{
        false
      }
    }

    val force:Boolean={
      if(settingProp.open_forcePush){
        settingProp.open_forcePush
      }else{
        os.getForce
      }
    }
    val tokens=os.getTokens.union(settingProp.tokens_send)
    val tokens_force=os.getTokens_force.union(settingProp.tokens_force)

    os.setForce(force)
      .setTokens(tokens)
      .setTokens_force(tokens_force)

    cache.collectStatus.put(watchObject,os)
    flag
  }

  override def remainStatus(): String = {
    if(settingProp.open_remind){
      if(cache.collectStatus.isEmpty){
        "collection is Empty 本次不发送信息"
      }else{
        val watchObjects=cache.collectStatus.keys()
        val size0=cache.collectStatus.size()
        while(watchObjects.hasMoreElements){
          val watchobject:String=watchObjects.nextElement()
          val msgs=cache.collectStatus.get(watchobject)

          val flag=this.sendcollection(msgs)
        }

        "发送信息:"+size0+"条"
      }
    }else{
      "open_remind="+settingProp.open_remind+"关闭"
    }
  }

  private def sendcollection(os:ObjStatus): Boolean ={

    val judgeType=os.getJt

    val force:Boolean=os.getForce

    val title=os.getTitle
    val text=os.getMsg
    val outurl=os.getOuturls
    val tokens=os.getTokens
    val tokens_force=os.getTokens_force
    if(judgeType.isNormal){
      val thetokens:util.Set[String]={
        if(force){
          tokens_force.union(tokens)
        }else{
          tokens_force
        }
      }
      sendHelper.text(thetokens,title,text,outurl,4)
    }else{
      sendHelper.text(tokens,title,text,outurl,2)
    }
  }
}
