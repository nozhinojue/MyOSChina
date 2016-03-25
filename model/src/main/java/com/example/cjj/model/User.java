package com.example.cjj.model;

/**
 * Created by CJJ on 2016/3/22.
 */
public class User {
    private String username;
    private String pwd;
    private int keep_login=1;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
