package com.cnpm.happylunch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.sql.Array;
import java.util.ArrayList;

public class BillDetail extends AppCompatActivity {

    public static ArrayList<BagRow> arrayBillItem = new ArrayList<>();
    private CartAdapter cartAdapter;
    private ListView lvItem;
    private Toolbar toolbar;
    private TextView txt_id, txt_time, txt_money, txt_status;
    public static String[] text = new String[5];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_detail);

        if(text[3].charAt(0) == 'C'){
            cartAdapter = new CartAdapter(this, R.layout.cart_element, arrayBillItem, false);
        }
        else
            cartAdapter = new CartAdapter(this, R.layout.cart_element, arrayBillItem);
        lvItem = findViewById(R.id.list_AccountBill);
        lvItem.setAdapter(cartAdapter);

        //toolbar =  (Toolbar)findViewById(R.id.toolbar_bill);
        txt_id = findViewById(R.id.textView_bill_id);
        txt_time = findViewById(R.id.textView_bill_time);
        txt_money = findViewById(R.id.textView_bill_money);
        txt_status = findViewById(R.id.textView_bill_status);

        //toolbar.setTitle(text[0]);
        txt_id.setText(String.format("Id: %s",text[1]));
        txt_time.setText(String.format("Time: %s",text[2]));
        txt_money.setText(String.format("%s",text[3]));
        txt_status.setText(String.format("Status: %s",text[4]));

    }

}
