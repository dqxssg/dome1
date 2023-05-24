package com.example.questionbank9_16.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.bean.Light;

import java.util.List;

import butterknife.BindView;

public class LightAdapter extends ArrayAdapter<Light> {

    public LightAdapter(@NonNull Context context, @NonNull List<Light> objects) {
        super(context, 0, objects);
    }


    public interface MyClick {
        void onClick(int lx, int position, boolean xz);
    }

    private MyClick myClick;

    public void setMyClick(MyClick myClick) {
        this.myClick = myClick;
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
        Log.d(TAG, "getView: " + light.getRed());
        holder.roadTv.setText(light.getNumber() + "");
        holder.redTv.setText(light.getRed() + "");
        holder.yellowTv.setText(light.getYellow() + "");
        holder.greenTv.setText(light.getGreen() + "");
        holder.checkLight.setChecked(light.isXz());
        holder.checkLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myClick.onClick(1, position, isChecked);
            }
        });
        holder.setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClick.onClick(2, position, false);
            }
        });
        return convertView;
    }

    private static final String TAG = "LightAdapter";

    static
    class ViewHolder {
        @BindView(R.id.road_tv)
        TextView roadTv;
        @BindView(R.id.red_tv)
        TextView redTv;
        @BindView(R.id.yellow_tv)
        TextView yellowTv;
        @BindView(R.id.green_tv)
        TextView greenTv;
        @BindView(R.id.set_btn)
        Button setBtn;
        @BindView(R.id.check_light)
        CheckBox checkLight;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
