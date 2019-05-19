package com.cnpm.happylunch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

public class AdBottom_Nav extends AppCompatActivity {

    private ActionBar toolBar;

    private AdWork adWork = new AdWork();
    private AdItem adItem = new AdItem();
    private AdRecharge adRecharge = new AdRecharge();
    private Customer adProfile = new Customer();

    private FragmentTransaction ft;
    private FrameLayout selectedFrameLayout;
    private FrameLayout flOder, flItem, flRecharge, flSetting;
    private View view;
    private String toolBarTitle;
    private class adBotNavListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.navigation_ordered:
                    selectedFrameLayout = flOder;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                    toolBarTitle = "Order";
                    break;
                case R.id.navigation_shop:
                    selectedFrameLayout = flItem;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                    toolBarTitle = "Item";
                    break;
                case R.id.navigation_customers:
                    selectedFrameLayout = flRecharge;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                    toolBarTitle = "Recharge";
                    break;
                case R.id.navigation_profile:
                    selectedFrameLayout = flSetting;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                    toolBarTitle = "Setting";
                    break;
            }
            toolBar.setTitle(toolBarTitle);

            flOder.setVisibility(View.INVISIBLE);
            flItem.setVisibility(View.INVISIBLE);
            flRecharge.setVisibility(View.INVISIBLE);
            flSetting.setVisibility(View.INVISIBLE);

            selectedFrameLayout.setVisibility(View.VISIBLE);

            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_main);
        toolBar = getSupportActionBar();

        flOder = findViewById(R.id.flOder);
        flItem = findViewById(R.id.flItem);
        flRecharge = findViewById(R.id.flCustomers);
        flSetting = findViewById(R.id.flSetting);

        flOder.setVisibility(View.VISIBLE);
        flItem.setVisibility(View.INVISIBLE);
        flRecharge.setVisibility(View.INVISIBLE);
        flSetting.setVisibility(View.INVISIBLE);

        selectedFrameLayout = flOder;

        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.flOder, adWork).add(R.id.flItem, adItem).add(R.id.flCustomers, adRecharge).add(R.id.flSetting, adProfile).commit();

        setHomeActionBar();

        BottomNavigationView botNav = findViewById(R.id.ad_bottom_nav);
        botNav.setOnNavigationItemSelectedListener(new adBotNavListener());
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void setHomeActionBar(){
        toolBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        view = layoutInflater.inflate(R.layout.activity_home_action_bar, null);


        toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        toolBar.setCustomView(view);
    }
}
