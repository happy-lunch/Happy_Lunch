package com.cnpm.happylunch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class ForgotPassword extends AppCompatActivity {

    private EditText email;
    private Button btnSendResetEmail;
    private TextView back;

    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = (EditText) findViewById(R.id.txtEmailInForgotPassword);
        btnSendResetEmail = (Button) findViewById(R.id.btnResetPassword);
        back = (TextView) findViewById(R.id.txtBackToLogin);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Đang tải");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(true);

        btnSendResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToLogin();
            }
        });
    }

    private void sendEmail(){
        final String strEmail = email.getText().toString();
        if(strEmail.equals("")){
            Toast.makeText(this, "Vui lòng nhập email của bạn", Toast.LENGTH_SHORT).show();
            return;
        }else if(!strEmail.matches("[(\\w)|(\\.)]+@[a-zA-Z\\.]+[a-zA-z]")) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
        }
        progressBar.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(strEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task){
                if(task.isSuccessful()) {
                    progressBar.cancel();
                    Toast.makeText(ForgotPassword.this, "Kiểm tra mail của bạn để đặt lại mật khẩu", Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.cancel();
                    try{
                        throw task.getException();
                    }
                    // if user enters wrong email.
                    catch (FirebaseAuthInvalidUserException invalidEmail){
                        Toast.makeText(ForgotPassword.this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void backToLogin(){
        progressBar.show();
        Handler hand = new Handler();
        hand.postDelayed(()->{
            finish();
            onBackPressed();
        }, 2000);

    }
}
