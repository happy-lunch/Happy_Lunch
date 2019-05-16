package com.cnpm.happylunch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodDetail extends AppCompatActivity {

    private TextView foodAmount, price, nameFood, bigNameFood;
    private Button btnAdd, btnSub;
    private ImageView imgFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        map();

        Intent i = getIntent();

        Food food = (Food) i.getSerializableExtra("Food");

        price.setText(food.getFoodPrice());
        nameFood.setText(food.getFoodName());
        bigNameFood.setText(food.getFoodName());
        imgFood.setImageResource(food.getFoodImg());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numFood = Integer.parseInt(foodAmount.getText().toString());
                if(numFood >= 10){
                    return;
                }
                foodAmount.setText(String.valueOf(++numFood));
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numFood = Integer.parseInt(foodAmount.getText().toString());
                if(numFood <= 1){
                    return;
                }
                foodAmount.setText(String.valueOf(--numFood));
            }
        });

    }

    private void map(){
        foodAmount = findViewById(R.id.txtFoodAmount);
        price = findViewById(R.id.txtPriceDetail);
        nameFood = findViewById(R.id.txtNameDetail);
        bigNameFood = findViewById(R.id.txtBigNameDetail);

        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);

        imgFood = findViewById(R.id.imgFood);
    }

}
