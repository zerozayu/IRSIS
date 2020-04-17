package com.example.irsis;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
