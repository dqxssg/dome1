package com.example.questionbank9_16.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.bean.Bus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BusAdapter extends ArrayAdapter<Bus> {
    public BusAdapter(@NonNull Context context, @NonNull List<Bus> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Bus bus = getItem(position);
        holder.idTv.setText((position + 1) + "");
        holder.busIdTv.setText(bus.getBus() + "");
        holder.totalBus.setText(bus.getPerson() + "");
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.id_tv)
        TextView idTv;
        @BindView(R.id.busId_tv)
        TextView busIdTv;
        @BindView(R.id.total_bus)
        TextView totalBus;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
