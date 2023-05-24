package com.example.questionbank17_24.dialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.bean.Record;
import com.example.questionbank17_24.net.OkHttpLo;
import com.example.questionbank17_24.net.OkHttpTo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @ClassName AccountDialog
 * @Author 史正龙
 * @date 2021.08.06 19:05
 */
public class AccountDialog extends DialogFragment {
    @BindView(R.id.car_id)
    TextView carId;
    @BindView(R.id.balance_et)
    EditText balanceEt;
    @BindView(R.id.recharge_dialog)
    Button rechargeDialog;
    @BindView(R.id.cancel_dialog)
    Button cancelDialog;
    private Unbinder unbinder;
    NetDialog dialog;
    private String Bh;
    private String[] Bhs, Jes, Cps;

    public interface UpDate {
        void upDate(int lx);
    }

    private UpDate upDate;

    public void setUpDate(UpDate upDate) {
        this.upDate = upDate;
    }

    public AccountDialog(String cp, String je, String bh) {
        Bh = bh;
        Bhs = bh.split(",");
        Cps = cp.split(",");
        Jes = je.split(",");
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (dialog != null) {
                dialog.dismiss();
            }
//            upDate.upDate(msg.what);
            dismiss();
            return false;
        }
    });

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view;
        view = inflater.inflate(R.layout.dialog_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        dialog = new NetDialog();
        carId.setText("车辆编号：" + Bh);
        balanceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (balanceEt.getText().toString().equals("0")) {
                        balanceEt.setText("");
                        Toast.makeText(requireContext(), "充值金额不能低于0元", Toast.LENGTH_SHORT).show();
                    }
                    if (Integer.parseInt(balanceEt.getText().toString()) > 999) {
                        balanceEt.setText("999");
                        balanceEt.setSelection(3);
                        Toast.makeText(requireContext(), "充值金额不能超过999元", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.recharge_dialog, R.id.cancel_dialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recharge_dialog:
                if (TextUtils.isEmpty(balanceEt.getText().toString())) {
                    Toast.makeText(requireContext(), "充值金额不能为空", Toast.LENGTH_SHORT).show();
                }
                for (int i = 0; i < Cps.length; i++) {
                    OkHttpTo okHttpTo = new OkHttpTo();
                    final int finalI = i;
                    okHttpTo.setUrl("set_balance")
                            .setJsonObject("UserName", "user1")
                            .setJsonObject("plate", Cps[i])
                            .setJsonObject("balance", balanceEt.getText())
                            .setOkHttpLo(new OkHttpLo() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    Record record = new Record();
                                    record.setPerson("史正龙");
                                    record.setBalance(Integer.parseInt(Jes[finalI]));
                                    record.setCzhBalance(Integer.parseInt(Jes[finalI]) + Integer.parseInt(balanceEt.getText().toString()));
                                    record.setId(Integer.parseInt(Bhs[finalI]));
                                    record.setTime(new SimpleDateFormat("yyyy.MM.dd HH:ss").format(new Date()));
                                    record.save();

                                    if (finalI == Cps.length - 1) {
                                        dialog.show(getChildFragmentManager(), "");
                                        handler.sendEmptyMessageDelayed(1, 1000);
                                        upDate.upDate(1);
                                    }
                                }

                                @Override
                                public void onFailure(IOException e) {
                                    dialog.show(getChildFragmentManager(), "");
                                    handler.sendEmptyMessageDelayed(2, 1000);
                                }
                            }).start();

                }
                break;
            case R.id.cancel_dialog:
                upDate.upDate(3);
                dismiss();
                break;
        }
    }
}
