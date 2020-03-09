package com.weather.bigdata.it.app.check_spring.service;

import java.util.Set;

public interface SendHelper {
    Boolean beat(String heartbeatTitle);

    Boolean text(Set<String> access_tokens,String title, String text, String outurl, Integer pushtype);
    Boolean text(String token,String title,String text, String outurl, Integer pushtype);
    Boolean text(String token,String title,String text, Integer pushtype);
}
