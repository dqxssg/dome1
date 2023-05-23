package com.example.tk1_5.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tk1_5.AppClient;
import com.example.tk1_5.R;
import com.example.tk1_5.bean.HJZB;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-17 at 10:06 ：）
 */
public class SSXS_Fragment1 extends Fragment {
    private TextView tvTitle;
    private LineChart lineChart;
    private AppClient appClient;
    private List<HJZB> hjzbs;
    private List<Entry> entries;
    private List<String> xValue;
    private boolean isLoop = true;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            setDataView();
            return false;
        }
    });

    private void setDataView() {
        hjzbs = appClient.getHjzbs();
        if (hjzbs.size() == 0) {
            return;
        }
        if (entries == null) {
            entries = new ArrayList<>();
            xValue = new ArrayList<>();
        } else {
            entries.clear();
            xValue.clear();
        }
        for (int i = 0; i < hjzbs.size(); i++) {
            HJZB hjzb = hjzbs.get(i);
            entries.add(new Entry(i, hjzb.getTemperature()));
            xValue.add(hjzb.getTime());
        }
        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setCircleColor(Color.GRAY);
        dataSet.setCircleHoleRadius(3f);
        dataSet.setColor(Color.GRAY);
        dataSet.setValueTextSize(20);
        dataSet.setLineWidth(4f);
        dataSet.setDrawCircleHole(false);
        LineData data = new LineData(dataSet);
        setX();
        setY();=
        lineChart.setData(data);
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.invalidate();
    }

    private void setY() {
        YAxis yAxis = lineChart.getAxisRight();
        yAxis.setEnabled(false);
        YAxis yAxis1 = lineChart.getAxisLeft();
        yAxis1.setAxisMinimum(0);
        yAxis1.setAxisMaximum(45);
        yAxis1.setTextSize(20);
        yAxis1.setDrawAxisLine(false);
    }

    private void setX() {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        xAxis.setLabelRotationAngle(90);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(xValue.size());
        xAxis.setGranularity(1f);
        xAxis.setTextSize(20);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ssxs_fragment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        appClient = (AppClient) getActivity().getApplication();
        tvTitle.setText("温度");
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

    private void initView() {
        tvTitle = getView().findViewById(R.id.tv_title);
        lineChart = getView().findViewById(R.id.line_chart);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isLoop = false;
    }
}
