package com.example.irsis.myclass;

public class Problem {
    private String name;
    private int imageId;
    private String content;

    public Problem() {
    }

    public Problem(String name, int imageId, String content) {
        this.name = name;
        this.imageId = imageId;
        this.content = content;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
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
}
