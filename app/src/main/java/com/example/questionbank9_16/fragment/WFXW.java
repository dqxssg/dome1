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
import com.github.mikephil.charting.charts.HorizontalBarChart;
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
 * @ClassName WFXW
 * @Author 史正龙
 * @date 2021.08.05 09:29
 */
public class WFXW extends Fragment {
    @BindView(R.id.title_chart)
    TextView titleChart;
    @BindView(R.id.wfxw_chart)
    HorizontalBarChart wfxwChart;
    private Unbinder unbinder;
    private List<BarEntry> barEntries;
    private List<Integer> colors;
    private List<Map.Entry<String, Integer>> typeList;
    private List<String> xValue;
    private int all;

    public WFXW(List<Map.Entry<String, Integer>> typeList) {
        this.typeList = typeList;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.wfxw_fragmnet, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleChart.setText("排名前十位的交通违法行为的占比统计");
        setData();
    }


    private void setData() {
        if (barEntries == null) {
            barEntries = new ArrayList<>();
            colors = new ArrayList<>();
            xValue = new ArrayList<>();
        }

        for (int i = 0; i < typeList.size(); i++) {
            all += typeList.get(i).getValue();
        }

        for (int i = 0; i < typeList.size(); i++) {
            xValue.add(typeList.get(i).getKey());
            barEntries.add(new BarEntry(i, (float) typeList.get(i).getValue() / (float) all));
        }

        colors.add(Color.parseColor("#a80205"));
        colors.add(Color.parseColor("#4e81b5"));
        colors.add(Color.parseColor("#77943a"));
        colors.add(Color.parseColor("#e86b06"));
        colors.add(Color.parseColor("#604a7c"));
        colors.add(Color.parseColor("#ffbd0d"));
        colors.add(Color.parseColor("#b7cb84"));
        colors.add(Color.parseColor("#ffad67"));
        colors.add(Color.parseColor("#b8adce"));
        colors.add(Color.parseColor("#92adcc"));
        BarDataSet dataSet = new BarDataSet(barEntries, "");
        dataSet.setColors(colors);
        BarData data = new BarData(dataSet);
        data.setValueTextSize(25);
        data.setValueTextColor(Color.WHITE);
        data.setBarWidth(0.6f);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("0.0").format(value) + "%";
            }
        });
        setXY();
        wfxwChart.setData(data);
        wfxwChart.getDescription().setEnabled(false);
        wfxwChart.getLegend().setEnabled(false);
        wfxwChart.setTouchEnabled(false);
        wfxwChart.invalidate();
    }


    private void setXY() {
        wfxwChart.getAxisLeft().setEnabled(false);
        YAxis right = wfxwChart.getAxisRight();
        right.setLabelCount(8);
        right.setTextSize(15);
        right.setTextColor(Color.BLACK);
        right.setAxisMinimum(0);
        right.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("0.00").format(value * 100) + "%";
            }
        });


        XAxis xAxis = wfxwChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setLabelCount(xValue.size());
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
