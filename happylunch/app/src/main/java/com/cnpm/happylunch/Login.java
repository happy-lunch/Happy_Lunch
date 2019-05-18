package com.cnpm.happylunch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    //private DatabaseReference databaseReference;

    private ProgressDialog progressBar;

    private TextView forgotPassword;
    private CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        check = findViewById(R.id.isEmployee);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Đăng nhập");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(true);

        forgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
            }
        });
    }

    public void isRememberMe(View view) {

    }

    public void eventLogin(View view) {

        progressBar.show();

        final String email = ((EditText)findViewById(R.id.txtUsername)).getText().toString();
        final String password = ((EditText)findViewById(R.id.txtPassword)).getText().toString();

        if(email.equals("") || password.equals("")){
            progressBar.cancel();
            Toast.makeText(Login.this, "Vui lòng điền đầy đủ email và password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    if (mAuth.getCurrentUser().isEmailVerified()) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();
                        databaseReference.child("Customers").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                App.user = dataSnapshot.getValue(User.class);
                                if (check.isChecked()) {
                                    startActivity(new Intent(Login.this, AdBottom_Nav.class));
                                }
                                else
                                    startActivity(new Intent(Login.this, Bottom_Nav.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        startActivity(new Intent(Login.this, VerifyEmail.class));
                    }
                }else{
                    progressBar.cancel();
                    try{
                        throw task.getException();
                    }
                    // if user enters wrong email.
                    catch (FirebaseAuthInvalidUserException invalidEmail){
                        Toast.makeText(Login.this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                    }catch (FirebaseAuthInvalidCredentialsException wrongPassword){
                        Toast.makeText(Login.this, "Mật khẩu sai", Toast.LENGTH_SHORT).show();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void signUp(View view) {
        startActivity(new Intent(Login.this, Sign_Up.class));
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

