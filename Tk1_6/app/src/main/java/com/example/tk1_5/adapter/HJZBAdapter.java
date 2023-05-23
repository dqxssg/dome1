package com.example.tk1_5.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tk1_5.R;
import com.example.tk1_5.bean.HJZB;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-15 at 22:45 ：）
 */
public class HJZBAdapter extends ArrayAdapter<HJZB> {
    private HJZB yz;
    private int now, max;
    private List<HJZB> hjzbs;
    private String[] arr = {"温度", "湿度", "光照", "CO2", "PM2.5", "道路状态"};

    public HJZBAdapter(@NonNull Context context, @NonNull List<HJZB> objects, HJZB yz) {
        super(context, 0, objects);
        this.yz = yz;
        this.hjzbs = objects;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hjzb_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HJZB hjzb = hjzbs.get(hjzbs.size() - 1);
        switch (position) {
            case 0:
                now = hjzb.getTemperature();
                max = yz.getTemperature();
                break;
            case 1:
                now = hjzb.getHumidity();
                max = yz.getHumidity();
                break;
            case 2:
                now = hjzb.getIllumination();
                max = yz.getIllumination();
                break;
            case 3:
                now = hjzb.getCo2();
                max = yz.getCo2();
                break;
            case 4:
                now = hjzb.getPm25();
                max = yz.getPm25();
                break;
            case 5:
                now = hjzb.getPath();
                max = yz.getPath();
                break;
        }
        if (now > max) {
            holder.bgColor.setBackgroundColor(Color.RED);
        } else {
            holder.bgColor.setBackgroundColor(Color.GREEN);
        }
        holder.tvNum.setText(now + "");
        holder.tvTitle.setText(arr[position]);
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.bg_color)
        RelativeLayout bgColor;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
