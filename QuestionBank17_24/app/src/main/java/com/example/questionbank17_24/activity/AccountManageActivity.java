package com.example.questionbank17_24.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.adapter.AccountAdapter;
import com.example.questionbank17_24.bean.Vehicle;
import com.example.questionbank17_24.dialog.AccountDialog;
import com.example.questionbank17_24.net.OkHttpLo;
import com.example.questionbank17_24.net.OkHttpTo;
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
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.account_list)
    ListView accountList;
    @BindView(R.id.czjl_tv)
    TextView czjlTv;
    @BindView(R.id.plcz_tv)
    TextView plczTv;
    private List<Vehicle> vehicles;
    private AccountAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        ButterKnife.bind(this);
        titleTv.setText("账户管理");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getVehicle();
    }

    private void getVehicle() {
        if (vehicles == null) {
            vehicles = new ArrayList<>();
        }
        OkHttpTo okHttpTo = new OkHttpTo();
        okHttpTo.setUrl("get_vehicle")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        vehicles = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(), new TypeToken<List<Vehicle>>() {
                        }.getType());

                            setAdapter();

                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }

    private void setAdapter() {
            adapter = new AccountAdapter(AccountManageActivity.this, vehicles);
            accountList.setAdapter(adapter);
            adapter.setMyClick(new AccountAdapter.MyClick() {
                @Override
                public void onClick(int fx, int position, boolean xz) {
                    Vehicle vehicle = vehicles.get(position);
                    if (fx == 1) {
                        AccountDialog dialog = new AccountDialog(vehicle.getPlate(), vehicle.getBalance() + "", vehicle.getNumber() + "");
                        dialog.show(getSupportFragmentManager(), "");
                        dialog.setUpDate(new AccountDialog.UpDate() {
                            @Override
                            public void upDate(int lx) {
                                if (lx == 1) {
                                    getVehicle();
                                    Toast.makeText(AccountManageActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                                } else if (lx == 2) {
                                    Toast.makeText(AccountManageActivity.this, "充值失败", Toast.LENGTH_SHORT).show();
                                } else if (lx == 3) {
                                    for (int i = 0; i < vehicles.size(); i++) {
                                        vehicles.get(i).setXz(false);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    } else {
                        vehicle.setXz(xz);
                        vehicles.set(position, vehicle);
                    }
                }
            });

    }

    @OnClick({R.id.czjl_tv, R.id.plcz_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.czjl_tv:
                break;
            case R.id.plcz_tv:
                String Cp = "", Je = "", Bh = "";
                for (int i = 0; i < vehicles.size(); i++) {
                    Vehicle vehicle = vehicles.get(i);
                    if (vehicle.isXz()) {
                        if ("".equals(Cp)) {
                            Cp = vehicle.getPlate();
                            Je = vehicle.getBalance() + "";
                            Bh = vehicle.getNumber() + "";
                        } else {
                            Cp += "," + vehicle.getPlate();
                            Je += "," + vehicle.getBalance();
                            Bh += "," + vehicle.getNumber();
                        }
                    }
                }
                if (TextUtils.isEmpty(Cp)) {
                    Toast.makeText(this, "您还没有选择车辆", Toast.LENGTH_SHORT).show();
                } else {
                    AccountDialog dialog = new AccountDialog(Cp, Je, Bh);
                    dialog.show(getSupportFragmentManager(), "");
                    dialog.setUpDate(new AccountDialog.UpDate() {
                        @Override
                        public void upDate(int lx) {
                            if (lx == 1) {
                                Toast.makeText(AccountManageActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                                getVehicle();
                            } else if (lx == 2) {
                                Toast.makeText(AccountManageActivity.this, "充值失败", Toast.LENGTH_SHORT).show();
                            } else if (lx == 3) {
                                for (int i = 0; i < vehicles.size(); i++) {
                                    vehicles.get(i).setXz(false);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                break;
        }
    }
}