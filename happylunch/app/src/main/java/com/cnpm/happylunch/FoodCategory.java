package com.cnpm.happylunch;

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
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

class FoodCategoryInfo {

    private String name;
    private String img;


    public FoodCategoryInfo(){}
    //constructor
    public FoodCategoryInfo(String name, String img) {
        this.name = name;
        this.img = img;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}



public class FoodCategory extends Fragment {
    private food_info foodFragment;
    private ListView listCategoryView;
    private ArrayList<FoodCategoryInfo> listCategory;

    private View view;

    //database
    private DatabaseReference catRef;

    private FirebaseStorage storage;
    private StorageReference storeRef;
    private FirebaseListAdapter<FoodCategoryInfo> categoryAdapter;

    private FoodCategoryInfo  newCat;
    Uri saveUrl;
    private final int PICK_IMAGE_REQUEST =71;
    //test
    private int i=-1;

    //dialogAdd
    MaterialEditText edtName,edtPrice,edtDes,edtMenuId;
    Button btnSelect;
    Button btnUpload;

    @Override
    /*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_item);
    */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_item_category, container, false);

        listCategoryView = view.findViewById(R.id.list_category_item);
        //Firedatabase and Storage
        catRef= FirebaseDatabase.getInstance().getReference("category");

        storage = FirebaseStorage.getInstance();
        storeRef= storage.getReference();

        categoryAdapter = new FirebaseListAdapter<FoodCategoryInfo>(getActivity(), FoodCategoryInfo.class,R.layout.list_category_element,catRef) {
            @Override
            protected void populateView(View v, FoodCategoryInfo foodCat, int position) {

                TextView txt= v.findViewById(R.id.txtViewCategory);
                txt.setText(foodCat.getName());

                ImageView img=v.findViewById(R.id.imageViewCategory);
                Picasso.get().load(foodCat.getImg()).into(img);

                ImageView imgIconMore= v.findViewById(R.id.iconToShow);
                imgIconMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog_click_item(categoryAdapter.getRef(position).getKey(),
                                categoryAdapter.getItem(position));
                    }
                });
            }
        };

        categoryAdapter.notifyDataSetChanged();
        listCategoryView.setAdapter(categoryAdapter);




        ImageButton btnSearch = view.findViewById(R.id.imgBtnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });

        ImageButton btnAdd = view.findViewById(R.id.imgBtnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

        listCategoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"Chuyển sang danh sách món ăn " , Toast.LENGTH_SHORT).show();
                foodFragment=new food_info();
                CurrentVariables.menuId = categoryAdapter.getRef(position).getKey();
                CurrentVariables.stillInFragment=true;
                /*
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft
                        .replace(R.id.ad_fragment_container, foodFragment)
                                .addToBackStack("my_fragment")
                        .show(foodFragment)
                        .commit();*/
                startActivity(new Intent(getContext(), food_info.class));
                //Option(position);
                //Dialog_click_item(position);
            }
        });

        return view;
    }

    private void Search(){

        Toast.makeText(getContext(), "Tìm kiếm " , Toast.LENGTH_SHORT).show();
    }


    private void Dialog_click_item(final String key, FoodCategoryInfo item){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.ad_item_dialog);
        dialog.setTitle("Bạn muốn làm gì ?");

        ImageButton btn_dialog_edit     = dialog.findViewById(R.id.imageButton_adItem_edit);
        ImageButton btn_dialog_delete   = dialog.findViewById(R.id.imageButton_idItem_delete);
        ImageButton btn_dialog_exit     = dialog.findViewById(R.id.imageButton_idItem_exit);

        //fix item
        btn_dialog_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(key,item);
                dialog.cancel();
            }
        });
        //delete item
        btn_dialog_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_click_delete(key, item);
                dialog.cancel();
            }
        });
        //exit
        btn_dialog_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"Cẩn thận đấy!!!", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        dialog.show();
    }

   private void Dialog_click_delete(final String key, FoodCategoryInfo item){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
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
                        Toast.makeText(getContext(),"image deleted " , Toast.LENGTH_SHORT).show();
                    }
                });
                catRef.child(key).removeValue();
                Toast.makeText(getContext(),"Bạn đã xóa item " , Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Cẩn thận đấy!!!", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.show();
    }


    private void showDialogUpdate(String key, FoodCategoryInfo item){
        Dialog alertDialog = new Dialog(getContext());
        alertDialog.setTitle("Edit Category");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_new_food_layout= inflater.inflate(R.layout.dialog_add_new_category_layout_,null);

        edtName= add_new_food_layout.findViewById(R.id.edtCategoryName);
        edtName.setText(item.getName());
        btnSelect= add_new_food_layout.findViewById(R.id.btnCategorySelect);
        btnUpload= add_new_food_layout.findViewById(R.id.btnCategoryUpload);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(item, key);
                alertDialog.cancel();

            }
        });


        alertDialog.setContentView(add_new_food_layout);


        /*
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setName(edtName.getText().toString());
                catRef.child(key).setValue(item);

            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/
        alertDialog.show();
    }
    private void showDialogAdd(){
        Dialog alertDialog = new Dialog(Objects.requireNonNull(getContext()));
        alertDialog.setTitle("Add new Category");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_new_food_layout= inflater.inflate(R.layout.dialog_add_new_category_layout_,null);

        edtName= add_new_food_layout.findViewById(R.id.edtCategoryName);

        btnSelect= add_new_food_layout.findViewById(R.id.btnCategorySelect);
        btnUpload= add_new_food_layout.findViewById(R.id.btnCategoryUpload);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
                alertDialog.cancel();
            }
        });

        alertDialog.setContentView(add_new_food_layout);

        /*
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (newCat != null){
                    catRef.push().setValue(newCat);

                    View v= Objects.requireNonNull(getActivity()).findViewById(R.id.list_item_category);
                    Snackbar.make(v,"New food: "+newCat.getName()+" was added",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/
        alertDialog.show();
    }

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
    }

    private void uploadImage(){
        if(saveUrl != null)
        {
            //show dialog
            ProgressDialog nDialog = new ProgressDialog(getContext());
            nDialog.setMessage("Uploading...");
            nDialog.show();

            String imageName = UUID.randomUUID().toString(); //convert random string
            StorageReference imageFolder = storeRef.child("categories/"+imageName);
            imageFolder.putFile(saveUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                nDialog.dismiss();
                                Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                btnUpload.setText("Uploaded");

                                imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                        public void onSuccess(Uri uri) {
                                            newCat= new FoodCategoryInfo(edtName.getText().toString(),uri.toString());
                                            catRef.push().setValue(newCat);
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
        };
    }
    private void changeImage( FoodCategoryInfo item, String key){
        if(saveUrl != null)
        {
            //show dialog
            ProgressDialog nDialog = new ProgressDialog(getContext());
            nDialog.setMessage("Uploading...");
            nDialog.show();

            String imageName = UUID.randomUUID().toString(); //convert random string
            StorageReference imageFolder = storeRef.child("categories/"+imageName);
            imageFolder.putFile(saveUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            nDialog.dismiss();
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(getContext(),"image changed " , Toast.LENGTH_SHORT).show();
                                            item.setName(Objects.requireNonNull(edtName.getText()).toString());
                                            catRef.child(key).setValue(item);
                                            catRef.child(key).child("id").setValue(key);
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
        };
    }
}
