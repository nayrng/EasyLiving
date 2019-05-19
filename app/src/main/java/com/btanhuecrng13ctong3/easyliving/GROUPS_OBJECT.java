package com.btanhuecrng13ctong3.easyliving;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class GROUPS_OBJECT {
   /* private String groupName;
    private int groupPass;
    private*/
   //String[] USER_ID;
   String ADMIN;
   ArrayList<FirebaseUser> users;
   ArrayList<String> chores;
   String groupName;
   String groupPass;

    public GROUPS_OBJECT() {

    }

//    public GROUPS_OBJECT(String admin,/*String[] users,*/ String groupName, String groupPass) {
//        this.ADMIN = admin;
//        //this.USER_ID = users;
//        this.groupName = groupName;
//        this.groupPass = groupPass;
//    }
    public GROUPS_OBJECT(String admin, ArrayList<FirebaseUser> user, ArrayList<String> chore, String groupName, String groupPass) {
        this.ADMIN = admin;
        //this.USER_ID = users;
        this.users = user;
        this.chores = chore;
        this.groupName = groupName;
        this.groupPass = groupPass;
    }


}
