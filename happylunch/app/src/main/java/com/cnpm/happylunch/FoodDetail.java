package com.cnpm.happylunch;

<<<<<<< HEAD
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodDetail extends AppCompatActivity {

    private TextView foodAmount, price, nameFood, bigNameFood;
    private Button btnAdd, btnSub;
    private ImageView imgFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        map();

        Intent i = getIntent();

        Food food = (Food) i.getSerializableExtra("Food");

        price.setText(food.getFoodPrice());
        nameFood.setText(food.getFoodName());
        bigNameFood.setText(food.getFoodName());
        imgFood.setImageResource(food.getFoodImg());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numFood = Integer.parseInt(foodAmount.getText().toString());
                if(numFood >= 10){
                    return;
                }
                foodAmount.setText(String.valueOf(++numFood));
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numFood = Integer.parseInt(foodAmount.getText().toString());
                if(numFood <= 1){
                    return;
                }
                foodAmount.setText(String.valueOf(--numFood));
            }
        });

    }

    private void map(){
        foodAmount = findViewById(R.id.txtFoodAmount);
        price = findViewById(R.id.txtPriceDetail);
        nameFood = findViewById(R.id.txtNameDetail);
        bigNameFood = findViewById(R.id.txtBigNameDetail);

        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);

        imgFood = findViewById(R.id.imgFood);
    }

=======
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class FoodDetail extends Fragment {

    private View view;
    public boolean isCreate = false;
    private int num = 1;

    private BagRow food = new BagRow();

    private ImageView img_img;
    private TextView txt_name, txt_price, txt_time, txt_numMax;
    private EditText txt_num;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.food_detail, container, false);

        ImageButton btn_back= view.findViewById(R.id.imageButton_foodDetail_back);
        ImageButton btn_down= view.findViewById(R.id.button_foodDetail_down);
        ImageButton btn_up  = view.findViewById(R.id.button_foodDetail_up);
        Button btn_confirm  = view.findViewById(R.id.button_foodDetail);

        img_img    = view.findViewById(R.id.imageView_foodDetail);
        txt_name   = view.findViewById(R.id.textView_foodDetail_name);
        txt_price  = view.findViewById(R.id.textView_foodDetail_price);
        txt_time   = view.findViewById(R.id.textView_foodDetail_time);
        txt_numMax = view.findViewById(R.id.textView_foodDetail_numMax);
        txt_num    = view.findViewById(R.id.textView_foodDetail_num);


        btn_back.setOnClickListener(v->Click_btn_back());
        btn_down.setOnClickListener(v->Click_btn_down());
        btn_up.setOnClickListener(v->Click_btn_up());
        btn_confirm.setOnClickListener(v->Click_btn_confirm());

        isCreate = true;

        return view;
    }

    public void set_food(Food food){
        this.food.setImg(food.getFoodImg());
        this.food.setName(food.getFoodName());
        this.food.setPrice(Integer.valueOf(food.getFoodPrice()));
        this.food.setTime("");
        this.food.setCount(0);
        set_bag(this.food);
    }

    public void set_bag(BagRow bag){
        this.food = bag;
        img_img.setImageResource(bag.getImg());
        txt_name.setText(bag.getName());
        txt_price.setText(String.valueOf(bag.getPrice()));
        if (food.getTime().length() > 0)
            txt_time.setText(String.format("Time : %s",bag.getTime()));
        else txt_time.setText("");
        if (food.getCount() > 0)
            txt_numMax.setText(String.format("NumSell : %s",bag.getCount()));
        else txt_numMax.setText("");
        txt_num.setText("1");
    }


    private void Click_btn_back(){
        num = 1;
        txt_num.setText("1");

        /*
        Bottom_Nav.selectedFrameLayout.setVisibility(View.VISIBLE);
        Bottom_Nav.flFoodDetail.setVisibility(View.INVISIBLE);
        */



        FragmentManager fragmentManager = getFragmentManager();
        assert fragmentManager != null;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, Bottom_Nav.homePage).commit();
    }

    private void Click_btn_down(){
        if (num > 1 ){
            txt_num.setText(String.valueOf(--num));
        }
    }
    private void Click_btn_up(){
        if (num < this.food.getCount() || this.food.getCount() ==0)
            txt_num.setText(String.valueOf(++num));
    }
    private void Click_btn_confirm(){
        num = Integer.valueOf(String.valueOf(txt_num.getText()));
        if (num > 0 && (num <= food.getCount() || food.getCount() == 0)) {

            Bottom_Nav.cart.arrayCart.add(new BagRow(food, num));

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
            alertDialog.setMessage(String.format("Bạn đã thêm %s %s vào Cart", num, food.getName()));
            alertDialog.show();
        }
        else{
            Toast.makeText(getContext(),"Nhập số lượng vừa phải", Toast.LENGTH_LONG).show();
        }
        Click_btn_back();
    }
>>>>>>> 71d06f8fe3d98fe44e4b0b371eff2bc96de6e9b9
}
