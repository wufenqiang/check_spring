package com.weather.bigdata.it.app.check_spring.service.Impl

import com.weather.bigdata.it.app.check_spring.prop.{CheckProp, SettingProp}
import com.weather.bigdata.it.app.check_spring.service.CheckFiles
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CheckFilesImpl extends CheckFiles{
  private val log:Logger=LoggerFactory.getLogger(this.getClass)
  @Autowired private var settingProp:SettingProp = _
  @Autowired private var checkProp:CheckProp =_

  override def anaFiles(): Unit = {

  }
}
