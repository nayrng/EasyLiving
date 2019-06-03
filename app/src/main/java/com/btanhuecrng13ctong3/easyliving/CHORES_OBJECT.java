package com.btanhuecrng13ctong3.easyliving;

import java.io.Serializable;

public class CHORES_OBJECT implements Serializable {
    public String USER_ID;
    public String CHORE_NAME;
    public Boolean CHORE_DONE;
    public String GROUP_ID;
    public String ASS_DATE;

    public CHORES_OBJECT() {

    }

    public CHORES_OBJECT(String user, String name, Boolean status, String group, String assdate) {
        this.USER_ID = user;
        this.CHORE_NAME = name;
        this.CHORE_DONE = status;
        this.GROUP_ID = group;
        this.ASS_DATE = assdate;
    }

}
