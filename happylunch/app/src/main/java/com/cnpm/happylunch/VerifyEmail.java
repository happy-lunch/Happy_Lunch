package com.cnpm.happylunch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class VerifyEmail extends AppCompatActivity {

    Button btnSendAgain, btnVerified;
    private FirebaseAuth mAuth;
    private ProgressDialog progressBar;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        //Setup progressDialog
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Đang tải");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(true);

        map();

        mAuth = FirebaseAuth.getInstance();

        btnSendAgain.setOnClickListener(v->{
            sendVerifyEmailAgain();
        });

        btnVerified.setOnClickListener(v->{
            verified();
        });

        sendVerifyEmailAgain();
    }

    private void sendVerifyEmailAgain(){
        //check
        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(VerifyEmail.this, "Đã gửi email xác nhận", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                sendVerifyEmailAgain();
            }
        });
    }

    private void verified(){

        progressBar.show();

        if(mAuth.getCurrentUser() == null){
            progressBar.cancel();
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.getCurrentUser().reload();
        Handler hand = new Handler();
        hand.postDelayed(()->{
            if(mAuth.getCurrentUser().isEmailVerified()){
                App.prepareUser();
                startActivity(new Intent(VerifyEmail.this, Bottom_Nav.class));
            }else{
                progressBar.cancel();
                Toast.makeText(this, "Bạn chưa xác nhận email", Toast.LENGTH_SHORT).show();
            }
        }, 3000);
    }

    private void map(){
        btnSendAgain = (Button) findViewById(R.id.btnSendVerifyEmailAgain);
        btnVerified = (Button) findViewById(R.id.btnVerified);
    }

}
