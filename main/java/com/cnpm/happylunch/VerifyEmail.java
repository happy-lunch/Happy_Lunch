package com.cnpm.happylunch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class VerifyEmail extends AppCompatActivity {

    Button btnSendAgain, btnVerified;
    private String email, pass;
    private FirebaseAuth mAuth;

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        map();

        mAuth = FirebaseAuth.getInstance();

        btnSendAgain.setOnClickListener(v->{
            sendVerifyEmailAgain();
        });

        btnVerified.setOnClickListener(v->{
            verified();
        });

        Intent i = getIntent();
        email = i.getStringExtra("email");
        pass = i.getStringExtra("password");

        mAuth.signInWithEmailAndPassword(email, pass);

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
        });
    }

    private void verified(){

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() && FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                    startActivity(new Intent(VerifyEmail.this, Bottom_Nav.class));
                }else{
                    Toast.makeText(VerifyEmail.this, "Vui lòng xác nhận email của bạn", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void map(){
        btnSendAgain = (Button) findViewById(R.id.btnSendVerifyEmailAgain);
        btnVerified = (Button) findViewById(R.id.btnVerified);
    }

}
