package com.cnpm.happylunch;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
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

import java.util.ArrayList;
import java.util.Objects;


public class AdWork extends Fragment {

    private ListView lvAdWork;
    public static ArrayList<Order> arrayOrder = new ArrayList<>();
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

        //AnhXa();
        adWorkAdapter = new BagAdapter(getContext(), R.layout.element_bag, arrayAdWork);
        lvAdWork.setAdapter(adWorkAdapter);

        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Order").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Order order = dataSnapshot.getValue(Order.class);
                if (order != null){
                    arrayOrder.add(order);
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

        lvAdWork.setOnItemClickListener((parent, view, position, id) -> {
            if (arrayAdWork.get(position).getStatus() == "Đang chế biến"){
                Toast.makeText(getContext(),"Đã hoàn thành " + arrayAdWork.get(position).getCount() + " " + arrayAdWork.get(position).getName(), Toast.LENGTH_SHORT).show();
                arrayAdWork.remove(position);
            }
            else {

                Dialog_click_item(position);
            }

        });




        return view;
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
            if (count[0] > 0){
                BagRow new_row = new BagRow(temp.getImg(), temp.getName(), temp.getTime(), count[0]);
                if (count[0] == temp.getCount()){
                    AdWork.arrayAdWork.remove(position);
                }
                AdWork.arrayAdWork.add(0,new_row);
                AdWork.adWorkAdapter.notifyDataSetChanged();
            }

            dialog.cancel();
        });

        dialog.show();
    }

    private void AnhXa(){
        arrayAdWork.add(new BagRow(R.drawable.ck_banh_bao_ba_xiu_2,   "Bánh bao xá xíu 2",
                "9:50",3));
        arrayAdWork.add(new BagRow(R.drawable.ck_com_chien,           "Cơm chiên",
                "9:45",2));
        arrayAdWork.add(new BagRow(R.drawable.ck_fruit_whole_nodish,  "Fruit whole nodish",
                "9:30",3));
        arrayAdWork.add(new BagRow(R.drawable.ck_salad_caron,         "Salad caron",
                "9:20",5));
        arrayAdWork.add(new BagRow(R.drawable.ck_single_banana,       "Single banana",
                "8:55",1));
    }
}
