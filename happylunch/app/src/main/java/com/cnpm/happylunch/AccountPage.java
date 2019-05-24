package com.cnpm.happylunch;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Calendar;

public class AccountPage extends Fragment {

    //Firebase
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReferenceFromUrl("gs://happylunch-802e2.appspot.com/");

    private final int REQUEST_CODE_IMAGE = 10;
    private View view;
    private Button btnLogOut, btnSet;
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
                Intent i = new Intent(getActivity(), Setting_account.class);
                startActivity(i);
            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        if(isCreate){
            txtName.setText(App.user.getFirstName() + " " + App.user.getLastName());
            txtID.setText(App.user.getMssv());

            if(!App.user.getAvaName().equals("")){
                avaUser.setImageResource(R.drawable.dang_tai);
                downloadAvaUser();
            }else{
                //Toast.makeText(getActivity(), "NULL", Toast.LENGTH_SHORT).show();
            }
        }

        isCreate = true;



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

}
