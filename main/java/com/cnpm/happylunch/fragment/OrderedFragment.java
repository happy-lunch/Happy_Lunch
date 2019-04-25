package com.cnpm.happylunch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnpm.happylunch.R;

public class OrderedFragment extends Fragment {

    public OrderedFragment() {
        // Required empty public constructor
    }

    public static OrderedFragment newInstance(String param1, String param2) {
        OrderedFragment fragment = new OrderedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ordered, container, false);
    }
}
