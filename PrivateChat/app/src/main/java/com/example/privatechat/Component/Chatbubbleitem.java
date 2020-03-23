package com.example.privatechat.Component;

import android.graphics.Bitmap;

public class Chatbubbleitem {
    private String msg;
    private int type;
    private String path;
    private Bitmap icon;

    public Chatbubbleitem(String msg, int type) {
        this.msg = msg;
        this.type = type;
    }

    public Chatbubbleitem(Bitmap icon, String path, int type) {
        this.icon = icon;
        this.type = type;
        this.path = path;

    }

    public String getMsg() {
        return msg;
    }

    public int getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setType(int type) {
        this.type = type;
    }


}
