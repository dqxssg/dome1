package com.example.questionbank9_16.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toolbar;


import com.example.questionbank9_16.R;
import com.example.questionbank9_16.fragment.PersonFragment;
import com.example.questionbank9_16.fragment.RecordFragment;
import com.example.questionbank9_16.fragment.ThresholdFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonActivity extends AppCompatActivity {


    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.person_tv)
    TextView personTv;
    @BindView(R.id.record_tv)
    TextView recordTv;
    @BindView(R.id.threshold_tv)
    TextView thresholdTv;
    @BindView(R.id.person_frame)
    FrameLayout personFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        titleTv.setText("个人中心");
        int type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            personTv.setBackgroundResource(R.drawable.line_textview);
            recordTv.setBackgroundResource(R.drawable.noline_textview);
            thresholdTv.setBackgroundResource(R.drawable.noline_textview);
            replaceFragment(new PersonFragment());
        } else if (type == 1) {
            replaceFragment(new RecordFragment());
            personTv.setBackgroundResource(R.drawable.noline_textview);
            recordTv.setBackgroundResource(R.drawable.line_textview);
            thresholdTv.setBackgroundResource(R.drawable.noline_textview);

        }
    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.person_frame, fragment).commit();
    }

    @OnClick({R.id.toolbar, R.id.person_tv, R.id.record_tv, R.id.threshold_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar:
                finish();
                break;
            case R.id.person_tv:
                replaceFragment(new PersonFragment());
                personTv.setBackgroundResource(R.drawable.line_textview);
                recordTv.setBackgroundResource(R.drawable.noline_textview);
                thresholdTv.setBackgroundResource(R.drawable.noline _textview);
                break;
            case R.id.record_tv:
                replaceFragment(new RecordFragment());
                personTv.setBackgroundResource(R.drawable.noline_textview);
                recordTv.setBackgroundResource(R.drawable.line_textview);
                thresholdTv.setBackgroundResource(R.drawable.noline_textview);
                break;
            case R.id.threshold_tv:
                replaceFragment(new ThresholdFragment());
                personTv.setBackgroundResource(R.drawable.noline_textview);
                recordTv.setBackgroundResource(R.drawable.noline_textview);
                thresholdTv.setBackgroundResource(R.drawable.line_textview);
                break;
        }
    }
}