package com.example.questionbank9_16.activity;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.net.OkHttpLo;
import com.example.questionbank9_16.net.OkHttpTo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrafficQueryActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_tcc)
    TextView tvTcc;
    @BindView(R.id.tv_xyl)
    TextView tvXyl;
    @BindView(R.id.hcks_2)
    TextView hcks2;
    @BindView(R.id.tv_lx1)
    TextView tvLx1;
    @BindView(R.id.hcks_3)
    TextView hcks3;
    @BindView(R.id.hcks_1)
    TextView hcks1;
    @BindView(R.id.hcgs_1)
    TextView hcgs1;
    @BindView(R.id.hcgs_2)
    TextView hcgs2;
    @BindView(R.id.hcks_4)
    TextView hcks4;
    @BindView(R.id.tv_xfl)
    TextView tvXfl;
    @BindView(R.id.tv_yyl)
    TextView tvYyl;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.iv_flash)
    ImageView ivFlash;
    @BindView(R.id.tv_wd)
    TextView tvWd;
    @BindView(R.id.tv_sd)
    TextView tvSd;
    @BindView(R.id.tv_pm)
    TextView tvPm;
    @BindView(R.id.iv_gif_1)
    ImageView ivGif1;
    @BindView(R.id.iv_gif_2)
    ImageView ivGif2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_query);
        ButterKnife.bind(this);
        titleTv.setText("路况查询");
        showTime();
        getSense();
        showPolice();
        getStatus();
    }

    private void showTime() {
        Calendar calendar = Calendar.getInstance();
        tvDate.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) +
                "\r\n" + new SimpleDateFormat("EEEE", Locale.CHINA).format(new Date()));
    }

    private void showPolice() {
        AnimationDrawable animationDrawable1 = (AnimationDrawable) ivGif1.getDrawable();
        AnimationDrawable animationDrawable2 = (AnimationDrawable) ivGif2.getDrawable();
        animationDrawable1.start();
        animationDrawable2.start();
    }

    private void getStatus() {
        new OkHttpTo().setUrl("get_road_status")
                .setJsonObject("UserName", "user1")
                .setJsonObject("RoadId", 0)
                .setLoop(true)
                .setTime(3000)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray("ROWS_DETAIL");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.optJSONObject(i);
                            getLuKou(object.optString("Name"), object.optInt("state"));
                        }
                    }
                }).start();
    }

    private void getLuKou(String name, int state) {
        switch (name) {
            case "学院路":
                showColor(tvXyl, state);
                break;
            case "联想路":
                showColor(tvLx1, state);
                break;
            case "医院路":
                showColor(tvYyl, state);
                break;
            case "幸福路":
                showColor(tvXfl, state);
                break;
            case "环城快速路":
                showColor(hcks1, state);
                showColor(hcks2, state);
                showColor(hcks3, state);
                showColor(hcks4, state);
                break;
            case "环城高速":
                showColor(hcgs1, state);
                showColor(hcgs2, state);
                break;
            case "停车场":
                showColor(tvTcc, state);
                break;
        }
    }

    private void showColor(TextView textView, int state) {
        switch (state) {
            case 1:
                textView.setBackgroundColor(Color.parseColor("#6ab82e"));
                break;
            case 2:
                textView.setBackgroundColor(Color.parseColor("#ece93a"));
                break;
            case 3:
                textView.setBackgroundColor(Color.parseColor("#f49b25"));
                break;
            case 4:
                textView.setBackgroundColor(Color.parseColor("#e33532"));
                break;
            case 5:
                textView.setBackgroundColor(Color.parseColor("#b01e23"));
                break;
        }
    }

    private void getSense() {
        new OkHttpTo().setUrl("get_all_sense")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        tvWd.setText(jsonObject.optString("temperature") + "℃");
                        tvSd.setText(jsonObject.optString("humidity") + "%");
                        tvPm.setText(jsonObject.optString("pm25") + "ug/m³");
                    }
                }).start();
    }

    @OnClick({R.id.toolbar, R.id.iv_flash})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.iv_flash:
                getSense();
                break;
        }
    }
}