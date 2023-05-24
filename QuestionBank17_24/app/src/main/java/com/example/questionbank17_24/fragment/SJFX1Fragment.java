package com.example.questionbank17_24.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.bean.Peccancy;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName SJFX1Fragment
 * @Author 史正龙
 * @date 2021.08.05 21:12
 */
public class SJFX1Fragment extends Fragment {
    @BindView(R.id.sjfx1_title)
    TextView sjfx1Title;
    @BindView(R.id.sjfx1_chart)
    PieChart sjfx1Chart;
    private Unbinder unbinder;
    private List<PieEntry> pieEntries;
    private List<Integer> colors;
    private List<Peccancy> yes, peccancies;
    private float y, n;

    public SJFX1Fragment(List<Peccancy> yes, List<Peccancy> peccancies) {
        this.yes = yes;
        this.peccancies = peccancies;
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.sjfx1_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sjfx1Title.setText("平台上有违章车辆和没违章车辆的占比统计");
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

        colors.add(Color.parseColor("#4572A6"));
        colors.add(Color.parseColor("#A94643"));

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setValueLinePart1OffsetPercentage(80.0f);
        dataSet.setValueLinePart1Length(1f);
        dataSet.setValueLinePart2Length(1f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueTextSize(25);
        dataSet.setColors(colors);
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("0.00").format(value) + "%";
            }
        });
        PieData data = new PieData(dataSet);

        sjfx1Chart.setData(data);
        sjfx1Chart.getDescription().setEnabled(false);
        Legend legend = sjfx1Chart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setTextSize(25);
        legend.setFormSize(25);
        sjfx1Chart.setUsePercentValues(true);
        sjfx1Chart.setDrawHoleEnabled(false);
        sjfx1Chart.setTouchEnabled(false);
        sjfx1Chart.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
