package com.cnpm.happylunch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

class AccBillElement {
    String txtType, txtId, txtMoney, txtTime;

    public AccBillElement(Bill bill) {
        this.txtType = "Order";
        this.txtId = bill.getId();
        this.txtMoney = String.valueOf(bill.getPrice());
        this.txtTime = bill.getTime();
    }

    public AccBillElement(BillResell billResell){
        this.txtType = "Resell";
        this.txtId = billResell.getId();
        this.txtMoney = String.valueOf(billResell.getCost());
        this.txtTime = billResell.getTime();
    }

    public int getTime(){
        return 24*60*Integer.valueOf(this.txtTime.substring(0,2)) + 60*Integer.valueOf(this.txtTime.substring(3,5)) + Integer.valueOf(this.txtTime.substring(6,8));
    }

    public String getTxtType() {
        return txtType;
    }

    public void setTxtType(String txtType) {
        this.txtType = txtType;
    }

    public String getTxtId() {
        return txtId;
    }

    public void setTxtId(String txtId) {
        this.txtId = txtId;
    }

    public String getTxtMoney() {
        return txtMoney;
    }

    public void setTxtMoney(String txtMoney) {
        this.txtMoney = txtMoney;
    }

    public String getTxtTime() {
        return txtTime;
    }

    public void setTxtTime(String txtTime) {
        this.txtTime = txtTime;
    }
}

class AccBillAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<AccBillElement> billList;

    public AccBillAdapter(Context context, int layout, List<AccBillElement> bagList) {
        this.context = context;
        this.layout = layout;
        this.billList = bagList;
    }

    @Override
    public int getCount() {
        return billList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtType, txtId, txtMoney, txtTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.txtType    = convertView.findViewById(R.id.textView_accBill_type);
            holder.txtId   = convertView.findViewById(R.id.textView_accBill_id);
            holder.txtMoney   = convertView.findViewById(R.id.textView_accBill_money);
            holder.txtTime  = convertView.findViewById(R.id.textView_accBill_time);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        AccBillElement bill = billList.get(position);

        holder.txtType.setText(bill.getTxtType());
        holder.txtTime.setText(bill.getTxtTime());
        holder.txtId.setText(String.valueOf(bill.getTxtId()));
        holder.txtMoney.setText(bill.getTxtMoney());

        return convertView;
    }
}

public class AccountBill extends AppCompatActivity {

    private ListView lvBill;
    private AccBillAdapter billAdapter;

    private DatabaseReference mData;

    private ArrayList<AccBillElement> arrayBill = new ArrayList<>();
    private ArrayList<Bill> arrayOrder = new ArrayList<>();
    private ArrayList<BillResell> arrayBillResell = new ArrayList<>();
    private ArrayList<FoodResell> arrayResell = new ArrayList<>();

    private boolean isTime=true, isMoney=false, isType = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        billAdapter = new AccBillAdapter(this, R.layout.account_bill_element, arrayBill);
        lvBill = findViewById(R.id.list_AccountBill);
        lvBill.setAdapter(billAdapter);

        mData = FirebaseDatabase.getInstance().getReference();
        getData();
        sort("timeDown");

