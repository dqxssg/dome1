package com.example.questionbank9_16.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.activity.JKZP1Activity;
import com.example.questionbank9_16.bean.CLWZRight;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RightAdapter extends ArrayAdapter<CLWZRight> {
    public RightAdapter(@NonNull Context context, @NonNull List<CLWZRight> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CLWZRight right = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.right_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.dateTv.setText(right.getTime());
        holder.roadRight.setText(right.getRoad());
        holder.msgTv.setText(right.getMessage());
        holder.scoreTv.setText("扣分：" + right.getScore() + "\r分");
        holder.moneyTv.setText("罚款：" + right.getMoney() + "\r元");
        holder.lineWzxq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), JKZP1Activity.class);
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.date_tv)
        TextView dateTv;
        @BindView(R.id.road_right)
        TextView roadRight;
        @BindView(R.id.msg_tv)
        TextView msgTv;
        @BindView(R.id.score_tv)
        TextView scoreTv;
        @BindView(R.id.money_tv)
        TextView moneyTv;
        @BindView(R.id.line_wzxq)
        LinearLayout lineWzxq;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
