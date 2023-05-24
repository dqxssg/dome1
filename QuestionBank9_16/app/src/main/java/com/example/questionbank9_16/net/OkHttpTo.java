package com.example.questionbank9_16.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkHttpTo extends Thread {
    private String Url = "http://10.173.77.36:8080/traffic/";
    private boolean isLoop;
    private int Time;
    private OkHttpLo okHttpLo;
    private JSONObject jsonObject = new JSONObject();
    private ProgressDialog dialog;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                okHttpLo.onFailure((IOException) msg.obj);
            } else if (msg.what == 2) {
                okHttpLo.onResponse((JSONObject) msg.obj);
            }
            return false;
        }
    });

    public OkHttpTo setDialog(Context context) {
        dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("提示：");
        dialog.setMessage("Loading...");
        dialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 500);
        return this;
    }

    public OkHttpTo setUrl(String url) {
        Url += url;
        return this;
    }

    public OkHttpTo setLoop(boolean loop) {
        isLoop = loop;
        return this;
    }

    public OkHttpTo setTime(int time) {
        Time = time;
        return this;
    }

    public OkHttpTo setOkHttpLo(OkHttpLo okHttpLo) {
        this.okHttpLo = okHttpLo;
        return this;
    }

    public OkHttpTo setJsonObject(String k,Object v) {
        try {
            jsonObject.put(k, v);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public void run() {
        super.run();
        do {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toString());
            Request request = new Request.Builder()
                    .url(Url)
                    .post(body).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = e;
                    handler.sendMessage(msg);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Message msg = Message.obtain();
                    msg.what = 2;
                    try {
                        msg.obj = new JSONObject(response.body().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    handler.sendMessage(msg);
                }
            });
            try {
                Thread.sleep(Time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (isLoop);
    }
}

