package com.cnpm.happylunch;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class check_money extends AppCompatActivity {

    //Toolbar toolbarcheckmoney;
    TextView txtthongbao, txtsodu;
    Button btnquaylai;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_money);
        Anhxa();
        controlbutton();
    }

    private void controlbutton() {
        btnquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent account = new Intent(check_money.this,Bottom_Nav.class);
                startActivity(account);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void Anhxa() {
        //toolbarcheckmoney = (Toolbar) findViewById(R.id.toolbarkiemtra);
        txtthongbao = (TextView) findViewById(R.id.txtsotien);
        txtsodu = (TextView) findViewById(R.id.txtsodu);
        btnquaylai = (Button) findViewById(R.id.btnquaylai);
    }
}
