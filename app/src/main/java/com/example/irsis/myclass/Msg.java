package com.example.irsis.myclass;

import org.litepal.crud.LitePalSupport;

public class Msg extends LitePalSupport {
    //表示这是一条收到的消息
    public static final int TYPE_RECEIVED = 0;

    //表示这是一条发送的消息
    public static final int TYPE_SENT = 1;

    //消息的内容
    private String content;

    //消息的类型
    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
