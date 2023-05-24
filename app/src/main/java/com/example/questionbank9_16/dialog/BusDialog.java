package com.example.questionbank9_16.dialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.activity.QueryBusActivity;
import com.example.questionbank9_16.adapter.BusAdapter;
import com.example.questionbank9_16.bean.Bus;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @ClassName BusDialog
 * @Author 史正龙
 * @date 2021.08.02 17:24
 */
public class BusDialog extends DialogFragment {

    @BindView(R.id.bus_list)
    ListView busList;
    @BindView(R.id.total_dialog)
    TextView totalDialog;
    @BindView(R.id.return_btn)
    Button returnBtn;
    private Unbinder unbinder;
    private QueryBusActivity activity;
    private List<Bus> buses;
    BusAdapter adapter;

    public BusDialog(QueryBusActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.bus_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            totalDialog.setText(activity.total + "");
            if (buses == null) {
                buses = new ArrayList<>();
            } else {
                buses.clear();
                buses.addAll(activity.buses);
            }

            if (adapter == null) {
                adapter = new BusAdapter(requireContext(), buses);
                busList.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
            return false;
        }
    });

    private boolean isLoop = true;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        isLoop = false;
    }

    @OnClick(R.id.return_btn)
    public void onClick() {
        isLoop = false;
        dismiss();
    }
}
