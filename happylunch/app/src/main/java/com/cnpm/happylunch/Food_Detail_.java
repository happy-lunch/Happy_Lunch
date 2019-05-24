package com.cnpm.happylunch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Food_Detail_ extends AppCompatActivity implements RatingDialogListener {
    static String userID="1714050";

    TextView food_Name, food_Price, food_Description;
    ImageView food_Image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;
    RatingBar rb;
    FloatingActionButton btnRating;

    String foodID="";

    FirebaseDatabase database;
    DatabaseReference foods;
    DatabaseReference ratings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        //error
        foods= FirebaseDatabase.getInstance().getReference();
        ratings= FirebaseDatabase.getInstance().getReference("Rating");

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);
        food_Description= (TextView) findViewById(R.id.food_description);
        food_Name= (TextView) findViewById(R.id.food_name);
        food_Price= (TextView) findViewById(R.id.food_price);
        food_Image= (ImageView) findViewById(R.id.img_food);

        rb= (RatingBar) findViewById(R.id.ratingBar);
        btnRating= (FloatingActionButton) findViewById(R.id.btn_rating);

        btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });

        collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        if (getIntent()!=null){
            foodID= getIntent().getStringExtra("food_id");
        }
        if (!foodID.isEmpty()){
            getDetailFood(foodID);
        }


    }
    private void getDetailFood(String foodID)
    {
        foods.child(foodID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                food_detail food= dataSnapshot.getValue(food_detail.class);

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
    private void showRatingDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very Bad","Not Good","Quite OK","Very Good","Excellent"))
                .setDefaultRating(0)
                .setTitle("Rate this food")
                .setDescription("Please select some stars and give your feedback")
                .setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Please write your comment here...")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(Food_Detail_.this)
                .show();
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int value, @NotNull String comments) {
        Rating rating = new Rating("1714050", foodID, String.valueOf(value),comments);
        ratings.child("1714050").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
