package com.example.questionbank9_16.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.adapter.RecordAdapter;
import com.example.questionbank9_16.bean.Record;
import com.example.questionbank9_16.bean.Root;
import com.example.questionbank9_16.net.OkHttpLo;
import com.example.questionbank9_16.net.OkHttpTo;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecordFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.avatar_iv)
    ImageView avatarIv;
    @BindView(R.id.name_record)
    TextView nameRecord;
    @BindView(R.id.total_money)
    TextView totalMoney;
    @BindView(R.id.record_list)
    ListView recordList;
    @BindView(R.id.empty_tv)
    TextView emptyTv;
    private int money;
    private Root root;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.record_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getRoot();
        List<Record> records = LitePal.findAll(Record.class);
        for (int i = 0; i < records.size(); i++) {
            Record record = records.get(i);
            money += Integer.parseInt(record.getCzqMoney());
        }
        Collections.sort(records, new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                try {
                    Date date1 = format.parse(o1.getTime());
                    Date date2 = format.parse(o1.getTime());
                    if (date1.getTime() > date2.getTime()) {
                        return 1;
                    } else if (date1.getTime() < date2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        totalMoney.setText(money + "");
        recordList.setAdapter(new RecordAdapter(requireContext(), records));
        recordList.setEmptyView(emptyTv);
    }

    private void getRoot() {
        new OkHttpTo().setUrl("get_root")
                .setJsonObject("UserName", "user1")
                .setJsonObject("Password", "1234")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {
                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        root = new Gson().fromJson(jsonObject.toString(), Root.class);
                        nameRecord.setText(root.getName());
                        if (root.getSex().equals("女")) {
                            avatarIv.setImageResource(R.drawable.touxiang_1);
                        } else {
                            avatarIv.setImageResource(R.drawable.touxiang_2);

                        }

                    }
                }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
