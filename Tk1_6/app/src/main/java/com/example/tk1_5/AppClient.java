package com.example.tk1_5;

import android.app.Application;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tk1_5.bean.HJZB;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-15 at 10:19 ：）
 */
public class AppClient extends Application {
    public static SharedPreferences preferences;
    public static RequestQueue requestQueue;
    public static final String IP = "App_Ip";
    public static final String PORT = "App_Port";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;



    private List<HJZB> hjzbs = new ArrayList<>();

    public List<HJZB> getHjzbs() {
        return hjzbs;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        preferences = getSharedPreferences("P", 0);
        requestQueue = Volley.newRequestQueue(this);
    }


    public static void add(JsonObjectRequest jsonObjectRequest) {
        requestQueue.add(jsonObjectRequest);
    }
}
