package com.cnpm.happylunch;

import android.content.Context;
import android.widget.ImageView;

public class FoodImageView extends android.support.v7.widget.AppCompatImageView {

    private Category cate;

    public FoodImageView(Context context, Category cate) {
        super(context);
        this.cate = cate;
    }

    public FoodImageView(Context context) {
        super(context);
    }

    public Category getCate() {
        return cate;
    }

    public void setCate(Category cate) {
        this.cate = cate;
    }
}