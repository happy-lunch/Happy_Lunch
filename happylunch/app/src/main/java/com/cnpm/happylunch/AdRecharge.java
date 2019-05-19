package com.cnpm.happylunch;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AdRechargeAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<User> adRechargeList;

    AdRechargeAdapter(Context context, int layout, List<User> adRechargeList) {
        this.context = context;
        this.layout = layout;
        this.adRechargeList = adRechargeList;
    }


    @Override
    public int getCount() { return adRechargeList.size(); }

    @Override
    public Object getItem(int position) { return null; }

    @Override
    public long getItemId(int position) { return 0; }

    private class ViewHolder{
        TextView txtMssv, txtName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.txtMssv   = convertView.findViewById(R.id.textView_adRecharge_mssv);
            holder.txtName   = convertView.findViewById(R.id.textView_adRecharge_name);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        User adRecharge = adRechargeList.get(position);

        holder.txtMssv.setText(String.format("%s", String.valueOf(adRecharge.getMssv())));
        holder.txtName.setText(String.format("%s %s", adRecharge.getFirstName(), adRecharge.getLastName()));

        return convertView;
    }
}

public class AdRecharge extends Fragment {

    private GridView gvAdRecharge;
    private ArrayList<User> arrayAdRecharge;
    private AdRechargeAdapter adRechargeAdapter;
    String mssv;
    int money;
    private EditText txtMssv, txtMoney;

    private View view;

    private DatabaseReference mData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ad_recharge, container, false);
        mData = FirebaseDatabase.getInstance().getReference();

        gvAdRecharge = view.findViewById(R.id.grid_adRecharge);
        arrayAdRecharge = new ArrayList<>();

        txtMssv  = (EditText)view.findViewById(R.id.editText_adRecharge_mssv);
        txtMoney = (EditText)view.findViewById(R.id.editText_adRecharge_money);

        adRechargeAdapter = new AdRechargeAdapter(getContext(), R.layout.ad_recharge_element, arrayAdRecharge);
        gvAdRecharge.setAdapter(adRechargeAdapter);

        mData.child("Customers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                arrayAdRecharge.add(user);
                insertionSort(arrayAdRecharge);
                adRechargeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        gvAdRecharge.setOnItemClickListener((parent, view, position, id) -> {
            if (isMoneyValid()){
                Dialog_click_item(position);
            }

        });

        return view;
    }

    private boolean isMoneyValid(){
        String temp = txtMoney.getText().toString();
        if (temp.length() < 1){
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage("Chưa nhập money");
            dialog.show();
        }
        else{
            money = Integer.valueOf(temp);
            if (money%10000 != 0){
                if (money == 0){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setMessage("Money phải là lớn hơn 0");
                    dialog.show();
                }
                else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setMessage("Money phải là bội số của 10.000");
                    dialog.show();
                }
            }
            else{
                return true;
            }
        }
        return false;
    }

    private void insertionSort(ArrayList<User> arrayList) {

        int i,j;
        for (i = 1; i < arrayList.size(); i++) {
            User key = arrayList.get(i);
            j = i;
            while((j > 0) && (Integer.valueOf(arrayList.get(j - 1).getMssv()) > Integer.valueOf(key.getMssv()))) {
                arrayList.set(j,arrayList.get(j - 1));
                j--;
            }
            arrayList.set(j,key);
        }
    }

    private void Dialog_click_item(final int position){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Hỏi lại chắc chắn!!!");
        alertDialog.setMessage("Nạp tiền cho MSSV " + arrayAdRecharge.get(position).getMssv() + "???");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Nap_tien(position);}
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Cẩn thận đấy!!!", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.show();
    }

    private void Nap_tien(int position){

        Toast.makeText(getContext(),"Giao dịch thành cômg", Toast.LENGTH_SHORT).show();

        User u = arrayAdRecharge.get(position);
        u.setHPCoin(u.getHPCoin() + money);
        mData.child("Customers").child(u.getUid()).setValue(u);


    }

    private void AnhXa() {
        arrayAdRecharge.add(new User("1713015", "Nguyễn Đức Anh", "Tài"));
        arrayAdRecharge.add(new User("1711445", "Đoàn Thái", "Học"));
        arrayAdRecharge.add(new User("1712281", "Lê Thị Kim", "Ngân"));
        arrayAdRecharge.add(new User("1714050", "Nguyễn Khải", "Vy"));
        arrayAdRecharge.add(new User("1711354", "Hà Huy", "Hiệu"));
    }
}
