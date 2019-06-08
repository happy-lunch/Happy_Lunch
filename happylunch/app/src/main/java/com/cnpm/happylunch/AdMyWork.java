package com.cnpm.happylunch;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
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


public class AdMyWork extends Fragment {

    private ListView lvAdWork;
    public static ArrayList<BagRow> arrayAdMyWork = new ArrayList<>();
    public static BagAdapter adMyWorkAdapter;

    private View view;

    private DatabaseReference mData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ad_work, container, false);

        lvAdWork = view.findViewById(R.id.list_adWork);

        mData = FirebaseDatabase.getInstance().getReference();

        adMyWorkAdapter = new BagAdapter(getContext(), R.layout.element_bag, arrayAdMyWork);
        lvAdWork.setAdapter(adMyWorkAdapter);

        lvAdWork.setOnItemClickListener((parent, view, position, id) -> {
            //Toast.makeText(getActivity(),"Đã hoàn thành " + arrayAdMyWork.get(position).getCount() + " " + arrayAdMyWork.get(position).getName(), Toast.LENGTH_SHORT).show();

            mData.child("Bill").child(arrayAdMyWork.get(position).getIdUser()).child("Order")
                    .child(arrayAdMyWork.get(position).getIdBIll()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Bill bill = dataSnapshot.getValue(Bill.class);
                    int index =0;
                    Log.e("111111111111111111111",bill.getId());
                    for(int i=0;i<bill.item.size();i++){
                        Log.e("22222222222222222222222", arrayAdMyWork.get(position).getIdFood());
                        if(arrayAdMyWork.get(position).getIdFood().equals(bill.item.get(i).getId())){
                            if(arrayAdMyWork.get(position).getCount() == bill.item.get(i).getNum()){
                                mData.child("Bill").child(arrayAdMyWork.get(position).getIdUser()).child("Order")
                                        .child(arrayAdMyWork.get(position).getIdBIll()).child("item")
                                        .child(String.valueOf(i)).child("status").setValue("Đã hoàn thành");
                            }
                            else{
                                mData.child("Bill").child(arrayAdMyWork.get(position).getIdUser()).child("Order")
                                        .child(arrayAdMyWork.get(position).getIdBIll()).child("item")
                                        .child(String.valueOf(i)).child("status").setValue(String.format("Đã làm %s",arrayAdMyWork.get(position).getCount()));
                            }
                            arrayAdMyWork.remove(position);
                            adMyWorkAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });





        });




        return view;
    }



}
