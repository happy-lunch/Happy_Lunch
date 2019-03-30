package com.example.login_happylunch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void isRememberMe(View view) {

    }

    public void eventLogin(View view) {
        String username = ((EditText)findViewById(R.id.txtUsername)).getText().toString();
        String password = ((EditText)findViewById(R.id.txtPassword)).getText().toString();

        if(username.equals("hocdoan") && password.equals("hocdoan")){
            Intent in = new Intent(Login.this, Hello.class);
            startActivity(in);
        }else{
            Toast.makeText(this, "Failed Login", Toast.LENGTH_SHORT).show();
        }
    }
}
