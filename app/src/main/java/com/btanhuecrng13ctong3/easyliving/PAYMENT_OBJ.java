package com.btanhuecrng13ctong3.easyliving;

import java.util.ArrayList;

public class PAYMENT_OBJ{
    String sender;
    String product;
    String groupname;
    double price;
    String receiver;

    public PAYMENT_OBJ(){

    }

    public PAYMENT_OBJ(String usr, String prod, String grp, double prc, String rec){
        this.sender = usr;
        this.product = prod;
        this.groupname = grp;
        this.price = prc;
        this.receiver = rec;
    }
}
