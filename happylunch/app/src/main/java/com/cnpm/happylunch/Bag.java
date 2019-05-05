package com.cnpm.happylunch;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class BagRow {
    private int img;
    private String name;
    private String time;
    private int count;
    private int status;
    private int price;

    BagRow(SecondShopElement item){
        this.img = item.getImg();
        this.name = item.getName();
        this.price = item.getPrice();
        this.time = item.getTime();
        this.status = R.drawable.ic_clear_black_18dp;
        this.count = 1;
    }

    BagRow(Food food){
        this.img = food.getFoodImg();
        this.name = food.getFoodName();
        this.time = "10:11";
        this.count = food.getNumSold();
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
            holder.imgStatus = convertView.findViewById(R.id.imageView_bag_status);
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
        holder.imgStatus.setImageResource(bag.getStatus());

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
        lvBag.setAdapter(bagAdapter);


        lvBag.setOnItemClickListener((parent, view, position, id) -> {
            if (arrayBag.get(position).getStatus() == R.drawable.ic_clear_black_18dp){
                Dialog_click_tra_mon(position);

            }
            else {
                Dialog_click_chuyen_mon(position);
            }
        });


        return view;
    }

    private void Dialog_click_tra_mon(final int position){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Cảnh báo!!!");
        alertDialog.setMessage("Bạn muốn trả lại " + arrayBag.get(position).getCount() + " " + arrayBag.get(position).getName() + "(phí 10%)???");

        alertDialog.setPositiveButton("Yes", (dialog, which) -> {
            Toast.makeText(getContext(),"Bạn đã mất " + (int) (((float) arrayBag.get(position).getPrice())/10) + "đ tiền phí giao dịch!", Toast.LENGTH_SHORT).show();
            Bottom_Nav.secondShop.arraySecondShop.add(new SecondShopElement(arrayBag.get(position)));
            arrayBag.remove(position);
            bagAdapter.notifyDataSetChanged();
        });

        alertDialog.setNegativeButton("No", (dialog, which) -> Toast.makeText(getContext(),"Cẩn thận đấy!!!", Toast.LENGTH_SHORT).show());

        alertDialog.show();
    }

    private void Dialog_click_chuyen_mon(final int position){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Cảnh báo!!!");
        alertDialog.setMessage("Đồ bạn đặt đã được Circle K làm xong, bạn không thể trả. Bạn có muốn bán lại " + arrayBag.get(position).getCount() + " " + arrayBag.get(position).getName() + "???");

        alertDialog.setPositiveButton("Yes", (dialog, which) -> {
            Toast.makeText(getContext(),"Bạn đã bán lại " + arrayBag.get(position).getCount() + " " + arrayBag.get(position).getName(), Toast.LENGTH_SHORT).show();
            arrayBag.remove(position);
            bagAdapter.notifyDataSetChanged();
        });

        alertDialog.setNegativeButton("No", (dialog, which) -> Toast.makeText(getContext(),"Cẩn thận đấy!!!", Toast.LENGTH_SHORT).show());

        alertDialog.show();
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
