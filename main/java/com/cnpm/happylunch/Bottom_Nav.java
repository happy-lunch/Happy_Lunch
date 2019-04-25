package com.cnpm.happylunch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

public class Bottom_Nav extends AppCompatActivity {

    private ActionBar toolBar;

    private HomePage home = new HomePage();
    private Bag shop = new Bag();
    private account_customer account = new account_customer();

    private View view;
    private String toolBarTitle;
    private Fragment selectedFragment = null;
    private class BotNavListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFragment = home;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    toolBar.setCustomView(view);
                    break;
                case R.id.nav_shopping_cart:
                    selectedFragment = shop;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    toolBar.setCustomView(view);
                    break;
                case R.id.nav_account:
                    selectedFragment = account;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    toolBar.setCustomView(view);
                    break;
            }
            toolBar.setTitle(toolBarTitle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).show(selectedFragment).commit();
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        toolBar = getSupportActionBar();


        LayoutInflater layoutInflater = LayoutInflater.from(this);
        view = layoutInflater.inflate(R.layout.activity_home_action_bar, null);

        //setHomeActionBar();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, shop).add(R.id.fragment_container, account).add(R.id.fragment_container, home).commit();

        BottomNavigationView botNav = findViewById(R.id.bottom_nav);
        botNav.setOnNavigationItemSelectedListener(new BotNavListener());
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void setHomeActionBar(){
        //toolBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        view = layoutInflater.inflate(R.layout.activity_home_action_bar, null);

        toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        toolBar.setCustomView(view);
    }

}
