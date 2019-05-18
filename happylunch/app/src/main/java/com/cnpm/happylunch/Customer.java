package com.cnpm.happylunch;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Customer extends Fragment {

    Toolbar toolbarcustomer;
    ImageView imgcustomer;
    TextView txtten,txtid;
    Button btnkiemtra,btncaidat,btnhoadon,btndangxuat;
    private int money;

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
                builder.setTitle("Bạn có muốn thoát?");
                builder.setMessage("Hãy lựa chọn bên dưới để xác nhận");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        App.user = null;
                        startActivity(new Intent(getActivity(), Login.class));
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
                Check_money();
            }
        });
        btnhoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hoadon = new Intent(getContext(),AccountBill.class);
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

    private void Check_money(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Thông báo !!!");
        alertDialog.setMessage("Số tiền của bạn là " + money + " VNĐ");
        alertDialog.show();
    }
}

