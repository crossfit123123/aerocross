package com.example.myapplication;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String descStr ;
    private int proudctcalorie1;
    private int proudctcalbo1;
    private int proudctprotein1;
    private int proudctfat1;

    private int buycount;

    public Integer getBuycount(){
        return buycount;
    }
    public void setBuycount(int buycount){
        this.buycount = buycount;
    }
    public void plusItemcount(){buycount++;}

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }
    public void setProudctcalorie(int proudctcalorie) {
        proudctcalorie1 = proudctcalorie ;
    }
    public void setProudctcalbo(int proudctcalbo) {
        proudctcalbo1= proudctcalbo ;
    }
    public void setProudctprotein(int proudctprotein) {
        proudctprotein1= proudctprotein ;
    }
    public void setProudctfat(int proudctfat) {
        proudctfat1 = proudctfat ;
    }



    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }

    public int getcalorie() {
        return this.proudctcalorie1 ;
    }
    public int getcalbo() {
        return this.proudctcalbo1 ;
    }
    public int getprotein() {
        return this.proudctprotein1 ;
    }
    public int getfat() {
        return this.proudctfat1 ;
    }


}