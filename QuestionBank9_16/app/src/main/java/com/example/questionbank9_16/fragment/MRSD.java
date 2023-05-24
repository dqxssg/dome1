package com.example.questionbank9_16.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.questionbank9_16.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName MRSD
 * @Author 史正龙
 * @date 2021.08.05 09:29
 */
public class MRSD extends Fragment {
    @BindView(R.id.title_chart)
    TextView titleChart;
    @BindView(R.id.mrsd_chart)
    BarChart mrsdChart;
    private Unbinder unbinder;
    private Map<Integer, Integer> mapTime;
    private List<BarEntry> barEntries;
    private List<Integer> colors;
    private List<String> xValue;

    public MRSD(Map<Integer, Integer> mapTime) {
        this.mapTime = mapTime;
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.mrsd_fragmnet, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleChart.setText("每日时段内车辆违章的占比统计");
        setData();
    }

    private void setData() {
        if (barEntries == null) {
            barEntries = new ArrayList<>();
            colors = new ArrayList<>();
            xValue = new ArrayList<>();
        }

        int size = 0;
        for (int i = 0; i < 12; i++) {
            size += mapTime.get(i);
        }

        for (int i = 0; i < 12; i++) {
            barEntries.add(new BarEntry(i, (float) mapTime.get(i) / (float) size));
        }

        colors.add(Color.parseColor("#456097"));
        colors.add(Color.parseColor("#30141c"));
        colors.add(Color.parseColor("#040468"));
        colors.add(Color.parseColor("#4f622b"));
        colors.add(Color.parseColor("#b8aee2"));
        colors.add(Color.parseColor("#79963b"));
        colors.add(Color.parseColor("#903632"));
        colors.add(Color.parseColor("#fbc601"));
        colors.add(Color.parseColor("#4b82b8"));
        colors.add(Color.parseColor("#e76a0e"));
        colors.add(Color.parseColor("#93266a"));
        colors.add(Color.parseColor("#4f171d"));

        BarDataSet dataSet = new BarDataSet(barEntries, "");
        dataSet.setColors(colors);
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.3f);
        data.setValueTextColor(Color.RED);
        data.setValueTextSize(25);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("0.00").format(value * 100) + "%";
            }
        });
        setXY();
        mrsdChart.setData(data);
        mrsdChart.getDescription().setEnabled(false);
        mrsdChart.getLegend().setEnabled(false);
        mrsdChart.setTouchEnabled(false);
        mrsdChart.invalidate();
    }


    private void setXY() {
        xValue.add("0:00:01-2:00:00");
        xValue.add("2:00:01-4:00:00");
        xValue.add("4:00:01-6:00:00");
        xValue.add("6:00:01-8:00:00");
        xValue.add("8:00:01-10:00:00");
        xValue.add("10:00:01-12:00:00");
        xValue.add("12:00:01-14:00:00");
        xValue.add("14:00:01-16:00:00");
        xValue.add("16:00:01-18:00:00");
        xValue.add("18:00:01-20:00:00");
        xValue.add("20:00:01-22:00:00");
        xValue.add("22:00:01-24:00:00");
        XAxis xAxis = mrsdChart.getXAxis();
        xAxis.setTextSize(15);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(-60);
        xAxis.setLabelCount(12);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));


        mrsdChart.getAxisRight().setEnabled(false);
        YAxis left = mrsdChart.getAxisLeft();
        left.setTextColor(Color.BLACK);
        left.setLabelCount(6);
        left.setAxisMinimum(0f);
        left.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("0.00").format(value*100)+"%";
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
