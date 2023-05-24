package com.example.questionbank9_16.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.bean.Peccancy;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
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
 * @ClassName YWWZ
 * @Author 史正龙
 * @date 2021.08.05 09:29
 */
public class CFWZ extends Fragment {
    @BindView(R.id.chart_title)
    TextView chartTitle;
    @BindView(R.id.cfwz_chart)
    PieChart cfwzChart;
    private Unbinder unbinder;
    private List<Integer> colors;
    private List<PieEntry> pieEntries;
    private Map<String, Integer> map;
    private List<Peccancy> yes;

    public CFWZ(Map<String, Integer> map, List<Peccancy> yes) {
        this.map = map;
        this.yes = yes;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.cfwz_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private float y, n;

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chartTitle.setText("有无重复违章记录的车辆的占比统计");
        setData();
    }

    private static final String TAG = "CFWZ";

    private void setData() {
        if (pieEntries == null) {
            pieEntries = new ArrayList<>();
            colors = new ArrayList<>();
        }

        y = (float) map.size() / (float) yes.size();
        n = 1 - y;

        Log.d(TAG, "y: " + map
                .size());
        Log.d(TAG, "n: " + yes.size());
        pieEntries.add(new PieEntry(y, "有重复违章"));
        pieEntries.add(new PieEntry(n, "无重复违章"));
        colors.add(Color.parseColor("#4572a7"));
        colors.add(Color.parseColor("#aa4643"));
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(colors);
        dataSet.setSliceSpace(3f);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueLinePart1Length(1f);
        dataSet.setValueLinePart2Length(1f);
        dataSet.setValueLinePart1OffsetPercentage(80.0f);
        PieData data = new PieData(dataSet);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(25);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("0.00").format(value) + "%";
            }
        });
        cfwzChart.setData(data);
        cfwzChart.setDrawHoleEnabled(false);
        cfwzChart.setUsePercentValues(true);
        cfwzChart.getDescription().setEnabled(false);
        Legend legend = cfwzChart.getLegend();
        legend.setFormSize(25);
        legend.setTextSize(25);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        cfwzChart.setTouchEnabled(false);
        cfwzChart.invalidate();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
