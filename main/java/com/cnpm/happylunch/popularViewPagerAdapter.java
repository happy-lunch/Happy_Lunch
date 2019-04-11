package com.cnpm.happylunch;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class popularViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;
    private int size = HomePage.numPopularItem;

    public popularViewPagerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public int getSize() {
        return size;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View v = inflater.inflate(R.layout.popular_food_layout, null);

        ImageView imageView = (ImageView) v.findViewById(R.id.img);
        TextView text = (TextView) v.findViewById(R.id.textView);
        imageView.setImageResource(HomePage.foods.get(position%getSize()).getFoodImg());
        text.setText(HomePage.foods.get(position%getSize()).getFoodName());

        ViewPager viewPager = (ViewPager) container;

        viewPager.addView(v);

        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //container.removeView((View) object);
    }
}

