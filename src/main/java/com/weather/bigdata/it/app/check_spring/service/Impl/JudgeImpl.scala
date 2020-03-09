package com.weather.bigdata.it.app.check_spring.service.Impl

import java.lang
import java.text.SimpleDateFormat
import java.util.Date

import com.weather.bigdata.it.app.check_spring.prop.SettingProp
import com.weather.bigdata.it.app.check_spring.service.Judge
import com.weather.bigdata.it.app.check_spring.utils.judge.{JudgeType, ReadURL}
import com.weather.bigdata.it.utils.felixfun.FormulaUtil.TimeFormula
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JudgeImpl extends Judge{
  private val log:Logger=LoggerFactory.getLogger(this.getClass)
  private val mod = new SimpleDateFormat("MM-dd,HH:mm:ss")

  @Autowired private var settingProp: SettingProp = _

  override def checkTime(data_time: Date, dt: lang.Long, update_time: Date, dupt: lang.Long): (JudgeType,String)= {
    val now = new Date()
    var msg:String=""


    val d:Long = ((now.getTime - data_time.getTime)/1000.0d).toLong
    val ud:Long = {
      if(update_time!=null){
        msg = {
          "数据时间:" + mod.format(data_time) + "\n" +
            "更新时间:" + mod.format(update_time)
        }
        ((now.getTime - update_time.getTime)/1000.0d).toLong
      }else{
        msg = {
          "数据时间:" + mod.format(data_time) + "\n" +
            "更新时间:没有设置"
        }
        0
      }
    }



    val flag0 = d < dt
    val flag1 = ud < dupt
    val judge:JudgeType={
      (flag0, flag1) match {
        case (true,true) => JudgeType.Normal
        case (true,false) => JudgeType.UpdateDelay
        case (false,true) => JudgeType.DataLatency
        case (false,false) => JudgeType.DataAbnormity
        case _=>JudgeType.ProgramException
      }
    }

    this.log.debug("data_time="+data_time+";dt="+dt+";update_time="+update_time+";dupt="+dupt+"==>"+judge+","+msg)

    msg={
      "\n"+
        "检验结果:"+judge.getDesc+"\n"+
      {
        if(settingProp.open_msg_detail){
          "检验时间:"+ mod.format(now)+"\n"+
            msg+"\n"+
            "数据阈值:"+TimeFormula.sec2HMSChinese(dt) + "\n" +
            "更新阈值:" + TimeFormula.sec2HMSChinese(dupt)
        }else{
          msg
        }
      }

    }

    (judge,msg)
  }

  override def urlAliveGet(url: String): (JudgeType, String) = {
//    try{
//      val sb = new StringBuilder
//      val metauri = new URL(url)
//      val conn = metauri.openConnection
//
//      val is = conn.getInputStream
//
//      val isr = new InputStreamReader(is)
//      val reader = new BufferedReader(isr)
//      var c = reader.readLine
//      while (null != c) {
//        sb.append(c)
//        c = reader.readLine
//      }
//      reader.close()
//      Tuple2.apply(JudgeType.Normal, sb.toString())
//    }catch {
//      case e:Exception=>{
//        Tuple2.apply(JudgeType.CallAbnormity, url+";"+e.getMessage)
//      }
//    }
    ReadURL.urlAliveGet(url)
  }
}
