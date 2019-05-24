package com.cnpm.happylunch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Setting_account extends AppCompatActivity {

    //Toolbar toolbarcaidat;
    EditText edittxtmatkhauhientai,edittxtmatkhauthaydoi,edittxtnhaplaimatkhau;
    Button btnhuy,btnxacnhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_account);
        Anhxa();
        ControlButton();
    }

    private void ControlButton() {
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent account = new Intent(Setting_account.this,AdBottom_Nav.class);
                startActivity(account);
            }
        });
    }

    private void Anhxa() {
        //toolbarcaidat = (Toolbar) findViewById(R.id.toolbarsetacc);
        edittxtmatkhauhientai = (EditText) findViewById(R.id.edittxtmatkhauhientai);
        edittxtmatkhauthaydoi = (EditText) findViewById(R.id.edittxtmatkhauthaydoi);
        edittxtnhaplaimatkhau = (EditText) findViewById(R.id.edittxtnhaplaimatkhau);
        btnhuy = (Button) findViewById(R.id.btnhuy);
        btnxacnhan = (Button) findViewById(R.id.btnxacnhan);
    }
}
