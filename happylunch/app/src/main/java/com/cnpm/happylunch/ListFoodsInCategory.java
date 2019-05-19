package com.cnpm.happylunch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListFoodsInCategory extends AppCompatActivity {

    private List<Food> foodsInCategory = new ArrayList<Food>();
    private GridView gridViewFoodsInCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_foods_in_category);

        getFoodsInCategory();
        gridViewFoodsInCategory = (GridView) findViewById(R.id.gridViewFoodsInCategory);
        gridViewFoodsInCategory.setAdapter(new FoodAdapter(this, foodsInCategory));


        gridViewFoodsInCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodDetail.food = foodsInCategory.get(position);
                startActivity(new Intent(getBaseContext(), FoodDetail.class));
                finish();
            }
        });

        //Set title ActionBar
        setActionBar();

    }

    private void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        /*actionBar.setTitle(getTittleActionBar());
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);*/
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.RED));

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.actionbar_foods_in_category, null);
        ImageView img = (ImageView) view.findViewById(R.id.arrowBack);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView actionBarTittle = (TextView) view.findViewById(R.id.titleActionBar);
        actionBarTittle.setText(getTittleActionBar());
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(view);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void getFoodsInCategory(){
        Intent i = getIntent();
        KindOfFood k = (KindOfFood)i.getSerializableExtra("KindOfFood");
        for(Food f:HomePage.foods){
            if(f.getKind() == k){
                foodsInCategory.add(f);
            }
        }
    }

    private String getTittleActionBar(){
        Intent i = getIntent();
        KindOfFood k = (KindOfFood)i.getSerializableExtra("KindOfFood");

        String tittle = null;
        switch (k) {
            case Com:
                tittle = "Cơm";
                break;
            case Mi:
                tittle = "Mì";
                break;
            case Banh_Bao:
                tittle = "Bánh Bao";
                break;
            case Banh_Mi:
                tittle = "Bánh Mì";
                break;
            case Sandwich:
                tittle = "Sandwich";
                break;
            case Trang_Mieng:
                tittle = "Trang Mieng";
                break;
                default: tittle = "NULL";
        }
        return tittle;
    }
}
