package com.example.questionbank9_16.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.bean.Bus;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BusExpander extends BaseExpandableListAdapter {

    private Map<String, List<Bus>> map;
    private String[] arr = {"中医院站", "联想大厦站"};

    public BusExpander(Map<String, List<Bus>> map) {
        this.map = map;
    }

    @Override
    public int getGroupCount() {
        return map.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return map.get(arr[groupPosition]).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderFu holderFu;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_item_fu, parent, false);
            holderFu = new ViewHolderFu(convertView);
            convertView.setTag(holderFu);
        } else {
            holderFu = (ViewHolderFu) convertView.getTag();
        }
        if (isExpanded) {
            holderFu.arrowIv.setImageResource(R.drawable.jiantou1);
        } else {
            holderFu.arrowIv.setImageResource(R.drawable.jiantou2);

        }
        holderFu.stationTv.setText(arr[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderZi holderZi;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_item_zi, parent, false);
            holderZi = new ViewHolderZi(convertView);
            convertView.setTag(holderZi);
        } else {
            holderZi = (ViewHolderZi) convertView.getTag();
        }
        Bus bus = map.get(arr[groupPosition]).get(childPosition);
        holderZi.busInformation.setText(bus.getBus() + "号" + "(" + bus.getPerson() + "人" + ")");
        holderZi.busTime.setText((bus.getDistance() / 5 / 60) + "分钟到达");
        holderZi.busDistance.setText("距离站台" + bus.getDistance() + "米");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static
    class ViewHolderFu {
        @BindView(R.id.station_tv)
        TextView stationTv;
        @BindView(R.id.arrow_iv)
        ImageView arrowIv;

        ViewHolderFu(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static
    class ViewHolderZi {
        @BindView(R.id.bus_information)
        TextView busInformation;
        @BindView(R.id.bus_time)
        TextView busTime;
        @BindView(R.id.bus_distance)
        TextView busDistance;

        ViewHolderZi(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
