package com.example.questionbank9_16.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.activity.LifeAssistantActivity;
import com.example.questionbank9_16.bean.Sense;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName WDFragment
 * @Author 史正龙
 * @date 2021.08.03 22:21
 */
public class CO2Fragment extends Fragment {

    @BindView(R.id.msg_co2)
    TextView msgCo2;
    @BindView(R.id.co2_chart)
    LineChart co2Chart;
    private Unbinder unbinder;
    private LifeAssistantActivity activity;
    private boolean isLoop = true;
    private List<Integer> integers;
    private List<Entry> entries;
    private List<Sense> senses;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            setData();
            return false;
        }
    });

    public CO2Fragment(LifeAssistantActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.co2_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (isLoop);
            }
        }).start();
    }


    private void setData() {
        senses = activity.senses;
        if (entries == null) {
            integers = new ArrayList<>();
            entries = new ArrayList<>();
        } else {
            entries.clear();
        }
        if (senses != null) {
            for (int i = 0; i < senses.size(); i++) {
                Sense sense = senses.get(i);
                integers.add(sense.getCo2());
                entries.add(new Entry(i, sense.getCo2()));
                msgCo2.setText("过去一分钟最大相对浓度：：" + Collections.max(integers));
            }
        }

        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setCircleColor(Color.WHITE);
        dataSet.setColor(Color.WHITE);
        dataSet.setDrawCircleHole(false);
        dataSet.setDrawValues(false);
        LineData data = new LineData(dataSet);
        setXY();
        co2Chart.setData(data);
        co2Chart.getLegend().setEnabled(false);
        co2Chart.getDescription().setText("(S)");
        co2Chart.setBackgroundColor(Color.parseColor("#52c6f7"));
        co2Chart.invalidate();
    }

    private void setXY() {
        XAxis xAxis = co2Chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(20);
        xAxis.setLabelCount(20);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("00").format((value + 1) * 3);
            }
        });
        xAxis.setGranularity(1f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setTextSize(20);

        co2Chart.getAxisRight().setEnabled(false);
        YAxis left = co2Chart.getAxisLeft();
        left.setAxisMaximum(7000);
        left.setAxisMinimum(0);
        left.setTextSize(20);
        left.setTextColor(Color.WHITE);
        left.setDrawAxisLine(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        isLoop = false;
    }
}
