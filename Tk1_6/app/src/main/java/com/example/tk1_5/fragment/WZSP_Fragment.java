package com.example.tk1_5.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.tk1_5.R;
import com.example.tk1_5.activity.Z_PlayVideo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-16 at 08:32 ：）
 */
public class WZSP_Fragment extends Fragment {
    @BindView(R.id.iv_image1)
    ImageView ivImage1;
    @BindView(R.id.layout_1)
    LinearLayout layout1;
    @BindView(R.id.iv_image2)
    ImageView ivImage2;
    @BindView(R.id.layout_2)
    LinearLayout layout2;
    @BindView(R.id.iv_image3)
    ImageView ivImage3;
    @BindView(R.id.layout_3)
    LinearLayout layout3;
    @BindView(R.id.iv_image4)
    ImageView ivImage4;
    @BindView(R.id.layout_4)
    LinearLayout layout4;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wzsp_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Glide.with(getContext()).load(R.raw.car1).into(ivImage1);
        Glide.with(getContext()).load(R.raw.car2).into(ivImage2);
        Glide.with(getContext()).load(R.raw.car3).into(ivImage3);
        Glide.with(getContext()).load(R.raw.car4).into(ivImage4);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.layout_1, R.id.layout_2, R.id.layout_3, R.id.layout_4})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getContext(), Z_PlayVideo.class);
        switch (view.getId()) {
            case R.id.layout_1:
                intent.putExtra("Bh", 0);
                break;
            case R.id.layout_2:
                intent.putExtra("Bh", 1);
                break;
            case R.id.layout_3:
                intent.putExtra("Bh", 2);
                break;
            case R.id.layout_4:
                intent.putExtra("Bh", 3);
                break;
        }
        startActivity(intent);
    }
}
