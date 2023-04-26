package com.hemant.resumemaker.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.hemant.resumemaker.DBHelper;
import com.hemant.resumemaker.PreferanceRef;
import com.hemant.resumemaker.R;

import java.io.IOException;
import java.util.Calendar;


public class PersonalInfoFragment extends Fragment{

    Spinner gender;
    TextInputEditText dob;

    Button uploadProfilePictureBtn,removeProfilePictureBtn;
    ImageView profileImage;
    TextInputEditText firstName,lastName,nationality,emailAddress,address,profession;
    TextInputEditText phoneNumber;

    public PersonalInfoFragment(){

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gender = (Spinner)getView().findViewById(R.id.gender);
        dob = (TextInputEditText)getView().findViewById(R.id.dob);
        profileImage=(ImageView)getView().findViewById(R.id.profileImage);
        uploadProfilePictureBtn = (Button)getView().findViewById(R.id.uploadProfilePictureBtn);
        removeProfilePictureBtn = (Button)getView().findViewById(R.id.removeProfilePictureBtn);
        firstName = (TextInputEditText)getView().findViewById(R.id.firstName);
        lastName = (TextInputEditText)getView().findViewById(R.id.lastName);
        phoneNumber = (TextInputEditText)getView().findViewById(R.id.phoneNumber);
        nationality = (TextInputEditText)getView().findViewById(R.id.nationality);
        emailAddress = (TextInputEditText)getView().findViewById(R.id.emailAddress);
        address = (TextInputEditText)getView().findViewById(R.id.address);
        profession = (TextInputEditText)getView().findViewById(R.id.profession);

        //set the gender list
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getContext(),R.array.gender_list, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        gender.setAdapter(genderAdapter);


        //Preference class for used SharedPreference
        PreferanceRef preferanceRef = new PreferanceRef(getContext());

        //SharedPreference used for give different default value
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ResumeData",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //get data from database
        DBHelper db = new DBHelper(getContext());

        //set the previous selected or written value on that position or edittext

        if(db.count()!=0) {
            gender.setSelection(Integer.parseInt(db.getData("genderId")));
            dob.setText(db.getData("dob"));
            profileImage.setImageURI(Uri.parse(db.getData("profilePictureUri")));
            firstName.setText(db.getData("firstName"));
            lastName.setText(db.getData("lastName"));
            phoneNumber.setText(db.getData("phoneNumber"));
            nationality.setText(db.getData("nationality"));
            emailAddress.setText(db.getData("emailAddress"));
            address.setText(db.getData("address"));
            profession.setText(db.getData("profession"));
        }

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                //set gender in sharepreference
                editor.putInt("genderId",gender.getSelectedItemPosition());
                editor.apply();
                preferanceRef.setSharedPreferences("gender", String.valueOf(gender.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date = day+"-"+(month+1)+"-"+year;
                        dob.setText(date);
                        preferanceRef.setSharedPreferences("dob",date);
                    }
                },year,month,day);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
                return true;
            }
        });

        uploadProfilePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(PersonalInfoFragment.this)
                        .crop()
                        .cropSquare()
                        .compress(1024)
                        .start(100);
            }
        });
        
        removeProfilePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("android.resource://"+getContext().getPackageName()+"/drawable/resume_user");
                profileImage.setImageURI(uri);
                //used to set the image string value in the sharepreference
                editor.putString("profilePictureUri",uri.toString());
                editor.apply();
            }
        });

        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //used to set the sharepreference value before text can touch or changed
                preferanceRef.setSharedPreferences("firstName",firstName.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //used to set the sharepreference value when text changing
                preferanceRef.setSharedPreferences("firstName",firstName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //used to set the sharepreference value after the changed
                preferanceRef.setSharedPreferences("firstName",firstName.getText().toString());
            }
        });

        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //used to set the sharepreference value before text can touch or changed
                preferanceRef.setSharedPreferences("lastName",lastName.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //used to set the sharepreference value when text changing
                preferanceRef.setSharedPreferences("lastName",lastName.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //used to set the sharepreference value after the changed
                preferanceRef.setSharedPreferences("lastName",lastName.getText().toString());
            }
        });


        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //used to set the sharepreference value before text can touch or changed
                preferanceRef.setSharedPreferences("phoneNumber",phoneNumber.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //used to set the sharepreference value when text changing
                preferanceRef.setSharedPreferences("phoneNumber",phoneNumber.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //used to set the sharepreference value after the changed
                preferanceRef.setSharedPreferences("phoneNumber",phoneNumber.getText().toString());
            }
        });

        nationality.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //used to set the sharepreference value before text can touch or changed
                preferanceRef.setSharedPreferences("nationality",nationality.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //used to set the sharepreference value when text changing
                preferanceRef.setSharedPreferences("nationality",nationality.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //used to set the sharepreference value after the changed
                preferanceRef.setSharedPreferences("nationality",nationality.getText().toString());
            }
        });

        emailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //used to set the sharepreference value before text can touch or changed
                preferanceRef.setSharedPreferences("emailAddress",emailAddress.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //used to set the sharepreference value when text changing
                preferanceRef.setSharedPreferences("emailAddress",emailAddress.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //used to set the sharepreference value after the changed
                preferanceRef.setSharedPreferences("emailAddress",emailAddress.getText().toString());
            }
        });

        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //used to set the sharepreference value before text can touch or changed
                preferanceRef.setSharedPreferences("address",address.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //used to set the sharepreference value when text changing
                preferanceRef.setSharedPreferences("address",address.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //used to set the sharepreference value after the changed
                preferanceRef.setSharedPreferences("address",address.getText().toString());
            }
        });

        profession.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //used to set the sharepreference value before text can touch or changed
                preferanceRef.setSharedPreferences("profession",profession.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //used to set the sharepreference value when text changing
                preferanceRef.setSharedPreferences("profession",profession.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //used to set the sharepreference value after the changed
                preferanceRef.setSharedPreferences("profession",profession.getText().toString());
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

                    if(requestCode==100){
                        Uri uri = data.getData();
                        profileImage.setImageURI(uri);

                        //used to set the image string value in the sharepreference
                        //SharedPreference used for give different default value
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ResumeData",0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("profilePictureUri",uri.toString());
                        editor.apply();
                    }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_info, container, false);
    }
}