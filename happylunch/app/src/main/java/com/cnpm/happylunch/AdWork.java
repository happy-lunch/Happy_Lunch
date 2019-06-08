package com.cnpm.happylunch;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class AdWork extends Fragment {

    private ListView lvAdWork;
    public static ArrayList<Order> arrayOrder = new ArrayList<>();
    public static ArrayList<BillRoot> arrayMSSV = new ArrayList<>();
    public static ArrayList<Bill> arrayBill = new ArrayList<>();
    public static ArrayList<BagRow> arrayAdWork = new ArrayList<>();
    public static BagAdapter adWorkAdapter;

    private View view;
    private DatabaseReference mData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ad_work, container, false);

        lvAdWork = view.findViewById(R.id.list_adWork);


        adWorkAdapter = new BagAdapter(getContext(), R.layout.element_bag, arrayAdWork);
        lvAdWork.setAdapter(adWorkAdapter);



        mData = FirebaseDatabase.getInstance().getReference();

        get_data();

        lvAdWork.setOnItemClickListener((parent, view, position, id) -> {
            Dialog_click_item(position);


        });




        return view;
    }

    private void get_data(){
        mData.child("Order").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Lấy mảng order về và sort theo time
                Order order = dataSnapshot.getValue(Order.class);
                if (order != null){
                    Log.e("1232132111112222222",order.getMSSV());
                    arrayOrder.add(order);
                    sortTime(arrayOrder);
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

        mData.child("Bill").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                RootBill order = dataSnapshot.getValue(RootBill.class);
                if (order != null){
                    //arrayMSSV.add(order);
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

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<arrayOrder.size();i++) {
                    final int a = i;
                    Log.e("AAAAAAAAAAAAAAAAAAAA",String.valueOf(i));
                    mData.child("Bill").child(arrayOrder.get(i).getMSSV()).child("Order").child(arrayOrder.get(i).getBill()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.e("AAAAAAAAAAAAAAAAAAAA","AAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                            Bill bill = dataSnapshot.getValue(Bill.class);
                            for (int j = 0; j < bill.item.size(); j++) {
                                arrayAdWork.add(new BagRow(bill.item.get(j), bill.getTime(),arrayOrder.get(a)));
                            }
                            adWorkAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        },2000);

        /*
        mData.child("Bill").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //Lấy mảng order về và sort theo time
                BillRoot order = dataSnapshot.getValue(BillRoot.class);
                if (order != null){
                    arrayMSSV.add(order);
                    for(int i=0;i<arrayMSSV.size();i++){
                        for(int j=0;j<arrayMSSV.get(i).getMssv().getClassOder().getArrayBill().size();j++){
                            Bill bill = arrayMSSV.get(i).getMssv().getClassOder().getArrayBill().get(j);
                            if (bill.getStatus().equals("Đang xử lí")){
                                arrayBill.add(bill);
                                for(int k=0;k<bill.item.size();k++){
                                    arrayAdWork.add(new BagRow(bill.item.get(k)));
                                }
                                adWorkAdapter.notifyDataSetChanged();
                            }
                        }

                    }
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
        });*/
    }

    private void sortTime(ArrayList<Order> arrayList) {

        for(int i=0;i<arrayList.size();i++) {
            for (int j = i + 1; j < arrayList.size(); j++) {
                if(arrayList.get(i).getTime().compareTo(arrayList.get(j).getTime())<0){
                    Order c = arrayList.get(i);
                    arrayList.set(i, arrayList.get(j));
                    arrayList.set(j,c);
                }
            }
        }
        /*
        int i,j;
        for (i = 1; i < arrayList.size(); i++) {
            Order key = arrayList.get(i);
            j = i;

            int time0 = get_time(arrayList.get(j-1).getTime());
            int time1 = get_time(arrayList.get(i).getTime());
            while((j > 0) && (time0 > time1)) {
                arrayList.set(j,arrayList.get(j - 1));
                time0 = get_time(arrayList.get(--j-1).getTime());
            }
            arrayList.set(j,key);
        }*/
    }

    private int get_time(String s){
        return 24*60*Integer.valueOf(s.substring(0, 2)) + 60*Integer.valueOf(s.substring(3, 5)) + Integer.valueOf(s.substring(6, 8));
    }

    private void Dialog_click_item(final int position){
        final Dialog dialog = new Dialog(Objects.requireNonNull(getContext()));
        dialog.setContentView(R.layout.ad_work_dialog);
        dialog.setTitle("Bạn muốn làm bao nhiêu " + arrayAdWork.get(position).getName() + "???");

        SeekBar seekBar      = dialog.findViewById(R.id.seekBar_adWork);
        TextView textView    = dialog.findViewById(R.id.textView_adWork_dialog);
        Button button        = dialog.findViewById(R.id.button_adWork_dialog);

        BagRow temp = arrayAdWork.get(position);
        textView.setText((String.valueOf(temp.getCount())));

        final int[] count = new int[1];
        count[0] = temp.getCount();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                count[0] = (int) ((float)(seekBar.getProgress()*temp.getCount()))/seekBar.getMax();
                textView.setText(String.valueOf(count[0]));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button.setOnClickListener(v -> {
            Log.e("GGGGGGGGGGGGGGGGGGGGGG","GGGGGGGGGGGGGGGGG");
            if (count[0] > 0){
                BagRow new_row = new BagRow(temp, count[0], temp.getIdUser(), temp.getIdBIll());
                if (count[0] == temp.getCount()){
                    Log.e(String.valueOf(count[0]), String.valueOf(temp.getCount()));
                    arrayAdWork.remove(position);
                }
                else {
                    Log.e(String.valueOf(count[0]), String.valueOf(temp.getCount()));
                    arrayAdWork.get(position).setCount(temp.getCount() - count[0]);
                }
                adWorkAdapter.notifyDataSetChanged();
                Log.e(String.valueOf(count[0]), String.valueOf(temp.getCount()));

                AdMyWork.arrayAdMyWork.add(new_row);
                AdMyWork.adMyWorkAdapter.notifyDataSetChanged();
            }

            dialog.cancel();
        });

        dialog.show();
    }
}
