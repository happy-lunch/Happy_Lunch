package com.cnpm.happylunch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class BagActivity extends AppCompatActivity {

    private ListView lvBag;
    private ArrayList<Bag> arrayBag ;
    private BagAdapter bagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag);

        lvBag = findViewById(R.id.list_bag);
        arrayBag = new ArrayList<>();
        AnhXa();
        bagAdapter = new BagAdapter(this, R.layout.row_in_bag, arrayBag);
        lvBag.setAdapter(bagAdapter);
    }

    public void AnhXa(){
        arrayBag.add(new Bag(R.drawable.ck_banh_bao_xa_xiu_2,   "Bánh bao xá xíu 2",
                "9:28",3,R.drawable.x));
        arrayBag.add(new Bag(R.drawable.ck_com_chien,           "Cơm chiên",
                "9:18",2,R.drawable.x));
        arrayBag.add(new Bag(R.drawable.ck_fruit_whole_nodish,  "Fruit whole nodish",
                "9:08",3,R.drawable.chamthan));
        arrayBag.add(new Bag(R.drawable.ck_salad_caron,         "Salad caron",
                "8:28",1,R.drawable.tichv));
        arrayBag.add(new Bag(R.drawable.ck_single_banana,       "Single_banana",
                "8:18",1,R.drawable.tichv));
        arrayBag.add(new Bag(R.drawable.ck_trung_cut,           "Trứng cút",
                "8:08",4,R.drawable.tichv));
        arrayBag.add(new Bag(R.drawable.ck_salad_caron,         "Slad caron",
                "8:00",1,R.drawable.tichv));

    }

}
