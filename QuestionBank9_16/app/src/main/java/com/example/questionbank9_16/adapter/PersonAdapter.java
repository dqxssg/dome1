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
import com.example.questionbank9_16.bean.Account;
import com.example.questionbank9_16.bean.Car;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PersonAdapter extends ArrayAdapter<Car> {
    public PersonAdapter(@NonNull Context context, @NonNull List<Car> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        Car car = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.person_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (car.getBrand()) {
            case "宝马":
                holder.brandIv.setImageResource(R.drawable.baoma);
                break;
            case "奥迪":
                holder.brandIv.setImageResource(R.drawable.audi);
                break;
            case "奔驰":
                holder.brandIv.setImageResource(R.drawable.benchi);
                break;
            case "中华":
                holder.brandIv.setImageResource(R.drawable.zhonghua);
                break;
        }
        holder.plateItem.setText(car.getPlate());
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.brand_iv)
        ImageView brandIv;
        @BindView(R.id.plate_item)
        TextView plateItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
