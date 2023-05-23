package com.example.tk1_5.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.VolleyError;
import com.example.tk1_5.R;
import com.example.tk1_5.adapter.HLDAdapter;
import com.example.tk1_5.bean.HLD;
import com.example.tk1_5.net.VolleyLo;
import com.example.tk1_5.net.VolleyTo;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-15 at 18:42 ：）
 */
public class Z_HLDGLActivity extends BaseActivity {
    @BindView(R.id.my_spinner)
    Spinner mySpinner;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.my_list)
    ListView myList;
    private List<HLD> hlds;
    private HLDAdapter adapter;

    @Override
    public int setContentView() {
        return R.layout.hld_layout;
    }

    @Override
    public String setTitle() {
        return "红绿灯管理";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        setDate();

    }

    private void setDate() {
        if (hlds == null) {
            hlds = new ArrayList<>();
        } else {
            hlds.clear();
        }
        for (int i = 1; i <= 5; i++) {
            setVolley(i);
        }
    }

    private void setVolley(int i) {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("get_traffic_light")
                .setJsonObject("UserName", "user1")
                .setJsonObject("number", i)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        hlds.add(new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").optJSONObject(0).toString(), HLD.class));
                        if (hlds.size() == 5) {
                            setSortList();
                            setListView();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void setListView() {
        if (adapter == null) {
            adapter = new HLDAdapter(this, hlds);
            myList.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void setSortList(){
        Collections.sort(hlds, new Comparator<HLD>() {
            @Override
            public int compare(HLD o1, HLD o2) {
                switch ((int) mySpinner.getSelectedItemId()) {
                    case 0:
                        return o1.getNumber() - o2.getNumber();
                    case 1:
                        return o2.getNumber() - o1.getNumber();
                    case 2:
                        return o1.getRed() - o2.getRed();
                    case 3:
                        return o2.getRed() - o1.getRed();
                    case 4:
                        return o1.getGreen() - o2.getGreen();
                    case 5:
                        return o2.getGreen() - o1.getGreen();
                    case 6:
                        return o1.getYellow() - o2.getYellow();
                    case 7:
                        return o2.getYellow() - o1.getYellow();
                    default:
                        return 0;
                }
            }
        });
    }

    @OnClick(R.id.bt_query)
    public void onViewClicked() {
        setSortList();
        adapter.notifyDataSetChanged();
    }
}


