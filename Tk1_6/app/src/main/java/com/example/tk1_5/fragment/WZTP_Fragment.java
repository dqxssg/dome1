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

import com.example.tk1_5.R;
import com.example.tk1_5.activity.Z_PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-16 at 09:44 ：）
 */
public class WZTP_Fragment extends Fragment {
    @BindView(R.id.image_1)
    ImageView image1;
    @BindView(R.id.image_2)
    ImageView image2;
    @BindView(R.id.image_3)
    ImageView image3;
    @BindView(R.id.image_4)
    ImageView image4;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wztp_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.image_1, R.id.image_2, R.id.image_3, R.id.image_4})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getContext(), Z_PhotoView.class);
        switch (view.getId()) {
            case R.id.image_1:
                intent.putExtra("Bh",0);
                break;
            case R.id.image_2:
                intent.putExtra("Bh",1);
                break;
            case R.id.image_3:
                intent.putExtra("Bh",2);
                break;
            case R.id.image_4:
                intent.putExtra("Bh",3);
                break;
        }
        startActivity(intent);
    }
}
