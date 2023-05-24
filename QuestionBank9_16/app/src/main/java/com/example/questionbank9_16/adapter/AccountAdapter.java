package com.example.questionbank9_16.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.questionbank9_16.R;
import com.example.questionbank9_16.bean.Account;

import java.util.List;

import butterknife.BindView;

public class AccountAdapter extends ArrayAdapter<Account> {
    private int yz;

    public AccountAdapter(@NonNull Context context, @NonNull List<Account> objects, int yz) {
        super(context, 0, objects);
        this.yz = yz;
    }


    public interface MyClick {
        void onClick(int lx, int position, boolean xz);
    }

    private MyClick myClick;

    public void setMyClick(MyClick myClick) {
        this.myClick = myClick;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.account_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Account account = getItem(position);
        holder.accountId.setText(account.getNumber() + "");
        switch (account.getBrand()) {
            case "宝马":
                holder.carIv.setImageResource(R.drawable.baoma);
                break;
            case "奥迪":
                holder.carIv.setImageResource(R.drawable.audi);
                break;
            case "奔驰":
                holder.carIv.setImageResource(R.drawable.benchi);
                break;
            case "中华":
                holder.carIv.setImageResource(R.drawable.zhonghua);
                break;
        }
        holder.balanceTv.setText("余额：" + account.getBalance());
        holder.ownerTv.setText("车主：" + account.getOwner());
        holder.plateTv.setText(account.getPlate());
        if (account.getBalance() < yz) {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffcc00"));
        } else {
            holder.linearLayout.setBackgroundColor(Color.WHITE);
        }
        holder.accountCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myClick.onClick(1, position, isChecked);
            }
        });
        holder.rechargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClick.onClick(2, position, false);
            }
        });
        return convertView;
    }


    static
    class ViewHolder {
        @BindView(R.id.account_id)
        TextView accountId;
        @BindView(R.id.car_iv)
        ImageView carIv;
        @BindView(R.id.plate_tv)
        TextView plateTv;
        @BindView(R.id.owner_tv)
        TextView ownerTv;
        @BindView(R.id.balance_tv)
        TextView balanceTv;
        @BindView(R.id.account_check)
        CheckBox accountCheck;
        @BindView(R.id.recharge_btn)
        Button rechargeBtn;
        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
