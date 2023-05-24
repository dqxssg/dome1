package com.example.questionbank17_24.bean;

import org.litepal.crud.LitePalSupport;

/**
 * @ClassName Type
 * @Author 史正龙
 * @date 2021.08.05 17:51
 */
public class Type extends LitePalSupport {
    private String type;
    private int yz;
    private int nowValue;

    public Type(String type, int yz, int nowValue) {
        this.type = type;
        this.yz = yz;
        this.nowValue = nowValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYz() {
        return yz;
    }

    public void setYz(int yz) {
        this.yz = yz;
    }

    public int getNowValue() {
        return nowValue;
    }

    public void setNowValue(int nowValue) {
        this.nowValue = nowValue;
    }
}
