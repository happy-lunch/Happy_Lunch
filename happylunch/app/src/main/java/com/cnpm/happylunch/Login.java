package com.cnpm.happylunch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    //private DatabaseReference databaseReference;

    private ProgressDialog progressBar;
    private CheckBox check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        //databaseReference = FirebaseDatabase.getInstance().getReference();
        check = findViewById(R.id.isEmployee);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Đăng nhập");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(true);
    }

    public void isRememberMe(View view) {

    }

    public void eventLogin(View view) {

        progressBar.show();

        final String email = ((EditText)findViewById(R.id.txtUsername)).getText().toString();
        final String password = ((EditText)findViewById(R.id.txtPassword)).getText().toString();

        if (check.isChecked()) {
            startActivity(new Intent(Login.this, AdBottom_Nav.class));
            return;
        }

        if(email.equals("") || password.equals("")){
            progressBar.cancel();
            Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            return;
        }



        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    if (mAuth.getCurrentUser().isEmailVerified()) {
                        /*databaseReference.child("Customers").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                App.user = dataSnapshot.getValue(User.class);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });*/
                        App.prepareUser();
                        startActivity(new Intent(Login.this, Bottom_Nav.class));

                    } else {
                        startActivity(new Intent(Login.this, VerifyEmail.class));
                    }
                }else{
                    progressBar.cancel();
                    Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.cancel();
                Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signUp(View view) {
        startActivity(new Intent(Login.this, Sign_Up.class));
    }
}
