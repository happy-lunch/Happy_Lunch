package com.cnpm.happylunch;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SearchHomePage extends AppCompatActivity {
	
	public static boolean isFromSearchPage = false;
	
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
				
				isFromSearchPage = true;
				
                Intent i = new Intent(SearchHomePage.this, FoodDetail.class);
                i.putExtra("Food", (Food)matchFood.get(position));
                startActivity(i);
            }
        });
        matchFood = new ArrayList<Food>();

        final EditText edtSearch = findViewById(R.id.edtSearch);
		
		edtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    Intent i = new Intent(SearchHomePage.this, SearchResult.class);
                    i.putExtra("Content", edtSearch.getText().toString().trim());
                    startActivity(i);
                    finish();
                }
                return false;
            }
        });
		
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                matchFood = new ArrayList<Food>();
                matchFood = (ArrayList<Food>) HomePage.foods.stream().filter(f -> f.getFoodName().toLowerCase().contains(edtSearch.getText().toString().toLowerCase())).collect(Collectors.toList());
                if(matchFood.isEmpty() || edtSearch.getText().toString().equals("")){
                    listView.setAdapter(null);
                    return;
                }
                ArrayAdapter<Food> listViewAdapter = new ArrayAdapter<Food>(SearchHomePage.this, android.R.layout.simple_list_item_1, matchFood);
                listView.setAdapter(listViewAdapter);
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
