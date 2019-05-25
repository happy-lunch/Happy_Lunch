package com.cnpm.happylunch;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.Barrier;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
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
import com.squareup.picasso.Picasso;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.Calendar;
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

        //holder.imgImg.setImageResource(bagResell.getImg());
        Picasso.get().load(bagResell.getImg()).into(holder.imgImg);
        holder.txtName.setText(bagResell.getName());
        holder.txtNum.setText(String.format("%s", String.valueOf(bagResell.getCount())));
        holder.txtPrice.setText(String.format("%s", String.valueOf(bagResell.getPrice())));
        holder.txtTime.setText(String.format("%s", bagResell.getTime()));

        return convertView;
    }
}

public class BagResell extends AppCompatActivity {

    private GridView gvBagResell;
    public volatile static ArrayList<BagRow> arrayBagResell = new ArrayList<>();
    public static BagResellAdapter bagResellAdapter;
    public static Boolean isCreate = false;
    public static int cost = 0;

    //private View view;

    private static TextView txt_cost;

    private DatabaseReference mData;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bag_resell);

    //public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //view = inflater.inflate(R.layout.bag_resell, container, false);
        isCreate = true;
        gvBagResell = findViewById(R.id.grid_bagResell);

        bagResellAdapter = new BagResellAdapter(getBaseContext(), R.layout.bag_resell_element, arrayBagResell);
        gvBagResell.setAdapter(bagResellAdapter);

        mData = FirebaseDatabase.getInstance().getReference();
        calendar = Calendar.getInstance();

        gvBagResell.setOnItemClickListener((parent, view, position, id) -> {
            //Dialog_click_item(position)
            BagDialog dialog = new BagDialog();
            dialog.position = position;
            dialog.show(getSupportFragmentManager(), "Dialog");
        });

        txt_cost = findViewById(R.id.textView_bagResell_cost);
        cost();
        txt_cost.setText(String.format("Chi phí giao dịch : %s", cost));

        //Button btn_bag      = findViewById(R.id.button_bagResell_bag);
        Button btn_resell   = findViewById(R.id.button_bagResell_resell);

        //btn_bag.setOnClickListener(v -> Click_btn_bag());
        btn_resell.setOnClickListener(v -> {
            //Click_btn_resell()

            cost();
            if (cost > 0){
                if (cost > App.user.getHPCoin()){
                    Toast.makeText(getBaseContext(),"Không đủ Happy Coin để thực hiện giao dịch", Toast.LENGTH_SHORT).show();
                }
                else{
                    App.user.setHPCoin(App.user.getHPCoin() - cost);
                    mData.child("Customers").child(App.user.getUid()).setValue(App.user);

                    String key0 = mData.child("Bill").child(App.user.getMssv()).child("Resell").push().getKey();
                    mData.child("Bill").child(App.user.getMssv()).child("Resell").child(Objects.requireNonNull(key0)).child("id").setValue(key0);
                    mData.child("Bill").child(App.user.getMssv()).child("Resell").child(key0).child("cost").setValue(cost);
                    String time = String.format("%sd%sh%sp", calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
                    mData.child("Bill").child(App.user.getMssv()).child("Resell").child(key0).child("time").setValue(time);
                    for (int i = 0; i < arrayBagResell.size(); i++){
                        String key = mData.child("Resell").push().getKey();
                        FoodResell foodResell = new FoodResell(key, arrayBagResell.get(i), App.user.getUid());
                        mData.child("Resell").push().setValue(foodResell);
                        mData.child("Bill").child(App.user.getMssv()).child("Resell").child(key0).child("item").child(String.valueOf(i)).child("idResell").setValue(key);
                        mData.child("Bill").child(App.user.getMssv()).child("Resell").child(key0).child("item").child(String.valueOf(i)).child("idBill").setValue(arrayBagResell.get(i).getIdBIll());
                        //mData.child("Bill").child(App.user.getMssv()).child("Order").child(arrayBagResell.get(i).getIdBIll())
                    }

                    arrayBagResell.removeAll(arrayBagResell);
                    bagResellAdapter.notifyDataSetChanged();
                    txt_cost.setText("Chi phí giao dịch :");


                    onBackPressed();
                    finish();
                    //Toast.makeText(getBaseContext(),"Đã thêm các món ăn vào trang Resell", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getBaseContext(),"Vui lòng thêm item muốn bán lại", Toast.LENGTH_SHORT).show();
                onBackPressed();
                finish();
            }
        });

        //return view;
    }

    public static void set_cost(){
        if (isCreate) {
            cost();
            txt_cost.setText(String.format("Chi phí giao dịch : %s", cost));
        }
    }

    private static void cost(){
        cost = 0;
        for (int i = 0; i < arrayBagResell.size(); i++) {
            cost += arrayBagResell.get(i).getPrice() * arrayBagResell.get(i).getCount() / 9;
        }
    }

    private void Click_btn_resell(){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getBaseContext());
        alertDialog.setTitle("Resell");
        alertDialog.setMessage("Bạn xác nhận bán lại các mặt hàng đã chọn với phí giao dịch 10%???");

        alertDialog.setPositiveButton("Yes", (dialog, which) -> {

            cost();
            if (cost > App.user.getHPCoin()){
                final AlertDialog.Builder alertDialog0 = new AlertDialog.Builder(getBaseContext());
                alertDialog0.setMessage("Không đủ Happy Coin để thực hiện giao dịch");
                alertDialog0.show();
            }
            else{

                App.user.setHPCoin(App.user.getHPCoin() - cost);
                mData.child("Customers").child(App.user.getUid()).setValue(App.user);

                for (int i = 0; i < arrayBagResell.size(); i++) {
                    SecondShop.arraySecondShop.add(new BagRow(arrayBagResell.get(i)));
                }


                arrayBagResell.removeAll(arrayBagResell);
                bagResellAdapter.notifyDataSetChanged();
                txt_cost.setText("Chi phí giao dịch :");

                onBackPressed();
                finish();
            }
        });

        alertDialog.setNegativeButton("No", (dialog, which) -> Toast.makeText(getBaseContext(),"Cẩn thận đấy!!!", Toast.LENGTH_SHORT).show());

        alertDialog.show();
    }

    private void Dialog_click_item(int position){
        final Dialog dialog = new Dialog(Objects.requireNonNull(getBaseContext()));
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
            if (num[0] < arrayBagResell.get(position).getCount()){
                txt.setText(String.valueOf(++num[0]));
            }
        });

        btn_confirm.setOnClickListener(v -> {
            num[0] = Integer.valueOf(String.valueOf(txt.getText()));
            if (num[0] > arrayBagResell.get(position).getCount()){
                Toast.makeText(getBaseContext(),"Số lượng trả lại không lớn hơn " + arrayBagResell.get(position).getCount(), Toast.LENGTH_SHORT).show();
            }
            else {
                if (num[0] > 0) {
                    Toast.makeText(getBaseContext(), "Bạn sẵn sàng trả " + num[0] + " " + arrayBagResell.get(position).getName(), Toast.LENGTH_SHORT).show();

                    BagRow food = new BagRow(arrayBagResell.get(position));
                    food.setCount(num[0]);
                    Bag.add(food);

                    arrayBagResell.get(position).setCount(arrayBagResell.get(position).getCount() - num[0]);
                    if (arrayBagResell.get(position).getCount() == 0) {
                        arrayBagResell.remove(position);
                    }
                    bagResellAdapter.notifyDataSetChanged();

                    cost();
                    txt_cost.setText(String.format("Chi phí giao dịch : %s", cost));
                }
                dialog.cancel();
            }
        });

        dialog.show();
    }

    public static void add(BagRow food){
        for(int i=0; i<arrayBagResell.size(); i++){
            if (arrayBagResell.get(i).getId().equals(food.getId())){
                arrayBagResell.get(i).setCount(arrayBagResell.get(i).getCount() + food.getCount());
                bagResellAdapter.notifyDataSetChanged();
                return;
            }
        }
        arrayBagResell.add(new BagRow(food));
    }
}

