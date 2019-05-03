package com.cnpm.happylunch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdEmployee extends Fragment {

    ImageView imgImg;
    TextView txtId,txtName;
    Button btnEdit, btnSale, btnHistory, btnExit;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ad_employee, container, false);

        Anhxa();
        ControlButton();

        return view;
    }

    private void ControlButton() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Chỉnh sửa thông tin nhân viên", Toast.LENGTH_SHORT).show();
            }
        });

        btnSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Thêm các combo sale", Toast.LENGTH_SHORT).show();
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Xem lịch sử công việc đã hoàn thành của nhân viên", Toast.LENGTH_SHORT).show();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Đăng xuất", Toast.LENGTH_SHORT).show();
                Intent setting = new Intent(getContext(), Login.class);
                startActivity(setting);
            }
        });

    }

    private void Anhxa() {
        imgImg  = (ImageView)   view.findViewById(R.id.imageView_adEmployee);
        txtId   = (TextView)    view.findViewById(R.id.textView_adEmployee_id);
        txtName = (TextView)    view.findViewById(R.id.textView_adEmployee_name);
        btnEdit = (Button)      view.findViewById(R.id.button_adEmployee_edit);
        btnSale = (Button)      view.findViewById(R.id.button_adEmployee_add);
        btnHistory = (Button)   view.findViewById(R.id.button_adEmployee_history);
        btnExit = (Button)      view.findViewById(R.id.button_adEmployee_exit);
    }
}

