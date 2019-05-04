package com.cnpm.happylunch;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

class foods {

    //private int img;
    //private String name;
    //private int price;
    //private int count;

    private String foodId;
    private String name;
    private String price;
    private String img;
    private String description;
    private float rating;
    private String menuId;


    public foods(){}
    public foods(String foodId,String name, String price, String img, String description, String menuId, float rating) {
        this.foodId=foodId;
        this.name = name;
        this.price = price;
        this.img = img;
        this.description = description;
        this.menuId = menuId;
        this.rating = rating;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}

class AdItemAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<foods> adItemList;

    AdItemAdapter(Context context, int layout, List<foods> adItemList) {
        this.context = context;
        this.layout = layout;
        this.adItemList = adItemList;
    }


    @Override
    public int getCount() { return adItemList.size(); }

    @Override
    public Object getItem(int position) {
        //return adItemList.get(position);
        return null; }

    @Override
    public long getItemId(int position) {
        //return position;
        return 0; }

    private class ViewHolder{
        ImageView imgImg;
        TextView txtName, txtPrice, txtDes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdItemAdapter.ViewHolder holder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layout, null);
            holder = new AdItemAdapter.ViewHolder();
            holder.imgImg    = convertView.findViewById(R.id.imageView_adItem_img);
            holder.txtName   = convertView.findViewById(R.id.textView_adItem_name);
            holder.txtPrice  = convertView.findViewById(R.id.textView_adItem_price);
            holder.txtDes  = convertView.findViewById(R.id.textView_adItem_des);
            convertView.setTag(holder);
        }
        else{
            holder = (AdItemAdapter.ViewHolder) convertView.getTag();
        }

        foods adItem = adItemList.get(position);

        Picasso.with(context).load(adItem.getImg()).into(holder.imgImg);
        holder.txtName.setText(adItem.getName());
        holder.txtPrice.setText(String.format("Price : %s", String.valueOf(adItem.getPrice())));
        holder.txtDes.setText(String.format("Des : %s", String.valueOf(adItem.getDescription())));

        return convertView;
    }
}

public class food_info extends Fragment {

    private GridView gvAdItem;
    private ArrayList<foods> arrayAdItem;
    private AdItemAdapter adItemAdapter;
    private ImageButton search, add;
    private String txtSearch;

    private View view;

    //database
    private DatabaseReference foodref;

    //test
    private int i=-1;

    @Override
    /*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_item);
    */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ad_item, container, false);

        gvAdItem = view.findViewById(R.id.grid_adItem);
        arrayAdItem = new ArrayList<>();

        foodref= FirebaseDatabase.getInstance().getReference("foods");
        //AnhXa();
        //vy did it
        foodref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                foods food=  dataSnapshot.getValue(foods.class);
                i++;
                if (food != null)
                    arrayAdItem.add(food);
                Toast.makeText(getContext(),"load duoc: "+ arrayAdItem.get(i).getName(), Toast.LENGTH_SHORT).show();
                adItemAdapter = new AdItemAdapter(getContext(), R.layout.ad_item_element, arrayAdItem);
                gvAdItem.setAdapter(adItemAdapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                foods food= dataSnapshot.getValue(foods.class);
                for (foods f : arrayAdItem) {
                    if (f.getFoodId().equals(food.getFoodId())) {
                        f.setFoodId(food.getFoodId());
                        f.setDescription(food.getDescription());
                        f.setImg(food.getImg());
                        f.setMenuId(food.getMenuId());
                        f.setName(food.getName());
                        f.setPrice(food.getPrice());
                        f.setRating(food.getRating());
                        break;
                    }
                }
                adItemAdapter = new AdItemAdapter(getContext(), R.layout.ad_item_element, arrayAdItem);
                gvAdItem.setAdapter(adItemAdapter);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                foods food= dataSnapshot.getValue(foods.class);
                for (foods f : arrayAdItem) {
                    if (f.getFoodId().equals(food.getFoodId())) {
                        arrayAdItem.remove(f);
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //---


        search = view.findViewById(R.id.imageButton_adItem_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });

        add = view.findViewById(R.id.imageButton_adItem_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add();
            }
        });

        gvAdItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getBaseContext(),"Chỉnh sửa item " + arrayAdItem.get(position).getName(), Toast.LENGTH_SHORT).show();
                //Option(position);
                Dialog_click_item(position);
            }
        });

        return view;
    }

    private void Search(){
        txtSearch = ((EditText)view.findViewById(R.id.editText_adItem_search)).getText().toString();
        Toast.makeText(getContext(), "Tìm kiếm " + txtSearch, Toast.LENGTH_SHORT).show();
    }

    private void Add(){
        Toast.makeText(getContext(), "Add item", Toast.LENGTH_SHORT).show();
    }

    private void Dialog_click_item(final int position){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.ad_item_dialog);
        dialog.setTitle("Bạn muốn làm gì " + arrayAdItem.get(position).getName() + "???");

        ImageButton btn_dialog_edit     = dialog.findViewById(R.id.imageButton_adItem_edit);
        ImageButton btn_dialog_delete   = dialog.findViewById(R.id.imageButton_idItem_delete);
        ImageButton btn_dialog_exit     = dialog.findViewById(R.id.imageButton_idItem_exit);

        btn_dialog_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Chỉnh sửa item " + arrayAdItem.get(position).getName(), Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        btn_dialog_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_click_delete(position);
                /*
                Toast.makeText(getBaseContext(),"Bạn đã xóa item " + arrayAdItem.get(position).getName(), Toast.LENGTH_SHORT).show();
                arrayAdItem.remove(position);
                adItemAdapter.notifyDataSetChanged();
                */
                dialog.cancel();
            }
        });

        btn_dialog_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"Cẩn thận đấy!!!", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void Dialog_click_delete(final int position){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Cảnh báo!!!");
        alertDialog.setMessage("Bạn chắc chắn muốn xóa " + arrayAdItem.get(position).getName() + "???");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Bạn đã xóa item " + arrayAdItem.get(position).getName(), Toast.LENGTH_SHORT).show();
                arrayAdItem.remove(position);
                adItemAdapter.notifyDataSetChanged();
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

    }
}
