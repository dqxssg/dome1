package com.example.tk1_5.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tk1_5.R;
import com.example.tk1_5.adapter.ZDGLAdapter;
import com.example.tk1_5.bean.CZJL;

import org.litepal.LitePal;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @LogIn Name zhangyingyu
 * @Create by 张瀛煜 on 2020-07-15 at 21:06 ：）
 */
public class Z_ZDGLActivity extends BaseActivity {
    @BindView(R.id.my_spinner)
    Spinner mySpinner;
    @BindView(R.id.bt_query)
    Button btQuery;
    @BindView(R.id.my_list)
    ListView myList;
    @BindView(R.id.tv_no_history)
    TextView tvNoHistory;
    private List<CZJL> czjls;
    private ZDGLAdapter adapter;

    @Override
    public int setContentView() {
        return R.layout.zdgl_layout;
    }

    @Override
    public String setTitle() {
        return "账单管理";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        czjls = LitePal.findAll(CZJL.class);
        setSortList();
        adapter = new ZDGLAdapter(this, czjls);
        myList.setAdapter(adapter);
        myList.setEmptyView(tvNoHistory);
    }

    private void setSortList() {
        Collections.sort(czjls, new Comparator<CZJL>() {
            @Override
            public int compare(CZJL o1, CZJL o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                Date date = null, date1 = null;
                try {
                    date = format.parse(o1.getTime());
                    date1 = format.parse(o2.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (mySpinner.getSelectedItemId() == 0) {
                    return date1.compareTo(date);
                } else {
                    return date.compareTo(date1);
                }
            }
        });
    }

    @OnClick(R.id.bt_query)
    public void onViewClicked() {
        setSortList();
        adapter.notifyDataSetChanged();

    }
}
