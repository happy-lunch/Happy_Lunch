package com.example.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class account_customer extends AppCompatActivity {

    Toolbar toolbarcustomer = findViewById(R.id.toolbaraccount);
    ImageView imgcustomer;
    TextView txtten,txtid;
    Button btnkiemtra,btncaidat,btnhoadon,btndangxuat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_customer);
        Anhxa();
        ControlButton();
    }

    private void ControlButton() {
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Bạn có muốn thoát khỏi CircleK ?");
                builder.setMessage("Hãy lựa chọn bên dưới để xác nhận");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        btncaidat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setting = new Intent(account_customer.this,setting_account.class);
                startActivities(setting);
            }
        });
        btnkiemtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent check = new Intent(account_customer.this,check_money.class);
                startActivities(check);
            }
        });
        btnhoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hoadon = new Intent(account_customer.this,hoa_don.class);
                startActivities(hoadon);
            }
        });
    }

    private void Anhxa() {
        toolbarcustomer = (Toolbar) findViewById(R.id.toolbaraccount);
        imgcustomer = (ImageView) findViewById(R.id.imagecustomer);
        txtten = (TextView) findViewById(R.id.texttenkhachhang);
        txtid = (TextView) findViewById(R.id.textid);
        btncaidat = (Button) findViewById(R.id.btncaidattaikhoan);
        btndangxuat = (Button) findViewById(R.id.btndangxuat);
        btnhoadon = (Button) findViewById(R.id.btnhoadon);
        btnkiemtra = (Button) findViewById(R.id.btnkiemtrataikhoan);
    }
}
