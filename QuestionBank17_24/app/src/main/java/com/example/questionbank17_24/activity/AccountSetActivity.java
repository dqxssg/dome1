package com.example.questionbank17_24.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.net.OkHttpLo;
import com.example.questionbank17_24.net.OkHttpTo;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountSetActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.threshold_tv)
    TextView thresholdTv;
    @BindView(R.id.threshold_et)
    EditText thresholdEt;
    @BindView(R.id.setting_btn)
    Button settingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_set);
        ButterKnife.bind(this);
        titleTv.setText("账户设置");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getThreshold();
    }

    private void getThreshold() {
        new OkHttpTo().setUrl("get_car_threshold")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        thresholdTv.setText(jsonObject.optJSONArray("ROWS_DETAIL").optJSONObject(0).optString("threshold"));
                    }

                    @Override
                    public void onFailure(IOException e) {

                    }
                }).start();
    }


    private void setThreshold() {
        new OkHttpTo().setUrl("set_car_threshold")
                .setJsonObject("UserName", "user1")
                .setJsonObject("threshold", String.valueOf(thresholdEt.getText()))
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        if (jsonObject.optString("RESULT").equals("S")) {
                            Toast.makeText(AccountSetActivity.this, "设置成功！", Toast.LENGTH_SHORT).show();
                            getThreshold();
                        } else {
                            Toast.makeText(AccountSetActivity.this, "设置失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(IOException e) {
                        Toast.makeText(AccountSetActivity.this, "设置失败", Toast.LENGTH_SHORT).show();

                    }
                }).start();
    }

    @OnClick({R.id.setting_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_btn:
                if (TextUtils.isEmpty(thresholdEt.getText().toString())) {
                    Toast.makeText(this, "输入值不能为空", Toast.LENGTH_SHORT).show();
                }
                setThreshold();
                break;
        }
    }
}