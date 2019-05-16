package com.cnpm.happylunch;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class BagResellAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<BagRow> bagResellList;

    BagResellAdapter(Context context, int layout, List<BagRow> bagResellList) {
        this.context = context;
        this.layout = layout;
        this.bagResellList = bagResellList;
    }


    @Override
    public int getCount() {
        return bagResellList.size();
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
        TextView txtName, txtNum, txtPrice, txtTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.imgImg = convertView.findViewById(R.id.imageView_bagResell);
            holder.txtName = convertView.findViewById(R.id.textView_bagResellElement_name);
            holder.txtNum = convertView.findViewById(R.id.textView_bagResellElement_num);
            holder.txtPrice = convertView.findViewById(R.id.textView_bagResellElement_price);
            holder.txtTime = convertView.findViewById(R.id.textView_bagResellElement_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BagRow bagResell = bagResellList.get(position);

        holder.imgImg.setImageResource(bagResell.getImg());
        holder.txtName.setText(bagResell.getName());
        holder.txtNum.setText(String.format("%s", String.valueOf(bagResell.getCount())));
        holder.txtPrice.setText(String.format("%s", String.valueOf(bagResell.getPrice())));
        holder.txtTime.setText(String.format("%s", bagResell.getTime()));

        return convertView;
    }
}

public class BagResell extends Fragment {

    private GridView gvBagResell;
    public volatile ArrayList<BagRow> arrayBagRell = new ArrayList<>();
    private BagResellAdapter bagResellAdapter;
    private Boolean isCreate = false;

    private View view;

    private TextView txt_cost;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bag_resell, container, false);
        isCreate = true;
        gvBagResell = view.findViewById(R.id.grid_bagResell);

        bagResellAdapter = new BagResellAdapter(getContext(), R.layout.bag_resell_element, arrayBagRell);
        gvBagResell.setAdapter(bagResellAdapter);

        gvBagResell.setOnItemClickListener((parent, view, position, id) -> Dialog_click_item(position));

        txt_cost = view.findViewById(R.id.textView_bagResell_cost);
        txt_cost.setText(String.format("Chi phí giao dịch : %s", cost()));

        Button btn_bag      = view.findViewById(R.id.button_bagResell_bag);
        Button btn_resell   = view.findViewById(R.id.button_bagResell_resell);

        btn_bag.setOnClickListener(v -> Click_btn_bag());
        btn_resell.setOnClickListener(v -> Click_btn_resell());

        return view;
    }

    public void set_cost(){
        if (isCreate) {
            txt_cost.setText(String.format("Chi phí giao dịch : %s", cost()));
        }
    }

    private int cost(){
        int cost = 0;
        for (int i = 0; i < arrayBagRell.size(); i++) {
            cost += arrayBagRell.get(i).getPrice() * arrayBagRell.get(i).getCount() / 9;
        }
        return cost;
    }

    private void Click_btn_bag(){

        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, Bottom_Nav.bag).commit();
        /*
        Bottom_Nav.selectedFrameLayout.setVisibility(View.VISIBLE);
        Bottom_Nav.flBagResell.setVisibility(View.INVISIBLE);*/
    }

    private void Click_btn_resell(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Resell");
        alertDialog.setMessage("Bạn xác nhận bán lại các mặt hàng đã chọn với phí giao dịch 10%???");

        alertDialog.setPositiveButton("Yes", (dialog, which) -> {
            for (int i = 0; i < arrayBagRell.size(); i++) {
                Bottom_Nav.resell.arraySecondShop.add(new BagRow(arrayBagRell.get(i)));
            }
            arrayBagRell.removeAll(arrayBagRell);
            bagResellAdapter.notifyDataSetChanged();
            txt_cost.setText("Chi phí giao dịch :");
        });

        alertDialog.setNegativeButton("No", (dialog, which) -> Toast.makeText(getContext(),"Cẩn thận đấy!!!", Toast.LENGTH_SHORT).show());

        alertDialog.show();
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
            if (num[0] < arrayBagRell.get(position).getCount()){
                txt.setText(String.valueOf(++num[0]));
            }
        });

        btn_confirm.setOnClickListener(v -> {
            num[0] = Integer.valueOf(String.valueOf(txt.getText()));
            if (num[0] > arrayBagRell.get(position).getCount()){
                Toast.makeText(getContext(),"Số lượng trả lại không lớn hơn " + arrayBagRell.get(position).getCount(), Toast.LENGTH_SHORT).show();
            }
            else {
                if (num[0] > 0) {
                    Toast.makeText(getContext(), "Bạn sẵn sàng trả " + num[0] + " " + arrayBagRell.get(position).getName(), Toast.LENGTH_SHORT).show();

                    BagRow food = new BagRow(arrayBagRell.get(position));
                    food.setCount(num[0]);
                    Bottom_Nav.bag.arrayBag.add(food);

                    arrayBagRell.get(position).setCount(arrayBagRell.get(position).getCount() - num[0]);
                    if (arrayBagRell.get(position).getCount() == 0) {
                        arrayBagRell.remove(position);
                    }
                    bagResellAdapter.notifyDataSetChanged();

                    txt_cost.setText(String.format("Chi phí giao dịch : %s", cost()));
                }
                dialog.cancel();
            }
        });

        dialog.show();
    }
}
