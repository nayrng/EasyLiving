package com.btanhuecrng13ctong3.easyliving;

import android.media.Image;
import android.widget.ImageView;

import java.util.ArrayList;

class SUPPLIES_OBJECT {
    public String SUPPLY_NAME, SUPPLY_BUYER, SUPPLY_GROUP;
    public int SUPPLY_NUM;
    public double SUPPLY_PRICE;

    public SUPPLIES_OBJECT(){

    }
    /*public SUPPLIES_OBJECT(String supply_name, String supply_buyer,
                           int supply_num, int supply_price){
        this.SUPPLY_NAME = supply_name;
        this.SUPPLY_BUYER = supply_buyer;
        this.SUPPLY_NUM = supply_num;
        this.SUPPLY_PRICE = supply_price;
    }
    public SUPPLIES_OBJECT(String supply_name, String supply_buyer){
        this.SUPPLY_NAME = supply_name;
        this.SUPPLY_BUYER = supply_buyer;
    }*/


    public SUPPLIES_OBJECT(String sname, String sbuyer, String sgroup,
                           int snum, Double sprice){
        this.SUPPLY_NAME = sname;
        this.SUPPLY_BUYER = sbuyer;
        this.SUPPLY_GROUP = sgroup;
        this.SUPPLY_NUM = snum;
        this.SUPPLY_PRICE = sprice;
    }

    /*public SUPPLIES_OBJECT(String supply_name, String supply_buyer, String supply_group){
        this.SUPPLY_NAME = supply_name;
        this.SUPPLY_GROUP = supply_group;
        this.SUPPLY_BUYER = supply_buyer;
    }*/


    /*public int getSUPPLY_NUM() {
        return SUPPLY_NUM;
    }

    public int getSUPPLY_PRICE() {
        return SUPPLY_PRICE;
    }

    public String getSUPPLY_BUYER() {
        return SUPPLY_BUYER;
    }

    public String getSUPPLY_NAME() {
        return SUPPLY_NAME;
    }*/

}
