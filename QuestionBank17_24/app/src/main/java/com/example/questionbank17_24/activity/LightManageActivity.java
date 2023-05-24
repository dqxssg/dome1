package com.example.questionbank17_24.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.adapter.LightAdapter;
import com.example.questionbank17_24.bean.Light;
import com.example.questionbank17_24.net.OkHttpLo;
import com.example.questionbank17_24.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.util.Objects.requireNonNull;

public class LightManageActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.light_iv)
    ImageView lightIv;
    @BindView(R.id.light_spinner)
    Spinner lightSpinner;
    @BindView(R.id.light_list)
    ListView lightList;
    LightAdapter adapter;
    private List<Light> lights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_manage);
        ButterKnife.bind(this);
        titleTv.setText("红绿灯管理");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        AnimationDrawable drawable = (AnimationDrawable) lightIv.getDrawable();
        drawable.start();
        lightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        for (int i = 1; i <= 5; i++) {
            getLight(i);

        }
    }

    private static final String TAG = "LightManageActivity";

    private void getLight(int i) {
        new OkHttpTo().setUrl("get_traffic_light")
                .setJsonObject("UserName", "user1")
                .setJsonObject("number", i)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //这种好像只能用于循环获取数组里有多条数据的情况，如果遇见这种需要换5个number的，就相当于换5次接口
                        //这个就不行了
//                        lights = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(),
//                                new TypeToken<List<Light>>() {
//                                }.getType());

                        Light light = new Gson().fromJson(requireNonNull(jsonObject.optJSONArray("ROWS_DETAIL").optJSONObject(0)).toString(), Light.class);
                        lights.add(light);
                        setAdapter();

                        setAdapter();

                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();

    }

    private void setAdapter() {
        sortList();
        if (adapter == null) {
            adapter = new LightAdapter(LightManageActivity.this, lights);
            lightList.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void sortList() {
        lights.sort(new Comparator<Light>() {
            @Override
            public int compare(Light o1, Light o2) {
                switch ((int) lightSpinner.getSelectedItemId()) {
                    case 0:
                        return o1.getNumber() - o2.getNumber();
                    case 1:
                        return o2.getNumber() - o1.getNumber();
                    case 2:
                        return o1.getRed() - o2.getRed();
                    case 3:
                        return o2.getRed() - o1.getRed();
                    case 4:
                        return o1.getYellow() - o2.getYellow();
                    case 5:
                        return o2.getYellow() - o1.getYellow();
                    case 6:
                        return o1.getGreen() - o2.getGreen();
                    case 7:
                        return o2.getGreen() - o1.getGreen();
                    default:
                        return 0;
                }
            }
        });
    }
}