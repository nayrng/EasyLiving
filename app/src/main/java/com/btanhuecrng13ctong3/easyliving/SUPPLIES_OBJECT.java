package com.btanhuecrng13ctong3.easyliving;

import android.media.Image;
import android.widget.ImageView;

import java.util.ArrayList;

class SUPPLIES_OBJECT {
    private String SUPPLY_NAME, SUPPLY_BUYER, SUPPLY_ID;
    private int SUPPLY_NUM, SUPPLY_PRICE, SUPPLY_IMAGE;

    public SUPPLIES_OBJECT(){

    }

    public SUPPLIES_OBJECT(String supply_name, String supply_buyer,
                           int supply_num, int supply_price){
        this.SUPPLY_NAME = supply_name;
        this.SUPPLY_BUYER = supply_buyer;
        this.SUPPLY_NUM = supply_num;
        this.SUPPLY_PRICE = supply_price;
    }

    public SUPPLIES_OBJECT(String supply_name, String supply_buyer){
        this.SUPPLY_NAME = supply_name;
        this.SUPPLY_BUYER = supply_buyer;
    }

    public String getSUPPLY_ID(){
        return SUPPLY_ID;
    }

    public int getSUPPLY_NUM() {
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
    }

    public int getSUPPLY_IMAGE() {
        return SUPPLY_IMAGE;
    }
}
