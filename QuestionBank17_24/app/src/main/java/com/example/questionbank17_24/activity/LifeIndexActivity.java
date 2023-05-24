package com.example.questionbank17_24.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.adapter.LifeAdapter;
import com.example.questionbank17_24.bean.Sense;
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

public class LifeIndexActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.life_grid)
    GridView lifeGrid;
    private List<Sense> senses;
    private Sense yz;
    private LifeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_index);
        ButterKnife.bind(this);
        titleTv.setText("生活指数");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getThreshold();
    }


    private void getSense() {
        if (senses == null) {
            senses = new ArrayList<>();
        }
        OkHttpTo okHttpTo = new OkHttpTo();
        okHttpTo.setUrl("get_all_sense")
                .setJsonObject("UserName", "user1")
                .setLoop(true)
                .setTime(3000)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Sense sense = new Gson().fromJson(jsonObject.toString(), Sense.class);
                        sense.save();
                        senses.add(sense);

                        if (senses.size() > 21) {
                            senses.remove(0);
                        }
                        if (adapter == null) {
                            setAdapter();
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {
                        Toast.makeText(LifeIndexActivity.this, "访问失败", Toast.LENGTH_SHORT).show();

                    }
                }).start();
    }

    private void getThreshold() {
        new OkHttpTo()
                .setUrl("get_threshold")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        yz = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").optJSONObject(0).toString(), Sense.class);
                        getSense();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void setAdapter() {
        adapter = new LifeAdapter(senses, yz);
        lifeGrid.setAdapter(adapter);
    }
}