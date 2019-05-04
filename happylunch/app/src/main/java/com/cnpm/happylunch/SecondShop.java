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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class SecondShopElement {

    private int img;
    private String name;
    private int price;
    private String time;

    SecondShopElement (int img, String name, int price, String time){
        this.img = img;
        this.name = name;
        this.price = price;
        this.time = time;
    }

    SecondShopElement(BagRow bag){
        this.img = bag.getImg();
        this.name = bag.getName();
        this.price = (int) (((float) bag.getPrice())*9/10);
        this.time = bag.getTime();
    }

    public int getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }
}

class SecondShopAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<SecondShopElement> secondShopList;

    SecondShopAdapter(Context context, int layout, List<SecondShopElement> secondShopList) {
        this.context = context;
        this.layout = layout;
        this.secondShopList = secondShopList;
    }


    @Override
    public int getCount() { return secondShopList.size(); }

    @Override
    public Object getItem(int position) { return null; }

    @Override
    public long getItemId(int position) { return 0; }

    private class ViewHolder{
        ImageView imgImg;
        TextView txtName, txtPrice, txtTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.imgImg    = convertView.findViewById(R.id.imageView_secondShop);
            holder.txtName   = convertView.findViewById(R.id.textView_secondShop_name);
            holder.txtPrice  = convertView.findViewById(R.id.textView_secondShop_price);
            holder.txtTime   = convertView.findViewById(R.id.textView_secondShop_time);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        SecondShopElement secondShop = secondShopList.get(position);

        holder.imgImg.setImageResource(secondShop.getImg());
        holder.txtName.setText(secondShop.getName());
        holder.txtPrice.setText(String.format("Price : %s", String.valueOf(secondShop.getPrice())));
        holder.txtTime.setText(String.format("Time : %s", secondShop.getTime()));

        return convertView;
    }
}

public class SecondShop extends Fragment {

    private GridView gvSecondShop;
    public volatile ArrayList<SecondShopElement> arraySecondShop = new ArrayList<>();

    private SecondShopAdapter secondShopAdapter;

    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.second_shop, container, false);

        gvSecondShop = view.findViewById(R.id.grid_second_shop);
        //AnhXa();

        secondShopAdapter = new SecondShopAdapter(getContext(), R.layout.second_shop_element, arraySecondShop);
        gvSecondShop.setAdapter(secondShopAdapter);

        gvSecondShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Dialog_click_item(position);
            }
        });

        return view;
    }

    private void Dialog_click_item(final int position){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Hỏi lại chắc chắn!!!");
        alertDialog.setMessage("Bạn muốn mua " + arraySecondShop.get(position).getName() + "???");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Giao dịch thành công !!!", Toast.LENGTH_SHORT).show();

                //SecondShopElement temp = arraySecondShop.get(position);
                Bottom_Nav.bag.arrayBag.add(new BagRow(arraySecondShop.get(position)));
                //arrayBag.add(new BagRow(temp.getImg(),temp.getName(),temp.getTime(),1,R.drawable.ic_clear_black_18dp));
                arraySecondShop.remove(position);
                secondShopAdapter.notifyDataSetChanged();
                //bagAdapter.notifyDataSetChanged();
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



    private void AnhXa() {
        arraySecondShop.add(new SecondShopElement(R.drawable.ck_banh_bao_ba_xiu_2,   "Bánh bao xá xíu 2",
                15000,"9:50"));
        arraySecondShop.add(new SecondShopElement(R.drawable.ck_com_chien,           "Cơm chiên",
                15000,"9:30"));
        arraySecondShop.add(new SecondShopElement(R.drawable.ck_fruit_whole_nodish,  "Fruit whole nodish",
                20000,"9:10"));
        arraySecondShop.add(new SecondShopElement(R.drawable.ck_salad_caron,         "Salad caron",
                18000,"9:00"));
        arraySecondShop.add(new SecondShopElement(R.drawable.ck_single_banana,       "Single banana",
                12000,"8:50"));
    }
}
