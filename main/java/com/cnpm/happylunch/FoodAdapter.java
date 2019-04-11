package com.cnpm.happylunch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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

        ImageView imageFodd = (ImageView) convertView.findViewById(R.id.imageViewFood);
        TextView nameFoodTextView = (TextView) convertView.findViewById(R.id.textViewFoodName);
        TextView priceTextView = (TextView) convertView.findViewById(R.id.textViewpPrice);

        imageFodd.setImageResource(food.getFoodImg());
        nameFoodTextView.setText(food.getFoodName());
        priceTextView.setText(food.getFoodPrice() + " " + String.valueOf('\u20AB'));

        return convertView;
    }
}
