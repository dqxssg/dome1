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
import com.example.questionbank17_24.bean.Sense;
import com.example.questionbank17_24.bean.Type;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName QueryAdapter
 * @Author 史正龙
 * @date 2021.08.05 17:30
 */
public class QueryAdapter extends ArrayAdapter<Type> {

    public QueryAdapter(@NonNull Context context, @NonNull List<Type> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.query_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Type type = getItem(position);
        holder.serialTv.setText(position + 1 + "");
        holder.typeTv.setText(type.getType());
        holder.yzTv.setText(type.getYz() + "");
        holder.nowValueTv.setText(type.getNowValue() + "");

        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.serial_tv)
        TextView serialTv;
        @BindView(R.id.type_tv)
        TextView typeTv;
        @BindView(R.id.yz_tv)
        TextView yzTv;
        @BindView(R.id.nowValue_tv)
        TextView nowValueTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
