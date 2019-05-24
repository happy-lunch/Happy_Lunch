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


public class AdMyWork extends Fragment {

    private ListView lvAdWork;
    public static ArrayList<BagRow> arrayAdMyWork = new ArrayList<>();
    public static BagAdapter adMyWorkAdapter;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ad_work, container, false);

        lvAdWork = view.findViewById(R.id.list_adWork);


        adMyWorkAdapter = new BagAdapter(getContext(), R.layout.element_bag, arrayAdMyWork);
        lvAdWork.setAdapter(adMyWorkAdapter);

        lvAdWork.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(getContext(),"Đã hoàn thành " + arrayAdMyWork.get(position).getCount() + " " + arrayAdMyWork.get(position).getName(), Toast.LENGTH_SHORT).show();
            arrayAdMyWork.remove(position);
            adMyWorkAdapter.notifyDataSetChanged();

        });




        return view;
    }



}
