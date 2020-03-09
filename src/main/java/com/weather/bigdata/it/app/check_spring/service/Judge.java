package com.weather.bigdata.it.app.check_spring.service;

import com.weather.bigdata.it.app.check_spring.utils.judge.JudgeType;
import scala.Tuple2;

import java.util.Date;

public interface Judge {
    Tuple2<JudgeType,String> checkTime(Date data_time, java.lang.Long dt , Date update_time, java.lang.Long dupt );
    Tuple2<JudgeType,String> urlAliveGet(String url);
}
