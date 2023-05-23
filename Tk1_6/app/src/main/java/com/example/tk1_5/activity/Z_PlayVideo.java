package com.example.tk1_5.activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.VideoView;

import com.example.tk1_5.R;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-16 at 09:04 ：）
 */
public class Z_PlayVideo extends AppCompatActivity {
    private int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyVideo myVideo = new MyVideo(this);
        setContentView(myVideo);

    }

    class MyVideo extends VideoView {

        public MyVideo(Context context) {
            super(context);
            initView();
        }

        public MyVideo(Context context, AttributeSet attrs) {
            super(context, attrs);
            initView();
        }

        public MyVideo(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initView();
        }

        private void initView() {
            index = getIntent().getIntExtra("Bh", 0);
            switch (index) {
                case 0:
                    this.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.car1));
                    break;
                case 1:
                    this.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.car2));
                    break;
                case 2:
                    this.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.car3));
                    break;
                case 3:
                    this.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.car4));
                    break;
            }
            start();
            setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    finish();
                }
            });
        }
    }

}
