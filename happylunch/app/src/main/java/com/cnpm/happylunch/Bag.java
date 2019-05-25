package com.cnpm.happylunch;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class BagRow {
    private String img;
    private String name;
    private String time;
    private int count;
    private String status = "Đang xử lí";
    private int price;
    private String id;
    private String idFood;
    private String idBIll;
    private String idUser;
    private String idResell;

    BagRow(){
        this.time = "";
        this.count = 0;
    }

    BagRow(BagRow food){
        this.img = food.getImg();
        this.name = food.getName();
        this.time = food.time;
        this.count = food.count;
        this.price = food.getPrice();
        this.idFood = food.getIdFood();
        this.idBIll = food.idBIll;
        this.status = food.status;
        this.id = food.getId();
        this.idResell = food.getIdResell();
        this.idUser = food.idUser;
    }


    BagRow(Food food){
        this.img = food.getImg();
        this.name = food.getName();
        this.count = 0;
        this.price = Integer.valueOf(food.getPrice());
        this.idFood = food.getFoodId();
        this.status = food.getDescription();
    }

    BagRow(BagRow food, int num){
        this.img = food.getImg();
        this.name = food.getName();
        this.time = food.time;
        this.count = num;
        this.price = food.getPrice();
        this.idFood = food.getIdFood();
        this.status = food.status;
        this.id = food.getId();
        this.idResell = food.getIdResell();
        this.idUser = food.idUser;
    }


    BagRow(String img, String name, String time, int count) {
        this.img = img;
        this.name = name;
        this.time = time;
        this.count = count;
        this.status = "Đang xử lí";
    }


    public BagRow(BillItem billItem, String time, String idBill) {
        this.idFood = billItem.getId();
        this.count = billItem.getNum();
        this.price = billItem.getPrice();
        this.status = billItem.getStatus();
        this.time = time;
        int i = get_food(billItem.getId());
        this.img = App.foods.get(i).getImg();
        this.name = App.foods.get(i).getName();
        this.idBIll = idBill;
    }


    private int get_food(String id){
        for (int i = 0; i < App.foods.size(); i++){
            if (App.foods.get(i).getFoodId().equals(id)) return i;
        }
        return -1;
    }

    public BagRow(FoodResell foodResell) {
        this.time = foodResell.getTime();
        this.price = foodResell.getPrice();
        this.count = foodResell.getNumSell();
        this.idFood = foodResell.getIdFood();
        int i = get_food(foodResell.getIdFood());
        this.img = App.foods.get(i).getImg();
        this.name = App.foods.get(i).getName();
        this.status = "Resell";
        this.idUser = foodResell.getIdUser();
        this.idResell = foodResell.getId();
    }

    String getImg() {
        return img;
    }

    public void setImg(String img) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getIdBIll() {
        return idBIll;
    }

    public void setIdBIll(String idBIll) {
        this.idBIll = idBIll;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdFood() {
        return idFood;
    }

    public void setIdFood(String idFood) {
        this.idFood = idFood;
    }

    public String getIdResell() {
        return idResell;
    }

    public void setIdResell(String idResell) {
        this.idResell = idResell;
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
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        BagRow bag = bagList.get(position);

        //holder.imgImg.setImageResource(bag.getImg());
        Picasso.get().load(bag.getImg()).into(holder.imgImg);
        holder.txtName.setText(bag.getName());
        holder.txtTime.setText(bag.getTime());
        holder.txtCount.setText(String.valueOf(bag.getCount()));

        return convertView;
    }
}

public class Bag extends Fragment {

    private ListView lvBag;

    public volatile static ArrayList<BagRow> arrayBag = new ArrayList<>();
    public volatile static ArrayList<Bill> arrayBill = new ArrayList<>();

    public static BagAdapter bagAdapter;
    private View view;
    private DatabaseReference mData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_bag, container, false);

        bagAdapter = new BagAdapter(getContext(), R.layout.element_bag, arrayBag);
        lvBag = view.findViewById(R.id.list_bag);

        //AnhXa();
        Button btn_resell = view.findViewById(R.id.button_bag_resell);

        lvBag.setAdapter(bagAdapter);


        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Bill").child(App.user.getMssv()).child("Order").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Bill bill = dataSnapshot.getValue(Bill.class);
                if (bill!=null){
                    if (bill.getStatus().equals("Đang xử lí")) {
                        arrayBill.add(bill);
                        for (int i = 0; i < bill.item.size(); i++) {
                            int numResell = 0;
                            if (bill.item.get(i).getStatus().substring(0,6).equals("Resell"))
                                numResell = Integer.valueOf(bill.item.get(i).getStatus().substring(7));
                            bill.item.get(i).setNum(bill.item.get(i).getNum() - numResell);
                            if (bill.item.get(i).getNum() > 0)
                                arrayBag.add(new BagRow(bill.item.get(i), bill.getTime(), bill.getId()));
                        }
                        bagAdapter.notifyDataSetChanged();
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
        });


        lvBag.setOnItemClickListener((parent, view, position, id) -> Dialog_click_tra_mon(position));

        btn_resell.setOnClickListener(v -> Click_btn_resell());

        return view;
    }

    private void Click_btn_resell(){

        /*
        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, Bottom_Nav.bagResell).commit();

        /*
        Bottom_Nav.selectedFrameLayout.setVisibility(View.INVISIBLE);
        Bottom_Nav.flBagResell.setVisibility(View.VISIBLE);
        Bottom_Nav.bagResell.set_cost();*/


        if (arrayBag.size() == 0) {
            Toast.makeText(getContext(), "Không có gì để bán lại", Toast.LENGTH_SHORT).show();
        }
        else{
            if (BagResell.isCreate){
                BagResell.bagResellAdapter.notifyDataSetChanged();
            }
            if (BagResell.arrayBagResell.size() == 0){
                Toast.makeText(getContext(), "Vui lòng thêm item muốn bán lại", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                BagResell.set_cost();
                startActivity(new Intent(getContext(), BagResell.class));
            }

        }

    }

    private void Dialog_click_tra_mon(final int position){
        final Dialog dialog = new Dialog(Objects.requireNonNull(getContext()));
        dialog.setContentView(R.layout.bag_dialog);
        dialog.setTitle("Chọn số lượng muốn trả");

        ImageButton btn_down= dialog.findViewById(R.id.button_bagDialog_down);
        ImageButton btn_up  = dialog.findViewById(R.id.button_bagDialog_up);
        Button btn_confirm  = dialog.findViewById(R.id.button_bagDialog);
        TextView txt        = dialog.findViewById(R.id.editText_bagDialog);

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
                    BagResell.add(food);
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

    public static void add(BagRow food){
        for(int i=0; i<arrayBag.size(); i++){
            if (arrayBag.get(i).getIdFood().equals(food.getIdFood())){
                arrayBag.get(i).setCount(arrayBag.get(i).getCount() + food.getCount());
                //bagAdapter.notifyDataSetChanged();
                break;
            }
        }
        arrayBag.add(new BagRow(food));
    }

    /*
    private void AnhXa(){
        arrayBag.add(new BagRow(R.drawable.ck_banh_bao_ba_xiu_2,   "Bánh bao xá xíu 2",
                "9:50",3));
        arrayBag.add(new BagRow(R.drawable.ck_com_chien,           "Cơm chiên",
                "9:45",2));
        arrayBag.add(new BagRow(R.drawable.ck_fruit_whole_nodish,  "Fruit whole nodish",
                "9:30",3));
        arrayBag.add(new BagRow(R.drawable.ck_salad_caron,         "Salad caron",
                "9:20",1));
        arrayBag.add(new BagRow(R.drawable.ck_single_banana,       "Single banana",
                "8:30",1));
        arrayBag.add(new BagRow(R.drawable.ck_trung_cut,           "Trứng cút",
                "8:20",4));
        arrayBag.add(new BagRow(R.drawable.ck_salad_caron,         "Salad caron",
                "8:00",1));
    }*/
}
