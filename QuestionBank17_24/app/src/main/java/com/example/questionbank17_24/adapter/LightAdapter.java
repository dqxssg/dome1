package com.example.questionbank17_24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.bean.Light;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName LightAdapter
 * @Author 史正龙
 * @date 2021.08.06 09:02
 */
public class LightAdapter extends ArrayAdapter<Light> {
    public LightAdapter(@NonNull Context context, @NonNull List<Light> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.light_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Light light = getItem(position);
        holder.roadItem.setText(light.getNumber() + "");
        holder.redItem.setText(light.getRed() + "");
        holder.yellowItem.setText(light.getYellow() + "");
        holder.greenItem.setText(light.getGreen() + "");
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.road_item)
        TextView roadItem;
        @BindView(R.id.red_item)
        TextView redItem;
        @BindView(R.id.yellow_item)
        TextView yellowItem;
        @BindView(R.id.green_item)
        TextView greenItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
