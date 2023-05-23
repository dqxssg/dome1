package com.example.tk1_5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tk1_5.AppClient;
import com.example.tk1_5.R;
import com.example.tk1_5.util.MyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-17 at 16:08 ：）
 */
public class Z_LoginActivity extends AppCompatActivity {
    @BindView(R.id.change)
    ImageView change;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.bt_log)
    Button btLog;
    private AppClient appClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);
        title.setText("登录");
        appClient = (AppClient) getApplication();
    }

    @OnClick({R.id.change, R.id.bt_log})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change:
                finish();
                break;
            case R.id.bt_log:
                String username = etUser.getText().toString();
                if (TextUtils.isEmpty(username)){
                    MyUtils.showDialog(this,"用户名不能为空");
                    return;
                }
                appClient.setUserName(username);
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
        }
    }
}
