package com.hemant.resumemaker.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.hemant.resumemaker.DBHelper;
import com.hemant.resumemaker.PreferanceRef;
import com.hemant.resumemaker.R;

public class HobbiesFragment extends Fragment {

    TextInputEditText hobbyName;

    public HobbiesFragment(){

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hobbyName = view.findViewById(R.id.hobbyName);

        PreferanceRef preferanceRef = new PreferanceRef(getContext());

        //get data from database
        DBHelper db = new DBHelper(getContext());

        //set the previous selected or written value on that position or edittext
        if(db.count()!=0) {
            hobbyName.setText(db.getData("hobbyName"));
        }

        hobbyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("hobbyName",hobbyName.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("hobbyName",hobbyName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("hobbyName",hobbyName.getText().toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hobbies, container, false);
    }
}