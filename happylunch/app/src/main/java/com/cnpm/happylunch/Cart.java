package com.cnpm.happylunch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

class CartBillItem{

    private int num;
    private int price;
    private String id;
    private String status = "Đang xử lí";

    CartBillItem(BagRow food){
        this.id = food.getId();
        this.num = food.getCount();
        this.price = food.getPrice();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

class CartBill{

    private String id;
    private int price;
    private String time;
    private String status = "Đang xử lí";
    public List<CartBillItem> item;

    CartBill(String id, int price, String time){
        this.item = new ArrayList<>();
        this.id = id;
        this.price = price;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

class CartAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<BagRow> cartList;

    CartAdapter(Context context, int layout, List<BagRow> cartList) {
        this.context = context;
        this.layout = layout;
        this.cartList = cartList;
    }


    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        ImageView imgImg;
        TextView txtName, txtNum, txtPrice;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.imgImg = convertView.findViewById(R.id.imageView_cartElement);
            holder.txtName = convertView.findViewById(R.id.textView_cartElement_name);
            holder.txtNum = convertView.findViewById(R.id.textView_cartElement_num);
            holder.txtPrice = convertView.findViewById(R.id.textView_cartElement_price);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BagRow cart = cartList.get(position);

        holder.imgImg.setImageResource(cart.getImg());
        holder.txtName.setText(cart.getName());
        holder.txtNum.setText(String.valueOf(cart.getCount()));
        holder.txtPrice.setText(String.valueOf(cart.getPrice()));

        return convertView;
    }
}

public class Cart extends Fragment {

    private GridView gvCart;
    public volatile ArrayList<BagRow> arrayCart = new ArrayList<>();
    private CartAdapter cartAdapter;

    private View view;

    public boolean isCreate = false;
    private TextView txt_cost;
    private int cost = 0;
    private String time = "";

    private DatabaseReference mData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cart, container, false);

        isCreate = true;
        gvCart = view.findViewById(R.id.grid_cart);
        cartAdapter = new CartAdapter(getContext(), R.layout.cart_element, arrayCart);
        gvCart.setAdapter(cartAdapter);

        mData = FirebaseDatabase.getInstance().getReference();

        txt_cost = view.findViewById(R.id.textView_cart_cost);
        set_cost();

        Button btn_order = view.findViewById(R.id.button_cart_order);

        btn_order.setOnClickListener(v -> Click_btn_order());
        gvCart.setOnItemClickListener((parent, view, position, id) -> Dialog_click_item(position));

        return view;
    }

    public void set_cost(){
        if (Bottom_Nav.cart.isCreate) {
            cost();
            txt_cost.setText(String.format("Tổng đơn hàng : %s", cost));
        }
    }

    private void cost(){
        cost = 0;
        for (int i = 0; i < arrayCart.size(); i++) {
            cost += arrayCart.get(i).getPrice() * arrayCart.get(i).getCount();
        }
    }

    void Click_btn_order(){
        final Dialog dialog = new Dialog(Objects.requireNonNull(getContext()));
        dialog.setContentView(R.layout.cart_dialog);
        dialog.setTitle("Nhập giờ lấy hàng");

        ImageButton btn_day_add         = dialog.findViewById(R.id.imageButton_cartDialog_dayAdd);
        ImageButton btn_day_remove      = dialog.findViewById(R.id.imageButton_cartDialog_dayRemove);
        ImageButton btn_hour_add        = dialog.findViewById(R.id.imageButton_cartDialog_hourAdd);
        ImageButton btn_hour_remove     = dialog.findViewById(R.id.imageButton_cartDialog_hourRemove);
        ImageButton btn_minute_add      = dialog.findViewById(R.id.imageButton_cartDialog_minuteAdd);
        ImageButton btn_minute_remove   = dialog.findViewById(R.id.imageButton_cartDialog_minuteRemove);
        EditText txt_day    = dialog.findViewById(R.id.editText_cartDialog_day);
        EditText txt_hour   = dialog.findViewById(R.id.editText_cartDialog_hour);
        EditText txt_minute = dialog.findViewById(R.id.editText_cartDialog_minute);
        Button btn_order    = dialog.findViewById(R.id.button_cartDialog_order);

        Calendar calendar = Calendar.getInstance();

        final int[] day = {calendar.get(Calendar.DATE)};
        final int[] hour = {calendar.get(Calendar.HOUR_OF_DAY)};
        final int[] minute = {calendar.get(Calendar.MINUTE)};

        txt_day.setText(String.valueOf(day[0]));
        txt_hour.setText(String.valueOf(hour[0]));
        txt_minute.setText(String.valueOf(minute[0]));

        btn_day_add.setOnClickListener(v -> {
            if (day[0] < 32) txt_day.setText(String.valueOf(++day[0]));
        });

        btn_day_remove.setOnClickListener(v -> {
            if (day[0] > 2) txt_day.setText(String.valueOf(--day[0]));
        });

        btn_hour_add.setOnClickListener(v -> {
            if (hour[0] < 24) txt_hour.setText(String.valueOf(++hour[0]));
        });

        btn_hour_remove.setOnClickListener(v -> {
            if (day[0] > 0) txt_hour.setText(String.valueOf(--hour[0]));
        });

        btn_minute_add.setOnClickListener(v -> {
            if (day[0] < 60) txt_minute.setText(String.valueOf(++minute[0]));
        });

        btn_minute_remove.setOnClickListener(v -> {
            if (day[0] > 0) txt_minute.setText(String.valueOf(--minute[0]));
        });

        btn_order.setOnClickListener(v -> {
            dialog.cancel();
            day[0] = Integer.valueOf(String.valueOf(txt_day.getText()));
            hour[0] = Integer.valueOf(String.valueOf(txt_hour.getText()));
            minute[0] = Integer.valueOf(String.valueOf(txt_minute.getText()));
            isValid(day[0], hour[0], minute[0]);
            time = String.format("%sd%sh%sp", day[0], hour[0], minute[0]);
            Dialog_click_order();
        });

        dialog.show();
    }

