package com.example.questionbank9_16.bean;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

public class Account extends LitePalSupport {
    public Account() {
    }

    /**
     *  "id": 1,
     * "number": 1,
     * "owner": "张三",
     * "balance": 11300,
     * "plate": "鲁A10001",
     * "brand": "奔驰",
     * "user": "user1"
     */



    private int number;
    private String owner;
    private int balance;
    private String plate;
    private String brand;
    private String user;
    private boolean xz;

    public boolean isXz() {
        return xz;
    }

    public void setXz(boolean xz) {
        this.xz = xz;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
