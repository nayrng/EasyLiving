package com.btanhuecrng13ctong3.easyliving;

public class CHORES_OBJECT {
    String USER_ID;
    String CHORE_NAME;
    Boolean CHORE_DONE;

    public CHORES_OBJECT() {

    }

    public CHORES_OBJECT(String user, String name, Boolean status) {
        this.USER_ID = user;
        this.CHORE_NAME = name;
        this.CHORE_DONE = status;
    }

}
