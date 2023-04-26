package com.hemant.resumemaker.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.hemant.resumemaker.DBHelper;
import com.hemant.resumemaker.PreferanceRef;
import com.hemant.resumemaker.R;

public class EducationFragment extends Fragment {

    public EducationFragment(){
        //default constructor is required
    }

    TextInputEditText collage,educationDegreeName,educationDesc,collageStartYear,collageEndYear;
    CheckBox currentEducation;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        collage = (TextInputEditText) getView().findViewById(R.id.collage);
        educationDegreeName = (TextInputEditText) getView().findViewById(R.id.educationDegreeName);
        educationDesc = (TextInputEditText) getView().findViewById(R.id.educationDesc);
        collageStartYear = (TextInputEditText) getView().findViewById(R.id.collageStartYear);
        collageEndYear = (TextInputEditText) getView().findViewById(R.id.collageEndYear);
        currentEducation = (CheckBox) getView().findViewById(R.id.currentEducation);

        //used to store like sharedpreference
        PreferanceRef preferanceRef = new PreferanceRef(getContext());

        //get data from database
        DBHelper db = new DBHelper(getContext());

        //set sharedPreferance for currentEducation
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ResumeData",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //set the previous selected or written value on that position or edittext

        if(db.count()!=0) {
            collage.setText(db.getData("collage"));
            educationDegreeName.setText(db.getData("educationDegreeName"));
            educationDesc.setText(db.getData("educationDesc"));
            collageStartYear.setText(db.getData("collageStartYear"));;
            if(sharedPreferences.getInt("currentEducation",0) == 1){
                currentEducation.setChecked(true);
                collageEndYear.setEnabled(false);
                collageEndYear.setHint("");
                collageEndYear.setText("");
            } else{
                collageEndYear.setText(db.getData("collageEndYear"));
            }
        }

        collage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("collage",collage.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("collage",collage.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("collage",collage.getText().toString());
            }
        });

        educationDegreeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("educationDegreeName",educationDegreeName.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("educationDegreeName",educationDegreeName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("educationDegreeName",educationDegreeName.getText().toString());
            }
        });

        educationDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("educationDesc",educationDesc.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("educationDesc",educationDesc.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("educationDesc",educationDesc.getText().toString());
            }
        });

        collageStartYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("collageStartYear",collageStartYear.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("collageStartYear",collageStartYear.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("collageStartYear",collageStartYear.getText().toString());
            }
        });

        collageEndYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("collageEndYear",collageEndYear.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("collageEndYear",collageEndYear.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("collageEndYear",collageEndYear.getText().toString());
            }
        });

        currentEducation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(currentEducation.isChecked()){
                    collageEndYear.setEnabled(false);
                    collageEndYear.setHint("");
                    collageEndYear.setText("");
                    preferanceRef.setSharedPreferences("collageEndYear","Continue");

                    editor.putInt("currentEducation",1);
                    editor.apply();

                    Toast.makeText(getContext(), "Not editable", Toast.LENGTH_SHORT).show();
                }
                else{
                    collageEndYear.setHint("2005");
                    collageEndYear.setEnabled(true);

                    editor.putInt("currentEducation",0);
                    editor.apply();
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_education, container, false);
    }
}