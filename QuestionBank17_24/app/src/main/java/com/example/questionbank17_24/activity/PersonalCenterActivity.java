package com.example.questionbank17_24.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.adapter.PersonAdapter;
import com.example.questionbank17_24.bean.Root;
import com.example.questionbank17_24.bean.Vehicle;
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

public class PersonalCenterActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.avatar_iv)
    ImageView avatarIv;
    @BindView(R.id.user_tv)
    TextView userTv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.sex_tv)
    TextView sexTv;
    @BindView(R.id.tel_tv)
    TextView telTv;
    @BindView(R.id.sfz_tv)
    TextView sfzTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.person_list)
    ListView personList;
    private List<Vehicle> vehicles, myCar;
    private Root root;
    private PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        ButterKnife.bind(this);
        titleTv.setText("个人中心");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getVehicle();
    }

    private void getRoot() {
        new OkHttpTo().setUrl("get_root")
                .setJsonObject("UserName", "user1")
                .setJsonObject("Password", "1234")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        root = new Gson().fromJson(jsonObject.toString(), Root.class);

                        if (root.getSex().equals("男")) {
                            avatarIv.setImageResource(R.drawable.touxiang_2);
                        } else {
                            avatarIv.setImageResource(R.drawable.touxiang_1);
                        }

                        userTv.setText("用户名：" + root.getUsername());
                        nameTv.setText("姓名：" + root.getName());
                        sexTv.setText("性别：" + root.getSex());
                        telTv.setText("手机：" + root.getTel());
                        sfzTv.setText("身份证号：" + root.getIdnumber());
                        timeTv.setText("注册时间：" + root.getRegistered_time());

                        setCar();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void getVehicle() {
        if (vehicles == null) {
            vehicles = new ArrayList<>();
        }
        new OkHttpTo().setUrl("get_vehicle")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        vehicles = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(),
                                new TypeToken<List<Vehicle>>() {
                                }.getType());

                        getRoot();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void setCar() {
        myCar = new ArrayList<>();
        List<String> plate = root.getPlate();

        for (int i = 0; i < plate.size(); i++) {
            for (int j = 0; j < vehicles.size(); j++) {
                Vehicle vehicle = vehicles.get(j);
                if (vehicle.getPlate().equals(plate.get(i))) {
                    myCar.add(vehicle);
                }
            }
        }
        if (adapter == null) {
            adapter = new PersonAdapter(PersonalCenterActivity.this, myCar);
            personList.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

    }

}