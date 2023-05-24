package com.example.questionbank9_16.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.adapter.PersonAdapter;
import com.example.questionbank9_16.bean.Account;
import com.example.questionbank9_16.bean.Car;
import com.example.questionbank9_16.bean.Root;
import com.example.questionbank9_16.net.OkHttpLo;
import com.example.questionbank9_16.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PersonFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.sex_tv)
    TextView sexTv;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.sfz_tv)
    TextView sfzTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.person_grid)
    GridView personGrid;
    @BindView(R.id.touxiang_iv)
    ImageView touxiangIv;
    private Root root;
    private List<Car> cars;
    private List<String> plates;
    PersonAdapter adapter;
    private List<Car> cars1;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.person_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getVehicle();
    }

    private void getVehicle() {
        new OkHttpTo().setUrl("get_vehicle")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {
                    }
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        cars = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<Car>>() {
                        }.getType());
                        getRoot();
                    }
                }).start();
    }

    private void getRoot() {
        new OkHttpTo().setUrl("get_root")
                .setJsonObject("UserName", "user1")
                .setJsonObject("Password", "1234")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {
                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        root = new Gson().fromJson(jsonObject.toString(), Root.class);
                        nameTv.setText("姓名：" + root.getName());
                        sexTv.setText("性别：" + root.getSex());
                        sfzTv.setText("身份证：" + root.getIdnumber());
                        phoneTv.setText("手机号码：" + root.getTel());
                        if (root.getSex().equals("男")) {
                            touxiangIv.setImageResource(R.drawable.touxiang_2);
                        } else {
                            touxiangIv.setImageResource(R.drawable.touxiang_1);
                        }
                        timeTv.setText("注册时间" + root.getRegistered_time());
                        setAdapter();
                    }
                }).start();
    }


    private static final String TAG = "PersonFragment";

    private void setAdapter() {
        cars1 = new ArrayList<>();
        plates = root.getPlate();
        for (int i = 0; i < plates.size(); i++) {
            for (int j = 0; j < cars.size(); j++) {
                Car car = cars.get(j);
                if (car.getPlate().equals(plates.get(i))) {
                    cars1.add(car);
                }
            }
        }
        adapter = new PersonAdapter(requireContext(), cars1);
        personGrid.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
