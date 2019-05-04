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

import java.util.ArrayList;
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

        holder.txtMssv.setText(String.format("MSSV : %s", String.valueOf(adRecharge.getMssv())));
        holder.txtName.setText(String.format("%s", adRecharge.getLastName()));

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
                User temp = dataSnapshot.getValue(User.class);
                assert temp != null;
                arrayAdRecharge.add(new User(temp.getMssv(),temp.getFirstName(), temp.getLastName()));
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
            //Toast.makeText(getBaseContext(),"Chỉnh sửa item " + arrayAdItem.get(position).getName(), Toast.LENGTH_SHORT).show();
            //Option(position);


            Dialog_click_item(position);
        });

        return view;
    }

    private void Dialog_click_item(final int position){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Hỏi lại chắc chắn!!!");
        alertDialog.setMessage("Nạp tiền cho MSSV " + arrayAdRecharge.get(position).getMssv() + "???");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mssv = String.valueOf(txtMssv.getText());
                money = 0;
                money = Integer.valueOf(txtMoney.getText().toString());
                Nap_tien(position);
                //Toast.makeText(getContext(),"Bạn nạp tiền thành công cho " + arrayAdRecharge.get(position).getName() + money, Toast.LENGTH_SHORT).show();
            }
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

        /*
        mData.child("Customers").child("171315")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String clubkey = dataSnapshot.getKey();


                        Toast.makeText(getContext(),clubkey,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                */

        /*
        mData.child("Customers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User temp = dataSnapshot.getValue(User.class);
                assert temp != null;
                temp.setHPCoin(temp.getHPCoin() + money);
                mData.child("Customers").push().setValue(temp.getHPCoin());
                Toast.makeText(getContext(),"aii",Toast.LENGTH_SHORT).show();
                adRechargeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        */
    }

    private void AnhXa() {
        arrayAdRecharge.add(new User("1713015", "Nguyễn Đức Anh", "Tài"));
        arrayAdRecharge.add(new User("1711445", "Đoàn Thái", "Học"));
        arrayAdRecharge.add(new User("1712281", "Lê Thị Kim", "Ngân"));
        arrayAdRecharge.add(new User("1714050", "Nguyễn Khải", "Vy"));
        arrayAdRecharge.add(new User("1711354", "Hà Huy", "Hiệu"));
    }
}
