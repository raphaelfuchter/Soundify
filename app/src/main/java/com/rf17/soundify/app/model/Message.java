package com.rf17.soundify.app.model;

public class Message {

    private String msg;
    private String data;

    public Message(String msg, String data) {
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public String getData() {
        return data;
    }
}
