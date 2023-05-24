package com.example.questionbank17_24.fragment;

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

import com.example.questionbank17_24.R;
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
 * @ClassName SJFX1Fragment
 * @Author 史正龙
 * @date 2021.08.05 21:12
 */
public class SJFX2Fragment extends Fragment {
    @BindView(R.id.sjfx2_title)
    TextView sjfx2Title;
    @BindView(R.id.sjfx2_chart)
    HorizontalBarChart sjfx2Chart;
    private Unbinder unbinder;
    private List<BarEntry> barEntries;
    private List<Integer> colors;
    private List<Map.Entry<String, Integer>> typeList;
    private List<String> xValue;
    private int allYes;

    public SJFX2Fragment(List<Map.Entry<String, Integer>> typeList) {
        this.typeList = typeList;
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.sjfx2_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sjfx2Title.setText("排名前十位的交通违法行为的占比统计");
        setData();
    }



    private static final String TAG = "SJFX2Fragment";
    private void setData() {
        if (barEntries == null) {
            barEntries = new ArrayList<>();
            colors = new ArrayList<>();
            xValue = new ArrayList<>();
        }

        Log.d(TAG, "setData: "+typeList.size());
        for (int i = 0; i < typeList.size(); i++) {
            allYes += typeList.get(i).getValue();
        }

        for (int i = 0; i < typeList.size(); i++) {
            xValue.add(typeList.get(i).getKey());
            barEntries.add(new BarEntry(i, (float) typeList.get(i).getValue() / (float) allYes));
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
        data.setBarWidth(0.6f);
        data.setValueTextSize(25);
        data.setValueTextColor(Color.WHITE);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("0.00").format(value * 100) + "%";
            }
        });
        setXY();
        sjfx2Chart.setData(data);
        sjfx2Chart.getLegend().setEnabled(false);
        sjfx2Chart.getDescription().setEnabled(false);
        sjfx2Chart.setTouchEnabled(false);
        sjfx2Chart.invalidate();
    }

    private void setXY() {
        XAxis xAxis = sjfx2Chart.getXAxis();
        xAxis.setLabelCount(10);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValue));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(15);
        xAxis.setDrawAxisLine(false);


        sjfx2Chart.getAxisLeft().setEnabled(false);
        YAxis right = sjfx2Chart.getAxisRight();
        right.setAxisMinimum(0);
        right.setTextSize(15);
        right.setLabelCount(7);
        right.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("0.00").format(value * 100) + "%";
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
