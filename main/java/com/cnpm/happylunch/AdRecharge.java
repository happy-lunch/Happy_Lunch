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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class AdRechargeElement {

    private int mssv;
    private String name;

    AdRechargeElement (int mssv, String name){
        this.mssv = mssv;
        this.name = name;
    }

    public void setMssv(int iMssv) {
        mssv = iMssv;
    }

    public void setName(String iName) {
        name = iName;
    }
    public int getMssv() {
        return mssv;
    }

    public String getName() {
        return name;
    }
}

class AdRechargeAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<AdRechargeElement> adRechargeList;

    AdRechargeAdapter(Context context, int layout, List<AdRechargeElement> adRechargeList) {
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

        AdRechargeElement adRecharge = adRechargeList.get(position);

        holder.txtMssv.setText(String.format("MSSV : %s", String.valueOf(adRecharge.getMssv())));
        holder.txtName.setText(adRecharge.getName());

        return convertView;
    }
}

public class AdRecharge extends Fragment {

    private GridView gvAdRecharge;
    private ArrayList<AdRechargeElement> arrayAdRecharge;
    private AdRechargeAdapter adRechargeAdapter;
    private DatabaseReference mData;
    private int mssv;
    private int money;

    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ad_recharge, container, false);

        gvAdRecharge = view.findViewById(R.id.grid_adRecharge);
        arrayAdRecharge = new ArrayList<>();

        mssv = view.findViewById(R.id.editText_adRecharge_mssv).getBaseline();
        money = view.findViewById(R.id.editText_adRecharge_money).getBaseline();


        //AnhXa();
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Customers").child(App.user.getMssv()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                //user.setHPCoin(user.getHPCoin() + money);
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String fullName  = user.firstName + " " + user.lastName;
                    AdRecharge.add(new AdRechargeElement(user.mssv, fullname));
                }
                insertionSort(AdRecharge);
                adRechargeAdapter.notifyDataSetChanged();

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

        adRechargeAdapter = new AdRechargeAdapter(getContext(), R.layout.ad_recharge_element, arrayAdRecharge);
        gvAdRecharge.setAdapter(adRechargeAdapter);

        gvAdRecharge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(),"Chỉnh sửa item " + arrayAdItem.get(position).getName(), Toast.LENGTH_SHORT).show();
                //Option(position);


                Dialog_click_item(position);
            }
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
                //nạp tiền
                Toast.makeText(getContext(),"Bạn nạp tiền thành công cho " + arrayAdRecharge.get(position).getName() + money, Toast.LENGTH_SHORT).show();
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

    public static void insertionSort(ArrayList<AdRechargeElement> Array) {

        int i,j;

        for (i = 1; i < Array.size(); i++) {
            AdRechargeElement key = new AdRechargeElement(0, " ");
            key.setMssv(Array.get(i).mssv);
            key.setName(Array.get(i).name);
            j = i;
            while((j > 0) && (Array.get(j - 1).mssv > key.mssv)) {
                Array.set(j,Array.get(j - 1));
                j--;
            }
            Array.set(j,key);
        }
    }

    /*private boolean updateMoney(String uId, int money) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Customers").child(id);

        //updating artist
        User user = new User();
        return true;
    }*/


    private void AnhXa() {
        arrayAdRecharge.add(new AdRechargeElement(1713015, "Nguyễn Đức Anh Tài"));
        arrayAdRecharge.add(new AdRechargeElement(1711445, "Đoàn Thái Học"));
        arrayAdRecharge.add(new AdRechargeElement(1712281, "Lê Thị Kim Ngân"));
        arrayAdRecharge.add(new AdRechargeElement(1714050, "Nguyễn Khải Vy"));
        arrayAdRecharge.add(new AdRechargeElement(1711354, "Hà Huy Hiệu"));
    }
}
