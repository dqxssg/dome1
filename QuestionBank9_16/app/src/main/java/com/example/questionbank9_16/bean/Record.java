package com.example.questionbank9_16.bean;

import org.litepal.crud.LitePalSupport;

public class Record extends LitePalSupport {
    private String plate;
    private String czqMoney;
    private int czhMoney;
    private String owner;
    private String time;

    public Record() {
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getCzqMoney() {
        return czqMoney;
    }

    public void setCzqMoney(String czqMoney) {
        this.czqMoney = czqMoney;
    }

    public int getCzhMoney() {
        return czhMoney;
    }

    public void setCzhMoney(int czhMoney) {
        this.czhMoney = czhMoney;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
