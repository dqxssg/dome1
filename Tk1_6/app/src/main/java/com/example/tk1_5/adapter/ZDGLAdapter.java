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
import com.example.tk1_5.bean.CZJL;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-15 at 21:11 ：）
 */
public class ZDGLAdapter extends ArrayAdapter<CZJL> {
    public ZDGLAdapter(@NonNull Context context, @NonNull List<CZJL> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.czjl_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CZJL czjl = getItem(position);
        holder.tvXh.setText(position + 1 + "");
        holder.tvCh.setText(czjl.getCh());
        holder.tvJe.setText(czjl.getJe());
        holder.tvName.setText(czjl.getUser());
        holder.tvTime.setText(czjl.getTime());
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.tv_xh)
        TextView tvXh;
        @BindView(R.id.tv_ch)
        TextView tvCh;
        @BindView(R.id.tv_je)
        TextView tvJe;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
