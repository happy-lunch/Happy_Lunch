package com.cnpm.happylunch;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Fragment {

    ImageView imgcustomer;
    TextView txtTenNV,txtid;
    Button btnCaiDatNV,btndangxuat;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_account_customer, container, false);

        Anhxa();

        txtTenNV.setText(App.employee.getName());
        txtid.setText(App.employee.getStd());

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
                        App.employee = null;
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
        btnCaiDatNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ListEmployee.class));
            }
        });

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
        btnCaiDatNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ListEmployee.class));
            }
        });
    }

    private void Anhxa() {
        imgcustomer = (ImageView) view.findViewById(R.id.imagecustomer);
        txtTenNV = (TextView) view.findViewById(R.id.txtTenNV);
        txtid = (TextView) view.findViewById(R.id.textid);
        btndangxuat = (Button) view.findViewById(R.id.btndangxuat);
        btnCaiDatNV = (Button) view.findViewById(R.id.btnCaiDatNV);
    }

}

