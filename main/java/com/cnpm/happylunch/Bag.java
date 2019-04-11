package com.cnpm.happylunch;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


    BagRow(int img, String name, String time, int count, int status) {
        this.img = img;
        this.name = name;
        this.time = time;
        this.count = count;
        this.status = status;
    }

    int getImg() {
        return img;
    }


    String getName() {
        return name;
    }


    String getTime() {
        return time;
    }


    int getCount() {
        return count;
    }


    int getStatus() {
        return status;
    }

}

class BagAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<BagRow> bagList;

    BagAdapter(Context context, int layout, List<BagRow> bagList) {
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

public class Bag extends AppCompatActivity {

    private ListView lvBag;
    private ArrayList<BagRow> arrayBag ;
    private BagAdapter bagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag);

        lvBag = findViewById(R.id.list_bag);
        arrayBag = new ArrayList<>();
        AnhXa();
        bagAdapter = new BagAdapter(this, R.layout.bag_row, arrayBag);
        lvBag.setAdapter(bagAdapter);

    }

    private void AnhXa(){
        arrayBag.add(new BagRow(R.drawable.ck_banh_bao_ba_xiu_2,   "Bánh bao xá xíu 2",
                "9:50",3,R.drawable.icb_x));
        arrayBag.add(new BagRow(R.drawable.ck_com_chien,           "Cơm chiên",
                "9:45",2,R.drawable.icb_x));
        arrayBag.add(new BagRow(R.drawable.ck_fruit_whole_nodish,  "Fruit whole nodish",
                "9:30",3,R.drawable.icb_chamthan));
        arrayBag.add(new BagRow(R.drawable.ck_salad_caron,         "Salad caron",
                "9:20",1,R.drawable.icb_tichv));
        arrayBag.add(new BagRow(R.drawable.ck_single_banana,       "Single banana",
                "8:55",1,R.drawable.icb_tichv));
        arrayBag.add(new BagRow(R.drawable.ck_trung_cut,           "Trứng cút",
                "8:45",4,R.drawable.icb_tichv));
        arrayBag.add(new BagRow(R.drawable.ck_salad_caron,         "Salad caron",
                "8:40",1,R.drawable.icb_tichv));
        arrayBag.add(new BagRow(R.drawable.ck_salad_caron,         "Salad caron",
                "8:40",1,R.drawable.icb_tichv));
        arrayBag.add(new BagRow(R.drawable.ck_single_banana,       "Single banana",
                "8:30",1,R.drawable.icb_tichv));
        arrayBag.add(new BagRow(R.drawable.ck_trung_cut,           "Trứng cút",
                "8:20",4,R.drawable.icb_tichv));
        arrayBag.add(new BagRow(R.drawable.ck_salad_caron,         "Salad caron",
                "8:00",1,R.drawable.icb_tichv));
    }

    public void clickBagRow(View view) {
        Toast.makeText(this, "Mở giao diện item_info của Vy", Toast.LENGTH_SHORT).show();

    }
}
