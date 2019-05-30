package com.btanhuecrng13ctong3.easyliving;

import java.util.ArrayList;

public class PAYMENT_OBJ{
    String sender;
    String product;
    String groupname;
    double price;
    ArrayList<String> receivers;
    ArrayList<String> chargecompleted;

    public PAYMENT_OBJ(){

    }

    public PAYMENT_OBJ(String usr, String prod, String grp, double prc, ArrayList<String> rec, ArrayList<String> chargeComp){
        this.sender = usr;
        this.product = prod;
        this.groupname = grp;
        this.price = prc;
        this.receivers = rec;
        this.chargecompleted = chargeComp;
    }
}
