package com.cnpm.happylunch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountPage extends Fragment {

    private View view;
    private Button btnLogOut;
    private TextView txtName, txtID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account_page, container, false);

        map();

        txtName.setText(App.user.getFirstName() + " " + App.user.getLastName());
        txtID.setText(App.user.getMssv());

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), SelectLoginType.class));
            }
        });

        return view;
    }

    private void map(){
        btnLogOut = view.findViewById(R.id.btnLogOut);
        txtName = view.findViewById(R.id.txtTenUser);
        txtID = view.findViewById(R.id.txtID);
    }

}
