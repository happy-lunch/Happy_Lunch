package com.cnpm.happylunch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectLoginType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login_type);
    }



    private void moveToLoginActivity(){
        Intent in = new Intent(SelectLoginType.this, Login.class);
        startActivity(in);
    }

    public void loginByUser(View view) {
        moveToLoginActivity();
    }

    public void loginByAdmin(View view) {
        moveToLoginActivity();
    }
}
