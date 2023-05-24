package com.example.questionbank17_24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.bean.Vehicle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName PersonAdapter
 * @Author 史正龙
 * @date 2021.08.05 22:12
 */
public class PersonAdapter extends ArrayAdapter<Vehicle> {
    public PersonAdapter(@NonNull Context context, @NonNull List<Vehicle> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.person_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Vehicle vehicle = getItem(position);
        holder.balanceTv.setText("余额：" + vehicle.getBalance() + "元");
        holder.plateTv.setText(vehicle.getPlate());
        switch (vehicle.getBrand()) {
            case "宝马":
                holder.brandIv.setImageResource(R.drawable.baoma);
                break;
            case "奔驰":
                holder.brandIv.setImageResource(R.drawable.benchi);
                break;
            case "奥迪":
                holder.brandIv.setImageResource(R.drawable.audi);
                break;
            case "中华":
                holder.brandIv.setImageResource(R.drawable.zhonghua);
                break;
        }
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.brand_iv)
        ImageView brandIv;
        @BindView(R.id.plate_tv)
        TextView plateTv;
        @BindView(R.id.balance_tv)
        TextView balanceTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
