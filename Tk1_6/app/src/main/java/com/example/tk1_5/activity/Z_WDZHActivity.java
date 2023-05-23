package com.example.tk1_5.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.tk1_5.R;
import com.example.tk1_5.bean.CZJL;
import com.example.tk1_5.net.VolleyLo;
import com.example.tk1_5.net.VolleyTo;
import com.example.tk1_5.util.MyUtils;

import org.json.JSONObject;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-15 at 10:24 ：）
 */
public class Z_WDZHActivity extends BaseActivity {


    @BindView(R.id.tv_ye)
    TextView tvYe;
    @BindView(R.id.sp_ch)
    Spinner spCh;
    @BindView(R.id.et_add)
    EditText etAdd;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.bt_recharge)
    Button btRecharge;

    @Override
    public int setContentView() {
        return R.layout.wdzh_layout;
    }

    @Override
    public String setTitle() {
        return "我的账户";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        setVolley("1", true);
        etAdd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (Integer.parseInt(s.toString()) > 999) {
                        etAdd.setText("999");
                        etAdd.setSelection(3);
                        Toast.makeText(Z_WDZHActivity.this, "只能输入1～999", Toast.LENGTH_SHORT).show();
                    }
                    if (s.toString().equals("0")) {
                        etAdd.setText("");
                        Toast.makeText(Z_WDZHActivity.this, "不能输入0", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void setVolley(String s, boolean showDialog) {
        VolleyTo volleyTo = new VolleyTo();
        if (showDialog) {
            volleyTo.setDialog(this);
        }
        volleyTo.setUrl("get_balance_b")
                .setJsonObject("UserName", "user1")
                .setJsonObject("number", s)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        tvYe.setText(jsonObject.optInt("balance") + "元");
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }

    @OnClick({R.id.bt_query, R.id.bt_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_query:
                setVolley(spCh.getSelectedItem().toString(), true);
                break;
            case R.id.bt_recharge:
                if (TextUtils.isEmpty(etAdd.getText())) {
                    MyUtils.showDialog(this, "充值金额不能为空");
                    return;
                }
                VolleyTo volleyTo = new VolleyTo();
                volleyTo.setUrl("get_plate")
                        .setJsonObject("UserName", "user1")
                        .setJsonObject("number", spCh.getSelectedItem().toString())
                        .setVolleyLo(new VolleyLo() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                setBalance(jsonObject.optString("plate"));
                            }

                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                MyUtils.showDialog(Z_WDZHActivity.this, "充值失败");
                            }
                        }).start();
                break;
        }
    }

    private void setBalance(String plate) {
        VolleyTo volleyTo = new VolleyTo();
        volleyTo.setUrl("set_balance")
                .setJsonObject("UserName", "user1")
                .setJsonObject("plate", plate)
                .setJsonObject("balance", etAdd.getText().toString())
                .setDialog(this)
                .setVolleyLo(new VolleyLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        if (jsonObject.optString("RESULT").equals("S")) {
                            CZJL czjl = new CZJL(spCh.getSelectedItem().toString(), etAdd.getText().toString()
                                    , "user1", MyUtils.SimpDate("yyyy.MM.dd HH:mm", new Date()));
                            if (czjl.save()) {
                                MyUtils.showDialog(Z_WDZHActivity.this, "充值成功");
                                setVolley(spCh.getSelectedItem().toString(), false);
                                etAdd.setText("");
                                return;
                            }
                        }
                        MyUtils.showDialog(Z_WDZHActivity.this, "充值失败");
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }).start();
    }
}
