package com.btanhuecrng13ctong3.easyliving;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.PropertyName;

import java.util.ArrayList;

public class GROUPS_OBJECT {
   /* private String groupName;
    private int groupPass;
    private*/
   //String[] USER_ID;

   private String ADMIN;
   private ArrayList<String> USERS;
   private ArrayList<String> CHORES;
   private String GROUPNAME;
   private String GROUPPASS;

    public GROUPS_OBJECT() {

    }


//    public GROUPS_OBJECT(String admin,/*String[] users,*/ String groupName, String groupPass) {
//        this.ADMIN = admin;
//        //this.USER_ID = users;
//        this.groupName = groupName;
//        this.groupPass = groupPass;
//    }
    public GROUPS_OBJECT(String insert_admin, ArrayList<String> insert_user, ArrayList<String> insert_chore, String name, String pass) {
        this.ADMIN = insert_admin;
        //this.USER_ID = users;
        this.USERS = insert_user;
        this.CHORES = insert_chore;
        this.GROUPNAME = name;
        this.GROUPPASS = pass;
    }



    public String getADMIN() {
        return ADMIN;
    }

    public ArrayList<String> getUSERS() {
        return this.USERS;
    }

    public ArrayList<String> getCHORES() {
        return this.CHORES;
    }

    public String getGROUPNAME() {
        return this.GROUPNAME;
    }

    public String getGROUPPASS() {
        return this.GROUPPASS;
    }
    /*GROUPS_OBJECT updateUsers(ArrayList<String> newUsers){
        /*this.ADMIN = this.getAdmin();
        this.users = newUsers;
        this.chores = this.getChores();
        this.groupName = this.getGroupName();
        this.groupPass = this.getPass();
        GROUPS_OBJECT obj = new GROUPS_OBJECT(this.ADMIN, newUsers, this.chores, this.groupName, this.groupPass);
        return obj;
    }*/

}
