package com.example.irsis.myclass;

import org.litepal.crud.LitePalSupport;

public class Problem extends LitePalSupport {

    private int Pid;
    private String Pname;
    private String Pcontent;
    private byte[] Pimage;

    public byte[] getIamge() {
        return Pimage;
    }

    public void setIamge(byte[] image) {
        this.Pimage = image;
    }



    public Problem() {
    }
    public Problem(int id, String name, String content) {
        this.Pid = id;
        this.Pname = name;
        this.Pcontent = content;
    }

    public int getpId() {
        return Pid;
    }

    public void setpId(int pId) {
        this.Pid = pId;
    }

    public String getName() {
        return Pname;
    }
    public void setName(String name) {
        this.Pname = name;
    }

    public String getContent() {
        return Pcontent;
    }
    public void setContent(String content) {
        this.Pcontent = content;
    }

}
