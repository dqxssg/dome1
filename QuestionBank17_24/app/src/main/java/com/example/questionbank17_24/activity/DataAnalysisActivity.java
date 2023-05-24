package com.example.questionbank17_24.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.adapter.PagerAdapter;
import com.example.questionbank17_24.bean.Peccancy;
import com.example.questionbank17_24.fragment.SJFX1Fragment;
import com.example.questionbank17_24.fragment.SJFX2Fragment;
import com.example.questionbank17_24.net.OkHttpLo;
import com.example.questionbank17_24.net.OkHttpTo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DataAnalysisActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.data_line)
    LinearLayout dataLine;
    private List<Peccancy> peccancies, yes, no;
    private Map<String, Integer> mapYesType;
    private List<Map.Entry<String, Integer>> typeList;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_analysis);
        ButterKnife.bind(this);
        titleTv.setText("数据分析");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getPeccancy();

    }

    private void getPeccancy() {
        new OkHttpTo().setUrl("get_peccancy")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        peccancies = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(),
                                new TypeToken<List<Peccancy>>() {
                                }.getType());
                        dataList();
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void dataList() {
        if (peccancies != null) {
            yes = new ArrayList<>();
            no = new ArrayList<>();
            mapYesType = new HashMap<>();
        }

        for (int i = 0; i < peccancies.size(); i++) {
            Peccancy peccancy = peccancies.get(i);
            if (peccancy.getPaddr().length() == 0) {
                no.add(peccancy);
            } else {
                yes.add(peccancy);
            }
        }

        for (int i = 0; i < yes.size(); i++) {
            String type = yes.get(i).getPaddr();
            Integer count = mapYesType.get(type);
            mapYesType.put(type, (count == null) ? 1 : count + 1);
        }

        typeList = new ArrayList<>(mapYesType.entrySet());
        typeList.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        initPager();
    }

    private void initPager() {
        fragments = new ArrayList<>();
        fragments.add(new SJFX1Fragment(yes, peccancies));
        fragments.add(new SJFX2Fragment(typeList));
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dataLine.getChildCount(); i++) {
                    ImageView imageView = (ImageView) dataLine.getChildAt(i);
                    if (i == position) {
                        imageView.setImageResource(R.drawable.bg_shape1);
                    } else {
                        imageView.setImageResource(R.drawable.bg_shape);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < fragments.size(); i++) {
            ImageView imageView = new ImageView(this);
            if (i == 0) {
                imageView.setImageResource(R.drawable.bg_shape1);
            } else {
                imageView.setImageResource(R.drawable.bg_shape);
            }
            imageView.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
            dataLine.addView(imageView);
        }
    }

}