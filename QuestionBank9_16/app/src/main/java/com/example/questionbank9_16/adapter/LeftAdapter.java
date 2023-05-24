package com.example.questionbank9_16.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.bean.CLWZLeft;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LeftAdapter extends ArrayAdapter<CLWZLeft> {
    public LeftAdapter(@NonNull Context context, @NonNull List<CLWZLeft> objects) {
        super(context, 0, objects);
    }

        public interface MyClick {
            void onClick(int position);
        }

        private MyClick myClick;

        public void setMyClick(MyClick myClick) {
            this.myClick = myClick;
        }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CLWZLeft left = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.left_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.leftPlate.setText(left.getCp());
        holder.frequencyTv.setText("未处理违章\r\r" + left.getCs() + "次");
        holder.scoreLeft.setText("扣\r" + left.getKf() + "分");
        holder.moneyLeft.setText("罚款\r" + left.getFk() + "元");
        holder.removeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClick.onClick(position);
            }
        });
        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.left_plate)
        TextView leftPlate;
        @BindView(R.id.frequency_tv)
        TextView frequencyTv;
        @BindView(R.id.remove_iv)
        ImageView removeIv;
        @BindView(R.id.score_left)
        TextView scoreLeft;
        @BindView(R.id.money_left)
        TextView moneyLeft;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
