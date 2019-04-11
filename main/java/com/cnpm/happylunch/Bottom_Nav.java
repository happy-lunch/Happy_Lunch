package com.cnpm.happylunch;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bottom_Nav extends AppCompatActivity {

    private ActionBar toolBar;
    //private EditText toolBarEditText;

    private HomePage home = new HomePage();
    private ShoppingPage shop = new ShoppingPage();
    private AccountPage account = new AccountPage();

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
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                    toolBarTitle = "Shopping cart";
                    break;
                case R.id.nav_account:
                    selectedFragment = account;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                    toolBarTitle = "Account";
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
        setHomeActionBar();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, shop).add(R.id.fragment_container, account).add(R.id.fragment_container, home).commit();

        BottomNavigationView botNav = findViewById(R.id.bottom_nav);
        botNav.setOnNavigationItemSelectedListener(new BotNavListener());
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
