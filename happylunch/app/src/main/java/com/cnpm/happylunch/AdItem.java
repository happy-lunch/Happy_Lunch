package com.cnpm.happylunch;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*

class AdItemElement {

    private int img;
    private String name;
    private int price;
    private int count;

    AdItemElement (int img, String name, int price, int count){
        this.img = img;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public int getImg() {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}


class AdItemAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<AdItemElement> adItemList;

    AdItemAdapter(Context context, int layout, List<AdItemElement> adItemList) {
        this.context = context;
        this.layout = layout;
        this.adItemList = adItemList;
    }


    @Override
    public int getCount() { return adItemList.size(); }

    @Override
    public Object getItem(int position) { return null; }

    @Override
    public long getItemId(int position) { return 0; }

    private class ViewHolder{
        ImageView imgImg;
        TextView txtName, txtPrice, txtCount;
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
            holder.txtCount  = convertView.findViewById(R.id.textView_adItem_count);
            convertView.setTag(holder);
        }
        else{
            holder = (AdItemAdapter.ViewHolder) convertView.getTag();
        }

        AdItemElement adItem = adItemList.get(position);

        holder.imgImg.setImageResource(adItem.getImg());
        holder.txtName.setText(adItem.getName());
        holder.txtPrice.setText(String.format("Price : %s", String.valueOf(adItem.getPrice())));
        holder.txtCount.setText(String.format("Count : %s", String.valueOf(adItem.getCount())));

        return convertView;
    }
}

public class AdItem extends Fragment {

    private GridView gvAdItem;
    private ArrayList<AdItemElement> arrayAdItem;
    private AdItemAdapter adItemAdapter;
    private ImageButton search, add;
    private String txtSearch;

    private View view;

    @Override
    /*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_item);
    */
/*
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ad_item, container, false);

        gvAdItem = view.findViewById(R.id.grid_adItem);
        arrayAdItem = new ArrayList<>();

        AnhXa();

        adItemAdapter = new AdItemAdapter(getContext(), R.layout.ad_item_element, arrayAdItem);
        gvAdItem.setAdapter(adItemAdapter);

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
/*
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
        arrayAdItem.add(new AdItemElement(R.drawable.ck_banh_bao_ba_xiu_2,  "Bánh bao xá xíu 2",
                15000, 5));
        arrayAdItem.add(new AdItemElement(R.drawable.ck_com_chien,          "Cơm chiên",
                15000, 5));
        arrayAdItem.add(new AdItemElement(R.drawable.ck_fruit_whole_nodish, "Fruit whole nodish",
                15000, 5));
        arrayAdItem.add(new AdItemElement(R.drawable.ck_salad_caron,        "Salad caron",
                15000, 5));
        arrayAdItem.add(new AdItemElement(R.drawable.ck_single_banana,      "Single banana",
                15000, 5));
        arrayAdItem.add(new AdItemElement(R.drawable.ck_trung_cut,          "Trứng cút",
                15000, 5));
        arrayAdItem.add(new AdItemElement(R.drawable.ck_steam_bun_cade,     "Steam bún cade",
                15000, 5));
        arrayAdItem.add(new AdItemElement(R.drawable.ck_dimsum_hai_san,     "Dimsum hải sản",
                15000, 5));
        arrayAdItem.add(new AdItemElement(R.drawable.ck_banh_gio,           "Bánh giò",
                15000, 5));
    }
}
*/