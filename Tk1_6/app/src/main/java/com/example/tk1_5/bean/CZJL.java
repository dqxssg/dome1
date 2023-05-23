package com.example.tk1_5.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-15 at 18:29 ：）
 */
public class CZJL  extends LitePalSupport {
    private int id;
    private String ch,je,user,time;

    public CZJL() {
    }

    public CZJL(String ch, String je, String user, String time) {
        this.ch = ch;
        this.je = je;
        this.user = user;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getJe() {
        return je;
    }

    public void setJe(String je) {
        this.je = je;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
