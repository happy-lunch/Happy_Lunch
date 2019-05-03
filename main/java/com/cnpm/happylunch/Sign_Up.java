package com.cnpm.happylunch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

    private ProgressDialog progressBar;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        map();

        //Setup ProgressDialog
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Đăng ký");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(true);

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

        progressBar.show();

        String firstName = txtFirstName.getText().toString();
        String lastName = txtLastName.getText().toString();
        String mssv = txtMSSV.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String verifyPassword = txtVerifyPassword.getText().toString();
        String[] arr = {firstName, lastName, mssv, email, password, verifyPassword};
        List<String> infos = new ArrayList<String>(Arrays.asList(arr));

        boolean fail = false;

        if(infos.contains("")){
            Toast.makeText(Sign_Up.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            fail = true;
        }else if(!email.matches("[(\\w)|(\\.)]+@[a-zA-Z\\.]+[a-zA-z]")){
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            fail = true;
        }
        else if(!password.equals(verifyPassword)){
            Toast.makeText(Sign_Up.this, "Password xác thực không khớp", Toast.LENGTH_SHORT).show();
            fail = true;
        }else if(!mssv.matches("[0-9]{7}")){
            Toast.makeText(this, "MSSV không hợp lệ", Toast.LENGTH_SHORT).show();
            fail = true;
        }

        if(fail){
            progressBar.cancel();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    progressBar.cancel();
                    Toast.makeText(Sign_Up.this, "Đăng kỳ không thành công", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseUser currentUser = mAuth.getCurrentUser();

                    if(currentUser == null){
                        return;
                    }

                    User user = new User(currentUser.getUid(),"", firstName, lastName, mssv, email, 0);

                    //Push user up database
                    mDatabase.child("Customers").child(user.getUid()).setValue(user);

                    startActivity(new Intent(Sign_Up.this, VerifyEmail.class));
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.cancel();
                Toast.makeText(Sign_Up.this, "Đăng kỳ không thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
