package com.example.questionbank9_16.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

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


public class LightDialog extends DialogFragment {

    @BindView(R.id.red_et)
    EditText redEt;
    @BindView(R.id.yellow_et)
    EditText yellowEt;
    @BindView(R.id.green_et)
    EditText greenEt;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    private Unbinder unbinder;
    private String[] Lks;

    public LightDialog(String lk) {
        Lks = lk.split(",");
    }

    public interface UpData {
        void onUpData(int lx);
    }

    private UpData upData;

    public void setUpData(UpData upData) {
        this.upData = upData;
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.light_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.submit_btn, R.id.cancel_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                if (TextUtils.isEmpty(redEt.getText())) {
                    Toast.makeText(requireContext(), "周期不能为空", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(yellowEt.getText())) {
                    Toast.makeText(requireContext(), "黄灯不能为空", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(greenEt.getText())) {
                    Toast.makeText(requireContext(), "绿灯周期不能为空", Toast.LENGTH_SHORT).show();
                }
                for (int i = 0; i < Lks.length; i++) {
                    OkHttpTo okHttpTo = new OkHttpTo();
                    int finalI = i;
                    if (finalI == Lks.length - 1) {
                        okHttpTo.setDialog(requireContext());
                    }
                    okHttpTo.setUrl("set_traffic_light")
                            .setJsonObject("UserName", "user1")
                            .setJsonObject("number", Lks[i])
                            .setJsonObject("red", String.valueOf(redEt.getText()))
                            .setJsonObject("yellow", String.valueOf(yellowEt.getText()))
                            .setJsonObject("green", String.valueOf(greenEt.getText()))
                            .setOkHttpLo(new OkHttpLo() {
                                @Override
                                public void onFailure(IOException e) {
                                    upData.onUpData(2);
                                    dismiss();
                                    Toast.makeText(requireContext(), "设置失败", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    if (jsonObject.optString("RESULT").equals("S")) {
                                        Toast.makeText(requireContext(), "设置成功", Toast.LENGTH_SHORT).show();
                                        if (finalI == Lks.length - 1) {
                                            upData.onUpData(1);
                                            dismiss();
                                        }
                                    } else {
                                        Toast.makeText(requireContext(), "设置失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).start();
                }

                break;
            case R.id.cancel_btn:
                upData.onUpData(3);
                dismiss();
                break;
        }
    }
}
