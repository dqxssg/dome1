package com.example.questionbank9_16.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.net.OkHttpLo;
import com.example.questionbank9_16.net.OkHttpTo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ThresholdFragment extends Fragment {
    @BindView(R.id.threshold_et)
    EditText thresholdEt;
    @BindView(R.id.setting_btn)
    Button settingBtn;
    @BindView(R.id.current_tv)
    TextView currentTv;
    private Unbinder unbinder;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.threshold_fragmnet, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getCarThreshold();
    }

    private void getCarThreshold() {
        new OkHttpTo().setUrl("get_car_threshold")
                .setJsonObject("UserName", "user1")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        currentTv.setText("当前1-4号小车的余额告警阈值为 " + jsonObject.optJSONArray("ROWS_DETAIL").optJSONObject(0).optInt("threshold") + "元");
                    }
                }).start();
    }

    private void setCarThreshold() {
        new OkHttpTo().setUrl("set_car_threshold")
                .setJsonObject("UserName", "user1")
                .setJsonObject("threshold", thresholdEt.getText())
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onFailure(IOException e) {
                        Toast.makeText(requireContext(), "设置失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        if (jsonObject.optString("RESULT").equals("S")) {
                            Toast.makeText(requireContext(), "设置成功", Toast.LENGTH_SHORT).show();
                            getCarThreshold();
                        } else {
                            Toast.makeText(requireContext(), "设置失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.setting_btn)
    public void onClick() {
        if (TextUtils.isEmpty(thresholdEt.getText())) {
            Toast.makeText(requireContext(), "值不能为空", Toast.LENGTH_SHORT).show();
        }
        setCarThreshold();
    }
}
