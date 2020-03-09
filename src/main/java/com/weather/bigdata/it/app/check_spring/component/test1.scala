//package com.weather.bigdata.it.app.check_spring.component
//
//import com.weather.bigdata.it.app.check_spring.prop.CheckProp
//import com.weather.bigdata.it.app.check_spring.service.SendHelper
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.{ApplicationArguments, ApplicationRunner}
//import org.springframework.stereotype.Component
//import scala.collection.JavaConversions._
//
//@Component
//class test1 extends ApplicationRunner {
//
//  @Autowired private var checkProp:CheckProp=null
//
//  override def run(args: ApplicationArguments): Unit = {
//    val rediss=checkProp.getRedis
//    rediss.foreach(f=>{
//      println(f)
//    })
//  }
//}
