package com.example.questionbank17_24.adapter;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.bean.Sense;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName LifeAdapter
 * @Author 史正龙
 * @date 2021.08.05 15:26
 */
public class LifeAdapter extends BaseAdapter {

    private List<Sense> senses;
    private Sense yz;

    public LifeAdapter(List<Sense> senses, Sense yz) {
        this.senses = senses;
        this.yz = yz;
    }


    //第二种
    private int index, nowValue;

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.life_item, parent, false);
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
                    holder.lifeTv.setText("弱" + "(" + sense.getIllumination() + ")");
                    holder.lifeMsg.setText("辐射较弱，涂擦SPF12~15、PA+护肤品");
                } else if (sense.getIllumination() > 3000) {
                    holder.lifeTv.setText("强" + "(" + sense.getIllumination() + ")");
                    holder.lifeMsg.setText("尽量减少外出，需要涂抹高倍数防晒霜");
                } else {
                    holder.lifeTv.setText("中等" + "(" + sense.getIllumination() + ")");
                    holder.lifeMsg.setText("涂擦SPF大于15、PA+防晒护肤品");
                }

                if (sense.getIllumination() > yz.getIllumination()) {
                    holder.lifeLine.setBackgroundColor(Color.RED);
                } else {
                    holder.lifeLine.setBackgroundColor(Color.parseColor("#daedff"));
                }
//                index = sense.getIllumination();
//                nowValue = yz.getIllumination();
                break;
            case 1:
                holder.lifeIv.setImageResource(R.drawable.ganmaozhisu);
                if (sense.getTemperature() < 8) {
                    holder.lifeTv.setText("较易发" + "(" + sense.getTemperature() + ")");
                    holder.lifeMsg.setText("温度低，风较大，较易发生感冒，注意防护");
                } else {
                    holder.lifeTv.setText("少发" + "(" + sense.getTemperature() + ")");
                    holder.lifeMsg.setText("无明显降温，感冒机率较低");
                }
                if (sense.getTemperature() > yz.getTemperature()) {
                    holder.lifeLine.setBackgroundColor(Color.RED);
                } else {
                    holder.lifeLine.setBackgroundColor(Color.parseColor("#daedff"));
                }
//                index = sense.getTemperature();
//                nowValue = yz.getTemperature();
                break;
            case 2:
                holder.lifeIv.setImageResource(R.drawable.chuanyizhisu);
                if (sense.getTemperature() < 12) {
                    holder.lifeTv.setText("冷" + "(" + sense.getTemperature() + ")");
                    holder.lifeMsg.setText("建议穿长衬衫、单裤等服装");
                } else if (sense.getTemperature() > 21) {
                    holder.lifeTv.setText("热" + "(" + sense.getTemperature() + ")");
                    holder.lifeMsg.setText("适合穿T恤、短薄外套等夏季服装");
                } else {
                    holder.lifeTv.setText("舒适" + "(" + sense.getTemperature() + ")");
                    holder.lifeMsg.setText("建议穿短袖衬衫、单裤等服装");
                }
                if (sense.getTemperature() > yz.getTemperature()) {
                    holder.lifeLine.setBackgroundColor(Color.RED);
                } else {
                    holder.lifeLine.setBackgroundColor(Color.parseColor("#daedff"));
                }
//                index = sense.getTemperature();
//                nowValue = yz.getTemperature();
                break;
            case 3:
                holder.lifeIv.setImageResource(R.drawable.yundongzhisu);
                if (sense.getCo2() > 0 && sense.getCo2() < 3000) {
                    holder.lifeTv.setText("适宜" + "(" + sense.getCo2() + ")");
                    holder.lifeMsg.setText("气候适宜，推荐您进行户外运动");
                } else if (sense.getCo2() < 6000) {
                    holder.lifeTv.setText("较不宜" + "(" + sense.getCo2() + ")");
                    holder.lifeMsg.setText("空气氧气含量低，请在室内进行休闲运动");
                } else {
                    holder.lifeTv.setText("中" + "(" + sense.getCo2() + ")");
                    holder.lifeMsg.setText("易感人群应适当减少室外活动");
                }
                if (sense.getCo2() > yz.getCo2()) {
                    holder.lifeLine.setBackgroundColor(Color.RED);
                } else {
                    holder.lifeLine.setBackgroundColor(Color.parseColor("#daedff"));
                }

//                index = sense.getCo2();
//                nowValue = yz.getCo2();
                break;
            case 4:
                holder.lifeIv.setImageResource(R.drawable.kongqiwurankuoanzhisu);
                if (sense.getPm25() > 0 && sense.getPm25() < 30) {
                    holder.lifeTv.setText("优" + "(" + sense.getPm25() + ")");
                    holder.lifeMsg.setText("空气质量非常好，非常适合户外活动，趁机出去多呼吸新鲜空气");
                } else if (sense.getPm25() > 100) {
                    holder.lifeTv.setText("污染" + "(" + sense.getPm25() + ")");
                    holder.lifeMsg.setText("空气质量差，不适合户外活动");
                } else {
                    holder.lifeTv.setText("良" + "(" + sense.getPm25() + ")");
                    holder.lifeMsg.setText("易感人群应适当减少室外活动");
                }
                if (sense.getPm25() > yz.getPm25()) {
                    holder.lifeLine.setBackgroundColor(Color.RED);
                } else {
                    holder.lifeLine.setBackgroundColor(Color.parseColor("#daedff"));
                }
//                index = sense.getPm25();
//                nowValue = yz.getPm25();
                break;
        }
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.life_iv)
        ImageView lifeIv;
        @BindView(R.id.life_tv)
        TextView lifeTv;
        @BindView(R.id.life_msg)
        TextView lifeMsg;
        @BindView(R.id.life_line)
        LinearLayout lifeLine;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
