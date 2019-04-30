package com.cnpm.happylunch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Food {
    private int foodImg;
    private String foodName;
    private String foodPrice;
    private double numStar;
    private int numSold;
    private KindOfFood kind;

    public Food(int foodImg, String foodName, String foodPrice) {
        this.foodImg = foodImg;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }

    public Food(int foodImg, String foodName, String foodPrice, int numSold, KindOfFood kind) {
        this.foodImg = foodImg;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.numSold = numSold;
        this.kind = kind;
    }

    public void setNumStar(double numStar) {
        this.numStar = numStar;
    }

    public void setNumSold(int numSold) {
        this.numSold = numSold;
    }

    public void setKind(KindOfFood kind) {
        this.kind = kind;
    }

    public void setFoodImg(int foodImg) {
        this.foodImg = foodImg;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getFoodImg() {
        return foodImg;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public double getNumStar() {
        return numStar;
    }

    public int getNumSold() {
        return numSold;
    }

    public KindOfFood getKind() {
        return kind;
    }
}

class FoodAdapter extends BaseAdapter {

    private Context c;
    private List<Food> foods;
    private LayoutInflater layoutInflater;

    public FoodAdapter(Context c, List<Food> foods) {
        this.c = c;
        this.foods = foods;
        layoutInflater = LayoutInflater.from(c);
    }

    public int getCount() {
        return foods.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

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

class FoodImageView extends android.support.v7.widget.AppCompatImageView {

    private KindOfFood kind;

    public FoodImageView(Context context, KindOfFood kind) {
        super(context);
        this.kind = kind;
    }

    public FoodImageView(Context context) {
        super(context);
    }

    public void setKind(KindOfFood kind) {
        this.kind = kind;
    }

    public KindOfFood getKind() {
        return kind;
    }
}

enum KindOfFood {
    Com, Mi, Banh_Mi, Banh_Bao, An_Nhe, Sandwich, Trang_Mieng
}