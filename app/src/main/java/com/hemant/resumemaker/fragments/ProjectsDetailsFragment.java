package com.hemant.resumemaker.fragments;

import android.content.SharedPreferences;
import android.net.Uri;
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

public class ProjectsDetailsFragment extends Fragment {

    public ProjectsDetailsFragment(){}

    TextInputEditText projectName,projectCompanyName,projectCompanyDesignation,projectDesc,projectStartYear,projectEndYear;
    CheckBox currentProject;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        projectName = (TextInputEditText) getView().findViewById(R.id.projectName);
        projectCompanyName = (TextInputEditText) getView().findViewById(R.id.projectCompanyName);
        projectCompanyDesignation = (TextInputEditText) getView().findViewById(R.id.projectCompanyDesignation);
        projectDesc = (TextInputEditText) getView().findViewById(R.id.projectDesc);
        projectStartYear = (TextInputEditText) getView().findViewById(R.id.projectStartYear);
        projectEndYear = (TextInputEditText) getView().findViewById(R.id.projectEndYear);
        currentProject = (CheckBox) getView().findViewById(R.id.currentProject);

        PreferanceRef preferanceRef = new PreferanceRef(getContext());

        //get data from database
        DBHelper db = new DBHelper(getContext());

        //set sharedPreferance for currentProject
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ResumeData",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(db.count()!=0) {
            projectName.setText(db.getData("projectName"));
            projectCompanyName.setText(db.getData("projectCompanyName"));
            projectCompanyDesignation.setText(db.getData("projectCompanyDesignation"));
            projectDesc.setText(db.getData("projectDesc"));
            projectStartYear.setText(db.getData("projectStartYear"));
            String endYear = db.getData("projectEndYear");
            if(sharedPreferences.getInt("currentProject",0) == 1){
                currentProject.setChecked(true);
                projectEndYear.setEnabled(false);
                projectEndYear.setHint("");
                projectEndYear.setText("");
            }
            else{
                projectEndYear.setText(endYear);
            }
        }

        projectName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("projectName",projectName.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("projectName",projectName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("projectName",projectName.getText().toString());
            }
        });

        projectCompanyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("projectCompanyName",projectCompanyName.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("projectCompanyName",projectCompanyName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("projectCompanyName",projectCompanyName.getText().toString());
            }
        });

        projectCompanyDesignation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("projectCompanyDesignation",projectCompanyDesignation.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("projectCompanyDesignation",projectCompanyDesignation.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("projectCompanyDesignation",projectCompanyDesignation.getText().toString());
            }
        });

        projectDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("projectDesc",projectDesc.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("projectDesc",projectDesc.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("projectDesc",projectDesc.getText().toString());
            }
        });

        projectStartYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("projectStartYear",projectStartYear.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("projectStartYear",projectStartYear.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("projectStartYear",projectStartYear.getText().toString());
            }
        });

        projectEndYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("projectEndYear",projectEndYear.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("projectEndYear",projectEndYear.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("projectEndYear",projectEndYear.getText().toString());
            }
        });

        currentProject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(currentProject.isChecked()){
                    projectEndYear.setEnabled(false);
                    projectEndYear.setHint("");
                    projectEndYear.setText("");
                    preferanceRef.setSharedPreferences("projectEndYear","Continue");

                    editor.putInt("currentProject",1);
                    editor.apply();
                    Toast.makeText(getContext(), "Not editable", Toast.LENGTH_SHORT).show();
                }
                else{
                    projectEndYear.setHint("2005");
                    projectEndYear.setEnabled(true);

                    editor.putInt("currentProject",1);
                    editor.apply();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_projects_details, container, false);
    }
}