package com.keer.vote.entity;

/**
 * @Author: 张经伦
 * @Date: 2023/11/8  13:13
 * @Description: 用户信息
 */
public class User {
    private String userName;
    private String pwd;
    private String zx;
    private String mg;
    private String cx;
    private String kj;
    private String tj;

    public User(String userName, String pwd) {
        this.userName = userName;
        this.pwd = pwd;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getZx() {
        return zx;
    }

    public void setZx(String zx) {
        this.zx = zx;
    }

    public String getMg() {
        return mg;
    }

    public void setMg(String mg) {
        this.mg = mg;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public String getKj() {
        return kj;
    }

    public void setKj(String kj) {
        this.kj = kj;
    }

    public String getTj() {
        return tj;
    }

    public void setTj(String tj) {
        this.tj = tj;
    }
}
