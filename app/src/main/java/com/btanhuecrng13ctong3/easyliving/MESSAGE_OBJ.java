package com.btanhuecrng13ctong3.easyliving;

public class MESSAGE_OBJ {

    private String POST_TITLE;
    private String POST_BODY;
    private String GROUP_NAME;
    private String POST_AUTHOR;
    private Boolean POST_ANON;

    public MESSAGE_OBJ() {}

    public MESSAGE_OBJ(String title, String body, String group, String author, Boolean anon) {
        this.POST_TITLE = title;
        this.POST_BODY = body;
        this.GROUP_NAME = group;
        this.POST_AUTHOR = author;
        this.POST_ANON = anon;
    }

    public String getPOST_TITLE() {
        return POST_TITLE;
    }

    public String getPOST_BODY() {
        return POST_BODY;
    }

    public String getGROUP_NAME() {
        return GROUP_NAME;
    }

    public String getPOST_AUTHOR() {
        return POST_AUTHOR;
    }

    public Boolean getPOST_ANON() {
        return POST_ANON;
    }

}
