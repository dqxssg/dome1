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
 * @ClassName NLQT
 * @Author 史正龙
 * @date 2021.08.05 09:29
 */
public class NLQT extends Fragment {
    @BindView(R.id.title_chart)
    TextView titleChart;
    @BindView(R.id.nlqt_chart)
    BarChart nlqtChart;
    private Unbinder unbinder;
    private List<BarEntry> barEntries;
    private List<Integer> colors;
    private Map<String, Integer> mapYesAge, mapNoAge;
    private int allYes, allNo;

    public NLQT(Map<String, Integer> mapYesAge, Map<String, Integer> mapNoAge) {
        this.mapYesAge = mapYesAge;
        this.mapNoAge = mapNoAge;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.nlqt_fragmnet, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleChart.setText("年龄群体车辆违章的占比统计");
        setData();
    }


    private static final String TAG = "NLQT";

    private void setData() {
        if (barEntries == null) {
            barEntries = new ArrayList<>();
            colors = new ArrayList<>();
        }

        for (int i = 9; i > 4; i--) {
            allYes += mapYesAge.get(i + "");
        }

        for (int i = 9; i > 4; i--) {
            allNo += mapNoAge.get(i + "");
        }

        for (int i = 0, j = 9; i < 5; i++, j--) {
            barEntries.add(new BarEntry(i, new float[]{(float) mapYesAge.get(j + "") / (float) allYes,
                    (float) mapNoAge.get(j + "") / (float) allNo}));
        }

        colors.add(Color.parseColor("#eb7208"));
        colors.add(Color.parseColor("#6a9800"));

        BarDataSet dataSet = new BarDataSet(barEntries, "");
        dataSet.setColors(colors);
        dataSet.setStackLabels(new String[]{"有违章", "无违章"});
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.3f);
        data.setValueTextSize(25f);
        data.setValueTextColor(Color.RED);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("00.0").format(value * 100) + "%";
            }
        });
        setXY();
        nlqtChart.setData(data);
        nlqtChart.getDescription().setEnabled(false);
        Legend legend = nlqtChart.getLegend();
        legend.setDrawInside(true);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setTextSize(25);
        legend.setFormSize(25);
        nlqtChart.setTouchEnabled(false);
        nlqtChart.invalidate();
    }

    private void setXY() {
        XAxis xAxis = nlqtChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"90后", "80后", "70后", "60后", "50后"}));
        xAxis.setLabelCount(5);


        nlqtChart.getAxisRight().setEnabled(false);
        YAxis left = nlqtChart.getAxisLeft();
        left.setTextSize(15);
        left.setAxisMinimum(0);
        left.setLabelCount(7);
        left.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("0").format(value * 1000);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
