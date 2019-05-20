package com.cnpm.happylunch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.ByteBufferUtil;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.UUID;


class foods {

    //private int img;
    //private String name;
    //private int price;
    //private int count;
    private String foodId;
    private String description;
    private String name;
    private String price;
    private String img;
    private String menuId;
    private float rating;


    public foods(){}

    public foods(String foodId,String name, String price, String img, String des,String menuId,float rating) {
        this.foodId=foodId;
        this.name = name;
        this.price = price;
        this.img = img;
        this.description=des;
        this.menuId=menuId;
        this.rating=rating;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }
}

public class food_info extends AppCompatActivity {
    private Food_detail_frag food_detail_frag;
    private ListView lvAdItem;

    private FirebaseListAdapter<foods> adItemAdapter;
    private ImageButton search, add;
    private String txtSearch;

    //private View view;

    //database
    public static DatabaseReference foodRef;

    public static FirebaseStorage storage;
    public static StorageReference storeRef;
    public static Uri saveUrl;
    public static final int PICK_IMAGE_REQUEST =71;
    //test
    private int i=-1;

    //dialogAdd
    public static MaterialEditText edtName,edtPrice,edtDes,edtMenuId;
    public static Button btnSelect;
    public static Button btnUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_item);
    //public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //view = inflater.inflate(R.layout.ad_item, container, false);

        lvAdItem = findViewById(R.id.list_item_in_cat);


        foodRef= FirebaseDatabase.getInstance().getReference("foods");

        storage = FirebaseStorage.getInstance();
        storeRef= storage.getReference();



        adItemAdapter= new FirebaseListAdapter<foods>(this, foods.class,
                R.layout.ad_item_element,foodRef.orderByChild("menuId").equalTo(CurrentVariables.menuId)) {
            @Override
            protected void populateView(View v, foods foodInCat, int position) {
                ImageView imgImg = v.findViewById(R.id.imgViewItemInCat);
                TextView txtName = v.findViewById(R.id.txtViewInCat1);
                TextView txtPrice = v.findViewById(R.id.txtViewInCat2);
                ImageView imgIconMore= v.findViewById(R.id.iconToShowInCat);

                Picasso.with(getParent()).load(foodInCat.getImg()).into(imgImg);
                txtName.setText(foodInCat.getName());
                txtPrice.setText(String.format("Price : %s", String.valueOf(foodInCat.getPrice())));

                imgIconMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Dialog_click_item(adItemAdapter.getRef(position).getKey(),
                                //adItemAdapter.getItem(position));
                        Dialog_click_item dialog = new Dialog_click_item();
                        Dialog_click_item.key = adItemAdapter.getRef(position).getKey();
                        Dialog_click_item.item = adItemAdapter.getItem(position);
                        dialog.show(getSupportFragmentManager(), "Dialog");
                    }
                });

            }
        };
        adItemAdapter.notifyDataSetChanged();
        lvAdItem.setAdapter(adItemAdapter);



        search = findViewById(R.id.imageButton_adItem_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });

        add = findViewById(R.id.imageButton_adItem_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add();
            }
        });

        lvAdItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(),"Chuyễn sang food_detail " , Toast.LENGTH_SHORT).show();
                food_detail_frag=new Food_detail_frag();
                CurrentVariables.foodId= adItemAdapter.getRef(position).getKey();
                CurrentVariables.stillInFragment=true;

                startActivity(new Intent(getBaseContext(), Food_detail_frag.class));
                /*
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft
                        .replace(R.id.ad_fragment_container, food_detail_frag)
                        .addToBackStack("my_fragment")
                        .show(food_detail_frag)
                        .commit();*/
            }
        });

        //return view;
    }

    private void Search(){
        txtSearch = ((EditText)findViewById(R.id.editText_adItem_search)).getText().toString();
        Toast.makeText(getBaseContext(), "Tìm kiếm " + txtSearch, Toast.LENGTH_SHORT).show();
    }

    private void Add(){
        //showDialogAdd();
        DialogAdd dialog = new DialogAdd();

        dialog.show(getSupportFragmentManager(), "Dialog");
    }

    /*
    private void Dialog_click_item(String key, foods item){
        final Dialog dialog = new Dialog(getBaseContext());
        dialog.setContentView(R.layout.ad_item_dialog);
        dialog.setTitle("Bạn muốn làm gì ?");

        ImageButton btn_dialog_edit     = dialog.findViewById(R.id.imageButton_adItem_edit);
        ImageButton btn_dialog_delete   = dialog.findViewById(R.id.imageButton_idItem_delete);
        ImageButton btn_dialog_exit     = dialog.findViewById(R.id.imageButton_idItem_exit);

        btn_dialog_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(key,item);
                dialog.cancel();
            }
        });

        btn_dialog_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_click_delete(key,item);
                dialog.cancel();
            }
        });

        btn_dialog_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"Cẩn thận đấy!!!", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        dialog.show();
    }*/

    /*
    private void Dialog_click_delete(final String key, foods item){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getBaseContext());
        alertDialog.setTitle("Cảnh báo!!!");
        alertDialog.setMessage("Bạn chắc chắn muốn xóa "  + "???");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StorageReference photoRef = FirebaseStorage
                        .getInstance()
                        .getReferenceFromUrl(item.getImg());
                photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getBaseContext(),"image deleted " , Toast.LENGTH_SHORT).show();
                    }
                });
                foodRef.child(key).removeValue();
                Toast.makeText(getBaseContext(),"Bạn đã xóa item " , Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(),"Cẩn thận đấy!!!", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.show();
    }*/

    /*
    private void chooseImage(){
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode== Activity.RESULT_OK
            && data != null && data.getData() !=null) {
            saveUrl = data.getData();
            btnSelect.setText("Image Selected");
        }
    }*/

    /*
    private void showDialogUpdate(String key, foods item){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getBaseContext());
        alertDialog.setTitle("Edit food detail");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_new_food_layout= inflater.inflate(R.layout.dialog_add_new_food_layout_,null);

        edtName= add_new_food_layout.findViewById(R.id.edtName);
        edtPrice=add_new_food_layout.findViewById(R.id.edtPrice);
        edtDes=add_new_food_layout.findViewById(R.id.edtDes);

        edtName.setText(item.getName());
        edtPrice.setText(item.getPrice());
        edtDes.setText(item.getDescription());

        btnSelect= add_new_food_layout.findViewById(R.id.btnSelect);
        btnUpload= add_new_food_layout.findViewById(R.id.btnUpload);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(item);
            }
        });

        alertDialog.setView(add_new_food_layout);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_nav);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setName(edtName.getText().toString());
                item.setPrice((edtPrice.getText().toString()));
                item.setDescription(edtDes.getText().toString());

                foodRef.child(key).setValue(item);

            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }*/
    /*
    private void changeImage( foods item){
        if(saveUrl != null)
        {
            //show dialog
            ProgressDialog nDialog = new ProgressDialog(getBaseContext());
            nDialog.setMessage("Uploading...");
            nDialog.show();

            String imageName = UUID.randomUUID().toString(); //convert random string
            StorageReference imageFolder = storeRef.child("Hình ảnh món ăn/"+imageName);
            imageFolder.putFile(saveUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            nDialog.dismiss();
                            Toast.makeText(getBaseContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            btnUpload.setText("Uploaded");

                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    StorageReference photoRef = FirebaseStorage
                                            .getInstance()
                                            .getReferenceFromUrl(item.getImg());
                                    photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getBaseContext(),"image changed " , Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    item.setImg(uri.toString());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            nDialog.dismiss();
                            Toast.makeText(getBaseContext(),""+ e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                            nDialog.setMessage("Uploaded"+progress+"%");
                        }
                    })
            ;
        }
    }*/
}

