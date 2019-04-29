package com.cnpm.happylunch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.cnpm.happylunch.fragment.ShopFragment;
import com.cnpm.happylunch.fragment.ProfileFragment;
import com.cnpm.happylunch.fragment.CustomersFragment;
import com.cnpm.happylunch.fragment.OrderedFragment;

public class MainActivity_admin extends AppCompatActivity {

    private ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_main);
        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        toolbar.setTitle("Shop");
        Intent in = new Intent(MainActivity_admin.this, AdItem.class);
        startActivity(in);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    toolbar.setTitle("Shop");
                    Intent in = new Intent(MainActivity_admin.this, AdItem.class);
                    startActivity(in);
                    return true;
                case R.id.navigation_customers:
                    toolbar.setTitle("Customers");
                    loadFragment(new CustomersFragment());
                    return true;
                case R.id.navigation_ordered:
                    toolbar.setTitle("Ordered");
                    Intent intentOrdered = new Intent(MainActivity_admin.this, AdWork.class);
                    startActivity(intentOrdered);
                    return true;
                case R.id.navigation_profile:
                    toolbar.setTitle("Profile");
                    loadFragment(new ProfileFragment());
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}