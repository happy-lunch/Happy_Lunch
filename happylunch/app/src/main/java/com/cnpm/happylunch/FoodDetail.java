package com.cnpm.happylunch;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smilerating.SmileRating;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodDetail extends AppCompatActivity {

    private static TextView foodAmount, price, nameFood, bigNameFood, txt_time, txt_numMax;
    private Button btnAdd, btnSub;
    private ImageButton btnFD;
    private static ImageView imgFood;
    public volatile  static  Food food;
    public volatile  static BagRow bag = new BagRow();
    public volatile  static Boolean isSet = false;

    //=================================================================
    android.support.v7.widget.Toolbar toolbar;
    //Toolbar toolbar;
    TextView food_Name, food_Price, food_Description;
    ImageView food_Image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart, btnRating;
    RatingBar rb;

    Food f;
    Dialog dialogRating;
    private int levelRate = 0;
    private int levelRated;
    private boolean isRated = false;
    private boolean isRestart = false;

    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Đang tải");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //rogressBar.setCancelable(true);

        map();

        dialogRating = new Dialog(this);
        dialogRating.setContentView(R.layout.dialog_rating);

        if(App.isIntent){
            App.isIntent = false;
            map();
            Intent i = getIntent();
            f = (Food)i.getSerializableExtra("Food");

            food_Description.setText(f.getDescription());
            food_Name.setText(f.getName());
            food_Price.setText(f.getPrice());
            rb.setRating(f.getRating());
            Picasso.get().load(f.getImg()).into(food_Image);
            toolbar.setTitle(f.getName().toUpperCase());
        }else if(isSet){
            set_bag(bag);
        }
        else {
            set(food);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numFood = Integer.parseInt(foodAmount.getText().toString());
                if(numFood < bag.getCount() || bag.getCount() ==0){
                    foodAmount.setText(String.valueOf(++numFood));
                }

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

        /*btnFD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.valueOf(String.valueOf(foodAmount.getText()));
                Cart.arrayCart.add(new BagRow(bag, num));
                Toast.makeText(getBaseContext(),String.format("Bạn đã thêm %s %s vào Cart", num, bag.getName()),Toast.LENGTH_SHORT).show();
                //final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getApplicationContext()));
                //alertDialog.setTitle("Thông báo");
                //alertDialog.setMessage(String.format("Bạn đã thêm %s %s vào Cart", num, bag.getName()));
                //alertDialog.show();
                //startActivity(new Intent(getBaseContext(), Bottom_Nav.class));
                //startActivity();
                onBackPressed();
                finish();
            }
        });*/

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRating.show();
                TextView txtYes = dialogRating.findViewById(R.id.DRBtnYes);
                TextView txtNo = dialogRating.findViewById(R.id.DRBtnNo);
                SmileRating sr = dialogRating.findViewById(R.id.smile_rating);

                if(!App.user.getRatingFoods().isEmpty()){
                    for(int i = 0; i < App.user.getRatingFoods().size(); i++){
                        if(App.user.getRatingFoods().get(i).getFoodId().equals(f.getFoodId())){
                            isRated = true;
                            levelRate = App.user.getRatingFoods().get(i).getLevel();
                            levelRated = levelRate;
                            switch (levelRate){
                                case 1:
                                    sr.setSelectedSmile(SmileRating.TERRIBLE);
                                    break;
                                case 2:
                                    sr.setSelectedSmile(SmileRating.BAD);
                                    break;
                                case 3:
                                    sr.setSelectedSmile(SmileRating.OKAY);
                                    break;
                                case 4:
                                    sr.setSelectedSmile(SmileRating.GOOD);
                                    break;
                                case 5:
                                    sr.setSelectedSmile(SmileRating.GREAT);
                                    break;
                            }
                            break;
                        }
                    }
                }

                sr.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
                    @Override
                    public void onRatingSelected(int level, boolean reselected) {
                        levelRate = level;
                    }
                });

                txtYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(levelRate == 0){
                            dialogRating.cancel();
                            return;
                        }
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        if(isRated){
                            float rating = (f.getRating()*f.getNumPeopleRating() - levelRated + levelRate)/(f.getNumPeopleRating());
                            databaseReference.child("foods").child(f.getFoodId()).child("rating").setValue(rating);
                            //ArrayList<RatingFood> rf = App.user.getRatingFoods();
                            for(RatingFood rf:App.user.getRatingFoods()){
                                if(rf.getFoodId().equals(f.getFoodId())){
                                    rf.setLevel(levelRate);
                                }
                            }
                        }else{
                            float rating = (f.getRating()*f.getNumPeopleRating() + levelRate)/(f.getNumPeopleRating() + 1);
                            databaseReference.child("foods").child(f.getFoodId()).child("rating").setValue(rating);
                            App.user.getRatingFoods().add(new RatingFood(f.getFoodId(), levelRate));
                            int numPeoRate = f.getNumPeopleRating() + 1;
                            databaseReference.child("foods").child(f.getFoodId()).child("numPeopleRating").setValue(numPeoRate);
                        }
                        databaseReference.child("Customers").child(App.user.getUid()).setValue(App.user);

                        dialogRating.cancel();
                        isRestart = true;
                        progressBar.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.cancel();
                                restart();
                            }
                        }, 2000);

                    }
                });

                txtNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogRating.cancel();
                    }
                });
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();



    }

    private void restart(){
        if(isRestart){
            isRestart = false;
            f = App.foods.get(App.foods.indexOf(f));
            food_Description.setText(f.getDescription());
            food_Name.setText(f.getName());
            food_Price.setText(f.getPrice());
            rb.setRating(f.getRating());
            Picasso.get().load(f.getImg()).into(food_Image);
            toolbar.setTitle(f.getName().toUpperCase());
        }
    }

    private void map(){
        /*foodAmount = findViewById(R.id.txtFoodAmount);
        price = findViewById(R.id.txtPriceDetail);
        nameFood = findViewById(R.id.txtNameDetail);
        bigNameFood = findViewById(R.id.txtBigNameDetail);

        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);

        imgFood = findViewById(R.id.imgFood);

        txt_numMax = findViewById(R.id.textView_foodDetail_numMax);
        txt_time = findViewById(R.id.textView_foodDetail_time);
        btnFD = findViewById(R.id.btnFD);*/

        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);
        btnRating = findViewById(R.id.btn_rating);
        rb = findViewById(R.id.ratingBar);

        food_Description= (TextView) findViewById(R.id.food_description);
        food_Name= (TextView) findViewById(R.id.food_name);
        food_Price= (TextView) findViewById(R.id.food_price);
        food_Image= (ImageView) findViewById(R.id.img_food);

        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);

        foodAmount = findViewById(R.id.txtFoodAmount);

        toolbar = findViewById(R.id.toolbarFoodName);
    }

    public static void set(Food food){
        bag.setImg(food.getImg());
        bag.setName(food.getName());
        bag.setPrice(Integer.valueOf(food.getPrice()));
        bag.setId(food.getFoodId());
        set_bag(bag);
    }

    public static void setBag(BagRow bagRow){
        isSet = true;
        bag.setId(bagRow.getId());
        bag.setCount(bagRow.getCount());
        bag.setTime(bagRow.getTime());
        bag.setPrice(bagRow.getPrice());
        bag.setStatus(bagRow.getStatus());
        bag.setName(bagRow.getName());
        //set_bag(bag);
    }

    public static void set_bag(BagRow bagRow){
        bag = bagRow;
        //imgFood.setImageResource(bag.getImg());
        Picasso.get().load(bagRow.getImg()).into(imgFood);
        nameFood.setText(bag.getName());
        price.setText(String.valueOf(bag.getPrice()));
        bigNameFood.setText(bag.getName());
        if (bag.getTime().length() > 0)
            txt_time.setText(String.format("Time : %s",bag.getTime()));
        else txt_time.setText("");
        if (bag.getCount() > 0)
            txt_numMax.setText(String.format("NumSell : %s",bag.getCount()));
        else txt_numMax.setText("");
        foodAmount.setText("1");
    }

}