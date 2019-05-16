package com.cnpm.happylunch;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

    private View view;
    private String toolBarTitle;
    public static HomePage homePage = new HomePage();
    private AccountPage accountPage = new AccountPage();
    public volatile static Fragment selectedFragment = homePage;
    public volatile static FoodDetail foodDetail = new FoodDetail();
    public volatile static Cart cart = new Cart();
    public volatile static Bag bag = new Bag();
    public volatile static BagResell bagResell = new BagResell();
    public volatile static SecondShop resell = new SecondShop();

    private class BotNavListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomePage();
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    toolBar.setCustomView(view);
                    break;
                case R.id.nav_resell:
                    selectedFragment = resell;
                    cart.set_cost();
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                    toolBarTitle = "Resell";
                    break;
                case R.id.nav_shopping_cart:
                    selectedFragment = cart;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                    toolBarTitle = "Shopping cart";
                    break;
                case R.id.nav_bag:
                    selectedFragment = bag;
                    toolBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
                    toolBarTitle = "Bag";
                    break;
                case R.id.nav_account:
                    selectedFragment = accountPage;
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


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).show(selectedFragment).commit();

        setHomeActionBar();

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
		
		EditText txtSearch = (EditText) view.findViewById(R.id.txtSearch);
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
