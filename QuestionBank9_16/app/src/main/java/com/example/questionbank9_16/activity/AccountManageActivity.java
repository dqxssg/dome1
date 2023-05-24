package com.example.questionbank9_16.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.adapter.AccountAdapter;
import com.example.questionbank9_16.bean.Account;
import com.example.questionbank9_16.dialog.RechargeDialog;
import com.example.questionbank9_16.net.OkHttpLo;
import com.example.questionbank9_16.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountManageActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.account_list)
    ListView accountList;
    @BindView(R.id.czjl_tv)
    TextView czjlTv;
    @BindView(R.id.plcz_tv)
    TextView plczTv;
    private List<Account> accounts;
    AccountAdapter adapter;
    int yz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        ButterKnife.bind(this);
        accounts = new ArrayList<>();
        titleTv.setText("账户管理");
        getVehicle();
        getCarThreshold();
    }

    private void getCarThreshold() {
        new OkHttpTo().setUrl("get_car_threshold")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        yz = jsonObject.optJSONArray("ROWS_DETAIL").optJSONObject(0).optInt("threshold");
                    }
                }).start();
    }

    private void getVehicle() {
        new OkHttpTo()
                .setUrl("get_vehicle")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {
                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        accounts = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<Account>>() {
                        }.getType());
                        for (int i = 0; i < accounts.size(); i++) {
                            Account account = accounts.get(i);
                            account.save();
                        }

                        setAdapter();

                    }
                }).start();
    }

    private void setAdapter() {
        adapter = new AccountAdapter(this, accounts, yz);
        accountList.setAdapter(adapter);
        adapter.setMyClick(new AccountAdapter.MyClick() {
            @Override
            public void onClick(int lx, int position, boolean xz) {
                Account account = accounts.get(position);
                if (lx == 1) {
                    account.setXz(xz);
                    accounts.set(position, account);
                } else {
                    RechargeDialog dialog = new RechargeDialog(account.getPlate(), account.getBalance() + "");
                    dialog.show(getSupportFragmentManager(), "");
                    dialog.setUpdate(new RechargeDialog.Update() {
                        @Override
                        public void setUpdate(int lx) {
                            if (lx == 1) {
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(AccountManageActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                                getVehicle();
                            } else if (lx == 2) {
                                Toast.makeText(AccountManageActivity.this, "充值失败", Toast.LENGTH_SHORT).show();
                            } else if (lx == 3) {
                                for (int i = 0; i < accounts.size(); i++) {
                                    accounts.get(i).setXz(false);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        });
    }


    @OnClick({R.id.czjl_tv, R.id.plcz_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.czjl_tv:
                Intent intent = new Intent(this, PersonActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.plcz_tv:
                String Cph = "", Je = "";
                for (int i = 0; i < accounts.size(); i++) {
                    Account account = accounts.get(i);
                    if (account.isXz()) {
                        if ("".equals(Cph)) {
                            Cph = account.getPlate();
                            Je = account.getBalance() + "";
                        } else {
                            Cph += "," + account.getPlate();
                            Je += "," + account.getBalance();
                        }
                    }
                }
                if (TextUtils.isEmpty(Cph)) {
                    Toast.makeText(this, "您没有选择车辆", Toast.LENGTH_SHORT).show();
                    return;
                }
                    RechargeDialog dialog = new RechargeDialog(Cph, Je);
                    dialog.show(getSupportFragmentManager(), "");
                    dialog.setUpdate(new RechargeDialog.Update() {
                        @Override
                        public void setUpdate(int lx) {
                            if (lx == 1) {
                                Toast.makeText(AccountManageActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                                getVehicle();
                            } else if (lx == 2) {
                                Toast.makeText(AccountManageActivity.this, "充值失败", Toast.LENGTH_SHORT).show();
                            } else if (lx == 3) {
                                for (int i = 0; i < accounts.size(); i++) {
                                    accounts.get(i).setXz(false);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                break;
            case R.id.toolbar:
                finish();
                break;
        }
    }
}