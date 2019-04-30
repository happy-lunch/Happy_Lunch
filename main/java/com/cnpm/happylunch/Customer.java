package com.cnpm.happylunch;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Customer extends Fragment {

    Toolbar toolbarcustomer;
    ImageView imgcustomer;
    TextView txtten,txtid;
    Button btnkiemtra,btncaidat,btnhoadon,btndangxuat;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_account_customer, container, false);

        Anhxa();
        ControlButton();

        return view;
    }

    private void ControlButton() {
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Bạn có muốn thoát khỏi CircleK ?");
                builder.setMessage("Hãy lựa chọn bên dưới để xác nhận");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
                Intent setting = new Intent(getContext(), Setting_account.class);
                startActivity(setting);
            }
        });
        btnkiemtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent check = new Intent(getContext(),Check_money.class);
                startActivity(check);
            }
        });
        btnhoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hoadon = new Intent(getContext(),Bill.class);
                startActivity(hoadon);
            }
        });
    }

    private void Anhxa() {
        toolbarcustomer = (Toolbar) view.findViewById(R.id.toolbaraccount);
        imgcustomer = (ImageView) view.findViewById(R.id.imagecustomer);
        txtten = (TextView) view.findViewById(R.id.texttenkhachhang);
        txtid = (TextView) view.findViewById(R.id.textid);
        btncaidat = (Button) view.findViewById(R.id.btncaidattaikhoan);
        btndangxuat = (Button) view.findViewById(R.id.btndangxuat);
        btnhoadon = (Button) view.findViewById(R.id.btnhoadon);
        btnkiemtra = (Button) view.findViewById(R.id.btnkiemtrataikhoan);
    }
}

class Setting_account extends AppCompatActivity {

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
                Intent account = new Intent(Setting_account.this,Bottom_Nav.class);
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

class Bill extends AppCompatActivity {

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
                Intent account = new Intent(Bill.this,Bottom_Nav.class);
                startActivity(account);
            }
        });
    }

    private void Anhxa() {
        //toolbarhoadon = (Toolbar) findViewById(R.id.toolbarhoadon);
        btnhoadon = (Button) findViewById(R.id.btnquaylai);
    }
}

class Check_money extends AppCompatActivity {

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
                Intent account = new Intent(Check_money.this,Bottom_Nav.class);
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