package com.example.questionbank9_16.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.activity.LifeAssistantActivity;
import com.example.questionbank9_16.bean.Sense;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName KQZLFragment
 * @Author 史正龙
 * @date 2021.08.03 22:19
 */
public class KQZLFragment extends Fragment {
    @BindView(R.id.msg_xdzl)
    TextView msgXdzl;
    @BindView(R.id.kqzl_chart)
    BarChart kqzlChart;
    private Unbinder unbinder;
    private boolean isLoop = true;
    private List<Sense> senses;
    private List<BarEntry> barEntries;
    private List<Integer> integers;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            setData();
            return false;
        }
    });



    public KQZLFragment(List<Sense> senses) {
        this.senses = senses;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.kqzl_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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


    private void setData() {
        if (barEntries == null) {
            barEntries = new ArrayList<>();
            integers = new ArrayList<>();
        } else {
            barEntries.clear();
        }

        if (senses != null) {
            for (int i = 0; i < senses.size(); i++) {
                Sense sense = senses.get(i);
                integers.add(sense.getPm25());
                msgXdzl.setText("过去1分钟空气质量最差值：" + Collections.min(integers)+"");
                barEntries.add(new BarEntry(i, sense.getPm25()));
            }
            BarDataSet dataSet = new BarDataSet(barEntries, "");
            dataSet.setColor(Color.GRAY);
            dataSet.setDrawValues(false);
            BarData data = new BarData(dataSet);
            setXY();
            kqzlChart.setData(data);
            kqzlChart.setDoubleTapToZoomEnabled(false);
            kqzlChart.getDescription().setText("(S)");
            kqzlChart.getLegend().setEnabled(false);
            MarkerAdapter markerAdapter = new MarkerAdapter(requireContext(), R.layout.marker_item);
            kqzlChart.setMarker(markerAdapter);
            markerAdapter.setChartView(kqzlChart);
            kqzlChart.invalidate();

        }

    }

    private void setXY() {
        XAxis xAxis = kqzlChart.getXAxis();
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(20);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(19.5f);
        xAxis.setAxisMinimum(0);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(20);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new DecimalFormat("00").format((value + 1) * 3);
            }
        });

        kqzlChart.getAxisRight().setEnabled(false);
        YAxis left = kqzlChart.getAxisLeft();
        left.setDrawAxisLine(false);
        left.setTextColor(Color.BLACK);
        left.setTextSize(20);
        left.setAxisMinimum(0);
        left.setAxisMaximum(400);
        left.setGranularity(1.5f);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLoop = false;
        unbinder.unbind();
    }
}

class MarkerAdapter extends MarkerView {

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    private TextView textView;

    public MarkerAdapter(Context context, int layoutResource) {
        super(context, layoutResource);
        textView = findViewById(R.id.mar_text);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        textView.setText("" + ((int) e.getY()));
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
