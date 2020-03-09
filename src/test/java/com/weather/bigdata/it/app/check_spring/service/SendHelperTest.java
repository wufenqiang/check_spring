package com.weather.bigdata.it.app.check_spring.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SendHelperTest {

    @Autowired
    SendHelper sendHelper;

    @org.junit.Test
    public void test(){
        sendHelper.text("8a4173e806b4c5659e29ab323ae666229d47acb4ec38c9080acdfe6f32450e70", "test", "hello email", "",  4);
    }
}