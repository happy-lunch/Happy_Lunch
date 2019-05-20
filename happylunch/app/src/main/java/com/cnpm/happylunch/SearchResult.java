package com.cnpm.happylunch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchResult extends AppCompatActivity {

    private GridView gridViewSearchResult;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        content = getIntent().getStringExtra("Content");

        gridViewSearchResult = (GridView) findViewById(R.id.gridViewSearchResult);
        if(!SearchHomePage.matchFood.isEmpty()) {
            gridViewSearchResult.setAdapter(new FoodAdapter(this, SearchHomePage.matchFood));
        }else{
            gridViewSearchResult.setAdapter(null);
        }

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
                onBackPressed();
            }
        });
        TextView actionBarTittle = (TextView) view.findViewById(R.id.titleActionBar);
        actionBarTittle.setText("Kết quả tìm kiếm cho " + "\"" + content + "\"");
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(view);
    }

}
