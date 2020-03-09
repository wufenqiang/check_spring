package com.weather.bigdata.it.app.check_spring_server.service.Impl

import java.nio.charset.Charset
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Date
import java.{lang, util}

import com.alibaba.fastjson.JSON
import com.weather.bigdata.it.app.check_spring_server.prop.{ContactsProp, SettingProp}
import com.weather.bigdata.it.app.check_spring_server.service.SendHelper
import com.weather.bigdata.it.app.check_spring_server.utils.cache.cache
import com.weather.bigdata.it.utils.felixfun.Http.HttpUtil
import com.weather.bigdata.it.utils.felixfun.systemUtil.{ipUtil, roleUtil}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.apache.commons.codec.binary.Hex

import scala.collection.JavaConversions._

@Service
class SendHelperImpl extends SendHelper{
  private val log:Logger=LoggerFactory.getLogger(this.getClass)
  @Autowired private var settingProp:SettingProp = _
  @Autowired private var contactsProp:ContactsProp = _
  private val mod = new SimpleDateFormat("MM-dd,HH:mm:ss")

  private val statusKey = "status"
  private val errormsgKey = "errormsg"

  private val signKey="sign"
  private val dtokenKey="dtoken"
  private val titileKey="name"
  private val messageKey="message"
  private val contactsKey="contacts"
  private val pushtypeKey="pushtype"
  private val messageurlKey="messageurl"
  private val key="tqsign"

  override def beat(heartbeatTitle: String): Boolean = {
    val user_dir = roleUtil.getRunRole
    val textMsg:String ={
      "监控开启时间:" + mod.format(cache.startTime) + "\n" + "监控所处ip:" + ipUtil.getIp + "\n" + "监控者:" + user_dir
    }

    this.text(settingProp.tokens_heartbeat, heartbeatTitle, textMsg,null,4)
  }

  override def text(access_tokens: util.Set[String], title: String, text: String, outurl: String,pushtype: Integer): Boolean = {
    val _MSOpen = settingProp.MonitorSystem_open
    if (_MSOpen){
      val sendflags=access_tokens.filter(p=>(!p.equals(""))).map(access_token=>{
        val sendflag:Boolean=this.text(access_token,title,text,outurl,pushtype)
        sendflag
      })
      sendflags.reduce((x,y)=>(x && y))
    } else {
      val access_tokensStr=access_tokens.reduce((x,y)=>(x+","+y))
      log.warn("_MSOpen={},信号发送关闭",_MSOpen)
      log.info("token={},title={},text={},outurl={},pushtype={}",access_tokensStr,title,text,outurl,pushtype)
      false
    }
  }
  override def text(token: String, title: String, text: String, outurl: String, pushtype: Integer): Boolean = {
    val hashMap:util.HashMap[String,Object] = new util.HashMap()

    val textMsg:String={
      if(settingProp.open_msg_detail){
        "发布时间:"+mod.format(new Date())+
          "\n"+text+
          "\n监控所在:"+ ipUtil.getIp()+
          "\n监控实例:"+ roleUtil.getRunRole()
      }else{
        text
      }
    }

    hashMap.put(this.signKey, this.getsign(this.key))
    hashMap.put(this.titileKey, title)
    hashMap.put(this.messageKey, text)


    hashMap.put(this.pushtypeKey, pushtype)

    if(outurl != null){
      if(!outurl.equals("")){
        hashMap.put(this.messageurlKey,outurl)
      }
    }

    if(token!=null){
      if(!token.equals("")){
        val contact=contactsProp.getContact(token)
        if(contact==null){
          hashMap.put(this.contactsKey, settingProp.MonitorSystem_defaultcontact)
        }else{
          hashMap.put(this.contactsKey, contact)
        }

        hashMap.put(this.dtokenKey,token)
      }else{
        hashMap.put(this.contactsKey, settingProp.MonitorSystem_defaultcontact)
      }
    }else{
      hashMap.put(this.contactsKey, settingProp.MonitorSystem_defaultcontact)
    }
    val MonitorSystem_url=settingProp.MonitorSystem_url
    val result = HttpUtil.postParams(MonitorSystem_url, hashMap)
    if(result==null){
      log.warn("无法连接"+MonitorSystem_url)
      false
    }else{
      if(result.equals("")){
        log.warn("无法连接"+MonitorSystem_url)
        false
      }else{
        val resultJson = JSON.parseObject(result)
        log.info("MonitoringSystem发送信息:{};MonitoringSystem返回值:{}",Array(hashMap.toString, result))
        val status = resultJson.getIntValue(statusKey)
        if (status == 0){
          true
        }else {
          val errormsg = resultJson.getString(errormsgKey)
          log.warn(errormsg)
          false
        }
      }
    }
  }
  override def text(token: String, title: String, text: String, pushtype: Integer): Boolean = {
    this.text(token,title,text,null,pushtype)
  }

  private def getMD5(str:String):String={
    val md:MessageDigest = MessageDigest.getInstance("MD5")
    val digestResult = md.digest(str.getBytes(Charset.forName("UTF-8")))
    Hex.encodeHexString(digestResult).toLowerCase()
  }
  private def getsign(key:String):String={
    val df = new SimpleDateFormat("yyyyMMdd")
    val nowdate = df.format(new Date())
    getMD5(key + nowdate)
  }


}
