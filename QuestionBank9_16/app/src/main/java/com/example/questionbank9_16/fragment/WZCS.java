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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName WZCS
 * @Author 史正龙
 * @date 2021.08.05 09:29
 */
public class WZCS extends Fragment {
    @BindView(R.id.title_chart)
    TextView titleChart;
    @BindView(R.id.wzcs_chart)
    HorizontalBarChart wzcsChart;
    private Unbinder unbinder;
    private int a, b, c;

    public WZCS(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.wzcs_fragmnet, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleChart.setText("违章车辆的违章次数占比分布图统计");
        setData();
    }

    private List<BarEntry> barEntries;
    private List<Integer> colors;

    private void setData() {
        if (barEntries == null) {
            barEntries = new ArrayList<>();
            colors = new ArrayList<>();
        }
        int total;
        total = a + b + c;
        barEntries.add(new BarEntry(0, (float) c / (float) total));
        barEntries.add(new BarEntry(1, (float) b / (float) total));
        barEntries.add(new BarEntry(2, (float) a / (float) total));
        colors.add(Color.parseColor("#4572a7"));
        colors.add(Color.parseColor("#aa4643"));
        colors.add(Color.GREEN);
        BarDataSet dataSet = new BarDataSet(barEntries, "");
        dataSet.setColors(colors);
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.3f);
        data.setValueTextSize(25f);
        data.setValueTextColor(Color.RED);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("0.00").format(value * 100) + "%";
            }
        });
        setXY();
        wzcsChart.setData(data);
        wzcsChart.setTouchEnabled(false);
        wzcsChart.getDescription().setEnabled(false);
        wzcsChart.getLegend().setEnabled(false);
        wzcsChart.invalidate();
    }

    private void setXY() {
        XAxis xAxis = wzcsChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(3);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"1-2条违章","3-5条违章","5条以上违章"}));
        xAxis.setTextSize(15);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);

        wzcsChart.getAxisLeft().setEnabled(false);
        YAxis right = wzcsChart.getAxisRight();
        right.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("0.00").format(value * 100) + "%";
            }
        });
        right.setAxisMinimum(0);
        right.setLabelCount(8);
        right.setTextSize(15);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
