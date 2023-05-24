package com.example.questionbank9_16.dialog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.bean.Account;
import com.example.questionbank9_16.bean.Record;
import com.example.questionbank9_16.net.OkHttpLo;
import com.example.questionbank9_16.net.OkHttpTo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @ClassName RechargeDialog
 * @Author 史正龙
 * @date 2021.08.01 21:56
 */
public class RechargeDialog extends DialogFragment {
    @BindView(R.id.tv_plate)
    TextView tvPlate;
    @BindView(R.id.money_et)
    EditText moneyEt;
    @BindView(R.id.btn_recharge)
    Button btnRecharge;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;
    String Cph;
    private String[] Cps, Jes;
    private List<Account> accounts;

    public RechargeDialog(String cph, String je) {
        Cph = cph;
        Cps = cph.split(",");
        Jes = je.split(",");
    }

    public interface Update {
        void setUpdate(int lx);
    }

    Unbinder unbinder;
    private Update update;

    public void setUpdate(Update update) {
        this.update = update;
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.recharge_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        accounts = LitePal.findAll(Account.class);
        tvPlate.setText(Cph);
        moneyEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (moneyEt.toString().equals("0")) {
                        moneyEt.setText("");
                        Toast.makeText(requireContext(), "充值金额不能为0", Toast.LENGTH_SHORT).show();
                    }
                    if (Integer.parseInt(s.toString()) > 999) {
                        moneyEt.setText("999");
                        moneyEt.setSelection(3);
                        Toast.makeText(requireContext(), "充值金额不能超过999元！", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_recharge, R.id.cancel_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_recharge:
                if (TextUtils.isEmpty(moneyEt.getText().toString())) {
                    Toast.makeText(requireContext(), "充值金额不能为空", Toast.LENGTH_SHORT).show();
                }
                for (int i = 0; i < Cps.length; i++) {
                    OkHttpTo okHttpTo = new OkHttpTo();
                    int finalI = i;
                    if (finalI == Cps.length - 1) {
                        okHttpTo.setDialog(getContext());
                    }
                    okHttpTo.setUrl("set_balance")
                            .setJsonObject("UserName", "user1")
                            .setJsonObject("plate", Cps[i])
                            .setJsonObject("balance", moneyEt.getText().toString())
                            .setOkHttpLo(new OkHttpLo() {
                                @Override
                                public void onFailure(IOException e) {
                                    Toast.makeText(requireContext(), "充值失败", Toast.LENGTH_SHORT).show();
                                    update.setUpdate(2);
                                    dismiss();

                                }

                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    if (jsonObject.optString("RESULT").equals("S")) {
                                        Toast.makeText(requireContext(), "充值成功", Toast.LENGTH_SHORT).show();
                                        Record record = new Record();
                                        record.setOwner(accounts.get(0).getOwner());
                                        record.setCzqMoney(moneyEt.getText().toString());
                                        record.setCzhMoney(Integer.parseInt(moneyEt.getText().toString()) + Integer.parseInt(Jes[finalI]));
                                        record.setPlate(Cps[finalI]);
                                        record.setTime(new SimpleDateFormat("yyyy.MM.dd HH:ss E").format(new Date()));
                                        record.save();
                                        if (finalI == Cps.length - 1) {
                                            update.setUpdate(1);
                                            dismiss();
                                        }
                                    } else {
                                        Toast.makeText(requireContext(), "充值失败", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }).start();
                }

                break;
            case R.id.cancel_btn:
                update.setUpdate(3);
                dismiss();
                break;
        }
    }
}
