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

public class AdBottom_Nav extends AppCompatActivity {

    private ActionBar toolBar;

    private HomePage home = new HomePage();
    private AdWork adWork = new AdWork();
    private AdItem adItem = new AdItem();
    private Bag bag = new Bag();
    private String toolBarTitle;
    private View view;
    private Fragment selectedFragment = null;
    private class adBotNavListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.navigation_shop:
                    selectedFragment = adItem;
                    toolBarTitle = "Kìa con bứm";
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    toolBar.setCustomView(view);
                    break;
                case R.id.navigation_ordered:
                    selectedFragment = adWork;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    toolBar.setCustomView(view);
                    break;
            }
            toolBar.setTitle(toolBarTitle);
            getSupportFragmentManager().beginTransaction().replace(R.id.ad_fragment_container, selectedFragment).show(selectedFragment).commit();
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_main);
        toolBar = getSupportActionBar();

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        view = layoutInflater.inflate(R.layout.activity_home_action_bar, null);

        getSupportFragmentManager().beginTransaction().add(R.id.ad_fragment_container, adItem).add(R.id.ad_fragment_container, adWork).commit();

        BottomNavigationView botNav = findViewById(R.id.ad_bottom_nav);
        botNav.setOnNavigationItemSelectedListener(new adBotNavListener());
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}
