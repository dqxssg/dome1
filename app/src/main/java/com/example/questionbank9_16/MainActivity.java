package com.example.questionbank9_16;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.questionbank9_16.activity.AccountManageActivity;
import com.example.questionbank9_16.activity.DataAnalysisActivity;
import com.example.questionbank9_16.activity.LifeAssistantActivity;
import com.example.questionbank9_16.activity.LightManageActivity;
import com.example.questionbank9_16.activity.PersonActivity;
import com.example.questionbank9_16.activity.QueryBusActivity;
import com.example.questionbank9_16.activity.TrafficQueryActivity;
import com.example.questionbank9_16.activity.VehicleViolationActivity;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        titleTv.setText("主菜单");
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.zhgl:
                        startActivity(new Intent(MainActivity.this, AccountManageActivity.class));
                        break;
                    case R.id.grzx:
                        startActivity(new Intent(MainActivity.this, PersonActivity.class));
                        break;
                    case R.id.gjcx:
                        startActivity(new Intent(MainActivity.this, QueryBusActivity.class));
                        break;
                    case R.id.hldgl:
                        startActivity(new Intent(MainActivity.this, LightManageActivity.class));
                        break;
                    case R.id.clwz:
                        startActivity(new Intent(MainActivity.this, VehicleViolationActivity.class));
                        break;
                    case R.id.lkcx:
                        startActivity(new Intent(MainActivity.this, TrafficQueryActivity.class));
                        break;
                    case R.id.shzs:
                        startActivity(new Intent(MainActivity.this, LifeAssistantActivity.class));
                        break;
                    case R.id.sjfx:
                        startActivity(new Intent(MainActivity.this, DataAnalysisActivity.class));
                        break;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.toolbar)
    public void onClick(View view) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}