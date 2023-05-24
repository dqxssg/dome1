package com.example.questionbank17_24.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.questionbank17_24.R;
import com.example.questionbank17_24.bean.Vehicle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName AccountAdapter
 * @Author 史正龙
 * @date 2021.08.06 18:47
 */
public class AccountAdapter extends ArrayAdapter<Vehicle> {
    public interface MyClick {
        void onClick(int fx, int position, boolean xz);
    }

    private MyClick myClick;

    public void setMyClick(MyClick myClick) {
        this.myClick = myClick;
    }

    public AccountAdapter(@NonNull Context context, @NonNull List<Vehicle> objects) {
        super(context, 0, objects);
    }

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
        Vehicle vehicle = getItem(position);
        holder.accountId.setText(vehicle.getNumber() + "");
        holder.balanceAccount.setText(vehicle.getBalance() + "元");
        holder.accountBox.setChecked(vehicle.isXz());
        holder.rechargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClick.onClick(1, position, false);
            }
        });
        holder.accountBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myClick.onClick(2,position,isChecked);
            }
        });
        return convertView;
    }

    static
    class ViewHolder {
        @BindView(R.id.account_id)
        TextView accountId;
        @BindView(R.id.balance_account)
        TextView balanceAccount;
        @BindView(R.id.account_box)
        CheckBox accountBox;
        @BindView(R.id.recharge_btn)
        Button rechargeBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
