package com.cnpm.happylunch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
    }

    public void isRememberMe(View view) {

    }

    public void eventLogin(View view) {
        final String email = ((EditText)findViewById(R.id.txtUsername)).getText().toString();
        final String password = ((EditText)findViewById(R.id.txtPassword)).getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    if (mAuth.getCurrentUser().isEmailVerified()) {
                        startActivity(new Intent(Login.this, Bottom_Nav.class));

                    } else {
                        Intent i = new Intent(Login.this, VerifyEmail.class);

                        i.putExtra("email", email);
                        i.putExtra("password", password);
                        startActivity(new Intent(Login.this, VerifyEmail.class));
                    }
                }else{
                    Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

