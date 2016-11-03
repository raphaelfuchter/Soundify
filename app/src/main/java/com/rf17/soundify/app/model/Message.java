package com.rf17.soundify.app.model;

public class Message {

    public static final int MYMSG_TYPE = 0;
    public static final int RECMSG_TYPE = 1;

    private String msg;
    private int type;

    public Message(String msg, int type) {
        this.msg = msg;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

}
