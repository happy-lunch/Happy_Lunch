package com.cnpm.happylunch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

public class AdBottom_Nav extends AppCompatActivity {

    private ActionBar toolBar;

    private AdWork adWork = new AdWork();
    private FoodCategory adItemCategory = new FoodCategory();
    private food_info adItem= new food_info();
    private AdRecharge adRecharge = new AdRecharge();
    private Customer adProfile = new Customer();
    private String toolBarTitle;
    private View view;
    private Fragment selectedFragment = null;
    private class adBotNavListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.navigation_shop:
                    selectedFragment = adItemCategory;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    toolBar.setCustomView(view);
                    break;
                case R.id.navigation_ordered:
                    selectedFragment = adWork;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    toolBar.setCustomView(view);
                    break;
                case R.id.navigation_customers:
                    selectedFragment = adRecharge;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    toolBar.setCustomView(view);
                    break;
                case R.id.navigation_profile:
                    selectedFragment = adProfile;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    toolBar.setCustomView(view);
                    break;
            }
            toolBar.setTitle(toolBarTitle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ad_fragment_container, selectedFragment)
                    .show(selectedFragment)
                    .commit();
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_main);
        toolBar = getSupportActionBar();

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        view = layoutInflater.inflate(R.layout.ad_action_bar, null);
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int stackHeight = getSupportFragmentManager().getBackStackEntryCount();
                if (stackHeight > 0) { // if we have something on the stack (doesn't include the current shown fragment)
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setHomeButtonEnabled(false);
                }
            }
        });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.ad_fragment_container, adProfile)
                .add(R.id.ad_fragment_container, adRecharge)
                .add(R.id.ad_fragment_container, adItemCategory)
                .add(R.id.ad_fragment_container, adWork)
                .commit();

        BottomNavigationView botNav = findViewById(R.id.ad_bottom_nav);
        botNav.setOnNavigationItemSelectedListener(new adBotNavListener());
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getSupportFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
