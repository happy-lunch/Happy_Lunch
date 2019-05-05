package com.cnpm.happylunch;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;


public class AdWork extends Fragment {

    private ListView lvAdWork;
    public volatile ArrayList<BagRow> arrayAdWork = new ArrayList<>();
    private BagAdapter adWorkAdapter;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_bag, container, false);

        lvAdWork = view.findViewById(R.id.list_bag);
        //arrayAdWork = new ArrayList<>();
        //AnhXa();
        adWorkAdapter = new BagAdapter(getContext(), R.layout.element_bag, arrayAdWork);
        lvAdWork.setAdapter(adWorkAdapter);

        lvAdWork.setOnItemClickListener((parent, view, position, id) -> {
            if (arrayAdWork.get(position).getStatus() == R.drawable.ic_favorite_black_24dp){
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
                BagRow new_row = new BagRow(temp.getImg(), temp.getName(), temp.getTime(), count[0],R.drawable.ic_favorite_black_24dp);
                if (count[0] == temp.getCount()){
                    arrayAdWork.remove(position);
                }
                arrayAdWork.add(0,new_row);
                adWorkAdapter.notifyDataSetChanged();
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
        arrayAdWork.add(new BagRow(R.drawable.ck_trung_cut,           "Trứng cút",
                "8:45",4));
        arrayAdWork.add(new BagRow(R.drawable.ck_salad_caron,         "Salad caron",
                "8:40",2));
        arrayAdWork.add(new BagRow(R.drawable.ck_salad_caron,         "Salad caron",
                "8:40",1));
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
        arrayAdWork.add(new BagRow(R.drawable.ck_trung_cut,           "Trứng cút",
                "8:45",4));
        arrayAdWork.add(new BagRow(R.drawable.ck_salad_caron,         "Salad caron",
                "8:40",2));
        arrayAdWork.add(new BagRow(R.drawable.ck_salad_caron,         "Salad caron",
                "8:40",1));
    }
}
