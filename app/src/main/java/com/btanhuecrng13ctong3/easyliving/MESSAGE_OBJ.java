package com.btanhuecrng13ctong3.easyliving;

public class MESSAGE_OBJ {

    public String POST_TITLE;
    public String POST_BODY;
    public String GROUP_NAME;
    public String POST_AUTHOR;

    public MESSAGE_OBJ() {}

    public MESSAGE_OBJ(String title, String body, String group, String author) {
        this.POST_TITLE = title;
        this.POST_BODY = body;
        this.GROUP_NAME = group;
        this.POST_AUTHOR = author;
    }

}
