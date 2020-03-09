package com.weather.bigdata.it.app.check_spring.scheduling;

import com.weather.bigdata.it.app.check_spring.prop.SettingProp;
import com.weather.bigdata.it.app.check_spring.service.CheckRedis;
import com.weather.bigdata.it.app.check_spring.service.CheckURL;
import com.weather.bigdata.it.app.check_spring.service.CollectionMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class TaskCrontab {

    @Autowired
    SettingProp settingProp;

    @Autowired
    CheckURL checkURL;

    @Autowired
    CheckRedis checkRedis;

    @Autowired
    CollectionMsg collectionMsg;

    @Scheduled(cron = "#{settingProp.redis_crontab}") // 每*执行一次
    public void redis(){
        this.log.trace("start");
        checkRedis.anaRedis();
        this.log.trace("end");
    }

//    @Scheduled(cron = "#{settingProp.urls_crontab}") // 每*执行一次
//    public void urls(){
//        this.log.trace("start");
//        checkURL.anaURL();
//        this.log.trace("end");
//    }

    //
//    @Scheduled(cron = "#{settingProp.files_crontab}") // 每*执行一次
//    public void files(){
//        this.log.trace("start");
//
//        this.log.trace("end");
//    }
//
    @Scheduled(cron = "#{settingProp.send_crontab}") // 每*执行一次
    public void sends(){
        this.log.trace("start");
        String msg=collectionMsg.remainStatus();
        System.out.println(msg);
        this.log.trace("end");
    }

//    @Scheduled(cron = "* * * * * ?") // 每*执行一次
//    public void test(){
//        this.log.trace("start");
//        System.out.println("test");
//        this.log.trace("end");
//    }
}
