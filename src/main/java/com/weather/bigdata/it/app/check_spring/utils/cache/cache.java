package com.weather.bigdata.it.app.check_spring.utils.cache;

import com.alibaba.fastjson.JSONObject;

import com.weather.bigdata.it.app.check_spring.utils.judge.JudgeType;
import com.weather.bigdata.it.app.check_spring.utils.judge.ReadURL;
import com.weather.bigdata.it.app.check_spring.utils.status.ObjStatus;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class cache {
    public static long startTime=System.currentTimeMillis();

    /**
     * 状态,force,title,msg,out_urls,tokens,tokens_force
     */
    public static ConcurrentHashMap<String, ObjStatus> collectStatus=new ConcurrentHashMap<>();


    private static Set<String> redisType=null;
    public static Set<String> getRedisType(String RedisServerUrl) {
        if(redisType==null){
            setRedisType(RedisServerUrl);
        }
        return redisType;
    }
    public static void setRedisType(String RedisServerUrl) {
        Tuple2<JudgeType,String> JudgeResult= ReadURL.urlAliveGet(RedisServerUrl);
        if(JudgeResult._1.isNormal()){
            JSONObject configJSON = JSONObject.parseObject(JudgeResult._2);
            //    println(configJSON)
            JSONObject result=configJSON.getJSONObject("result");
            cache.redisType = result.keySet();
        }
    }

}
