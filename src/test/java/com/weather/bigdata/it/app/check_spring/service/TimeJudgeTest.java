package com.weather.bigdata.it.app.check_spring.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TimeJudgeTest {


    @Autowired
    Judge judge;

    @org.junit.Test
    public void test(){
        judge.checkTime(new Date(),1L,new Date(),1L);
    }
}