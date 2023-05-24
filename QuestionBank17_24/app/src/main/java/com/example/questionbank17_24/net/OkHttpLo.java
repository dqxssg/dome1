package com.example.questionbank17_24.net;

import org.json.JSONObject;

import java.io.IOException;

public interface OkHttpLo {
    void onResponse(JSONObject jsonObject);
    void onFailure(IOException e);
}
