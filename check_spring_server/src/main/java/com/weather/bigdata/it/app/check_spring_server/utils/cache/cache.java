package com.weather.bigdata.it.app.check_spring_server.utils.cache;

import org.springframework.stereotype.Component;

@Component
public class cache {
    public static long startTime=System.currentTimeMillis();

//    /**
//     * 状态,force,title,msg,out_urls,tokens,tokens_force
//     */
//    public static ConcurrentHashMap<String, ObjStatus> collectStatus=new ConcurrentHashMap<>();
//
//
//    private static Set<String> redisType=null;
//    public static Set<String> getRedisType(String RedisServerUrl) {
//        if(redisType==null){
//            setRedisType(RedisServerUrl);
//        }
//        return redisType;
//    }
//    public static void setRedisType(String RedisServerUrl) {
//        Tuple2<JudgeType,String> JudgeResult= ReadURL.urlAliveGet(RedisServerUrl);
//        if(JudgeResult._1.isNormal()){
//            JSONObject configJSON = JSONObject.parseObject(JudgeResult._2);
//            //    println(configJSON)
//            JSONObject result=configJSON.getJSONObject("result");
//            cache.redisType = result.keySet();
//        }
//    }

}
