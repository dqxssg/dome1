package com.example.questionbank9_16.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.adapter.LightAdapter;
import com.example.questionbank9_16.bean.Light;
import com.example.questionbank9_16.dialog.LightDialog;
import com.example.questionbank9_16.net.OkHttpLo;
import com.example.questionbank9_16.net.OkHttpTo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LightManageActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_light)
    Spinner spinnerLight;
    @BindView(R.id.query_btn)
    Button queryBtn;
    @BindView(R.id.plsz_btn)
    Button plszBtn;
    @BindView(R.id.light_list)
    ListView lightList;
    private List<Light> lights;
    LightAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_manage);
        ButterKnife.bind(this);
        titleTv.setText("红绿灯管理");
        for (int i = 1; i <= 5; i++) {
            getLight(i);
        }
    }

    private static final String TAG = "LightManageActivity";

    private void getLight(int i) {
        OkHttpTo okHttpTo = new OkHttpTo();
        if (lights == null) {
            lights = new ArrayList<>();
        } else {
            lights.clear();
        }
        //这样设置一下可以进这个程序稍微快一点
        if (i == 5) {
            okHttpTo.setDialog(this);
        }
        okHttpTo.setUrl("get_traffic_light")
                .setJsonObject("UserName", "user1")
                .setJsonObject("number", i)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        lights.add(new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").optJSONObject(0).toString()
                                , Light.class));

                        if (lights.size() == 5) {
                            sortLight();
                        }

                    }
                }).start();
    }

    private void sortLight() {
        Collections.sort(lights, new Comparator<Light>() {
            @Override
            public int compare(Light o1, Light o2) {
                switch ((int) spinnerLight.getSelectedItemId()) {
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

        if (adapter == null) {
            adapter = new LightAdapter(LightManageActivity.this, lights);
            adapter.setMyClick(new LightAdapter.MyClick() {
                @Override
                public void onClick(int lx, int position, boolean xz) {
                    final Light light = lights.get(position);
                    if (lx == 1) {
                        light.setXz(xz);
                        lights.set(position, light);
                    } else if (lx == 2) {
                        LightDialog dialog = new LightDialog(light.getNumber() + "");
                        dialog.show(getSupportFragmentManager(), "");
                        dialog.setUpData(new LightDialog.UpData() {
                            @Override
                            public void onUpData(int lx) {
                                if (lx == 1) {
                                    Toast.makeText(LightManageActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                                    for (int i = 1; i <= 5; i++) {
                                        getLight(i);
                                    }
                                } else if (lx == 2) {
                                    Toast.makeText(LightManageActivity.this, "设置失败", Toast.LENGTH_SHORT).show();
                                } else if (lx == 3) {
                                    for (int i = 0; i < lights.size(); i++) {
                                        lights.get(i).setXz(false);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            });
            lightList.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.toolbar, R.id.query_btn, R.id.plsz_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.query_btn:
                for (int i = 1; i <= 5; i++) {
                    getLight(i);
                }
                break;
            case R.id.plsz_btn:
                String lk = "";
                for (int i = 0; i < lights.size(); i++) {
                    Light light = lights.get(i);
                    if (light.isXz()) {
                        if (" ".equals(lk)) {
                            lk = light.getNumber() + "";
                        } else {
                            lk += "," + light.getNumber();
                        }
                    }
                }
                if (TextUtils.isEmpty(lk)) {
                    Toast.makeText(this, "没有选中的路口", Toast.LENGTH_SHORT).show();
                    return;
                }
                LightDialog dialog = new LightDialog(lk);
                dialog.show(getSupportFragmentManager(), "");
                dialog.setUpData(new LightDialog.UpData() {
                    @Override
                    public void onUpData(int lx) {
                        if (lx == 1) {
                            Toast.makeText(LightManageActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                            for (int i = 1; i <= 5; i++) {
                                getLight(i);
                            }
                        } else if (lx == 2) {
                            Toast.makeText(LightManageActivity.this, "设置失败", Toast.LENGTH_SHORT).show();
                        } else if (lx == 3) {
                            for (int i = 0; i < lights.size(); i++) {
                                lights.get(i).setXz(false);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                break;
        }
    }
}