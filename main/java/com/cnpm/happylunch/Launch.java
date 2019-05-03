package com.cnpm.happylunch;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Launch extends AppCompatActivity {

    private ProgressBar progressBar;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            if(user.isEmailVerified()){
                App.prepareUser();
                i = new Intent(Launch.this, Bottom_Nav.class);
            }else{
                i = new Intent(Launch.this, VerifyEmail.class);
            }
        }else{
            i = new Intent(Launch.this, Login.class);
        }

        Handler hand = new Handler();
        hand.postDelayed(()->{
            startActivity(i);
        }, 3000);

    }
}
