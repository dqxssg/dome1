package com.example.questionbank9_16.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.bean.Record;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecordAdapter extends ArrayAdapter<Record> {

    public RecordAdapter(@NonNull Context context, @NonNull List<Record> objects) {
        super(context, 0, objects);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        Record record = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.record_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String[] s = record.getTime().split(" ");
        holder.recordBalance.setText("余额：" + record.getCzhMoney());
        holder.recordRecharge.setText("充值：" + record.getCzqMoney());
        holder.recordUser.setText("充值人：" + record.getOwner());
        holder.recordPlate.setText("车牌号：" + record.getPlate());
        holder.czsjTv.setText(s[0] + " " + s[1]);
        holder.recordTime.setText(s[0] + "\r\n" + s[2]);
        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.record_time)
        TextView recordTime;
        @BindView(R.id.record_user)
        TextView recordUser;
        @BindView(R.id.record_plate)
        TextView recordPlate;
        @BindView(R.id.record_recharge)
        TextView recordRecharge;
        @BindView(R.id.record_balance)
        TextView recordBalance;
        @BindView(R.id.czsj_tv)
        TextView czsjTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
