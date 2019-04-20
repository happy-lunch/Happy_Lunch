package com.cnpm.happylunch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sign_Up extends AppCompatActivity {

    private EditText txtFirstName, txtLastName,txtMSSV, txtEmail, txtPassword, txtVerifyPassword;
    private Button btnSignUp;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        map();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void map(){
        txtFirstName = (EditText) findViewById(R.id.txtHo);
        txtLastName = (EditText) findViewById(R.id.txtTen);
        txtMSSV = (EditText) findViewById(R.id.txtMssv);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPass);
        txtVerifyPassword = (EditText) findViewById(R.id.txtVerifyPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
    }

    private void signUp(){
        String firstName = txtFirstName.getText().toString();
        String lastName = txtLastName.getText().toString();
        String mssv = txtMSSV.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String verifyPassword = txtVerifyPassword.getText().toString();
        String[] arr = {firstName, lastName, mssv, email, password, verifyPassword};
        List<String> infos = new ArrayList<String>(Arrays.asList(arr));

        if(infos.contains("")){
            Toast.makeText(Sign_Up.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }else if(!password.equals(verifyPassword)){
            Toast.makeText(Sign_Up.this, "OK", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(Sign_Up.this, "Đăng kỳ không thành công", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            return;
        }

        User user = new User(currentUser.getUid(), firstName, lastName, mssv, email, 0);

        //Push user up database
        mDatabase.child("Customers").child(user.getUid()).setValue(user);

        Intent i = new Intent(Sign_Up.this, VerifyEmail.class);

        i.putExtra("email", email);
        i.putExtra("password", password);

        startActivity(i);
    }
}
