package com.cnpm.happylunch;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListEmployee extends AppCompatActivity {

    Dialog add;
    ProgressDialog progressBar;
    ListView listEmployees;
    Activity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_employee);

        thisActivity = this;

        add = new Dialog(this);
        add.setContentView(R.layout.dialog_add_employee);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Đang tải");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        listEmployees = (ListView) findViewById(R.id.listEmployee);
        Button txtAdd = (Button) findViewById(R.id.btnAddEmployee);


        listEmployees.setAdapter(new EmployeeAdapter(this, App.employees));

        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!App.employee.isOwner()){
                    Toast.makeText(ListEmployee.this, "Bạn không có quyền thực hiện thao tác này", Toast.LENGTH_SHORT).show();
                    return;
                }
                add = new Dialog(thisActivity);
                add.setContentView(R.layout.dialog_add_employee);

                EditText edtName = (EditText) add.findViewById(R.id.edtNameEmployee);
                EditText edtSDT = (EditText) add.findViewById(R.id.edtSDTEmployee);
                EditText edtUsername = (EditText) add.findViewById(R.id.edtAdminName);
                EditText edtPassword = (EditText) add.findViewById(R.id.edtPassAdmin);

                CheckBox isOwner = (CheckBox) add.findViewById(R.id.isOwner);

                TextView txtAdd = (TextView) add.findViewById(R.id.add);
                TextView txtCancel = (TextView) add.findViewById(R.id.cancel);

                txtAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        String id = databaseReference.child("Employees").push().getKey();
                        databaseReference.child("Employees").child(id).setValue(new Employee(id,isOwner.isChecked() ,edtName.getText().toString(), edtUsername.getText().toString(), edtPassword.getText().toString(), edtSDT.getText().toString()));
                        progressBar.show();
                        Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setListViewAdapter();
                                progressBar.cancel();
                                add.cancel();
                            }
                        },2000);

                    }
                });

                txtCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add.cancel();
                    }
                });

                add.show();
            }
        });

        setActionBar();

    }

    private void setListViewAdapter(){
        listEmployees.setAdapter(new EmployeeAdapter(this, App.employees));
    }

    private void setActionBar(){
        ActionBar actionBar = getSupportActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.RED));

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.actionbar_foods_in_category, null);
        ImageView img = (ImageView) view.findViewById(R.id.arrowBack);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView actionBarTittle = (TextView) view.findViewById(R.id.titleActionBar);
        actionBarTittle.setText("Danh sách nhân viên");
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(view);
    }

    class EmployeeAdapter extends BaseAdapter {

        private Context c;
        private ArrayList<Employee> employees;
        private LayoutInflater layoutInflater;

        public EmployeeAdapter(Context c, ArrayList<Employee> employees) {
            this.c = c;
            this.employees = employees;
            layoutInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return employees.size();
        }

        @Override
        public Object getItem(int position) {
            return employees.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Employee employee = employees.get(position);
            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.item_in_listview_employee, null);
            }

            TextView nameEmployee = (TextView) convertView.findViewById(R.id.txtNameEmployee) ;
            ImageView imgvDelete = (ImageView) convertView.findViewById(R.id.imgvDeleteEmployee);
            //================================================================================

            nameEmployee.setText(employee.getName());

            imgvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!App.employee.isOwner() || employee.isOwner()){
                        Toast.makeText(ListEmployee.this, "Bạn không có quyền thực hiện thao tác này", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("Employees").child(employee.getId()).removeValue();
                    progressBar.show();
                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setListViewAdapter();
                            progressBar.cancel();
                        }
                    },2000);
                }
            });

            return convertView;
        }
    }

}