@SuppressLint("ValidFragment")
class Dialog_click_item extends AppCompatDialogFragment {

    public volatile static String key;
    public volatile static foods item;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceStace){
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.ad_item_dialog, null);

        dialog.setTitle("Bạn muốn làm gì ?");
        dialog.setView(view);

        ImageButton btn_dialog_edit     = view.findViewById(R.id.imageButton_adItem_edit);
        ImageButton btn_dialog_delete   = view.findViewById(R.id.imageButton_idItem_delete);
        ImageButton btn_dialog_exit     = view.findViewById(R.id.imageButton_idItem_exit);

        btn_dialog_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                DialogUpdate dialog0 = new DialogUpdate();
                DialogUpdate.key = key;
                DialogUpdate.item = item;
                assert getFragmentManager() != null;
                dialog0.show(getFragmentManager(), "Dialog0");
            }
        });

        btn_dialog_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                DialogDelete dialog0 = new DialogDelete();
                DialogDelete.key = key;
                DialogDelete.item = item;
                assert getFragmentManager() != null;
                dialog0.show(getFragmentManager(), "Dialog0");
            }
        });

        btn_dialog_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"Cẩn thận đấy!!!", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return dialog.create();
    }
}

@SuppressLint("ValidFragment")
class DialogUpdate extends AppCompatDialogFragment {

