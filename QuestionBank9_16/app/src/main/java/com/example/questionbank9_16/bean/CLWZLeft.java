package com.example.questionbank9_16.bean;

import java.util.List;

public class CLWZLeft {
    private String cp;
    private int Cs, Kf, Fk;
    private List<CLWZRight> clwzRights;


    public CLWZLeft(String cp, int cs, int kf, int fk, List<CLWZRight> clwzRights) {
        this.cp = cp;
        Cs = cs;
        Kf = kf;
        Fk = fk;
        this.clwzRights = clwzRights;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public int getCs() {
        return Cs;
    }

    public void setCs(int cs) {
        Cs = cs;
    }

    public int getKf() {
        return Kf;
    }

    public void setKf(int kf) {
        Kf = kf;
    }

    public int getFk() {
        return Fk;
    }

    public void setFk(int fk) {
        Fk = fk;
    }

    public List<CLWZRight> getClwzRights() {
        return clwzRights;
    }

    public void setClwzRights(List<CLWZRight> clwzRights) {
        this.clwzRights = clwzRights;
    }
}
