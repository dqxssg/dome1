package com.example.questionbank9_16.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.questionbank9_16.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JKZP1Activity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.image4)
    ImageView image4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jkzp1);
        ButterKnife.bind(this);
        titleTv.setText("监控抓拍");

    }

    @OnClick({R.id.toolbar, R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.btn_cancel})
    public void onClick(View view) {
        Intent intent = new Intent(this, JKZP2Activity.class);
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.image1:
                intent.putExtra("bh", 0);
                break;
            case R.id.image2:
                intent.putExtra("bh", 1);
                break;
            case R.id.image3:
                intent.putExtra("bh", 2);
                break;
            case R.id.image4:
                intent.putExtra("bh", 3);
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
        startActivity(intent);
    }
}