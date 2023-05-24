package com.example.questionbank17_24.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @ClassName Record
 * @Author 史正龙
 * @date 2021.08.06 22:15
 */
public class Record extends LitePalSupport {
    private int id;
    private int balance;
    private int czhBalance;
    private String person;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCzhBalance() {
        return czhBalance;
    }

    public void setCzhBalance(int czhBalance) {
        this.czhBalance = czhBalance;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
