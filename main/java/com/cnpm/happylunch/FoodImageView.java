package com.cnpm.happylunch;

import android.content.Context;

public class FoodImageView extends android.support.v7.widget.AppCompatImageView {

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
