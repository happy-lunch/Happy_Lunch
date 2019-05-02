package com.cnpm.happylunch;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class SelectLoginType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login_type);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            if(user.isEmailVerified()){
                App.prepareUser();
                startActivity(new Intent(SelectLoginType.this, Bottom_Nav.class));
            }else{
                startActivity(new Intent(SelectLoginType.this, VerifyEmail.class));
            }
        }

    }



    private void moveToLoginActivity(){
        startActivity(new Intent(SelectLoginType.this, Login.class));
    }

    public void loginByUser(View view) {
        moveToLoginActivity();
    }

    public void loginByAdmin(View view) {
        moveToLoginActivity();
    }

    public void signUp(View view) {
        startActivity(new Intent(SelectLoginType.this, Sign_Up.class));
    }
}
