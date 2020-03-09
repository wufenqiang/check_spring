package com.weather.bigdata.it.app.check_spring.utils.judge;

import lombok.extern.slf4j.Slf4j;
import scala.Tuple2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
public class UrlJudge {
    public static void alive(String url){
        StringBuilder sb  = new StringBuilder();
        try{
            URL metauri=new URL(url);
            URLConnection conn = metauri.openConnection();

            InputStream is= conn.getInputStream();

            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String c = reader.readLine();
            while (null != c){
                sb.append(c);
                c=reader.readLine();
            }
            reader.close();
            Tuple2<Boolean,StringBuilder> result=Tuple2.apply(true,sb);
        }catch(Exception e){
            log.warn(e.getMessage());
            Tuple2<Boolean,StringBuilder> result=Tuple2.apply(false,sb);
        }

    }
}
