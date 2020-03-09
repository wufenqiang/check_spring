package com.weather.bigdata.it.app.check_spring.prop;

import lombok.SneakyThrows;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

@Component
@PropertySource(value = "classpath:contacts.properties",encoding = "utf-8")
public class ContactsProp {

    @SneakyThrows
    private Properties loadProperties() {
        this.prop0=new Properties();

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("contacts.properties");
        BufferedReader bf= new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        this.prop0.load(bf);
        return this.prop0;
    }

    private Properties prop0=null;
    private Properties getProp() {
        if(this.prop0==null){
            this.prop0=this.loadProperties();
        }
        return this.prop0;
    }

    public String getContact(String token){
        Properties prop=this.getProp();
        if(prop.containsKey(token)){
            return prop.getProperty(token);
        }else{
            return null;
        }
    }
}
