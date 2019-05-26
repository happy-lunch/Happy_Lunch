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

import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;

import android.util.Log;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

class FoodCategoryInfo {

    private String name;
    private String img;
    private String id;

    public FoodCategoryInfo(){}
    //constructor
    public FoodCategoryInfo(String id ,String name, String img) {
        this.name = name;
        this.img = img;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    private ListView listCategoryView;

    private boolean onStartSearch=false;
    private ArrayList<String> suggestList=new ArrayList<String>();
    private FirebaseListAdapter<FoodCategoryInfo> searchAdapter;
    private MaterialSearchBar materialSearchBar;

    private View view;

    //database
    private DatabaseReference catRef;
    private FirebaseStorage storage;
    private StorageReference storeRef;
    private FirebaseListAdapter<FoodCategoryInfo> categoryAdapter;

    private FoodCategoryInfo  newCat;
    Uri saveUrl;
    private final int PICK_IMAGE_REQUEST =71;

    //dialogAdd
    MaterialEditText edtName;
    Button btnSelect;
    Button btnUpload;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_item_category, container, false);

        listCategoryView = view.findViewById(R.id.list_category_item);
        //Firedatabase and Storage
        catRef= FirebaseDatabase.getInstance().getReference("category");

        storage = FirebaseStorage.getInstance();
        storeRef= storage.getReference();

        suggestList.clear();

        categoryAdapter = new FirebaseListAdapter<FoodCategoryInfo>(getActivity(), FoodCategoryInfo.class,R.layout.list_category_element,catRef.orderByChild("name")) {
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
                        Log.d("debug: ","populate: "+foodCat.getName());
                        Dialog_click_item(categoryAdapter.getRef(position).getKey(),
                                categoryAdapter.getItem(position));
                    }
                });
            }
        };

        categoryAdapter.notifyDataSetChanged();
        listCategoryView.setAdapter(categoryAdapter);

        //Button Add
        ImageButton btnAdd = view.findViewById(R.id.imgBtnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

        //For Search Function
        materialSearchBar =  view.findViewById(R.id.searchBar);
        materialSearchBar.setHint("Hãy nhập loại món ăn");
        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest =new ArrayList<String>();
                for (String search: suggestList){
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())) suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled){
                    listCategoryView.setAdapter(categoryAdapter);
                    onStartSearch=false;
                    btnAdd.setVisibility(View.VISIBLE);
                }
                else{
                    btnAdd.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        listCategoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CurrentVariables.menuId = categoryAdapter.getRef(position).getKey();
                CurrentVariables.stillInFragment=true;

                if (onStartSearch)
                {CurrentVariables.menuId= searchAdapter.getRef(position).getKey(); onStartSearch=false;}
                else CurrentVariables.menuId= categoryAdapter.getRef(position).getKey();

                startActivity(new Intent(getContext(), food_info.class));
            }
        });

        return view;
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
                deleteAllImageInCat(key);
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Edit Category");
        alertDialog.setMessage("Please fill full information");

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


                catRef.child(key).setValue(item);

            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
    private void showDialogAdd(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Add new Category");
        alertDialog.setMessage("Please fill full information");

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
            }
        });

        alertDialog.setView(add_new_food_layout);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_nav);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (newCat != null){
                    String catId= catRef.push().getKey();
                    newCat.setId(catId);
                    catRef.child(catId).setValue(newCat);


                    View v=getActivity().findViewById(R.id.list_item_category);
                    Snackbar.make(v,"New food: "+newCat.getName()+" was added",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
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
                                    newCat= new FoodCategoryInfo("",edtName.getText().toString(),uri.toString());
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
    private void changeImage( FoodCategoryInfo item){
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
                                            Toast.makeText(getContext(),"Old image deleted " , Toast.LENGTH_SHORT).show();
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

    //For Search functiom
    private void loadSuggest(){

        catRef.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                suggestList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren() ){
                    FoodCategoryInfo item = postSnapshot.getValue(FoodCategoryInfo.class);
                    suggestList.add(item.getName());
                }
                materialSearchBar.setLastSuggestions(suggestList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void startSearch(CharSequence text) {

        onStartSearch=true;
        searchAdapter = new FirebaseListAdapter<FoodCategoryInfo>(getActivity(), FoodCategoryInfo.class,R.layout.list_category_element,catRef.orderByChild("name").equalTo(text.toString()))  {
            @Override
            protected void populateView(View v, FoodCategoryInfo foodCat, int position) {
                TextView txt= v.findViewById(R.id.txtViewCategory);
                txt.setText(foodCat.getName());

                ImageView img=v.findViewById(R.id.imageViewCategory);
                Picasso.get().load(foodCat.getImg()).into(img);

                ImageView imgIconMore= v.findViewById(R.id.iconToShow);

                imgIconMore.setVisibility(View.INVISIBLE);
            }
        };
        searchAdapter.notifyDataSetChanged();
        listCategoryView.setAdapter(searchAdapter);
    }
    private void deleteAllImageInCat(String key) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("food");
        ref.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren() ){
                    FoodCategoryInfo item = postSnapshot.getValue(FoodCategoryInfo.class);

                    StorageReference photoRef = FirebaseStorage
                            .getInstance()
                            .getReferenceFromUrl(item.getImg());

                    photoRef.delete();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
