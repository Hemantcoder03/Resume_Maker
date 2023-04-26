package com.hemant.resumemaker.fragments;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.telephony.PreciseDataConnectionState;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.hemant.resumemaker.DBHelper;
import com.hemant.resumemaker.PreferanceRef;
import com.hemant.resumemaker.R;

public class SkillFragment extends Fragment {

    Spinner skillLevel;
    TextInputEditText skills;

    public SkillFragment(){}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        skills = (TextInputEditText) getView().findViewById(R.id.skills);
        skillLevel = (Spinner) getView().findViewById(R.id.skillLevel);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ResumeData",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ArrayAdapter<CharSequence> skillLevelAdapter = ArrayAdapter.createFromResource(getContext(),R.array.skill_level_list, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        skillLevelAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        skillLevel.setAdapter(skillLevelAdapter);

        //set the data from database
        DBHelper db = new DBHelper(getContext());

        if(db.count()!=0) {
            skills.setText(db.getData("skills"));
            skillLevel.setSelection(Integer.parseInt(db.getData("skillLevelId")));
        }


        PreferanceRef preferanceRef = new PreferanceRef(getContext());

        skills.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("skills",skills.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                preferanceRef.setSharedPreferences("skills",skills.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                preferanceRef.setSharedPreferences("skills",skills.getText().toString());
            }
        });

        skillLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                editor.putInt("skillLevelId",skillLevel.getSelectedItemPosition());
                editor.apply();
                preferanceRef.setSharedPreferences("skillLevel",String.valueOf(skillLevel.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_skill, container, false);
    }
}