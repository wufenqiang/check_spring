package com.weather.bigdata.it.app.check_spring_server.service;

import com.weather.bigdata.it.app.check_spring_server.utils.judge.JudgeType;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import scala.Tuple2;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    SendHelper sendHelper;

    @org.junit.Test
    public void test0(){
        sendHelper.text("8a4173e806b4c5659e29ab323ae666229d47acb4ec38c9080acdfe6f32450e70", "test", "hello email", "",  4);
    }

    @Autowired
    Judge judge;

    @org.junit.Test
    public void test1(){
//        Tuple2<JudgeType, String> result=judge.urlAliveGet("http://localhost:8080/prompt-language/test/test");
//        System.out.println(result);
    }

}