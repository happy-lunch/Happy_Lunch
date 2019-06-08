package com.cnpm.happylunch;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;


public class Launch extends AppCompatActivity {

    private Intent i;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private boolean isStartActivity = false;
    private boolean isSignIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            if(user.isEmailVerified()){
                i = new Intent(Launch.this, Bottom_Nav.class);
                isSignIn = true;
            }else{
                i = new Intent(Launch.this, VerifyEmail.class);
            }
        }else{
            i = new Intent(Launch.this, Login.class);
        }
        if(isSignIn){
            databaseReference.child("Customers").child(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    App.user = dataSnapshot.getValue(User.class);
                    isStartActivity = true;
                    //startActivity(i);
                    //finish();
                    Handler hand = new Handler();
                    hand.postDelayed(()->{
                        startActivity(i);
                        finish();
                    }, 3000);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
			
			databaseReference.child("Customers").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                App.user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
            });
        }else{
            Handler hand = new Handler();
            hand.postDelayed(()->{
                if(!isStartActivity) {
                    startActivity(i);
                    finish();
                }
            }, 3000);
        }
        //=======================================================================================
        //Load Food
        databaseReference.child("food").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    Log.e("a",data.getKey());
                    databaseReference.getRoot().child("food").child(data.getKey()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            Food food = dataSnapshot.getValue(Food.class);
                            Log.e("a",food.getName() + "\n" + food.getImg());
                            App.foods.add(food);
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            Food food = dataSnapshot.getValue(Food.class);
                            App.foods.set(App.foods.indexOf(food), food);
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //=======================================================================================
        //Load Categories
        databaseReference.child("categories").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Category category = dataSnapshot.getValue(Category.class);
                Log.e("a",category.getImg());
                App.categories.add(category);
                Log.e("a",String.valueOf(App.categories.size()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("Customers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user_temp = dataSnapshot.getValue(User.class);
                App.customers.add(user_temp);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("Employees").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                App.employees.add(dataSnapshot.getValue(Employee.class));
                Log.e("AAAAAAAAAAAAAAAAAAAAAAAA", "EMEMEMEMMEMEMEMEMEMEMMEME");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                App.employees.remove(dataSnapshot.getValue(Employee.class));
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}