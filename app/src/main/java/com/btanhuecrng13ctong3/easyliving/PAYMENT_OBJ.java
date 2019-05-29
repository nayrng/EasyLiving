package com.btanhuecrng13ctong3.easyliving;

import java.util.ArrayList;

public class PAYMENT_OBJ{
    String sender;
    String product;
    String groupname;
    double price;
    ArrayList<String> receivers;

    public PAYMENT_OBJ(){

    }

    public PAYMENT_OBJ(String usr, String prod, String grp, double prc, ArrayList<String> rec){
        this.sender = usr;
        this.product = prod;
        this.groupname = grp;
        this.price = prc;
        this.receivers = rec;
    }
}
