package com.weather.bigdata.it.app.check_spring_server.utils.judge;

public enum JudgeType {
    Normal("检验正常"),

    DataAbnormity("数据异常"),
    DataLatency("数据延迟"),
    UpdateDelay("更新延迟"),

    CallAbnormity("访问异常"),

    ProgramException("未知异常"),
    NoData("没有数据");

    private String desc;

    private JudgeType(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return getDesc();
    }

    public String getDesc() {
        return this.desc;
    }

    public Boolean isNormal() {
        if (this.desc.equals(Normal.toString())) {
            return true;
        } else {
            return false;
        }
    }
}
