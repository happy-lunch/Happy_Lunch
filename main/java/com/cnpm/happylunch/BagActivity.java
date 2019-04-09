package com.cnpm.happylunch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    private void AnhXa(){
        arrayBag.add(new Bag(R.drawable.ck_banh_bao_ba_xiu_2,   "Bánh bao xá xíu 2",
                "9:50",3,R.drawable.icb_x));
        arrayBag.add(new Bag(R.drawable.ck_com_chien,           "Cơm chiên",
                "9:45",2,R.drawable.icb_x));
        arrayBag.add(new Bag(R.drawable.ck_fruit_whole_nodish,  "Fruit whole nodish",
                "9:30",3,R.drawable.icb_chamthan));
        arrayBag.add(new Bag(R.drawable.ck_salad_caron,         "Salad caron",
                "9:20",1,R.drawable.icb_tichv));
        arrayBag.add(new Bag(R.drawable.ck_single_banana,       "Single banana",
                "8:55",1,R.drawable.icb_tichv));
        arrayBag.add(new Bag(R.drawable.ck_trung_cut,           "Trứng cút",
                "8:45",4,R.drawable.icb_tichv));
        arrayBag.add(new Bag(R.drawable.ck_salad_caron,         "Salad caron",
                "8:40",1,R.drawable.icb_tichv));
        arrayBag.add(new Bag(R.drawable.ck_salad_caron,         "Salad caron",
                "8:40",1,R.drawable.icb_tichv));
        arrayBag.add(new Bag(R.drawable.ck_single_banana,       "Single banana",
                "8:30",1,R.drawable.icb_tichv));
        arrayBag.add(new Bag(R.drawable.ck_trung_cut,           "Trứng cút",
                "8:20",4,R.drawable.icb_tichv));
        arrayBag.add(new Bag(R.drawable.ck_salad_caron,         "Salad caron",
                "8:00",1,R.drawable.icb_tichv));
    }

    public void clickBagRow(View view) {
        setContentView(R.layout.activity_item);

    }
}
