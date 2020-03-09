//package com.weather.bigdata.it.app.check_spring.utils.prop;
//
//
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.IOException;
//import java.util.Properties;
//import java.util.Set;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class CheckPropTest {
//
//    @Autowired
//    CheckProp checkProp;
//
//    @org.junit.Test
//    public void getUrls() throws IOException {
//        Properties prop=checkProp.getUrls();
//        Set<Object> strs=prop.keySet();
//        for(Object str:strs){
//            System.out.println(str);
//        }
//    }
//
//    @org.junit.Test
//    public void getRedis() throws IOException {
//        Properties prop=checkProp.getRedis();
//        Set<Object> strs=prop.keySet();
//        for(Object str:strs){
//            System.out.println(str);
//        }
//    }
//
//    @org.junit.Test
//    public void getFiles() throws IOException {
//        Properties prop=checkProp.getFiles();
//        Set<Object> strs=prop.keySet();
//        for(Object str:strs){
//            System.out.println(str);
//        }
//    }
//}