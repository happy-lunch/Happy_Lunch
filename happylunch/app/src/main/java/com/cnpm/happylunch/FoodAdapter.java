package com.cnpm.happylunch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends BaseAdapter {

    private Context c;
    private List<Food> foods;
    private LayoutInflater layoutInflater;

    public FoodAdapter(Context c, List<Food> foods) {
        this.c = c;
        this.foods = foods;
        layoutInflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Food food = foods.get(position);
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.grid_item_layout, null);
        }

        ImageView imageFood = (ImageView) convertView.findViewById(R.id.imageViewFood);
        TextView nameFoodTextView = (TextView) convertView.findViewById(R.id.textViewFoodName);
        TextView priceTextView = (TextView) convertView.findViewById(R.id.textViewpPrice);
        //================================================================================

        Picasso.get().load(food.getImg()).into(imageFood);
        nameFoodTextView.setText(food.getName());
        priceTextView.setText(food.getPrice() + " " + String.valueOf('\u20AB'));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, FoodDetail.class);
                i.putExtra("Food", food);
                App.isIntent = true;
                c.startActivity(i);
            }
        });

        return convertView;
    }
}