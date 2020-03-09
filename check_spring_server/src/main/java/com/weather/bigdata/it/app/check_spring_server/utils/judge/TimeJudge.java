package com.weather.bigdata.it.app.check_spring_server.utils.judge;

import com.weather.bigdata.it.app.check_spring_server.prop.SettingProp;
import com.weather.bigdata.it.utils.felixfun.FormulaUtil.TimeFormula;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class TimeJudge {

    private static SimpleDateFormat publicmod = new SimpleDateFormat("MM-dd,HH:mm:ss");

    @Autowired
    static SettingProp settingProp;

    public static void checkTime(Date rftime_time, Long dtime, Date update_time, Long dupt) {

        Long now =System.currentTimeMillis()/1000L;
        String msg0="";

        if(update_time==null){
            if(settingProp.open_msg_detail){
                msg0="数据时间:"+publicmod.format(rftime_time)+"\n" + "数据阈值:"+ TimeFormula.sec2HMSChinese(dtime)+"\n" + "更新时间:没有设置\n" +"更新阈值:"+TimeFormula.sec2HMSChinese(dupt);
            }else{
                msg0="数据时间:"+publicmod.format(rftime_time)+"\n" + "更新时间:没有设置";
            }
            log.debug(msg0);
            update_time=new Date();
        }else{
            if(settingProp.open_msg_detail){
                msg0="数据时间:"+publicmod.format(rftime_time)+"\n" + "数据阈值:"+TimeFormula.sec2HMSChinese(dtime)+"\n" + "更新时间:"+publicmod.format(update_time)+"\n" +"更新阈值:"+TimeFormula.sec2HMSChinese(dupt);
            }else{
                msg0="数据时间:"+publicmod.format(rftime_time)+"\n" + "更新时间:"+publicmod.format(update_time);
            }

            log.debug(msg0);

        }

        Long timeStamp=rftime_time.getTime()/1000L;
        Long uptimeStamp=update_time.getTime()/1000L;


        Long d=now-timeStamp;
        Long ud=now-uptimeStamp;

        log.debug("time="+timeStamp+";uptime="+uptimeStamp+";now="+now+";d="+TimeFormula.sec2HMSChinese(d)+";ud="+TimeFormula.sec2HMSChinese(ud));

        Boolean flag0=d<dtime;
        Boolean flag1=ud<dupt;

        JudgeType type=null;

        if(flag0 && flag1){
            type=JudgeType.Normal;
        }else if(flag0 && !flag1){
            type=JudgeType.UpdateDelay;
        }else if(!flag0 && flag1){
            type=JudgeType.DataLatency;
        }else if(!flag0 && !flag1){
            type=JudgeType.DataAbnormity;
        }else{
            type=JudgeType.ProgramException;
        }

        if(settingProp.open_msg_detail){
            msg0="数据延迟:" + TimeFormula.sec2HMSChinese(d)+ "\n"+"更新延迟:"+TimeFormula.sec2HMSChinese(ud)+"\n"+msg0;
        }




    }
}