    public volatile static String key;
    public volatile static foods item;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceStace){
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_new_food_layout_, null);

        dialog.setTitle("Edit food detail");
        dialog.setMessage("Please fill full information");
        dialog.setView(view);
        dialog.setIcon(R.drawable.ic_shopping_cart_nav);

        food_info.edtName= view.findViewById(R.id.edtName);
        food_info.edtPrice= view.findViewById(R.id.edtPrice);
        food_info.edtDes= view.findViewById(R.id.edtDes);

        food_info.btnSelect= view.findViewById(R.id.btnSelect);
        food_info.btnUpload= view.findViewById(R.id.btnUpload);

        food_info.edtName.setText(item.getName());
        food_info.edtDes.setText(item.getDescription());
        food_info.edtPrice.setText(item.getPrice());

        food_info.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        food_info.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(item);
                dismiss();


            }
        });

        /*
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setName(food_info.edtName.getText().toString());
                item.setPrice((food_info.edtPrice.getText().toString()));
                item.setDescription(food_info.edtDes.getText().toString());

                food_info.foodRef.child(key).setValue(item);

            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/
        return dialog.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == food_info.PICK_IMAGE_REQUEST && resultCode== Activity.RESULT_OK
                && data != null && data.getData() !=null) {
            food_info.saveUrl = data.getData();
            food_info.btnSelect.setText("Image Selected");
        }
    }

    private void chooseImage(){
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),food_info.PICK_IMAGE_REQUEST);

    }
    private void changeImage( foods item){
        if(food_info.saveUrl != null)
        {
            //show dialog
            ProgressDialog nDialog = new ProgressDialog(getContext());
            nDialog.setMessage("Uploading...");
            nDialog.show();

            String imageName = UUID.randomUUID().toString(); //convert random string
            StorageReference imageFolder = food_info.storeRef.child("Hình ảnh món ăn/"+imageName);
            imageFolder.putFile(food_info.saveUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            nDialog.dismiss();
                            //Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            food_info.btnUpload.setText("Uploaded");

                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    StorageReference photoRef = FirebaseStorage
                                            .getInstance()
                                            .getReferenceFromUrl(item.getImg());
                                    photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //Toast.makeText(getContext(),"image changed " , Toast.LENGTH_SHORT).show();
                                            item.setName(food_info.edtName.getText().toString());
                                            item.setPrice((food_info.edtPrice.getText().toString()));
                                            item.setDescription(food_info.edtDes.getText().toString());

                                            food_info.foodRef.child(key).setValue(item);
                                        }
                                    });
                                    item.setImg(uri.toString());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            nDialog.dismiss();
                            Toast.makeText(getContext(),""+ e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                            nDialog.setMessage("Uploaded"+progress+"%");
                        }
                    })
            ;
        }
    }
}

@SuppressLint("ValidFragment")
class DialogDelete extends AppCompatDialogFragment{
    public volatile static String key;
    public volatile static foods item;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceStace){
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //View view = inflater.inflate(R.layout.ad_item_dialog, null);

        dialog.setTitle("Cảnh báo!!!");
        dialog.setMessage("Bạn chắc chắn muốn xóa "  + "???");
        //dialog.setView(view);

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StorageReference photoRef = FirebaseStorage
                        .getInstance()
                        .getReferenceFromUrl(item.getImg());
                photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(getContext(),"image deleted " , Toast.LENGTH_SHORT).show();
                    }
                });
                food_info.foodRef.child(key).removeValue();
                Toast.makeText(getContext(),"Bạn đã xóa item " , Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Cẩn thận đấy!!!", Toast.LENGTH_SHORT).show();
            }
        });

        return dialog.create();
    }
}

@SuppressLint("ValidFragment")
class DialogAdd extends AppCompatDialogFragment {

    private foods newFood;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceStace){
        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_new_food_layout_, null);

        dialog.setTitle("Add new Item");
        dialog.setMessage("Please fill full information");
        dialog.setView(view);
        dialog.setIcon(R.drawable.ic_shopping_cart_nav);

        food_info.edtName= view.findViewById(R.id.edtName);
        food_info.edtPrice= view.findViewById(R.id.edtPrice);
        food_info.edtDes= view.findViewById(R.id.edtDes);

        food_info.btnSelect= view.findViewById(R.id.btnSelect);
        food_info.btnUpload= view.findViewById(R.id.btnUpload);

        food_info.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        food_info.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
                dismiss();
            }
        });

        /*
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (newFood != null){
                    String foodId= food_info.foodRef.push().getKey();
                    newFood.setFoodId(foodId);
                    food_info.foodRef.child(foodId).setValue(newFood);
                    View v= view.findViewById(R.id.ad_item_layout);
                    Snackbar.make(v,"New food: "+newFood.getName()+" was added",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/

        return dialog.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == food_info.PICK_IMAGE_REQUEST && resultCode== Activity.RESULT_OK
                && data != null && data.getData() !=null) {
            food_info.saveUrl = data.getData();
            food_info.btnSelect.setText("Image Selected");
        }
    }

    private void chooseImage(){
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),food_info.PICK_IMAGE_REQUEST);

    }

    private void uploadImage(){
        if(food_info.saveUrl != null)
        {

            ProgressDialog nDialog = new ProgressDialog(getContext());
            nDialog.setMessage("Uploading...");
            nDialog.show();
            String imageName = UUID.randomUUID().toString();
            StorageReference imageFolder = food_info.storeRef.child("hình ảnh món ăn/"+imageName);
            imageFolder.putFile(food_info.saveUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            nDialog.dismiss();
                            //Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            food_info.btnUpload.setText("Uploaded");
                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String foodId = food_info.foodRef.push().getKey();
                                    newFood= new foods(foodId, Objects.requireNonNull(food_info.edtName.getText()).toString(),food_info.edtPrice.getText().toString(), uri.toString(),
                                            food_info.edtDes.getText().toString(),CurrentVariables.menuId,0);
                                    assert foodId != null;
                                    food_info.foodRef.child(foodId).setValue(newFood);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            nDialog.dismiss();
                            Toast.makeText(getContext(),""+ e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                            nDialog.setMessage("Uploaded"+progress+"%");
                        }
                    })
            ;
        }
    }
}
