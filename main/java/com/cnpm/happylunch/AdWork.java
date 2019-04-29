package com.cnpm.happylunch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cnpm.happylunch.fragment.CustomersFragment;
import com.cnpm.happylunch.fragment.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

class AdWorkRow {
    private int img;
    private String name;
    private int count;
    private int status = R.drawable.icb_dauchan;


    AdWorkRow(int img, String name, int count) {
        this.img = img;
        this.name = name;
        this.count = count;
    }

    int getImg() { return img; }
    String getName() { return name; }
    int getCount() { return count; }
    int getStatus() { return status; }

    public void setCount(int count) { this.count = count; }
    public void setStatus(int status) { this.status = status; }
}

class AdWorkAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<AdWorkRow> adWorkList;

    AdWorkAdapter(Context context, int layout, List<AdWorkRow> adWorkList) {
        this.context = context;
        this.layout = layout;
        this.adWorkList = adWorkList;
    }

    @Override
    public int getCount() { return adWorkList.size(); }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgImg;
        TextView txtName, txtCount;
        ImageButton imgBtnStatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.imgImg    = convertView.findViewById(R.id.imageView_adWork_img);
            holder.txtName   = convertView.findViewById(R.id.textView_adWork_name);
            holder.txtCount  = convertView.findViewById(R.id.textView_adWork_count);
            holder.imgBtnStatus = convertView.findViewById(R.id.imageButton_adWork_status);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        AdWorkRow adWork = adWorkList.get(position);

        holder.imgImg.setImageResource(adWork.getImg());
        holder.txtName.setText(adWork.getName());
        holder.txtCount.setText(String.valueOf(adWork.getCount()));
        holder.imgBtnStatus.setImageResource(adWork.getStatus());

        return convertView;
    }
}

public class AdWork extends AppCompatActivity {

    private ListView lvAdWork;
    private ArrayList<AdWorkRow> arrayAdWork ;
    private AdWorkAdapter adWorkAdapter;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_work);

        toolbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // attaching bottom sheet behaviour - hide / show on scroll
       /* CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        toolbar.setTitle("Shop");
        Intent in = new Intent(MainActivity_admin.this, AdItem.class);
        startActivity(in);*/

        lvAdWork = findViewById(R.id.list_ad_work);
        arrayAdWork = new ArrayList<>();
        AnhXa();
        adWorkAdapter = new AdWorkAdapter(this, R.layout.ad_work_row, arrayAdWork);
        lvAdWork.setAdapter(adWorkAdapter);

    }

    void AnhXa(){
        arrayAdWork.add(new AdWorkRow(R.drawable.ck_banh_bao_ba_xiu_2,   "Bánh bao xá xíu 2",   3));
        arrayAdWork.add(new AdWorkRow(R.drawable.ck_com_chien,           "Cơm chiên",           2));
        arrayAdWork.add(new AdWorkRow(R.drawable.ck_fruit_whole_nodish,  "Fruit whole nodish",  3));
        arrayAdWork.add(new AdWorkRow(R.drawable.ck_salad_caron,         "Salad caron",         1));
        arrayAdWork.add(new AdWorkRow(R.drawable.ck_single_banana,       "Single banana",       1));
        arrayAdWork.add(new AdWorkRow(R.drawable.ck_trung_cut,           "Trứng cút",           4));
        arrayAdWork.add(new AdWorkRow(R.drawable.ck_salad_caron,         "Salad caron",         1));
        arrayAdWork.add(new AdWorkRow(R.drawable.ck_salad_caron,         "Salad caron",         1));
        arrayAdWork.add(new AdWorkRow(R.drawable.ck_single_banana,       "Single banana",       1));
        arrayAdWork.add(new AdWorkRow(R.drawable.ck_trung_cut,           "Trứng cút",           4));
        arrayAdWork.add(new AdWorkRow(R.drawable.ck_salad_caron,         "Salad caron",         1));
    }

    public void clickAdWorkRow(View view) {
        Toast.makeText(this, "Mở giao diện để chọn làm một phần số lượng", Toast.LENGTH_SHORT).show();

    }

    public void clickAdWork_full(View view) {
        if (arrayAdWork.get(0).getStatus() == R.drawable.icb_dauchan){
            Toast.makeText(this, "Nhân viên nhận làm hết số lượng được giao", Toast.LENGTH_SHORT).show();
            arrayAdWork.get(0).setStatus(R.drawable.icb_tichv);
        }
        else{
            Toast.makeText(this, "Nhân viên xác nhận đã hoàn thành tất cả số lượng", Toast.LENGTH_SHORT).show();
        }


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    toolbar.setTitle("Shop");
                    Intent in = new Intent(AdWork.this, AdItem.class);
                    startActivity(in);
                    return true;
                case R.id.navigation_customers:
                    toolbar.setTitle("Customers");
                    /*Intent intentMain = new Intent(AdWork.this, MainActivity_admin.class);
                    startActivity(intentMain);*/
                    return true;
                case R.id.navigation_ordered:
                    toolbar.setTitle("Ordered");
                    /*Intent intentOrdered = new Intent(AdWork.this, AdWork.class);
                    startActivity(intentOrdered);*/
                    return true;
                case R.id.navigation_profile:
                    toolbar.setTitle("Profile");
                   /* Intent intentMain1 = new Intent(AdWork.this, MainActivity_admin.class);
                    startActivity(intentMain1);*/
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

