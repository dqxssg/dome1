package com.example.questionbank9_16.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.adapter.LifeAdapter;
import com.example.questionbank9_16.adapter.PagerAdapter;
import com.example.questionbank9_16.bean.Sense;
import com.example.questionbank9_16.fragment.CO2Fragment;
import com.example.questionbank9_16.fragment.WDFragment;
import com.example.questionbank9_16.fragment.KQZLFragment;
import com.example.questionbank9_16.fragment.XDSDFragment;
import com.example.questionbank9_16.net.OkHttpLo;
import com.example.questionbank9_16.net.OkHttpTo;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LifeAssistantActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.wd_tv)
    TextView wdTv;
    @BindView(R.id.today_wd)
    TextView todayWd;
    @BindView(R.id.flash_iv)
    ImageView flashIv;
    @BindView(R.id.life_chart)
    LineChart lifeChart;
    @BindView(R.id.life_grid)
    GridView lifeGrid;
    @BindView(R.id.life_pager)
    ViewPager lifePager;
    @BindView(R.id.life_line)
    LinearLayout lifeLine;
    private List<Entry> entries1;
    private List<Entry> entries2;
    private List<ILineDataSet> iLineDataSets;
    public List<Sense> senses;
    private LifeAdapter adapter;
    private List<Fragment> fragments;
    private String[] names = {"空气质量", "温度", "相对湿度", "二氧化碳"};

    private static final String TAG = "LifeAssistantActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_assistant);
        ButterKnife.bind(this);
        titleTv.setText("生活指数");
        fragments = new ArrayList<>();
        getWeather();
        getSense();
    }

    private void initPager() {
        fragments.add(new KQZLFragment(senses));
        fragments.add(new WDFragment(this));
        fragments.add(new XDSDFragment(this));
        fragments.add(new CO2Fragment(this));
        lifePager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments));
        lifePager.setCurrentItem(0);
        lifePager.setOffscreenPageLimit(fragments.size());
        lifePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < lifeLine.getChildCount(); i++) {
                    TextView textView = (TextView) lifeLine.getChildAt(i);
                    if (i == position) {
                        if (position == fragments.size() - 1) {
                            textView.setBackgroundResource(R.drawable.blue_bg);
                        } else {
                            textView.setBackgroundResource(R.drawable.et_shape);
                        }
                    } else {
                        textView.setBackgroundColor(Color.WHITE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < names.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(names[i]);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(20);
            textView.setGravity(Gravity.CENTER);
            textView.setWidth(120);
            lifeLine.addView(textView);
        }
    }

    private void getSense() {
        if (senses == null) {
            senses = new ArrayList<>();
        } else {
            senses.clear();
        }
        new OkHttpTo().setUrl("get_all_sense")
                .setJsonObject("UserName", "user1")
                .setLoop(true)
                .setTime(3000)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        senses.add(new Gson().fromJson(jsonObject.toString(), Sense.class));
                        if (senses.size() > 21) {
                            senses.remove(0);
                        }
                        if (adapter == null) {
                            adapter = new LifeAdapter(LifeAssistantActivity.this, senses);
                            lifeGrid.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                        if (fragments.size() == 0) {
                            initPager();
                        }
                    }
                }).start();
    }

    private void getWeather() {
        new OkHttpTo().setUrl("get_weather_info")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        wdTv.setText(jsonObject.optString("temperature") + "°");
                        todayWd.setText(jsonObject.optJSONArray("ROWS_DETAIL").optJSONObject(0).optString("interval"));
                        if (entries1 == null) {
                            entries1 = new ArrayList<>();
                            entries2 = new ArrayList<>();
                        } else {
                            entries1.clear();
                            entries2.clear();
                        }
                        JSONArray jsonArray = jsonObject.optJSONArray("ROWS_DETAIL");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String[] intervals = jsonArray.optJSONObject(i).optString("interval").split("~");
                            entries1.add(new Entry(i, Float.parseFloat(intervals[0])));
                            entries2.add(new Entry(i, Float.parseFloat(intervals[1])));
                        }
                        setData();
                    }
                }).start();
    }

    private void setData() {
        LineDataSet dataSet1 = new LineDataSet(entries1, "");
        dataSet1.setDrawValues(false);
        dataSet1.setCircleColor(Color.parseColor("#aa4643"));
        dataSet1.setColor(Color.parseColor("#4572a7"));

        dataSet1.setDrawCircleHole(false);

        LineDataSet dataSet2 = new LineDataSet(entries2, "");
        dataSet2.setDrawValues(false);
        dataSet2.setCircleColor(Color.parseColor("#4572a7"));
        dataSet2.setColor(Color.parseColor("#4572a7"));
        dataSet2.setDrawCircleHole(false);

        iLineDataSets = new ArrayList<>();
        iLineDataSets.add(dataSet1);
        iLineDataSets.add(dataSet2);
        LineData data = new LineData(iLineDataSets);
        setXY();
        lifeChart.setData(data);
        lifeChart.getDescription().setEnabled(false);
        lifeChart.getLegend().setEnabled(false);
        lifeChart.invalidate();
    }

    private void setXY() {
        XAxis xAxis = lifeChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.WHITE);


        YAxis left = lifeChart.getAxisLeft();
        lifeChart.getAxisRight().setEnabled(false);
        left.setTextColor(Color.WHITE);
        left.setAxisLineColor(Color.WHITE);
    }

    @OnClick(R.id.toolbar)
    public void onClick() {
        finish();
    }
}