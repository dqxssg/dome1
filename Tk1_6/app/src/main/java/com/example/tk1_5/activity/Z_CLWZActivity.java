package com.example.tk1_5.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.tk1_5.R;
import com.example.tk1_5.fragment.WZSP_Fragment;
import com.example.tk1_5.fragment.WZTP_Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-15 at 22:58 ：）
 */
public class Z_CLWZActivity extends BaseActivity {
    @BindView(R.id.tv_video)
    TextView tvVideo;
    @BindView(R.id.tv_photo)
    TextView tvPhoto;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;

    @Override
    public int setContentView() {
        return R.layout.clwz_layout;
    }

    @Override
    public String setTitle() {
        return "车辆违章";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        replaceFragment(new WZSP_Fragment());
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,fragment).commit();

    }

    @OnClick({R.id.tv_video, R.id.tv_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_video:
                tvVideo.setBackgroundResource(R.drawable.bg);
                tvPhoto.setBackgroundResource(R.drawable.bg_gray);
                replaceFragment(new WZSP_Fragment());
                break;
            case R.id.tv_photo:
                tvVideo.setBackgroundResource(R.drawable.bg_gray);
                tvPhoto.setBackgroundResource(R.drawable.bg);
                replaceFragment(new WZTP_Fragment());
                break;
        }
    }
}
