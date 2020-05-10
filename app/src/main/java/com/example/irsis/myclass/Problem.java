package com.example.irsis.myclass;


public class Problem {

    private String Pname;
    private String Pcontent;
    private byte[] Pimage;


    public Problem() {
    }

    public Problem(String pname, String pcontent, byte[] pimage) {
        Pname = pname;
        Pcontent = pcontent;
        Pimage = pimage;
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

    public byte[] getIamge() {
        return Pimage;
    }
    public void setIamge(byte[] image) {
        this.Pimage = image;
    }


}