        Button btnTime = findViewById(R.id.button_accBill_time);
        Button btnMoney = findViewById(R.id.button_accBill_money);
        Button btnType = findViewById(R.id.button_accBill_type);

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTime) sort("timeUp");
                else sort("timeDown");
            }
        });

        btnMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMoney) sort("moneyUp");
                else sort("moneyDown");
            }
        });

        btnType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isType) sort("resell");
                else sort("order");
            }
        });

        lvBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i=0;i<arrayOrder.size();i++){
                    if(arrayBill.get(position).getTxtId().equals(arrayOrder.get(i).getId())){
                        BillDetail.text = new String[]{"Order Bill", arrayBill.get(position).txtId, arrayBill.get(position).getTxtTime(), "Price: "+arrayBill.get(position).getTxtMoney(), arrayOrder.get(i).getStatus()};
                        for(int j=0;j<arrayOrder.get(i).item.size();j++){
                            if(BillDetail.arrayBillItem.size()>0)
                                BillDetail.arrayBillItem.removeAll(BillDetail.arrayBillItem);
                            BillDetail.arrayBillItem.add(new BagRow(arrayOrder.get(i).item.get(j)));
                        }
                        startActivity(new Intent(getBaseContext(), BillDetail.class));
                    }
                }
                for (int i=0;i<arrayBillResell.size();i++){
                    if(arrayBill.get(position).getTxtId().equals(arrayBillResell.get(i).getId())) {

                        if(BillDetail.arrayBillItem.size()>0)
                            BillDetail.arrayBillItem.removeAll(BillDetail.arrayBillItem);

                        String status ="Đã bán hết";
                        for(int j=0;j<arrayBillResell.get(i).getItem().size();j++){
                            for(int k=0;k<arrayResell.size();k++){
                                if(arrayBillResell.get(i).getItem().get(j).getIdResell().equals(arrayResell.get(k).getId())){
                                    status = "Đang bán";
                                    break;
                                }
                            }
                            BillDetail.arrayBillItem.add(new BagRow(arrayBillResell.get(i).item.get(j)));
                        }
                        BillDetail.text = new String[]{"Resell Bill", arrayBill.get(position).txtId, arrayBill.get(position).getTxtTime(), "Cost: "+arrayBill.get(position).getTxtMoney(), status};
                        startActivity(new Intent(getBaseContext(), BillDetail.class));
                    }
                }
            }
        });

    }

    private void getData(){
        mData.child("Bill").child(App.user.getMssv()).child("Order").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Bill bill = dataSnapshot.getValue(Bill.class);
                if (bill!=null){
                    arrayOrder.add(bill);
                    arrayBill.add(new AccBillElement(bill));
                    billAdapter.notifyDataSetChanged();

                }
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

        mData.child("Bill").child(App.user.getMssv()).child("Resell").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BillResell billResell = dataSnapshot.getValue(BillResell.class);
                if (billResell!=null){
                    arrayBillResell.add(billResell);
                    arrayBill.add(new AccBillElement(billResell));
                    billAdapter.notifyDataSetChanged();

                }
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

        mData.child("Resell").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FoodResell resell = dataSnapshot.getValue(FoodResell.class);
                if (resell!=null){
                    arrayResell.add(resell);
                    billAdapter.notifyDataSetChanged();

                }
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
    }

    private void sort(String caseSort){
        for(int i=0;i<arrayBill.size();i++){
            for(int j=i+1;j<arrayBill.size();j++) {
                switch (caseSort){
                    case "timeDown":
                        set();
                        isTime = true;
                        if(arrayBill.get(i).getTxtTime().compareTo(arrayBill.get(j).getTxtTime())<0){
                        //if(arrayBill.get(i).getTime() < arrayBill.get(j).getTime()){
                            swap(i,j);
                        }
                        break;
                    case "timeUp":
                        set();
                        if(arrayBill.get(i).getTxtTime().compareTo(arrayBill.get(j).getTxtTime())>0){
                            swap(i,j);
                        }
                        break;
                    case "moneyDown":
                        set();
                        isMoney = true;
                        if(Integer.valueOf(arrayBill.get(i).getTxtMoney()) < Integer.valueOf(arrayBill.get(j).getTxtMoney())){
                            swap(i,j);
                        }
                        break;
                    case "moneyUp":
                        set();
                        if(Integer.valueOf(arrayBill.get(i).getTxtMoney()) > Integer.valueOf(arrayBill.get(j).getTxtMoney())){
                            swap(i,j);
                        }
                        break;
                    case "order":
                        set();
                        isType=true;
                        if(arrayBill.get(i).getTxtType().equals("Resell") && arrayBill.get(j).getTxtType().equals("Order")){
                            swap(i,j);
                        }
                        break;
                    case "resell":
                        set();
                        if(arrayBill.get(i).getTxtType().equals("Order") && arrayBill.get(j).getTxtType().equals("Resell")){
                            swap(i,j);
                        }
                        break;
                }
            }
        }
        if (caseSort.equals("order")){
            for(int i=0;i<arrayOrder.size();i++){
                for(int j=1;j<arrayOrder.size();j++){
                    if(arrayBill.get(i).getTxtTime().compareTo(arrayBill.get(j).getTxtTime())<0){
                        swap(i,j);
                    }
                }
            }
            for(int i=arrayOrder.size();i<arrayBill.size();i++){
                for(int j=i+1;j<arrayOrder.size();j++){
                    if(arrayBill.get(i).getTxtTime().compareTo(arrayBill.get(j).getTxtTime())<0){
                        swap(i,j);
                    }
                }
            }
        }
        if (caseSort.equals("resell")){
            for(int i=0;i<arrayBillResell.size();i++){
                for(int j=1;j<arrayOrder.size();j++){
                    if(arrayBill.get(i).getTxtTime().compareTo(arrayBill.get(j).getTxtTime())<0){
                        swap(i,j);
                    }
                }
            }
            for(int i=arrayBillResell.size();i<arrayBill.size();i++){
                for(int j=i+1;j<arrayOrder.size();j++){
                    if(arrayBill.get(i).getTxtTime().compareTo(arrayBill.get(j).getTxtTime())<0){
                        swap(i,j);
                    }
                }
            }
        }
        billAdapter.notifyDataSetChanged();
    }

    private void set(){
        isMoney = false;
        isTime = false;
        isType = false;
    }

    private void swap(int a, int b){
        AccBillElement c = arrayBill.get(a);
        arrayBill.set(a, arrayBill.get(b));
        arrayBill.set(b,c);
    }
}
