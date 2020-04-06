package com.example.irsis.myclass;

public class Msg {
    //表示这是一条收到的消息
    public static final int TYPE_RECEIVED=0;

    //表示这是一条发送的消息
    public static final int TYPE_SENT=1;

    //消息的内容
    private String content;

    //消息的类型
    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
