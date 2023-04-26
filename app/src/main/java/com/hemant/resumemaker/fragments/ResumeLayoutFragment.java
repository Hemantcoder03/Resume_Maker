package com.hemant.resumemaker.fragments;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hemant.resumemaker.DBHelper;
import com.hemant.resumemaker.R;

import java.io.File;
import java.io.FileOutputStream;

public class ResumeLayoutFragment extends Fragment {

    TextView fName,lName,designation,phoneNo,email,address,about,gender,nationality,
    dob,lang,collageName,collageDegreeName,collageDesc,collageStartYear,collageEndYear,
    skillName,skillLevel,companyName,companyDesignationName,companyDesc,workStartYear,
    workEndYear,projectName,projectDesignationName,projectCompanyName,projectDesc,
    projectStartYear,projectEndYear,hobbyName,hobbies,achievementName,achievementDesc;
    ImageView profilePhoto,signaturePhoto;
    LinearLayout mainLinearlayout;

    public ResumeLayoutFragment(){}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainLinearlayout = getView().findViewById(R.id.mainLinearlayout);

        fName = getView().findViewById(R.id.fName);
        lName = getView().findViewById(R.id.lName);
        about = getView().findViewById(R.id.about);
        designation = getView().findViewById(R.id.designation);
        phoneNo = getView().findViewById(R.id.phone);
        email = getView().findViewById(R.id.email);
        address = getView().findViewById(R.id.address);
        about = getView().findViewById(R.id.about);
        gender = getView().findViewById(R.id.gender);
        nationality = getView().findViewById(R.id.nationality);
        dob = getView().findViewById(R.id.dob);
        lang = getView().findViewById(R.id.lang);
        collageName = getView().findViewById(R.id.collageName);
        collageDegreeName = getView().findViewById(R.id.collageDegreeName);
        collageDesc = getView().findViewById(R.id.collageDesc);
        collageStartYear = getView().findViewById(R.id.collageStartYear);
        collageEndYear = getView().findViewById(R.id.collageEndYear);
        skillName = getView().findViewById(R.id.skillName);
        skillLevel = getView().findViewById(R.id.skillLevel);
        companyName = getView().findViewById(R.id.companyName);
        companyDesignationName = getView().findViewById(R.id.companyDesignationName);
        companyDesc = getView().findViewById(R.id.companyDesc);
        workStartYear = getView().findViewById(R.id.workStartYear);
        workEndYear = getView().findViewById(R.id.workEndYear);
        projectName = getView().findViewById(R.id.projectName);
        projectDesignationName = getView().findViewById(R.id.projectDesignationName);
        projectCompanyName = getView().findViewById(R.id.projectCompanyName);
        projectDesc = getView().findViewById(R.id.projectDesc);
        projectStartYear = getView().findViewById(R.id.projectStartYear);
        projectEndYear = getView().findViewById(R.id.projectEndYear);
        hobbyName = getView().findViewById(R.id.hobbyName);
        hobbies = getView().findViewById(R.id.hobbies);
        achievementName = getView().findViewById(R.id.achievementName);
        achievementDesc = getView().findViewById(R.id.achievementDesc);
        profilePhoto = getView().findViewById(R.id.photo);
        signaturePhoto = getView().findViewById(R.id.signature);

        //we can retrieve the data from sqlite database and display on the screen in form of resume
        DBHelper db = new DBHelper(getContext());

        fName.setText(db.getData("firstName"));
        lName.setText(db.getData("lastName"));
        profilePhoto.setImageURI(Uri.parse(db.getData("profilePictureUri")));
        about.setText(db.getData("about"));
        designation.setText(db.getData("profession"));
        phoneNo.setText(db.getData("phoneNumber"));
        email.setText(db.getData("emailAddress"));
        address.setText(db.getData("address"));
        gender.setText(db.getData("gender"));
        nationality.setText(db.getData("nationality"));
        dob.setText(db.getData("dob"));
        lang.setText(db.getData("languages"));
        collageName.setText(db.getData("collage"));
        collageDegreeName.setText(db.getData("educationDegreeName"));
        collageDesc.setText(db.getData("educationDesc"));
        collageStartYear.setText(db.getData("collageStartYear"));
        collageEndYear.setText(db.getData("collageEndYear"));
        skillName.setText(db.getData("skills"));
        skillLevel.setText(db.getData("skillLevel"));
        companyName.setText(db.getData("companyName"));
        companyDesignationName.setText(db.getData("companyDesignation"));
        companyDesc.setText(db.getData("workExperienceDesc"));
        workStartYear.setText(db.getData("workStartYear"));
        workEndYear.setText(db.getData("workEndYear"));
        projectName.setText(db.getData("projectName"));
        projectDesignationName.setText(db.getData("projectCompanyDesignation"));
        projectCompanyName.setText(db.getData("projectCompanyName"));
        projectDesc.setText(db.getData("projectDesc"));
        projectStartYear.setText(db.getData("projectStartYear"));
        projectEndYear.setText(db.getData("projectEndYear"));
        hobbyName.setText(db.getData("hobbyName"));
        hobbies.setText(""); //not defined in database we can update after some time
        achievementName.setText(db.getData("achievements"));
        achievementDesc.setText(db.getData("achievementDesc"));
        signaturePhoto.setImageURI(Uri.parse(db.getData("signaturePictureUri")));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resume_layout, container, false);
    }
}