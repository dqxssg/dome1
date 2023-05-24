package com.example.questionbank17_24.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.bean.Type;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class XXFXFragment extends Fragment {
    @BindView(R.id.title_sjfx)
    TextView titleSjfx;
    @BindView(R.id.sjfx_chart)
    PieChart sjfxChart;
    @BindView(R.id.pm_tv)
    TextView pmTv;
    @BindView(R.id.gz_tv)
    TextView gzTv;
    @BindView(R.id.wd_tv)
    TextView wdTv;
    @BindView(R.id.sd_tv)
    TextView sdTv;
    @BindView(R.id.co_tv)
    TextView coTv;
    @BindView(R.id.empty_tv)
    TextView emptyTv;
    @BindView(R.id.xxfx_line)
    LinearLayout xxfxLine;
    private Unbinder unbinder;
    private int wd, gz, sd, pm, co;
    private List<Type> types;
    private boolean isLoop = true;
    private List<Integer> colors;
    private List<PieEntry> pieEntries;

    public XXFXFragment(List<Type> types) {
        this.types = types;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (pieEntries == null) {
                pieEntries = new ArrayList<>();
                colors = new ArrayList<>();

            }

            for (int i = 0; i < types.size(); i++) {
                Type type = types.get(i);
                switch (type.getType()) {
                    case "【湿度】报警":
                        sdTv.setText(sd + "");
                        sd++;
                        break;
                    case "【温度】报警":
                        wdTv.setText(wd + "");
                        wd++;
                        break;
                    case "【CO2】报警":
                        coTv.setText(co + "");
                        co++;
                        break;
                    case "【光照】报警":
                        gzTv.setText(gz + "");
                        gz++;
                        break;
                    case "【PM2.5】报警":
                        pmTv.setText(pm + "");
                        pm++;
                        break;
                }

                if (types.size() == 0) {
                    xxfxLine.setVisibility(View.GONE);
                    emptyTv.setVisibility(View.VISIBLE);
                } else {
                    xxfxLine.setVisibility(View.VISIBLE);
                    emptyTv.setVisibility(View.INVISIBLE);

                }

                colors.add(Color.parseColor("#bfdd7b"));
                colors.add(Color.parseColor("#e3dca1"));
                colors.add(Color.parseColor("#78ea5a"));
                colors.add(Color.parseColor("#ee80b6"));
                colors.add(Color.parseColor("#bfdd7b"));
                pieEntries.clear();
                pieEntries.add(new PieEntry(sd, ""));
                pieEntries.add(new PieEntry(wd, ""));
                pieEntries.add(new PieEntry(co, ""));
                pieEntries.add(new PieEntry(gz, ""));
                pieEntries.add(new PieEntry(pm, ""));
                PieDataSet dataSet = new PieDataSet(pieEntries, "");
                dataSet.setValueLinePart1OffsetPercentage(80.0f);
                dataSet.setValueLinePart1Length(1f);
                dataSet.setValueLinePart2Length(1f);
                dataSet.setColors(colors);
                PieData data = new PieData(dataSet);
                data.setDrawValues(false);
                sjfxChart.setData(data);
                sjfxChart.setTouchEnabled(false);
                sjfxChart.setDrawHoleEnabled(false);
                sjfxChart.setUsePercentValues(true);
                sjfxChart.getLegend().setEnabled(false);
                sjfxChart.getDescription().setEnabled(false);
                sjfxChart.invalidate();
            }


            return false;
        }
    });

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.xxfx_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        titleSjfx.setText("实时数据分析");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLoop = false;
        unbinder.unbind();
    }
}
