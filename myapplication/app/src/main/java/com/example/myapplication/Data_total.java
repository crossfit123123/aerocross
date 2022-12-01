package com.example.myapplication;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Data_total {
    private int Eatcalorie;
    private int Eatcalbo;
    private int Eatfat;
    private int Eatprotein;


    public Data_total(){}

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

    public Data_total(int Eatcalorie, int Eatcalbo, int Eatfat, int Eatprotein) {
        this.Eatcalorie = Eatcalorie;
        this.Eatcalbo = Eatcalbo;
        this.Eatfat = Eatfat;
        this.Eatprotein = Eatprotein;
    }

    private String Sex;
    private int Age;
    private int Height;
    private int Weight;
    private float Activitylevel;

    public String getSex(){
        return Sex;
    }
    public void setSex(String Sex){
        this.Sex = Sex;
    }

    public int getAge(){
        return Age;
    }
    public void setAge(int Age){
        this.Age = Age;
    }

    public int getHeight(){
        return Height;
    }
    public void setHeight(int Height){
        this.Height = Height;
    }

    public int getWeight(){
        return Weight;
    }
    public void setWeight(int Weight){
        this.Weight = Weight;
    }

    public float getActivitylevel(){
        return Activitylevel;
    }
    public void setActivitylevel(float Activitylevel){
        this.Activitylevel = Activitylevel;
    }


    private int totalcost;
    private int limitcost;
    private int warningcost;
    public int getTotalcost() { return totalcost;}
    public void setTotalcost(int totalcost) {this.totalcost=totalcost;}
    public int getLimitcost() { return  limitcost;}
    public void setLimitcost(int limitcost) {this.limitcost=limitcost;}
    public int getWarningcost() { return  warningcost;}
    public void setWarningcost(int warningcost) {this.warningcost=warningcost;}




}