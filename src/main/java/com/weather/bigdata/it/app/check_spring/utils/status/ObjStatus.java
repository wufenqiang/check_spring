package com.weather.bigdata.it.app.check_spring.utils.status;

import com.weather.bigdata.it.app.check_spring.utils.judge.JudgeType;

import java.util.Set;

public class ObjStatus {
    /**
     * 状态,force,title,msg,out_urls,tokens,tokens_force
     */
    private JudgeType jt;
    private Boolean force;
    private String title;
    private String msg;
    private String outurls;
    private Set<String> tokens;
    private Set<String> tokens_force;

    public JudgeType getJt() {
        return jt;
    }

    public ObjStatus(JudgeType jt, Boolean force, String title, String msg, String outurls, Set<String> tokens, Set<String> tokens_force) {
        this.jt = jt;
        this.force = force;
        this.title = title;
        this.msg = msg;
        this.outurls = outurls;
        this.tokens = tokens;
        this.tokens_force = tokens_force;
    }

    public ObjStatus setJt(JudgeType jt) {
        this.jt = jt;
        return this;
    }

    public Boolean getForce() {
        return force;
    }

    public ObjStatus setForce(Boolean force) {
        this.force = force;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ObjStatus setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ObjStatus setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getOuturls() {
        return outurls;
    }

    public ObjStatus setOuturls(String outurls) {
        this.outurls = outurls;
        return this;
    }

    public Set<String> getTokens() {
        return tokens;
    }

    public ObjStatus setTokens(Set<String> tokens) {
        this.tokens = tokens;
        return this;
    }

    public Set<String> getTokens_force() {
        return tokens_force;
    }

    public ObjStatus setTokens_force(Set<String> tokens_force) {
        this.tokens_force = tokens_force;
        return this;
    }
}
