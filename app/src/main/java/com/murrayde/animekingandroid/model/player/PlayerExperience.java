package com.murrayde.animekingandroid.model.player;

import androidx.annotation.Keep;

@Keep
public class PlayerExperience {
    private long level;
    private int req_exp;
    private int total_exp;
    private String user_id;
    private String user_name;

    public PlayerExperience() {}

    public PlayerExperience(long level, int req_exp, int total_exp, String user_id, String user_name) {
        this.level = level;
        this.req_exp = req_exp;
        this.total_exp = total_exp;
        this.user_id = user_id;
        this.user_name = user_name;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public int getReq_exp() {
        return req_exp;
    }

    public void setReq_exp(int req_exp) {
        this.req_exp = req_exp;
    }

    public int getTotal_exp() {
        return total_exp;
    }

    public void setTotal_exp(int total_exp) {
        this.total_exp = total_exp;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }
}
