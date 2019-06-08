package com.cnpm.happylunch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Objects;

public class AccountPage extends Fragment {

    //Firebase
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReferenceFromUrl("gs://happylunch-802e2.appspot.com/");

    //Firebase của HIệu
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private final int REQUEST_CODE_IMAGE = 10;
    private View view;
    private Button btnLogOut, btnSet, btnBill;
    private TextView txtName, txtID;
    private ImageView avaUser;
    private Boolean isCreate = false;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account_page, container, false);

        map();
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getActivity(), Setting_account.class);
                //startActivity(i);
                ChangePassword();
            }
        });

		view.findViewById(R.id.btnKiemTraTK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Tài khoản của bạn có " + String.valueOf(App.user.getHPCoin() + String.valueOf('\u20AB')), Toast.LENGTH_SHORT).show();
            }
        });

        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AccountBill.class));
            }
        });
		
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bag.arrayBill.removeAll(Bag.arrayBill);
                Bag.arrayBag.removeAll(Bag.arrayBag);
                BagResell.arrayBagResell.removeAll(BagResell.arrayBagResell);
                FirebaseAuth.getInstance().signOut();
                App.user = null;
                startActivity(new Intent(getActivity(), Login.class));
            }
        });

        avaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_PICK);

                File pictureDic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDicPath = pictureDic.getPath();

                Uri data = Uri.parse(pictureDicPath);

                in.setDataAndType(data, "image/*");

                startActivityForResult(in, REQUEST_CODE_IMAGE);
            }
        });

        //if(isCreate){
            txtName.setText(App.user.getFirstName() + " " + App.user.getLastName());
            txtID.setText(App.user.getMssv());

            if(!App.user.getAvaName().equals("")){
                avaUser.setImageResource(R.drawable.dang_tai);
                downloadAvaUser();
            }else{
                //Toast.makeText(getActivity(), "NULL", Toast.LENGTH_SHORT).show();
            }
        //}

        //isCreate = true;


        return view;
    }

    /*@Override
    public void onStart() {

        super.onStart();
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            //Bitmap avatar = (Bitmap) data.getExtras().get("data");

            Uri imageUri = data.getData();

            try(InputStream inputStream = getContext().getContentResolver().openInputStream(imageUri)){

                Bitmap avatar = BitmapFactory.decodeStream(inputStream);
                avaUser.setImageBitmap(avatar);
                uploadAvaUser();
                
            }catch(Exception e){
                Toast.makeText(getActivity(), "Lỗi khi chọn hình ảnh", Toast.LENGTH_SHORT).show();
            }
           
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void map(){
        btnSet = view.findViewById(R.id.btnCaiDatTK);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        txtName = view.findViewById(R.id.txtTenUser);
        txtID = view.findViewById(R.id.txtID);
        avaUser = view.findViewById(R.id.avaUser);
        btnBill = view.findViewById(R.id.btnKTHoaDon);
    }

    private void uploadAvaUser(){
        String imgName = "image" + Calendar.getInstance().getTimeInMillis() + ".png";
        App.user.setAvaName(imgName);
        FirebaseDatabase.getInstance().getReference().child("Customers").child(App.user.getUid()).setValue(App.user);

        StorageReference avaStorage = storageReference.child("Avatar_User").child(imgName);

        avaUser.setDrawingCacheEnabled(true);
        avaUser.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) avaUser.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = avaStorage.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...

            }
        });
    }

    private void downloadAvaUser(){
        StorageReference avaStorage = storageReference.child("Avatar_User").child(App.user.getAvaName());

        final long TEN_MEGABYTE = 10*1024*1024;
        avaStorage.getBytes(TEN_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap avatar = BitmapFactory.decodeByteArray(bytes, 0 ,bytes.length);

                avaUser.setImageBitmap(avatar);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Download Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ChangePassword() {
        AlertDialog.Builder alerDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        alerDialog.setTitle("CHANGE PASSWORD");
        alerDialog.setMessage("Please fill information ");
        LayoutInflater inflater = LayoutInflater.from(getContext());
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
                                Toast.makeText(getContext(),"Password is coincident!", Toast.LENGTH_LONG).show();
                            }
                            else if (newpassword.getText().toString().equals("")){
                                Toast.makeText(getContext(),"Please fill new password!",Toast.LENGTH_LONG).show();
                            }
                            else if (repeatpassword.getText().toString().equals("")){
                                Toast.makeText(getContext(),"Please fill repeat password!", Toast.LENGTH_LONG).show();
                            }
                            else if(!newpassword.getText().toString().equals(repeatpassword.getText().toString())){
                                Toast.makeText(getContext(),"Wrong password!",Toast.LENGTH_LONG).show();
                            }
                            else {
                                user.updatePassword(newpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(getContext(), "Change password is successfull!",Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Toast.makeText(getActivity(), " Error", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                        else {
                            Toast.makeText(getContext(), "Login fail",Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
        alerDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alerDialog.show();
    }

}
