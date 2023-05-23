package com.example.tk1_5.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tk1_5.AppClient;
import com.example.tk1_5.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.change)
    ImageView change;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.nav_menu)
    NavigationView navMenu;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private AppClient appClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        title.setText("主界面");
        appClient = (AppClient) getApplication();
        if (!appClient.getUserName().equals("user1")) {
            navMenu.getMenu().findItem(R.id.hld).setVisible(false);
        }
        navMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Class myClass = null;
                switch (menuItem.getItemId()) {
                    case R.id.wdzh:
                        myClass = Z_WDZHActivity.class;
                        break;
                    case R.id.hld:
                        myClass = Z_HLDGLActivity.class;
                        break;
                    case R.id.zdgl:
                        myClass = Z_ZDGLActivity.class;
                        break;
                    case R.id.hjzb:
                        myClass = Z_HJZBActivity.class;
                        break;
                    case R.id.clwz:
                        myClass = Z_CLWZActivity.class;
                        break;
                    case R.id.ssxs:
                        myClass = Z_SSXSActivity.class;
                        break;
                }
                if (myClass != null) {
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(MainActivity.this, myClass));
                }
                return false;
            }
        });


    }

    @OnClick(R.id.change)
    public void onViewClicked() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);

        }
    }
}
