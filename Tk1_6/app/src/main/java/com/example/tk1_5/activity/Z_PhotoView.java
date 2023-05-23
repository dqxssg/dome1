package com.example.tk1_5.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.tk1_5.R;
import com.example.tk1_5.util.ImageListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-16 at 09:49 ：）
 */
public class Z_PhotoView extends AppCompatActivity {
    @BindView(R.id.image_view)
    ImageView imageView;
    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoview_layout);
        ButterKnife.bind(this);
        index = getIntent().getIntExtra("Bh", 0);
        switch (index) {
            case 0:
                imageView.setImageResource(R.mipmap.weizhang1);
                break;
            case 1:
                imageView.setImageResource(R.mipmap.weizhang02);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.weizhang01);
                break;
            case 3:
                imageView.setImageResource(R.mipmap.weizhang4);
                break;
        }
        imageView.setOnTouchListener(new ImageListener(imageView));

    }


}
