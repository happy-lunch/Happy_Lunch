package com.cnpm.happylunch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

public class Bottom_Nav extends AppCompatActivity {

    private ActionBar toolBar;
    //private EditText toolBarEditText;

    private FragmentTransaction ft;
    private HomePage home = new HomePage();
    private AccountPage account = new AccountPage();
    public static Cart cart = new Cart();
    public static Bag bag = new Bag();
    public static SecondShop resell = new SecondShop();

    private FrameLayout selectedFrameLayout;
    private FrameLayout flHome, flResell, flCart, flBag, flAccount;
    private View view;
    private String toolBarTitle;
    private class BotNavListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    //selectedFragment = home;
                    selectedFrameLayout = flHome;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    toolBar.setCustomView(view);
                    break;
                case R.id.nav_resell:
                    selectedFrameLayout = flResell;
                    SecondShop.secondShopAdapter.notifyDataSetChanged();
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                    toolBarTitle = "Resell";
                    break;
                case R.id.nav_shopping_cart:
                    //selectedFragment = shop;
                    selectedFrameLayout = flCart;
                    cart.set_cost();
                    Cart.cartAdapter.notifyDataSetChanged();
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                    toolBarTitle = "Shopping cart";
                    break;
                case R.id.nav_bag:
                    selectedFrameLayout = flBag;
                    Bag.bagAdapter.notifyDataSetChanged();
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                    toolBarTitle = "Bag";
                    break;
                case R.id.nav_account:
                    //selectedFragment = account;
                    selectedFrameLayout = flAccount;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                    toolBarTitle = "Account";
                    break;
            }
            toolBar.setTitle(toolBarTitle);
            //getSupportFragmentManager().beginTransaction().replace(R.id.flHome, selectedFragment).show(selectedFragment).commit();
            //ft.hide(home).hide(shop).hide(account);
            //ft.show(selectedFragment);
            //ft.add(R.id.fragment_container, selectedFragment);
            //ft.commit();

            flHome.setVisibility(View.INVISIBLE);
            flResell.setVisibility(View.INVISIBLE);
            flCart.setVisibility(View.INVISIBLE);
            flBag.setVisibility(View.INVISIBLE);
            flAccount.setVisibility(View.INVISIBLE);


            selectedFrameLayout.setVisibility(View.VISIBLE);

            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        toolBar = getSupportActionBar();

        flHome = findViewById(R.id.flHome);
        flResell = findViewById(R.id.flResell);
        flCart = findViewById(R.id.flCart);
        flBag = findViewById(R.id.flBag);
        flAccount = findViewById(R.id.flAccount);

        flHome.setVisibility(View.VISIBLE);
        flResell.setVisibility(View.INVISIBLE);
        flCart.setVisibility(View.INVISIBLE);
        flBag.setVisibility(View.INVISIBLE);
        flAccount.setVisibility(View.INVISIBLE);

        selectedFrameLayout = flHome;

        //getSupportFragmentManager().beginTransaction().replace(R.id.flHome, selectedFragment).show(selectedFragment).commit();
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.flAccount, account).add(R.id.flHome, home).add(R.id.flBag, bag).add(R.id.flCart, cart).add(R.id.flResell, resell).commit();

        setHomeActionBar();

        BottomNavigationView botNav = findViewById(R.id.bottom_nav);
        botNav.setOnNavigationItemSelectedListener(new BotNavListener());

        EditText txtSearch = view.findViewById(R.id.txtSearch);
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchHomePage.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void setHomeActionBar(){
        toolBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        view = layoutInflater.inflate(R.layout.activity_home_action_bar, null);

        EditText txtSearch = view.findViewById(R.id.txtSearch);
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bottom_Nav.this, SearchHomePage.class));
            }
        });

        toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        toolBar.setCustomView(view);
    }

}
