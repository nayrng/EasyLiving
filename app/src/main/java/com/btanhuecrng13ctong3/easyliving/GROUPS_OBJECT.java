package com.btanhuecrng13ctong3.easyliving;

public class GROUPS_OBJECT {
   /* private String groupName;
    private int groupPass;
    private*/
   //String[] USER_ID;
   String ADMIN;
   String groupName;
   String groupPass;

    public GROUPS_OBJECT() {

    }

    public GROUPS_OBJECT(String admin,/*String[] users,*/ String groupName, String groupPass) {
        this.ADMIN = admin;
        //this.USER_ID = users;
        this.groupName = groupName;
        this.groupPass = groupPass;
    }

}
