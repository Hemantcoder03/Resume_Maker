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

public class WorkExperienceFragment extends Fragment {

    public WorkExperienceFragment(){}

    TextInputEditText companyName,companyDesignation,workExperienceDesc,workStartYear,workEndYear;
    CheckBox currentWork;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        companyName = (TextInputEditText) getView().findViewById(R.id.companyName);
        companyDesignation = (TextInputEditText) getView().findViewById(R.id.companyDesignation);
        workExperienceDesc = (TextInputEditText) getView().findViewById(R.id.workExperienceDesc);
        workStartYear = (TextInputEditText) getView().findViewById(R.id.workStartYear);
        workEndYear = (TextInputEditText) getView().findViewById(R.id.workEndYear);
        currentWork = (CheckBox) getView().findViewById(R.id.currentWork);

        PreferanceRef preferanceRef = new PreferanceRef(getContext());

        //set the data from database
        DBHelper db = new DBHelper(getContext());

        //set sharedPreferance for currentWork
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ResumeData",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(db.count()!=0) {
            companyName.setText(db.getData("companyName"));
            companyDesignation.setText(db.getData("companyDesignation"));
            workExperienceDesc.setText(db.getData("workExperienceDesc"));
            workStartYear.setText(db.getData("workStartYear"));
            if(sharedPreferences.getInt("currentWork",0) == 1){
                currentWork.setChecked(true);
                workEndYear.setEnabled(false);
                workEndYear.setHint("");
                workEndYear.setText("");
            }
            else{
                workEndYear.setText(db.getData("workEndYear"));
            }
        }

        companyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("companyName",companyName.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("companyName",companyName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("companyName",companyName.getText().toString());
            }
        });

        companyDesignation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("companyDesignation",companyDesignation.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("companyDesignation",companyDesignation.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("companyDesignation",companyDesignation.getText().toString());
            }
        });

        workExperienceDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("workExperienceDesc",workExperienceDesc.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("workExperienceDesc",workExperienceDesc.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("workExperienceDesc",workExperienceDesc.getText().toString());
            }
        });

        workStartYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("workStartYear",workStartYear.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("workStartYear",workStartYear.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("workStartYear",workStartYear.getText().toString());
            }
        });

        workEndYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("workEndYear",workEndYear.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("workEndYear",workEndYear.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("workEndYear",workEndYear.getText().toString());
            }
        });

        currentWork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(currentWork.isChecked()){
                    workEndYear.setEnabled(false);
                    workEndYear.setHint("");
                    workEndYear.setText("");
                    preferanceRef.setSharedPreferences("workEndYear","Continue");

                    editor.putInt("currentWork",1);
                    editor.apply();

                    Toast.makeText(getContext(), "Not editable", Toast.LENGTH_SHORT).show();
                }
                else{
                    workEndYear.setHint("2005");
                    workEndYear.setEnabled(true);

                    editor.putInt("currentWork",0);
                    editor.apply();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_experience, container, false);
    }
}