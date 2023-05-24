package com.example.questionbank9_16.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.adapter.BusExpander;
import com.example.questionbank9_16.bean.Bus;
import com.example.questionbank9_16.dialog.BusDialog;
import com.example.questionbank9_16.net.OkHttpLo;
import com.example.questionbank9_16.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QueryBusActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.detail_btn)
    Button detailBtn;
    @BindView(R.id.bus_expand)
    ExpandableListView busExpand;
    @BindView(R.id.total_tv)
    TextView totalTv;
    public List<Bus> buses;
    private Map<String, List<Bus>> map;
    BusExpander adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_bus);
        ButterKnife.bind(this);
        titleTv.setText("公交查询");
        getDistance();
    }

    private void getDistance() {
        if (buses == null) {
            buses = new ArrayList<>();
            map = new HashMap<>();
        } else {
            buses.clear();
            map.clear();
        }
        new OkHttpTo().setUrl("get_bus_stop_distance")
                .setTime(3000)
                .setLoop(true)
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        buses = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<Bus>>() {
                        }.getType());

                        getAll();

                        List<Bus> buses1 = new Gson().fromJson(jsonObject.optJSONArray("中医院站").toString(), new TypeToken<List<Bus>>() {
                        }.getType());
                        Collections.sort(buses1, new Comparator<Bus>() {
                            @Override
                            public int compare(Bus o1, Bus o2) {
                                return o1.getDistance() - o2.getDistance();
                            }
                        });
                        map.put("中医院站", buses1);

                        List<Bus> buses2 = new Gson().fromJson(jsonObject.optJSONArray("中医院站").toString(), new TypeToken<List<Bus>>() {
                        }.getType());

                        Collections.sort(buses2, new Comparator<Bus>() {
                            @Override
                            public int compare(Bus o1, Bus o2) {
                                return o1.getDistance() - o2.getDistance();
                            }
                        });
                        map.put("联想大厦站", buses2);
                        if (adapter == null) {
                            adapter = new BusExpander(map);
                            busExpand.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                }).start();
    }

    public int total = 0;

    private void getAll() {
        total = 0;
        for (int i = 0; i < buses.size(); i++) {
            total += buses.get(i).getPerson();
        }
        totalTv.setText("当前承载能力：" + total + "人");
    }

    @OnClick({R.id.toolbar, R.id.detail_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.detail_btn:
                BusDialog dialog = new BusDialog(this);
                dialog.show(getSupportFragmentManager(), "");
                break;
        }
    }
}