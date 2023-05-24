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
 * @ClassName NNWZ
 * @Author 史正龙
 * @date 2021.08.05 09:29
 */
public class NNWZ extends Fragment {
    @BindView(R.id.title_chart)
    TextView titleChart;
    @BindView(R.id.nnwz_chart)
    BarChart nnwzChart;
    private Unbinder unbinder;
    private List<BarEntry> barEntries;
    private List<Integer> colors;
    private Map<String, Integer> mapYesSex, mapNoSex;
    private int allMen, allWoman;
    private float men_yes, men_no, woman_yes, woman_no;

    public NNWZ(Map<String, Integer> mapYesSex, Map<String, Integer> mapNoSex) {
        this.mapYesSex = mapYesSex;
        this.mapNoSex = mapNoSex;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.nnwz_fragmnet, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleChart.setText("平台上男性和女性有无车辆违章的占比统计");
        setData();
    }


    private void setData() {
        if (barEntries == null) {
            barEntries = new ArrayList<>();
            colors = new ArrayList<>();
        }

        allMen = mapYesSex.get("男") + mapNoSex.get("男");
        allWoman = mapYesSex.get("女") + mapNoSex.get("女");
        men_yes = (float) mapYesSex.get("男") / (float) allMen;
        men_no = 1 - men_yes;
        woman_yes = (float) mapYesSex.get("女") / (float) allWoman;
        woman_no = 1 - woman_yes;


        barEntries.add(new BarEntry(0, new float[]{woman_yes, woman_no}));
        barEntries.add(new BarEntry(1, new float[]{men_yes, men_no}));

        colors.add(Color.parseColor("#eb7208"));
        colors.add(Color.parseColor("#6a9800"));

        BarDataSet dataSet = new BarDataSet(barEntries, "");
        dataSet.setColors(colors);
        dataSet.setStackLabels(new String[]{"有违法", "无违法"});
        BarData data = new BarData(dataSet);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(25);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("00.0").format(value * 100) + "%";
            }
        });
        data.setBarWidth(0.3f);
        setXY();
        nnwzChart.setData(data);
        nnwzChart.getDescription().setEnabled(false);
        Legend legend = nnwzChart.getLegend();
        legend.setFormSize(25f);
        legend.setTextSize(25f);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setDrawInside(true);
        nnwzChart.setTouchEnabled(false);
        nnwzChart.invalidate();
    }

    private void setXY() {
        XAxis xAxis = nnwzChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(2);
        xAxis.setTextSize(15);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"女性", "男性"}));


        nnwzChart.getAxisRight().setEnabled(false);
        YAxis left = nnwzChart.getAxisLeft();
        left.setAxisMinimum(0);
        left.setLabelCount(7);
        left.setTextSize(15f);
        left.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("0").format(value * 1000);
            }
        });
        left.setTextColor(Color.BLACK);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
