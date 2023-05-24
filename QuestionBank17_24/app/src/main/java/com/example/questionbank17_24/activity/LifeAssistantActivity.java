package com.example.questionbank17_24.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.bean.Weather;
import com.example.questionbank17_24.net.OkHttpLo;
import com.example.questionbank17_24.net.OkHttpTo;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LifeAssistantActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.wd_tv)
    TextView wdTv;
    @BindView(R.id.interval_tv)
    TextView intervalTv;
    @BindView(R.id.flash_iv)
    ImageView flashIv;
    @BindView(R.id.date1)
    TextView date1;
    @BindView(R.id.date2)
    TextView date2;
    @BindView(R.id.date3)
    TextView date3;
    @BindView(R.id.life_chart)
    LineChart lifeChart;
    private String[] week = {"", "周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private List<Weather> weathers;
    private List<Entry> maxEntries, minEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_assistant);
        ButterKnife.bind(this);
        titleTv.setText("生活助手");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setDate();
        getWeather();

    }


    private void setDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) + 2);
        date1.setText(week[calendar.get(Calendar.DAY_OF_WEEK)]);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) + 1);
        date2.setText(week[calendar.get(Calendar.DAY_OF_WEEK)]);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) + 1);
        date3.setText(week[calendar.get(Calendar.DAY_OF_WEEK)]);
    }

    private void getWeather() {
        new OkHttpTo().setUrl("get_weather_info")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        wdTv.setText(jsonObject.optString("temperature"));
                        JSONArray jsonArray = jsonObject.optJSONArray("ROWS_DETAIL");
                        intervalTv.setText(jsonArray.optJSONObject(0).optString("interval"));
                        weathers = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<Weather>>() {
                        }.getType());

                        setData();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void setData() {
        if (maxEntries == null || minEntries == null) {
            maxEntries = new ArrayList<>();
            minEntries = new ArrayList<>();
        }

        for (int i = 0; i < weathers.size(); i++) {
            String[] interval = weathers.get(i).getInterval().split("~");
            maxEntries.add(new Entry(i, Integer.parseInt(interval[1])));
            minEntries.add(new Entry(i, Integer.parseInt(interval[0])));
        }

        LineDataSet dataSet1 = new LineDataSet(maxEntries, "");
        LineDataSet dataSet2 = new LineDataSet(minEntries, "");
        dataSet1.setDrawCircleHole(false);
        dataSet1.setCircleColor(Color.parseColor("#ae615e"));
        dataSet1.setColor(Color.parseColor("#ae615e"));
        dataSet2.setDrawCircleHole(false);
        dataSet2.setColor(Color.parseColor("#4572a7"));
        dataSet2.setCircleColor(Color.parseColor("#4572a7"));
        List<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(dataSet1);
        iLineDataSets.add(dataSet2);
        LineData data = new LineData(iLineDataSets);
        lifeChart.setData(data);
        lifeChart.getXAxis().setEnabled(false);
        lifeChart.getAxisRight().setEnabled(false);
        YAxis left = lifeChart.getAxisLeft();
        left.setDrawAxisLine(false);
        lifeChart.getDescription().setEnabled(false);
        lifeChart.getLegend().setEnabled(false);
        lifeChart.setTouchEnabled(false);
        lifeChart.invalidate();
    }

    @OnClick(R.id.flash_iv)
    public void onClick() {
        getWeather();
    }
}