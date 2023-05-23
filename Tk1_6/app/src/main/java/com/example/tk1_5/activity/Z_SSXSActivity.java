package com.example.tk1_5.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tk1_5.R;
import com.example.tk1_5.fragment.SSXS_Fragment1;
import com.example.tk1_5.fragment.SSXS_Fragment2;
import com.example.tk1_5.fragment.SSXS_Fragment5;
import com.example.tk1_5.fragment.SSXS_Fragment4;
import com.example.tk1_5.fragment.SSXS_Fragment3;
import com.example.tk1_5.fragment.SSXS_Fragment6;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-17 at 10:01 ：）
 */
public class Z_SSXSActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.linear_layout)
    LinearLayout linearLayout;
    private List<Fragment> fragments;
    private int index  = 0;

    @Override
    public int setContentView() {
        return R.layout.sszs_layout;
    }

    @Override
    public String setTitle() {
        return "实时显示";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        index =getIntent().getIntExtra("Bh",0);
        fragments = new ArrayList<>();
        fragments.add(new SSXS_Fragment1());
        fragments.add(new SSXS_Fragment2());
        fragments.add(new SSXS_Fragment3());
        fragments.add(new SSXS_Fragment4());
        fragments.add(new SSXS_Fragment5());
        fragments.add(new SSXS_Fragment6());
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    ImageView imageView = (ImageView) linearLayout.getChildAt(j);
                    if (j==i){
                        imageView.setImageResource(R.mipmap.page_indicator_focused);
                    }else{
                        imageView.setImageResource(R.mipmap.page_indicator_unfocused);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setCurrentItem(index);
        for (int i = 0; i < fragments.size(); i++) {
            ImageView imageView = new ImageView(this);
            if (i==index){
                imageView.setImageResource(R.mipmap.page_indicator_focused);
            }else{
                imageView.setImageResource(R.mipmap.page_indicator_unfocused);
            }

            imageView.setLayoutParams(new ViewGroup.LayoutParams(70,70));
            linearLayout.addView(imageView);
        }

    }


    class  MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
