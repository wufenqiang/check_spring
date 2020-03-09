//package com.weather.bigdata.it.app.check_spring.utils
//
//class RedisKey(startLat:Double, startLon:Double, latStep:Double, lonStep:Double) {
//
//  def anaKey(key:String): (String,Double,Double,Int,Int) ={
//    val keys=key.split(RedisKey.splitStr)
//    val ncType=keys(0).toLowerCase
//    val latIndex=keys(1).toInt
//    val lonIndex=keys(2).toInt
//    val lat:Double=latIndex*latStep+startLat
//    val lon:Double=lonIndex*lonStep+startLon
//    (ncType,lat,lon,latIndex,lonIndex)
//  }
//  def key2latlon(key:String): (java.lang.Double,java.lang.Double) ={
//    val keys=key.split(RedisKey.splitStr)
//    val latIndex=keys(1).toInt
//    val lonIndex=keys(2).toInt
//    val lat:Double=latIndex*latStep+startLat
//    val lon:Double=lonIndex*lonStep+startLon
//    (lat,lon)
//  }
//  def latlon2key(ncType:String,lat:Double,lon:Double): String ={
//    val latIndex = ((lat - startLat) / latStep).toInt
//    val lonIndex = ((lon - startLon) / lonStep).toInt
//    ncType.toLowerCase+RedisKey.splitStr+latIndex+RedisKey.splitStr+lonIndex
//  }
//
//  //  def key2ncType(key:String): String ={
//  //    RedisKey.key2ncType(key)
//  //  }
//  //  def key2latlonIndex(key:String): (Int,Int) ={
//  //    RedisKey.key2latlonIndex(key)
//  //  }
//}
//object RedisKey{
//  private val splitStr="_"
//  def factory(startLat:Double,startLon:Double,latStep:Double,lonStep:Double): RedisKey ={
//    new RedisKey(startLat,startLon,latStep,lonStep)
//  }
//  def factory(startLat:Double,startLon:Double,latlonStep:Double): RedisKey ={
//    this.factory(startLat,startLon,latlonStep,latlonStep)
//  }
//
//  def key2ncType(key:String): String ={
//    val keys=key.split(this.splitStr)
//    val ncType=keys(0).toLowerCase
//    ncType
//  }
//  def key2latlonIndex(key:String): (Int,Int) ={
//    val keys=key.split(RedisKey.splitStr)
//    val latIndex=keys(1).toInt
//    val lonIndex=keys(2).toInt
//    (latIndex,lonIndex)
//  }
//}