package com.cnpm.happylunch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchHomePage extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Food> matchFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_home_page);

        listView = findViewById(R.id.lvInSearch);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchHomePage.this, FoodDetail.class);
                i.putExtra("Food", (Food)matchFood.get(position));
                startActivity(i);
            }
        });
        matchFood = new ArrayList<Food>();

        final EditText edtSearch = findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                matchFood = new ArrayList<Food>();
                matchFood = (ArrayList<Food>) HomePage.foods.stream().filter(f -> f.getFoodName().toLowerCase().contains(edtSearch.getText().toString().toLowerCase())).collect(Collectors.toList());
                if(matchFood.isEmpty() || edtSearch.getText().toString().equals("")){
                    listView.setAdapter(null);
                    //Toast.makeText(SearchHomePage.this, "Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayAdapter<Food> listViewAdapter = new ArrayAdapter<Food>(SearchHomePage.this, android.R.layout.simple_list_item_1, matchFood);
                listView.setAdapter(listViewAdapter);

                //Toast.makeText(SearchHomePage.this, String.valueOf(matchFood.size()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void goBack(View view) {
        onBackPressed();
    }
}
