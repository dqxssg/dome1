package com.example.questionbank9_16.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.net.OkHttpLo;
import com.example.questionbank9_16.net.OkHttpTo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VehicleViolationActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.plate_et)
    EditText plateEt;
    @BindView(R.id.btn_query)
    Button btnQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_violation);
        ButterKnife.bind(this);
        titleTv.setText("车辆违章");
    }

    private void getPeccancy(String cp) {
        new OkHttpTo()
                .setUrl("get_peccancy_plate")
                .setJsonObject("UserName", "user1")
                .setJsonObject("plate", cp)
                .setOkHttpLo(ew OkHttpLo(n) {
                    @Override
                    public void onFailure(IOException e) {
                        Toast.makeText(VehicleViolationActivity.this, "没有查询到" + cp + "车的违章数据！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray("id");
                        if (jsonArray.length() == 0) {
                            Toast.makeText(VehicleViolationActivity.this, "没有查询到" + cp + "车的违章数据！", Toast.LENGTH_SHORT).show();
                        } else {
                            String[] ids = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                ids[i] = jsonArray.optString(i);
                            }
                            Intent intent = new Intent(VehicleViolationActivity.this, ResultActivity.class);
                            intent.putExtra("id", ids);
                            intent.putExtra("plate", cp);
                            startActivity(intent);
                        }

                    }
                }).start();
    }

    @OnClick({R.id.toolbar, R.id.btn_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.btn_query:
                if (TextUtils.isEmpty(plateEt.getText().toString())) {
                    Toast.makeText(this, "请输入车牌号", Toast.LENGTH_SHORT).show();
                }
                final String cp = "鲁" + plateEt.getText().toString().toUpperCase();
                getPeccancy(cp);
                break;
        }
    }
}