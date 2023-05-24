package com.example.questionbank9_16.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.adapter.PagerAdapter;
import com.example.questionbank9_16.bean.Peccancy;
import com.example.questionbank9_16.fragment.CFWZ;
import com.example.questionbank9_16.fragment.MRSD;
import com.example.questionbank9_16.fragment.NLQT;
import com.example.questionbank9_16.fragment.NNWZ;
import com.example.questionbank9_16.fragment.WFXW;
import com.example.questionbank9_16.fragment.WZCS;
import com.example.questionbank9_16.fragment.YWWZ;
import com.example.questionbank9_16.net.OkHttpLo;
import com.example.questionbank9_16.net.OkHttpTo;
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.data_line)
    LinearLayout dataLine;
    private List<Peccancy> peccancies, yes, no;
    private Map<String, Integer> map, mapYesAge, mapNoAge, mapyesSex, mapnoSex, mapType;
    private List<Map.Entry<String, Integer>> typeList;
    private Map<Integer, Integer> mapTime;
    private int a, b, c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_analysis);
        ButterKnife.bind(this);
        titleTv.setText("数据分析");
        fragments = new ArrayList<>();
        getPeccancy();
    }

    private void getPeccancy() {
        if (peccancies == null) {
            peccancies = new ArrayList<>();
        }
        new OkHttpTo().setUrl("get_peccancy")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        peccancies = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<Peccancy>>() {
                        }.getType());

                        setDataList();
                    }
                }).start();
    }

    private void setDataList() {

        if (peccancies != null) {
            yes = new ArrayList<>();
            no = new ArrayList<>();
            map = new HashMap<>();
            mapYesAge = new HashMap<>();
            mapNoAge = new HashMap<>();
            mapyesSex = new HashMap<>();
            mapnoSex = new HashMap<>();
            mapTime = new HashMap<>();
            mapType = new HashMap<>();
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
            String id = yes.get(i).getCarnumber();
            Integer count = map.get(id);
            map.put(id, (count == null) ? 1 : count + 1);
        }

        for (Integer count : map.values()) {
            if (count > 5) {
                a++;
            } else if (count >= 3 && count <= 5) {
                b++;
            } else {
                c++;
            }
        }


        for (int i = 0; i < yes.size(); i++) {
            String birthday = yes.get(i).getShengri().substring(0, 4);
            String year = ((Integer.parseInt(birthday) - 1900) / 10) + "";
            Integer count = mapYesAge.get(year);
            mapYesAge.put(year, (count == null) ? 1 : count + 1);
        }

        for (int i = 0; i < no.size(); i++) {
            String birthday = no.get(i).getShengri().substring(0, 4);
            String year = ((Integer.parseInt(birthday) - 1900) / 10) + "";
            Integer count = mapNoAge.get(year);
            mapNoAge.put(year, (count == null) ? 1 : count + 1);
        }

        for (int i = 0; i < yes.size(); i++) {
            String sex = yes.get(i).getSex();
            Integer count = mapyesSex.get(sex);
            mapyesSex.put(sex, (count == null) ? 1 : count + 1);
        }
        for (int i = 0; i < no.size(); i++) {
            String sex = no.get(i).getSex();
            Integer count = mapnoSex.get(sex);
            mapnoSex.put(sex, (count == null) ? 1 : count + 1);
        }

        for (int i = 0; i < yes.size(); i++) {
            String[] index = yes.get(i).getDatetime().split(" ");
            String[] time = index[1].split(":");
            if (time[0].equals("00")) {
                Integer count = mapTime.get(time[0]);
                mapTime.put(0, (count == null) ? 1 : count + 1);
            } else {
                int hour = Integer.parseInt(time[0]) - 1;
                Integer count = mapTime.get(hour / 2);
                mapTime.put(hour / 2, (count == null) ? 1 : count + 1);
            }
        }

        for (int i = 0; i < yes.size(); i++) {
            String type = yes.get(i).getPaddr();
            Integer count = mapType.get(type);
            mapType.put(type, (count == null) ? 1 : count + 1);
        }

        typeList = new ArrayList<>(mapType.entrySet());
        typeList.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        initPager();
    }

    private List<Fragment> fragments;

    private void initPager() {
        fragments.add(new YWWZ(yes, peccancies));
        fragments.add(new CFWZ(map, yes));
        fragments.add(new WZCS(a, b, c));
        fragments.add(new NLQT(mapYesAge, mapNoAge));
        fragments.add(new NNWZ(mapyesSex, mapnoSex));
        fragments.add(new MRSD(mapTime));
        fragments.add(new WFXW(typeList));
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
                        imageView.setImageResource(R.drawable.page_indicator_focused);
                    } else {
                        imageView.setImageResource(R.drawable.page_indicator_unfocused);
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
                imageView.setImageResource(R.drawable.page_indicator_focused);
            } else {
                imageView.setImageResource(R.drawable.page_indicator_unfocused);
            }
            imageView.setLayoutParams(new ViewGroup.LayoutParams(50, 50));
            dataLine.addView(imageView);
        }
    }

    @OnClick(R.id.toolbar)
    public void onClick() {
        finish();
    }
}