package com.example.tk1_5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.VolleyError;
import com.example.tk1_5.AppClient;
import com.example.tk1_5.R;
import com.example.tk1_5.adapter.HJZBAdapter;
import com.example.tk1_5.bean.HJZB;
import com.example.tk1_5.net.VolleyLo;
import com.example.tk1_5.net.VolleyTo;
import com.example.tk1_5.util.MyUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-15 at 21:22 ：）
 */
public class Z_HJZBActivity extends BaseActivity {
    @BindView(R.id.gird_view)
    GridView girdView;
    private VolleyTo volleyTo;
    private AppClient appClient;
    private HJZB yz;
    private List<HJZB> hjzbs;
    private HJZBAdapter adapter;

    @Override
    public int setContentView() {
        return R.layout.hjzb_layout;
    }

    @Override
    public String setTitle() {
        return "环境指标";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        appClient = (AppClient) getApplication();
        hjzbs = appClient.getHjzbs();
        setVolley_Yz();
        girdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Z_HJZBActivity.this, Z_SSXSActivity.class);
                intent.putExtra("Bh", position);
                startActivity(intent);
            }
        });
    }

    private void setVolley_Yz() {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("get_threshold")
                .setJsonObject("UserName", "user1")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        yz = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").opt(0).toString(), HJZB.class);
                        setVolleyRoad();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();

    }

    private void setVolleyRoad() {
        volleyTo = new VolleyTo();
        volleyTo.setUrl("get_road_status")
                .setJsonObject("UserName", "user1")
                .setJsonObject("RoadId", "1")
                .setLoop(true)
                .setTime(3000)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        seyVolley_Sense(jsonObject.optJSONArray("ROWS_DETAIL").optJSONObject(0).optInt("state"));
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void seyVolley_Sense(final int state) {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("get_all_sense")
                .setJsonObject("UserName", "user1")
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        HJZB hjzb = new Gson().fromJson(jsonObject.toString(), HJZB.class);
                        hjzb.setPath(state);
                        hjzb.setTime(MyUtils.SimpDate("mm:ss",new Date()));
                        hjzbs.add(hjzb);
                        if (hjzbs.size() == 21) {
                            hjzbs.remove(0);
                        }
                        setGirdAdapter();
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    private void setGirdAdapter() {
        if (hjzbs.size() == 0) {
            return;
        }
        if (adapter == null) {
            adapter = new HJZBAdapter(this, hjzbs, yz);
            girdView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (volleyTo != null) {
//            volleyTo.setLoop(false);
//            volleyTo = null;
//        }
    }
}
