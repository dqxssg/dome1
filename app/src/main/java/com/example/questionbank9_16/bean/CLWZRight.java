package com.example.questionbank9_16.bean;

/**
 * @ClassName CLWZRight
 * @Author 史正龙
 * @date 2021.08.03 12:08
 */
public class CLWZRight {
    private String time;
    private String road;
    private String message;
    private int score, money;


    public CLWZRight(String time, String road, String message, int score, int money) {
        this.time = time;
        this.road = road;
        this.message = message;
        this.score = score;
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
