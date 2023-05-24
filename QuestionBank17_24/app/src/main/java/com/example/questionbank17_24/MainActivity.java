package com.example.questionbank17_24;

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

import com.example.questionbank17_24.activity.AccountManageActivity;
import com.example.questionbank17_24.activity.AccountSetActivity;
import com.example.questionbank17_24.activity.DataAnalysisActivity;
import com.example.questionbank17_24.activity.LifeAssistantActivity;
import com.example.questionbank17_24.activity.LifeIndexActivity;
import com.example.questionbank17_24.activity.LightManageActivity;
import com.example.questionbank17_24.activity.MyMessageActivity;
import com.example.questionbank17_24.activity.PersonalCenterActivity;
import com.example.questionbank17_24.activity.QueryRoadActivity;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
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
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.shzs:
                        startActivity(new Intent(MainActivity.this, LifeIndexActivity.class));
                        break;
                    case R.id.wdxx:
                        startActivity(new Intent(MainActivity.this, MyMessageActivity.class));
                        break;
                    case R.id.sjfx:
                        startActivity(new Intent(MainActivity.this, DataAnalysisActivity.class));
                        break;
                    case R.id.grzx:
                        startActivity(new Intent(MainActivity.this, PersonalCenterActivity.class));
                        break;
                    case R.id.hldgl:
                        startActivity(new Intent(MainActivity.this, LightManageActivity.class));
                        break;
                    case R.id.zhsz:
                        startActivity(new Intent(MainActivity.this, AccountSetActivity.class));
                        break;
                    case R.id.shZS:
                        startActivity(new Intent(MainActivity.this, LifeAssistantActivity.class));
                        break;
                    case R.id.lkcx:
                        startActivity(new Intent(MainActivity.this, QueryRoadActivity.class));
                        break;
                    case R.id.zhgl:
                        startActivity(new Intent(MainActivity.this, AccountManageActivity.class));
                        break;
                }
                return false;
            }
        });
    }
}