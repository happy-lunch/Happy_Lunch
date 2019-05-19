package com.cnpm.happylunch;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.UUID;


class Food_Detail{

    private String name;
    private String img;
    private String price;
    private Float rating;
    private String description;
    public Food_Detail(){}
    public Food_Detail(String name, String img, String price, Float rating, String description) {
        this.name = name;
        this.img = img;
        this.price = price;
        this.rating = rating;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

public class Food_detail_frag extends AppCompatActivity {

    TextView food_Name, food_Price, food_Description;
    ImageView food_Image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RatingBar rb;



    DatabaseReference foods;


    //private View view;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail_show);
    //public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //view = inflater.inflate(R.layout.activity_food_detail_show, container, false);
        foods = FirebaseDatabase.getInstance().getReference("foods");



        food_Description = (TextView) findViewById(R.id.food_description);
        food_Name = (TextView) findViewById(R.id.food_name);
        food_Price = (TextView) findViewById(R.id.food_price);
        food_Image = (ImageView) findViewById(R.id.img_food);

        rb = (RatingBar) findViewById(R.id.ratingBar);


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        getDetailFood(CurrentVariables.foodId);
        //return view;
    }
    private void getDetailFood(String foodID)
    {
        foods.child(foodID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Food_Detail food= dataSnapshot.getValue(Food_Detail.class);

                //set Image
                Picasso.with(getBaseContext()).load(food.getImg()).into(food_Image);

                collapsingToolbarLayout.setTitle(food.getName());
                food_Price.setText(food.getPrice());

                food_Name.setText(food.getName());

                food_Description.setText(food.getDescription());

                rb.setRating(food.getRating());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
