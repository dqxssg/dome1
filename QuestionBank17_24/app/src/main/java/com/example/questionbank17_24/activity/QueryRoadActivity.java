package com.example.questionbank17_24.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.bean.Road;
import com.example.questionbank17_24.net.OkHttpLo;
import com.example.questionbank17_24.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QueryRoadActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.pm_road)
    TextView pmRoad;
    @BindView(R.id.wd_road)
    TextView wdRoad;
    @BindView(R.id.sd_road)
    TextView sdRoad;
    @BindView(R.id.road1)
    TextView road1;
    @BindView(R.id.road2)
    TextView road2;
    @BindView(R.id.road3)
    TextView road3;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    private List<Road> roads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_road);
        ButterKnife.bind(this);
        titleTv.setText("路况查询");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSense();
        getRoad();
    }

    private void getSense() {
        new OkHttpTo().setUrl("get_all_sense")
                .setTime(3000)
                .setLoop(true)
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        pmRoad.setText(jsonObject.optString("pm25"));
                        wdRoad.setText(jsonObject.optString("temperature"));
                        sdRoad.setText(jsonObject.optString("humidity"));
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void getRoad() {
        if (roads == null) {
            roads = new ArrayList<>();
        }
        new OkHttpTo().setUrl("get_road_status")
                .setJsonObject("UserName", "user1")
                .setLoop(true)
                .setTime(3000)
                .setJsonObject("RoadId", "0")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray("ROWS_DETAIL");
                        roads = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<Road>>() {
                        }.getType());

                        setRoad();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void setRoad() {
        for (int i = 0; i < roads.size(); i++) {
            Road road = roads.get(i);
            switch (road.getRoadId()) {
                case 1:
                    setColor(road.getState(), 1, road1, tv1);
                    break;
                case 2:
                    setColor(road.getState(), 2, road2, tv2);
                    break;
                case 3:
                    setColor(road.getState(), 3, road3, tv3);
                    break;
            }
        }
    }

    private void setColor(int i, int y, TextView textView1, TextView textView2) {
        switch (i) {
            case 1:
                textView1.setText(y + "号道路：" + "通畅");
                textView2.setBackgroundColor(Color.parseColor("#0ebd12"));
                break;
            case 2:
                textView1.setText(y + "号道路：" + "较通畅");
                textView2.setBackgroundColor(Color.parseColor("#98ed1f"));
                break;
            case 3:
                textView1.setText(y + "号道路：" + "拥挤");
                textView2.setBackgroundColor(Color.parseColor("#ffff01"));
                break;
            case 4:
                textView1.setText(y + "号道路：" + "堵塞");
                textView2.setBackgroundColor(Color.parseColor("#ff0103"));
                break;
            case 5:
                textView1.setText(y + "号道路：" + "爆表");
                textView2.setBackgroundColor(Color.parseColor("#4c060e"));
                break;
        }
    }

}