    private void isValid(int day, int hour, int minute){

    }

    private void Dialog_click_order(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Xác nhận nhận đặt hàng");
        dialog.setMessage(String.format("Bạn chắc chắn đặt vào ngày %s với giá %s VNĐ", time, cost));

        dialog.setPositiveButton("Yes", (dialog1, which) -> {

            Push_db();

            for (int i = 0; i < arrayCart.size(); i++) {
                arrayCart.get(i).setTime(time);
                Bottom_Nav.bag.arrayBag.add(new BagRow(arrayCart.get(i)));
            }
            arrayCart.removeAll(arrayCart);
            cartAdapter.notifyDataSetChanged();

            cost();
            txt_cost.setText(String.format("Tổng đơn hàng : %s", cost));
        });

        dialog.setNegativeButton("No", (dialog1, which) -> Toast.makeText(getContext(),"Cẩn thận đấy!!!", Toast.LENGTH_SHORT).show());

        dialog.show();
    }

    private void Push_db(){
        String key = mData.getRoot().child("Bill").child(App.user.getMssv()).push().getKey();
        CartBill bill = new CartBill(key, cost, time);

        ArrayList<CartBillItem> arrayItem = new ArrayList<>();
        for (int i = 0; i < arrayCart.size(); i ++)
            arrayItem.add(new CartBillItem(arrayCart.get(i)));

        bill.item.addAll(arrayItem);
        assert key != null;
        mData.getRoot().child("Bill").child(App.user.getMssv()).child(key).setValue(bill);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage("Bạn đã đặt hàng thành công");
        dialog.show();
    }

    private void Dialog_click_item(int position){
        final Dialog dialog = new Dialog(Objects.requireNonNull(getContext()));
        dialog.setContentView(R.layout.bag_dialog);
        dialog.setTitle("Chọn số lượng muốn trả");


        ImageButton btn_down= dialog.findViewById(R.id.button_bagDialog_down);
        ImageButton btn_up  = dialog.findViewById(R.id.button_bagDialog_up);
        Button btn_confirm  = dialog.findViewById(R.id.button_bagDialog);
        EditText txt        = dialog.findViewById(R.id.editText_bagDialog);

        final int[] num = {0};


        btn_down.setOnClickListener(v -> {
            if (num[0] > 0)
                txt.setText(String.valueOf(--num[0]));
        });

        btn_up.setOnClickListener(v -> {
            if (num[0] < arrayCart.get(position).getCount()){
                txt.setText(String.valueOf(++num[0]));
            }
        });

        btn_confirm.setOnClickListener(v -> {
            num[0] = Integer.valueOf(String.valueOf(txt.getText()));
            if (num[0] > arrayCart.get(position).getCount()){
                Toast.makeText(getContext(),"Số lượng trả lại không lớn hơn " + arrayCart.get(position).getCount(), Toast.LENGTH_SHORT).show();
            }
            else {
                if (num[0] > 0) {
                    Toast.makeText(getContext(), "Bạn đã trả " + num[0] + " " + arrayCart.get(position).getName(), Toast.LENGTH_SHORT).show();

                    arrayCart.get(position).setCount(arrayCart.get(position).getCount() - num[0]);
                    if (arrayCart.get(position).getCount() == 0) {
                        arrayCart.remove(position);
                    }
                    cartAdapter.notifyDataSetChanged();

                    cost();
                    txt_cost.setText(String.format("Tổng đơn hàng : %s", cost));
                }
                dialog.cancel();
            }
        });

        dialog.show();
    }
}