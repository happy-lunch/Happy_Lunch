package com.cnpm.happylunch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ListFoodsInCategory extends AppCompatActivity {

    private ArrayList<Food> foodsInCategory = new ArrayList<Food>();
    private GridView gridViewFoodsInCategory;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_foods_in_category);

        Intent i = getIntent();
        category = (Category) i.getSerializableExtra("KindOfFood");

        getFoodsInCategory();
        gridViewFoodsInCategory = (GridView) findViewById(R.id.gridViewFoodsInCategory);
        if(!foodsInCategory.isEmpty()) {
            gridViewFoodsInCategory.setAdapter(new FoodAdapter(this, foodsInCategory));
        }else{
            gridViewFoodsInCategory.setAdapter(null);
        }


        gridViewFoodsInCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //FoodDetail.food = foodsInCategory.get(position);
                startActivity(new Intent(getBaseContext(), FoodDetail.class));
                finish();
            }
        });

        //Set title ActionBar
        setActionBar();

    }

    private void setActionBar(){
        ActionBar actionBar = getSupportActionBar();

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
        actionBarTittle.setText(category.getName());
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(view);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void getFoodsInCategory(){
        for(Food f:App.foods){
            if(f.getMenuId().equals(category.getId())){
                foodsInCategory.add(f);
            }
        }
    }

}