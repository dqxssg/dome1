package com.example.questionbank9_16.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.adapter.LeftAdapter;
import com.example.questionbank9_16.adapter.RightAdapter;
import com.example.questionbank9_16.bean.CLWZLeft;
import com.example.questionbank9_16.bean.CLWZRight;
import com.example.questionbank9_16.net.OkHttpLo;
import com.example.questionbank9_16.net.OkHttpTo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.left_list)
    ListView leftList;
    @BindView(R.id.right_list)
    ListView rightList;
    LeftAdapter leftAdapter;
    RightAdapter rightAdapter;
    @BindView(R.id.add_iv)
    ImageView addIv;
    private List<CLWZLeft> clwzLefts;
    private List<CLWZRight> clwzRights;
    String nowCp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        titleTv.setText("查询结果");
        clwzLefts = new ArrayList<>();
        clwzRights = new ArrayList<>();
        getAllPeccancy();
    }

    private void getAllPeccancy() {
        new OkHttpTo().setUrl("get_all_car_peccancy")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        getPessancyInfo(jsonObject);
                    }
                }).start();
    }

    private void getPessancyInfo(JSONObject jsonObject1) {
        new OkHttpTo().setUrl("get_pessancy_infos")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        setData(jsonObject1, jsonObject);
                    }
                }).start();
    }

    private void setData(JSONObject jsonObject1, JSONObject jsonObject2) {
        JSONArray jsonArray1 = jsonObject1.optJSONArray("ROWS_DETAIL");
        /**
         *             "id": 1,
         *             "time": "2017-04-02 14:55:23",
         *             "infoid": "10101"
         */
        JSONArray jsonArray2 = jsonObject2.optJSONArray("ROWS_DETAIL");
        /**
         *             "infoid": "10101",
         *             "road": "学院路",
         *             "message": "在交叉路口不按导向标线行驶在相应车道。",
         *             "deduct": 1,//扣的分
         *             "fine": 0    //罚金
         */

        String[] ids = getIntent().getStringArrayExtra("id");
        int score = 0;
        int money = 0;
        List<CLWZRight> clwzRights1 = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            for (int j = 0; j < jsonArray1.length(); j++) {
                if (ids[i].equals(jsonArray1.optJSONObject(j).optString("id"))) {
                    String time = jsonArray1.optJSONObject(j).optString("time");
                    for (int k = 0; k < jsonArray2.length(); k++) {
                        if (jsonArray1.optJSONObject(j).optString("infoid")
                                .equals(jsonArray2.optJSONObject(k).optString("infoid"))) {
                            int deduct = jsonArray2.optJSONObject(k).optInt("deduct");
                            score += deduct;
                            int fine = jsonArray2.optJSONObject(k).optInt("fine");
                            money += fine;
                            clwzRights1.add(new CLWZRight(time, jsonArray2.optJSONObject(k).optString("road"),
                                    jsonArray2.optJSONObject(k).optString("message"), score, money));
                            break;
                        }
                    }
                    break;
                }
            }
        }
        for (int i = 0; i < clwzLefts.size(); i++) {
            if (getIntent().getStringExtra("plate").equals(clwzLefts.get(i).getCp())) {
                clwzLefts.remove(i);
            }
        }
        clwzLefts.add(0, new CLWZLeft(getIntent().getStringExtra("plate"), ids.length, score, money, clwzRights1));
        showListView();

    }

    private void showListView() {
        leftAdapter = new LeftAdapter(this, clwzLefts);
        leftList.setAdapter(leftAdapter);
        leftAdapter.setMyClick(new LeftAdapter.MyClick() {
            @Override
            public void onClick(int position) {
                clwzLefts.remove(position);
                leftAdapter.notifyDataSetChanged();
                if (clwzLefts.size() == 0) {
                    clwzRights.clear();
                    rightAdapter.notifyDataSetChanged();
                } else {
                    clwzRights.clear();
                    clwzRights.addAll(clwzLefts.get(0).getClwzRights());
                    rightAdapter.notifyDataSetChanged();
                }
            }
        });
        clwzRights.clear();
        clwzRights.addAll(clwzLefts.get(0).getClwzRights());
        rightAdapter = new RightAdapter(this, clwzRights);
        rightList.setAdapter(rightAdapter);
    }


    @OnClick({R.id.toolbar, R.id.add_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.add_iv:
                startActivity(new Intent(this, VehicleViolationActivity.class));
                break;
        }
    }
}