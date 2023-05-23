package com.example.tk1_5.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tk1_5.R;
import com.example.tk1_5.bean.HLD;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-15 at 20:51 ：）
 */
public class HLDAdapter extends ArrayAdapter<HLD> {
    public HLDAdapter(@NonNull Context context, @NonNull List<HLD> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hld_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        HLD hld = getItem(position);
        holder.tvRoad.setText(hld.getNumber()+"");
        holder.tvRed.setText(hld.getRed()+"");
        holder.tvGreen.setText(hld.getGreen()+"");
        holder.tvYellow.setText(hld.getYellow()+"");
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.tv_road)
        TextView tvRoad;
        @BindView(R.id.tv_red)
        TextView tvRed;
        @BindView(R.id.tv_yellow)
        TextView tvYellow;
        @BindView(R.id.tv_green)
        TextView tvGreen;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
