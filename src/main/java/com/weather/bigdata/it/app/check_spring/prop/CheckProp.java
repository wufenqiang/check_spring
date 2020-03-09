package com.weather.bigdata.it.app.check_spring.prop;

import com.weather.bigdata.it.app.check_spring.utils.cache.cache;
import com.weather.bigdata.it.utils.felixfun.FormulaUtil.FormulaCalculation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import scala.Tuple8;
import scala.Tuple9;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
@PropertySource(value = "classpath:application.properties",encoding = "utf-8")
public class CheckProp {
    @Autowired
    private SettingProp settingProp;

    @Value("${spring.profiles.active}")
    private String profile;
    @SneakyThrows
    private Properties loadProperties(){
        this.prop0=new Properties();

        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(profile+"/check.properties");
        BufferedReader bf= new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        this.prop0.load(bf);


        return this.prop0;
    }

    private Properties prop0=null;
    private Properties getProp(){
        if(this.prop0==null){
            this.prop0=this.loadProperties();
        }
        return this.prop0;
    }

    public Properties UrlsProperties(){
        Properties prop_url=new Properties();

        Properties prop= this.getProp();

        Set<Object> keys=prop.keySet();
        for(Object key:keys){
            if(key.toString().startsWith("http")){
                prop_url.put(key,prop.get(key));
            }
        }

        return prop_url;
    }
    public Set<String> getUrls(){
        Properties prop=this.UrlsProperties();
        Set<Object> keys0=prop.keySet();
        Set<String> keys=new HashSet<>();
        for(Object key0:keys0){
            keys.add(key0.toString());
        }
        return keys;
    }
    public Tuple8<Boolean,Set<String>,Set<String>,SimpleDateFormat,Long,SimpleDateFormat,Long,String> anaUrlProp(String url) throws Exception {
        Properties prop=this.UrlsProperties();
        String urlProp=prop.getProperty(url);
        String[] urlProps=urlProp.split(";");
        try{
            Boolean force= Boolean.valueOf(urlProps[0]);
            Set<String> tokens=new HashSet<>(Arrays.asList(urlProps[1].split(",")));
            Set<String> tokens_force=new HashSet<String>(Arrays.asList(urlProps[2].split(",")));
            SimpleDateFormat data_timeSD=new SimpleDateFormat(urlProps[3]);
            Long data_dtime= FormulaCalculation.str2value(urlProps[4]);
            SimpleDateFormat update_timeSD=new SimpleDateFormat(urlProps[5]);
            Long update_dtime= FormulaCalculation.str2value(urlProps[6]);
            String title=urlProps[7];
            return Tuple8.apply(force,tokens,tokens_force,data_timeSD,data_dtime,update_timeSD,update_dtime,title);
        }catch (Exception e){
            Exception e0=new Exception(url+";配置文件写错"+urlProp+";"+e);
            throw e0;
        }
    }
    
    
    
    public Properties RedisProperties(){
        Properties prop_redis=new Properties();
        Properties prop=this.getProp();
        Set<Object> keys=prop.keySet();
        Set<String> redisKeys= cache.getRedisType(settingProp.redis_metaapiURL+"?param=alltype");

        for(Object key:keys){
            if(redisKeys.contains(key)){
                prop_redis.put(key,prop.get(key));
            }
        }

        return prop_redis;
    }
    public Set<String> getRedis(){
        Properties prop=this.RedisProperties();
        Set<Object> keys0=prop.keySet();
        Set<String> keys=new HashSet<>();
        for(Object key0:keys0){
            keys.add(key0.toString());
        }
        return keys;
    }
    public Tuple8<Boolean,Set<String>,Set<String>,SimpleDateFormat,Long,SimpleDateFormat,Long,String> anaRedisProp(String ncType){
        Properties prop=this.RedisProperties();
        String redisProp=prop.getProperty(ncType);
        String[] redisProps=redisProp.split(";");
        Boolean force= Boolean.valueOf(redisProps[0]);
        Set<String> tokens=new HashSet<String>(Arrays.asList(redisProps[1].split(",")));
        Set<String> tokens_force=new HashSet<String>(Arrays.asList(redisProps[2].split(",")));
        SimpleDateFormat data_timeSD=new SimpleDateFormat(redisProps[3]);
        Long data_dtime= FormulaCalculation.str2value(redisProps[4]);
        SimpleDateFormat update_timeSD=new SimpleDateFormat(redisProps[5]);
        Long update_dtime= FormulaCalculation.str2value(redisProps[6]);
        String title=redisProps[7];
        return Tuple8.apply(force,tokens,tokens_force,data_timeSD,data_dtime,update_timeSD,update_dtime,title);
    }
//    public Long getdt_redis(String ncType){
//        Tuple8<Boolean,Set<String>,Set<String>,SimpleDateFormat,Long,SimpleDateFormat,Long,String> p=this.anaRedisProp(ncType);
//        return p._5();
//    }
//    public Long getdupt_redis(String ncType){
//        Tuple8<Boolean,Set<String>,Set<String>,SimpleDateFormat,Long,SimpleDateFormat,Long,String> p=this.anaRedisProp(ncType);
//        return p._7();
//    }
    
    
    public Properties FilesProperties(){
        Properties prop_files=new Properties();
        Properties prop_urls=this.UrlsProperties();
        Properties prop_redis=this.RedisProperties();

        Properties prop=this.getProp();
        Set<Object> keys=prop.keySet();
        for(Object key:keys){
            if(!prop_urls.containsKey(key) && !prop_redis.containsKey(key)){
                String key1=this.toymd(key.toString());
                prop_files.put(key1,prop.get(key));
            }
        }

        return prop_files;
    }
    public Set<String> getFiles(){
        Properties prop=this.FilesProperties();
        Set<Object> keys0=prop.keySet();
        Set<String> keys=new HashSet<>();
        for(Object key0:keys0){
            keys.add(key0.toString());
        }
        return keys;
    }
    public Tuple9<Boolean,Set<String>,Set<String>,SimpleDateFormat,Long,Integer,Long,String,String> anaFilesProp(String filePath){
        Properties prop=this.FilesProperties();
        String fileProp=prop.getProperty(filePath);
        String[] fileProps=fileProp.split(";");
        Boolean force= Boolean.valueOf(fileProps[0]);
        Set<String> tokens=new HashSet<String>(Arrays.asList(fileProps[1].split(",")));
        Set<String> tokens_force=new HashSet<String>(Arrays.asList(fileProps[2].split(",")));
        SimpleDateFormat data_timeSD=new SimpleDateFormat(fileProps[3]);
        Long data_dtime= FormulaCalculation.str2value(fileProps[4]);
        Integer file_Num= Integer.valueOf(fileProps[5]);
        Long update_dtime= FormulaCalculation.str2value(fileProps[6]);
        String containKey=fileProps[7];
        String title=fileProps[8];
        return Tuple9.apply(force,tokens,tokens_force,data_timeSD,data_dtime,file_Num,update_dtime,containKey,title);
    }

    private String toymd(String fileName){
        if(fileName.contains("$")){
            String[] fs=fileName.split("\\$");
            String ymdfs=fs[1];
            String ymdfsStr= new SimpleDateFormat(ymdfs).format(new Date());
            return fileName.replace("$"+ymdfs+"$",ymdfsStr);
        }else{
            return fileName;
        }
    }
}
