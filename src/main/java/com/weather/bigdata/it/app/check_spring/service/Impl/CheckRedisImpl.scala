package com.weather.bigdata.it.app.check_spring.service.Impl

import java.util

import com.alibaba.fastjson.{JSON, JSONObject}
import com.weather.bigdata.it.app.check_spring.prop.{CheckProp, SettingProp}
import com.weather.bigdata.it.app.check_spring.service.{CheckRedis, CollectionMsg, Judge}
import com.weather.bigdata.it.app.check_spring.utils.status.ObjStatus
import com.weather.bigdata.it.spark.platform.split.SplitJson
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import redis.clients.jedis.Jedis

import scala.collection.JavaConversions._
import scala.util.Random

@Service
class CheckRedisImpl extends CheckRedis{
  private val log:Logger=LoggerFactory.getLogger(this.getClass)

  @Autowired private var settingProp:SettingProp = _
  @Autowired private var checkProp:CheckProp =_
  @Autowired private var judge:Judge=_
  @Autowired private var collectionMsg:CollectionMsg=_

  private val resultKey = "result"
  private val connKey = "CONN"
  private val hostNameKey = "host_name"
  private val portKey = "port"
  private val passwordKey = "password"
  private val rfKey = "_RF"
  private val latStartKey = "LAT_START"
  private val lonStartKey = "LON_START"
  private val latlonStepKey = "RESOLUTION_RATIO"

  override def anaRedis(): Unit = {
    this.anaRedis(settingProp.redis_metaapiURL)
  }

  override def anaRedis(metaapiURL: String): Unit = {
    val open_check_redis=settingProp.open_check_redis
    if(open_check_redis){
      val (judgeType,msg)=judge.urlAliveGet(metaapiURL+"?param=alltype")
      if(judgeType.isNormal){
        val splitArr:Array[SplitJson] = SplitJson.ReadJsonArr(this.settingProp.redis_splitFile)


        val json:JSONObject=JSON.parseObject(msg).getJSONObject(this.resultKey)
        val ncTypes:util.Set[String]=json.keySet()
        ncTypes.foreach(ncType=>{
          if(this.checkProp.getRedis.contains(ncType)){

            val (force, tokens, tokens_force, data_timeSD, data_dtime, update_timeSD, update_dtime, title)=checkProp.anaRedisProp(ncType)

            val ncType_INFO = json.getJSONObject(ncType)
            val ncType_CONN = ncType_INFO.getJSONObject(this.connKey)


            val latlonStep = ncType_INFO.getDouble(this.latlonStepKey)
            val startLat = ncType_INFO.getDouble(this.latStartKey)
            val startLon = ncType_INFO.getDouble(this.lonStartKey)


            val dbKeys = ncType_CONN.keySet
            dbKeys.foreach(dbKey=>{
              val db_result = ncType_CONN.getJSONObject(dbKey)
              val host = db_result.getString(this.hostNameKey)
              val port = db_result.getInteger(this.portKey)
              val password = db_result.getString(this.passwordKey)

              val jedis = new Jedis(host, port, this.settingProp.redis_redisTimeOut)

              jedis.auth(password)

              val randomSplit=splitArr.apply(new Random().nextInt(splitArr.length))

              val (lat,lon)=randomSplit.randomLatLon()

              val redisKey:String=RedisKey.factory(startLat,startLon,latlonStep).latlon2key(ncType,lat,lon)

              val ncType_hgetall: util.Map[String, String]= jedis.hgetAll(redisKey)

              val ttl:Long = jedis.ttl(redisKey)

              val rftimeStr:String = ncType_hgetall.get(this.rfKey)



              println()

              jedis.close()
            })
          }
        })

        println()

      }else{
        val os=new ObjStatus(judgeType,false,"redis配置中间件异常",judgeType.getDesc,metaapiURL,new util.HashSet[String],new util.HashSet[String])
        val flag:Boolean=collectionMsg.add(metaapiURL,os)
      }
    }else{
      log.warn("open_check_redis={},url检测关闭",open_check_redis)
    }
  }

  class RedisKey(startLat:Double, startLon:Double, latStep:Double, lonStep:Double) {

    def anaKey(key:String): (String,Double,Double,Int,Int) ={
      val keys=key.split(RedisKey.splitStr)
      val ncType=keys(0).toLowerCase
      val latIndex=keys(1).toInt
      val lonIndex=keys(2).toInt
      val lat:Double=latIndex*latStep+startLat
      val lon:Double=lonIndex*lonStep+startLon
      (ncType,lat,lon,latIndex,lonIndex)
    }
    def key2latlon(key:String): (java.lang.Double,java.lang.Double) ={
      val keys=key.split(RedisKey.splitStr)
      val latIndex=keys(1).toInt
      val lonIndex=keys(2).toInt
      val lat:Double=latIndex*latStep+startLat
      val lon:Double=lonIndex*lonStep+startLon
      (lat,lon)
    }
    def latlon2key(ncType:String,lat:Double,lon:Double): String ={
      val latIndex = ((lat - startLat) / latStep).toInt
      val lonIndex = ((lon - startLon) / lonStep).toInt
      ncType.toLowerCase+RedisKey.splitStr+latIndex+RedisKey.splitStr+lonIndex
    }

    //  def key2ncType(key:String): String ={
    //    RedisKey.key2ncType(key)
    //  }
    //  def key2latlonIndex(key:String): (Int,Int) ={
    //    RedisKey.key2latlonIndex(key)
    //  }
  }
  object RedisKey{
    private val splitStr="_"
    def factory(startLat:Double,startLon:Double,latStep:Double,lonStep:Double): RedisKey ={
      new RedisKey(startLat,startLon,latStep,lonStep)
    }
    def factory(startLat:Double,startLon:Double,latlonStep:Double): RedisKey ={
      this.factory(startLat,startLon,latlonStep,latlonStep)
    }

    def key2ncType(key:String): String ={
      val keys=key.split(this.splitStr)
      val ncType=keys(0).toLowerCase
      ncType
    }
    def key2latlonIndex(key:String): (Int,Int) ={
      val keys=key.split(RedisKey.splitStr)
      val latIndex=keys(1).toInt
      val lonIndex=keys(2).toInt
      (latIndex,lonIndex)
    }
  }
}
