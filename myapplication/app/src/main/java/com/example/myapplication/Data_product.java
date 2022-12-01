package com.example.myapplication;

import com.google.firebase.database.IgnoreExtraProperties;


public class Data_product {
    private int Productcalorie;
    private int Productcalbo;
    private int Productprotein;
    private int Productfat;
    public Data_product(){}

    public Integer getProductcalorie(){
        return Productcalorie;
    }
    public void setProductcalorie(int Productcalorie){this.Productcalorie=Productcalorie;}

    public Integer getProductcalbo(){
        return Productcalbo;
    }
    public void setProductcalbo(int Productcalbp){this.Productcalbo=Productcalbo;}

    public Integer getProductprotein(){
        return Productprotein;
    }
    public void setProductprotein(int Productprotein){this.Productprotein=Productprotein;}

    public Integer getProductfat(){
        return Productfat;
    }
    public void setProductfat(int Productfat){this.Productfat=Productfat;}


    public  Data_product(int Productcalorie, int Productcalbo, int Productprotein, int Productfat)
    {
        this.Productcalorie=Productcalorie;
        this.Productcalbo=Productcalbo;
        this.Productprotein=Productprotein;
        this.Productfat=Productfat;
    }




}
