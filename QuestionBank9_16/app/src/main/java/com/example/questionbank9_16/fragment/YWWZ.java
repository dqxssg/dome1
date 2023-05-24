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
import com.example.questionbank9_16.bean.Peccancy;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName YWWZ
 * @Author 史正龙
 * @date 2021.08.05 09:29
 */
public class YWWZ extends Fragment {
    @BindView(R.id.chart_title)
    TextView chartTitle;
    @BindView(R.id.ywwz_chart)
    PieChart ywwzChart;
    private Unbinder unbinder;
    private List<Integer> colors;
    private List<PieEntry> pieEntries;
    private float y, n;
    private List<Peccancy> yes, peccancies;

    public YWWZ(List<Peccancy> yes, List<Peccancy> peccancies) {
        this.yes = yes;
        this.peccancies = peccancies;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.ywwz_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chartTitle.setText("平台上有违章车辆和没违章车辆的占比统计");
        setData();
    }

    private void setData() {
        if (pieEntries == null) {
            pieEntries = new ArrayList<>();
            colors = new ArrayList<>();
        }

        y = (float) yes.size() / (float) peccancies.size();
        n = 1 - y;
        pieEntries.add(new PieEntry(y, "有违章"));
        pieEntries.add(new PieEntry(n, "无违章"));
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
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("0.00").format(value) + "%";
            }
        });
        ywwzChart.setData(data);
        ywwzChart.getDescription().setEnabled(false);
        Legend legend = ywwzChart.getLegend();
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextSize(25);
        legend.setFormSize(25);
        ywwzChart.setTouchEnabled(false);
        ywwzChart.setUsePercentValues(true);
        ywwzChart.setDrawHoleEnabled(false);
        ywwzChart.invalidate();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
