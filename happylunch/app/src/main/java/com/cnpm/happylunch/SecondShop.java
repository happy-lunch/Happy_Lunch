package com.cnpm.happylunch;

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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class SecondShopAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<BagRow> secondShopList;

    SecondShopAdapter(Context context, int layout, List<BagRow> secondShopList) {
        this.context = context;
        this.layout = layout;
        this.secondShopList = secondShopList;
    }


    @Override
    public int getCount() {
        return secondShopList.size();
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
        TextView txtName, txtPrice, txtTime, txtNumMax;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.imgImg = convertView.findViewById(R.id.imageView_secondShop);
            holder.txtName = convertView.findViewById(R.id.textView_secondShop_name);
            holder.txtPrice = convertView.findViewById(R.id.textView_secondShop_price);
            holder.txtTime = convertView.findViewById(R.id.textView_secondShop_time);
            holder.txtNumMax = convertView.findViewById(R.id.textView_secondShop_numMax);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BagRow secondShop = secondShopList.get(position);

        holder.imgImg.setImageResource(secondShop.getImg());
        holder.txtName.setText(secondShop.getName());
        holder.txtPrice.setText(String.format("Price : %s", String.valueOf(secondShop.getPrice())));
        holder.txtTime.setText(String.format("Time : %s", secondShop.getTime()));
        holder.txtNumMax.setText(String.format("NumSell : %s", secondShop.getCount()));

        return convertView;
    }
}

public class SecondShop extends Fragment {

    private GridView gvSecondShop;
    public volatile ArrayList<BagRow> arraySecondShop = new ArrayList<>();
    private SecondShopAdapter secondShopAdapter;

    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.second_shop, container, false);

        gvSecondShop = view.findViewById(R.id.grid_second_shop);

        secondShopAdapter = new SecondShopAdapter(getContext(), R.layout.second_shop_element, arraySecondShop);
        gvSecondShop.setAdapter(secondShopAdapter);

        gvSecondShop.setOnItemClickListener((parent, view, position, id) -> Order(arraySecondShop.get(position)));

        return view;
    }

    void Order(BagRow food){

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, Bottom_Nav.foodDetail).commitNow();

        //Bottom_Nav.selectedFrameLayout.setVisibility(View.INVISIBLE);
        //Bottom_Nav.flFoodDetail.setVisibility(View.VISIBLE);
        Bottom_Nav.foodDetail.set_bag(food);
    }
}
