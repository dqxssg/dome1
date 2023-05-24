package com.example.questionbank17_24.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @ClassName Sense
 * @Author 史正龙
 * @date 2021.08.05 15:23
 */
public class Sense extends LitePalSupport {
    private int temperature;
    private int humidity;
    private int illumination;
    private int co2;
    private int pm25;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getIllumination() {
        return illumination;
    }

    public void setIllumination(int illumination) {
        this.illumination = illumination;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }
}
