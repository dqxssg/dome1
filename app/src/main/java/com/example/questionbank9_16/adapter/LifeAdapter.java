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
import com.example.questionbank9_16.bean.Sense;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LifeAdapter extends ArrayAdapter<Sense> {

    private List<Sense> senses;

    public LifeAdapter(@NonNull Context context, @NonNull List<Sense> objects) {
        super(context, 0, objects);
        senses = objects;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.life_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Sense sense = senses.get(senses.size() - 1);
        switch (position) {
            case 0:
                holder.lifeIv.setImageResource(R.drawable.zhiwaixianzhishu);
                if (sense.getIllumination() > 0 && sense.getIllumination() < 1000) {
                    holder.exponentTv.setText("弱" + "(" + sense.getIllumination() + ")");
                    holder.msgTv.setText("辐射较弱，涂擦SPF12~15、PA+护肤品");
                } else if (sense.getIllumination() <= 3000) {
                    holder.exponentTv.setText("中等" + "(" + sense.getIllumination() + ")");
                    holder.msgTv.setText("涂擦SPF大于15、PA+防晒护肤品");
                } else {
                    holder.exponentTv.setText("强" + "(" + sense.getIllumination() + ")");
                    holder.msgTv.setText("尽量减少外出，需要涂抹高倍数防晒霜");
                }
                break;
            case 1:
                holder.lifeIv.setImageResource(R.drawable.ganmaozhisu);
                if (sense.getTemperature() < 8) {
                    holder.exponentTv.setText("较易发" + "(" + sense.getTemperature() + ")");
                    holder.msgTv.setText("温度低，风较大，较易发生感冒，注意防护");
                } else {
                    holder.exponentTv.setText("少发" + "(" + sense.getTemperature() + ")");
                    holder.msgTv.setText("无明显降温，感冒机率较低");
                }
                break;
            case 2:
                holder.lifeIv.setImageResource(R.drawable.chuanyizhisu);
                if (sense.getTemperature() < 12) {
                    holder.exponentTv.setText("冷" + "(" + sense.getTemperature() + ")");
                    holder.msgTv.setText("温度低，风较大，较易发生感冒，注意防护");
                } else if (sense.getTemperature() >= 12 && sense.getTemperature() <= 21) {
                    holder.exponentTv.setText("舒适" + "(" + sense.getTemperature() + ")");
                    holder.msgTv.setText("建议穿短袖、单裤等服装");
                } else {
                    holder.exponentTv.setText("热" + "(" + sense.getTemperature() + ")");
                    holder.msgTv.setText("适合穿T恤、短薄外套等夏季服装");
                }
                break;
            case 3:
                holder.lifeIv.setImageResource(R.drawable.yundongzhisu);
                if (sense.getCo2() > 0 && sense.getCo2() < 3000) {
                    holder.exponentTv.setText("适宜" + "(" + sense.getCo2() + ")");
                    holder.msgTv.setText("气候适宜，推荐您进行户外运动");
                } else if (sense.getCo2() <= 3000) {
                    holder.exponentTv.setText("中" + "(" + sense.getCo2() + ")");
                    holder.msgTv.setText("易感人群应适当减少室外活动");
                } else {
                    holder.exponentTv.setText("较不宜" + "(" + sense.getCo2() + ")");
                    holder.msgTv.setText("空气氧气含量低，请在室内进行休闲运动");
                }
                break;
            case 4:
                holder.lifeIv.setImageResource(R.drawable.kongqiwurankuoanzhisu);
                if (sense.getPm25() > 0 && sense.getPm25() < 30) {
                    holder.exponentTv.setText("弱" + "(" + sense.getPm25() + ")");
                    holder.msgTv.setText("空气质量非常好，非常适合户外活动，趁机出去多呼吸新鲜空气");
                } else if (sense.getPm25() <= 100 && sense.getPm25() >= 30) {
                    holder.exponentTv.setText("良" + "(" + sense.getPm25() + ")");
                    holder.msgTv.setText("易感人群应适当减少室外活动");
                } else {
                    holder.exponentTv.setText("强" + "(" + sense.getPm25() + ")");
                    holder.msgTv.setText("空气质量差，不适合户外活动");
                }
                break;
        }
        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.life_iv)
        ImageView lifeIv;
        @BindView(R.id.exponent_tv)
        TextView exponentTv;
        @BindView(R.id.msg_tv)
        TextView msgTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
