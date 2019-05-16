package com.cnpm.happylunch;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class BagRow {
    private int img;
    private String name;
    private String time;
    private int count;
    private int status;
    private int price;
    private String id = "null";

    BagRow(){

    }

    BagRow(BagRow food){
        this.img = food.getImg();
        this.name = food.getName();
        this.time = food.time;
        this.count = food.count;
        this.status = R.drawable.ic_clear_black_18dp;
        this.price = food.getPrice();
    }

    BagRow(BagRow food, int num){
        this.img = food.getImg();
        this.name = food.getName();
        this.time = food.time;
        this.count = num;
        this.status = R.drawable.ic_clear_black_18dp;
        this.price = food.getPrice();
    }

    BagRow(Food food, int num){
        this.img = food.getFoodImg();
        this.name = food.getFoodName();
        this.time = "10:11";
        this.count = num;
        this.status = R.drawable.ic_clear_black_18dp;
        this.price = Integer.valueOf(food.getFoodPrice());
    }

    BagRow(int img, String name, String time, int count, int status) {
        this.img = img;
        this.name = name;
        this.time = time;
        this.count = count;
        this.status = status;
    }

    BagRow(int img, String name, String time, int count) {
        this.img = img;
        this.name = name;
        this.time = time;
        this.count = count;
        this.status = R.drawable.ic_favorite_border_black_24dp;
    }

    int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setId(String id) {this.id = id;}

    public String getId(){return id;}
}

class BagAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<BagRow> bagList;

    public BagAdapter(Context context, int layout, List<BagRow> bagList) {
        this.context = context;
        this.layout = layout;
        this.bagList = bagList;
    }

    @Override
    public int getCount() {
        return bagList.size();
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
        ImageView imgImg, imgStatus;
        TextView txtName, txtTime, txtCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.imgImg    = convertView.findViewById(R.id.imageView_bag_img);
            holder.txtName   = convertView.findViewById(R.id.textView_bag_name);
            holder.txtTime   = convertView.findViewById(R.id.textView_bag_time);
            holder.txtCount  = convertView.findViewById(R.id.textView_bag_count);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        BagRow bag = bagList.get(position);

        holder.imgImg.setImageResource(bag.getImg());
        holder.txtName.setText(bag.getName());
        holder.txtTime.setText(bag.getTime());
        holder.txtCount.setText(String.valueOf(bag.getCount()));

        return convertView;
    }
}

public class Bag extends Fragment {

    private ListView lvBag;

    public volatile ArrayList<BagRow> arrayBag = new ArrayList<>();
    private BagAdapter bagAdapter;
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_bag, container, false);

        bagAdapter = new BagAdapter(getContext(), R.layout.element_bag, arrayBag);
        lvBag = view.findViewById(R.id.list_bag);

        //AnhXa();
        Button btn_resell = view.findViewById(R.id.button_bag_resell);

        lvBag.setAdapter(bagAdapter);


        lvBag.setOnItemClickListener((parent, view, position, id) -> Dialog_click_tra_mon(position));

        btn_resell.setOnClickListener(v -> Click_btn_resell());

        return view;
    }

    private void Click_btn_resell(){

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, Bottom_Nav.bagResell).commit();

        /*
        Bottom_Nav.selectedFrameLayout.setVisibility(View.INVISIBLE);
        Bottom_Nav.flBagResell.setVisibility(View.VISIBLE);*/
        Bottom_Nav.bagResell.set_cost();
    }

    private void Dialog_click_tra_mon(final int position){
        final Dialog dialog = new Dialog(Objects.requireNonNull(getContext()));
        dialog.setContentView(R.layout.bag_dialog);
        dialog.setTitle("Chọn số lượng muốn trả");

        ImageButton btn_down= dialog.findViewById(R.id.button_bagDialog_down);
        ImageButton btn_up  = dialog.findViewById(R.id.button_bagDialog_up);
        Button btn_confirm  = dialog.findViewById(R.id.button_bagDialog);
        EditText txt        = dialog.findViewById(R.id.editText_bagDialog);

        final int[] num = {0};

        //txt.setText(0);


        btn_down.setOnClickListener(v -> {
            if (num[0] > 0)
                txt.setText(String.valueOf(--num[0]));
        });

        btn_up.setOnClickListener(v -> {
            if (num[0] < arrayBag.get(position).getCount()){
                txt.setText(String.valueOf(++num[0]));
            }
        });

        btn_confirm.setOnClickListener(v -> {
            num[0] = Integer.valueOf(String.valueOf(txt.getText()));
            if (num[0] > arrayBag.get(position).getCount()){
                Toast.makeText(getContext(),"Số lượng trả lại không lớn hơn " + arrayBag.get(position).getCount(), Toast.LENGTH_SHORT).show();
            }
            else {
                if (num[0] > 0) {
                    Toast.makeText(getContext(), "Bạn sẵn sàng trả " + num[0] + " " + arrayBag.get(position).getName(), Toast.LENGTH_SHORT).show();

                    BagRow food = new BagRow(arrayBag.get(position));
                    food.setPrice((int)(food.getPrice()*0.9));
                    food.setCount(num[0]);
                    Bottom_Nav.bagResell.arrayBagRell.add(food);
                    arrayBag.get(position).setCount(arrayBag.get(position).getCount() - num[0]);
                    if (arrayBag.get(position).getCount() == 0) {
                        arrayBag.remove(position);
                    }
                    bagAdapter.notifyDataSetChanged();
                }
                dialog.cancel();
            }
        });

        dialog.show();
    }


    private void AnhXa(){
        arrayBag.add(new BagRow(R.drawable.ck_banh_bao_ba_xiu_2,   "Bánh bao xá xíu 2",
                "9:50",3,R.drawable.ic_clear_black_18dp));
        arrayBag.add(new BagRow(R.drawable.ck_com_chien,           "Cơm chiên",
                "9:45",2,R.drawable.ic_clear_black_18dp));
        arrayBag.add(new BagRow(R.drawable.ck_fruit_whole_nodish,  "Fruit whole nodish",
                "9:30",3,R.drawable.ic_clear_black_18dp));
        arrayBag.add(new BagRow(R.drawable.ck_salad_caron,         "Salad caron",
                "9:20",1,R.drawable.ic_clear_black_18dp));
        arrayBag.add(new BagRow(R.drawable.ck_single_banana,       "Single banana",
                "8:55",1,R.drawable.ic_done_black_18dp));
        arrayBag.add(new BagRow(R.drawable.ck_trung_cut,           "Trứng cút",
                "8:45",4,R.drawable.ic_done_black_18dp));
        arrayBag.add(new BagRow(R.drawable.ck_salad_caron,         "Salad caron",
                "8:40",1,R.drawable.ic_done_black_18dp));
        arrayBag.add(new BagRow(R.drawable.ck_salad_caron,         "Salad caron",
                "8:40",1,R.drawable.ic_done_black_18dp));
        arrayBag.add(new BagRow(R.drawable.ck_single_banana,       "Single banana",
                "8:30",1,R.drawable.ic_done_black_18dp));
        arrayBag.add(new BagRow(R.drawable.ck_trung_cut,           "Trứng cút",
                "8:20",4,R.drawable.ic_done_black_18dp));
        arrayBag.add(new BagRow(R.drawable.ck_salad_caron,         "Salad caron",
                "8:00",1,R.drawable.ic_done_black_18dp));
    }
}
