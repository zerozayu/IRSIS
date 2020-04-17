package com.example.irsis.myclass;

import org.litepal.crud.LitePalSupport;

public class Problem extends LitePalSupport {

    private int pId;
    private int imageId;
    private String name;
    private String content;
    private byte[] iamge;

    public byte[] getIamge() {
        return iamge;
    }

    public void setIamge(byte[] iamge) {
        this.iamge = iamge;
    }



    public Problem() {
    }
    public Problem(int pId, int imageId, String name, String content) {
        this.pId = pId;
        this.imageId = imageId;
        this.name = name;
        this.content = content;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public int getImageId() {
        return imageId;
    }
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
