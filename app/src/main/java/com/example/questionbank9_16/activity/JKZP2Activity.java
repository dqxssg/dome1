package com.example.questionbank9_16.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.util.ImageListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JKZP2Activity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_jkzp)
    ImageView imageJkzp;
    @BindView(R.id.jkzp_cancel)
    Button jkzpCancel;
    int[] images = {R.drawable.weizhang5, R.drawable.weizhang6, R.drawable.weizhang7, R.drawable.weizhang8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jkzp2);
        ButterKnife.bind(this);
        int bh = getIntent().getIntExtra("bh", 0);
        imageJkzp.setImageResource(images[bh]);
        imageJkzp.setOnTouchListener(new ImageListener(imageJkzp));
    }

    @OnClick({R.id.toolbar, R.id.jkzp_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.jkzp_cancel:
                finish();
                break;
        }
    }
}