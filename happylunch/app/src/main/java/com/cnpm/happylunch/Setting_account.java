package com.cnpm.happylunch;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.SettingInjectorService;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.internal.firebase_auth.zzeu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseUserMetadata;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.zzv;
import com.google.firebase.auth.zzx;

import java.util.List;

public class Setting_account extends AppCompatActivity {

    //Toolbar toolbarcaidat;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangePassword();
    }

    private void ChangePassword() {
        AlertDialog.Builder alerDialog = new AlertDialog.Builder(Setting_account.this);
        alerDialog.setTitle("CHANGE PASSWORD");
        alerDialog.setMessage("Please fill information ");
        LayoutInflater inflater = LayoutInflater.from(this);
        View i = inflater.inflate(R.layout.activity_setting_account,null);
        EditText password = (EditText)i.findViewById(R.id.edtPassword);
        EditText newpassword = (EditText)i.findViewById(R.id.edtNewpassword);
        EditText repeatpassword = (EditText)i.findViewById(R.id.edtRepeatPassword);
        EditText email = (EditText)i.findViewById(R.id.edtEmail);
        alerDialog.setView(i);
        alerDialog.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            if(newpassword.getText().toString().equals(password.getText().toString()))
                            {
                                Toast.makeText(Setting_account.this,"Password is coincident!", Toast.LENGTH_LONG).show();
                            }
                            else if (newpassword.getText().toString().equals("")){
                                Toast.makeText(Setting_account.this,"Please fill new password!",Toast.LENGTH_LONG).show();
                            }
                            else if (repeatpassword.getText().toString().equals("")){
                                Toast.makeText(Setting_account.this,"Please fill repeat password!", Toast.LENGTH_LONG).show();
                            }
                            else if(newpassword.getText().toString().equals(repeatpassword.getText().toString())){
                                Toast.makeText(Setting_account.this,"Wrong password!",Toast.LENGTH_LONG).show();
                            }
                            else {
                                user.updatePassword(newpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(Setting_account.this, "Change password is successfull!",Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Toast.makeText(Setting_account.this, " Error", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                        else {
                            Toast.makeText(Setting_account.this, " Error",Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
        alerDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            }
        });
        alerDialog.show();

    }


}
