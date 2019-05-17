package com.cnpm.happylunch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AccountBill extends AppCompatActivity {

    //Toolbar toolbarhoadon;
    Button btnhoadon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        Anhxa();
        controlbutton();
    }

    private void controlbutton() {
        btnhoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent account = new Intent(AccountBill.this,AdBottom_Nav.class);
                startActivity(account);
            }
        });
    }

    private void Anhxa() {
        //toolbarhoadon = (Toolbar) findViewById(R.id.toolbarhoadon);
        btnhoadon = (Button) findViewById(R.id.btnquaylai);
    }
}
