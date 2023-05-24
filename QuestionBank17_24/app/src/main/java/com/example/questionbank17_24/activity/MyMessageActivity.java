package com.example.questionbank17_24.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.bean.Sense;
import com.example.questionbank17_24.bean.Type;
import com.example.questionbank17_24.fragment.QueryFragment;
import com.example.questionbank17_24.fragment.XXFXFragment;
import com.example.questionbank17_24.net.OkHttpLo;
import com.example.questionbank17_24.net.OkHttpTo;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyMessageActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.xxcx_tv)
    TextView xxcxTv;
    @BindView(R.id.xxfx_tv)
    TextView xxfxTv;
    public Sense yz;
    public List<Type> types;
    private List<Sense> senses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_news);
        ButterKnife.bind(this);
        titleTv.setText("我的消息");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        types = new ArrayList<>();
        getSense();
        replaceFragment(new QueryFragment(types));
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
                    public void onResponse(JSONObject jsonObject) {
                        Sense sense = new Gson().fromJson(jsonObject.toString(), Sense.class);
                        senses.add(sense);
                        if (senses.size() > 21) {
                            senses.remove(0);
                        }
                        getThreshold();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }


    private void getThreshold() {
        new OkHttpTo().setUrl("get_threshold")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        yz = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").optJSONObject(0).toString(),
                                Sense.class);
                        Alarm();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }


    private void Alarm() {
        for (int i = 0; i < senses.size(); i++) {
            Sense sense = senses.get(i);
            if (sense.getTemperature() > yz.getTemperature()) {
                types.add(new Type("【温度】报警", yz.getTemperature(), sense.getTemperature()));
            }
            if (sense.getHumidity() > yz.getHumidity()) {
                types.add(new Type("【湿度】报警", yz.getHumidity(), sense.getHumidity()));
            }
            if (sense.getIllumination() > yz.getIllumination()) {
                types.add(new Type("【光照】报警", yz.getIllumination(), sense.getIllumination()));
            }
            if (sense.getCo2() > yz.getCo2()) {
                types.add(new Type("【CO2】报警", yz.getCo2(), sense.getCo2()));
            }
            if (sense.getPm25() > yz.getPm25()) {
                types.add(new Type("【PM2.5】报警", yz.getPm25(), sense.getPm25()));
            }
        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }


    @OnClick({R.id.xxcx_tv, R.id.xxfx_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xxcx_tv:
                xxcxTv.setBackgroundResource(R.drawable.xx_shape);
                xxfxTv.setBackgroundResource(R.drawable.xx_shape2);
                replaceFragment(new QueryFragment(types));
                break;
            case R.id.xxfx_tv:
                xxfxTv.setBackgroundResource(R.drawable.xx_shape);
                xxcxTv.setBackgroundResource(R.drawable.xx_shape2);
                replaceFragment(new XXFXFragment(types));
                break;
        }
    }
}