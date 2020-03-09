package com.weather.bigdata.it.app.check_spring_server.prop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@PropertySource(value = "classpath:${spring.profiles.active}/setting.properties",encoding = "utf-8")
public class SettingProp {

    @Value("${MonitorSystem.open}")
    public Boolean MonitorSystem_open;
    @Value("${MonitorSystem.url}")
    public String MonitorSystem_url;
    @Value("${MonitorSystem.defaultcontact}")
    public String MonitorSystem_defaultcontact;

    @Value("${open.forcePush}")
    public Boolean open_forcePush;
    @Value("${open.heart.beat.send}")
    public Boolean open_heart_beat_send;

    @Value("${open.check.files}")
    public Boolean open_check_files;
    @Value("${open.check.redis}")
    public Boolean open_check_redis;
    @Value("${open.check.url}")
    public Boolean open_check_url;

    @Value("${tokens.heartbeat}")
    public Set<String> tokens_heartbeat;
    @Value("${tokens.send}")
    public Set<String> tokens_send;
    @Value("${tokens.force}")
    public Set<String> tokens_force;

    @Value("${open.report}")
    public Boolean open_report;
    @Value("${open.msg.detail}")
    public Boolean open_msg_detail;

    @Value("${redis.metaapiURL}")
    public String redis_metaapiURL;

    @Value("${redis.redisTimeOut}")
    public Integer redis_redisTimeOut;

    @Value("${redis.crontab}")
    public String redis_crontab;

    @Value("${redis.splitFile}")
    public String redis_splitFile;

    @Value("${urls.crontab}")
    public String urls_crontab;

    @Value("${files.crontab}")
    public String files_crontab;

    @Value("${send.crontab}")
    public String send_crontab;

    @Value("${open.remind}")
    public Boolean open_remind;
}
