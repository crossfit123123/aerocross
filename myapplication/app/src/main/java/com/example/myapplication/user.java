package com.example.myapplication;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class user {
    private int Eatcalorie;
    private int Eatcalbo;
    private int Eatfat;
    private int Eatprotein;

    public user(){}

    public Integer getEatcalorie(){
        return Eatcalorie;
    }

    public void setEatcalorie(int Eatcalorie){
        this.Eatcalorie = Eatcalorie;
    }

    public Integer getEatcalbo(){
        return Eatcalbo;
    }

    public void setEatcalbo(int Eatcalbo){
        this.Eatcalbo = Eatcalbo;
    }

    public Integer getEatfat(){
        return Eatfat;
    }

    public void setEatfat(int Eatfat){
        this.Eatfat = Eatfat;
    }

    public Integer getEatprotein(){
        return Eatprotein;
    }

    public void setEatprotein(int Eatprotein){
        this.Eatprotein = Eatprotein;
    }

    public user(int Eatcalorie, int Eatcalbo, int Eatfat, int Eatprotein) {
        this.Eatcalorie = Eatcalorie;
        this.Eatcalbo = Eatcalbo;
        this.Eatfat = Eatfat;
        this.Eatprotein = Eatprotein;
    }
}