@SuppressLint("ValidFragment")
class BagDialog extends AppCompatDialogFragment{
    public volatile static int position;
    private ImageButton btn_down, btn_up;
    private Button btn_confirm;
    TextView txt;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceStace){
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.bag_dialog, null);

        dialog.setTitle("Chọn số lượng muốn trả");
        dialog.setView(view);

        btn_down= view.findViewById(R.id.button_bagDialog_down);
        btn_up  = view.findViewById(R.id.button_bagDialog_up);
        btn_confirm  = view.findViewById(R.id.button_bagDialog);
        txt        = view.findViewById(R.id.editText_bagDialog);

        final int[] num = {1};


        btn_down.setOnClickListener(v -> {
            if (num[0] > 1)
                txt.setText(String.valueOf(--num[0]));
        });

        btn_up.setOnClickListener(v -> {
            if (num[0] < BagResell.arrayBagResell.get(position).getCount()){
                txt.setText(String.valueOf(++num[0]));
            }
        });

        btn_confirm.setOnClickListener(v -> {
            num[0] = Integer.valueOf(String.valueOf(txt.getText()));
            if (num[0] > BagResell.arrayBagResell.get(position).getCount()){
                Toast.makeText(getActivity(),"Số lượng trả lại không lớn hơn " + BagResell.arrayBagResell.get(position).getCount(), Toast.LENGTH_SHORT).show();
            }
            else {
                if (num[0] > 0) {
                    Toast.makeText(getActivity(), "Bạn sẵn sàng trả " + num[0] + " " + BagResell.arrayBagResell.get(position).getName(), Toast.LENGTH_SHORT).show();

                    BagRow food = new BagRow(BagResell.arrayBagResell.get(position));
                    food.setCount(num[0]);
                    Bag.arrayBag.add(food);
                    Bag.bagAdapter.notifyDataSetChanged();

                    BagResell.arrayBagResell.get(position).setCount(BagResell.arrayBagResell.get(position).getCount() - num[0]);
                    if (BagResell.arrayBagResell.get(position).getCount() == 0) {
                        BagResell.arrayBagResell.remove(position);
                    }
                    BagResell.bagResellAdapter.notifyDataSetChanged();
                    BagResell.set_cost();
                    Bottom_Nav.botNav.setSelectedItemId(R.id.nav_bag);
                }
            }
            dismiss();
        });

        return dialog.create();
    }
}
