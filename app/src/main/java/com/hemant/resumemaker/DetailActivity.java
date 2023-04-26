package com.hemant.resumemaker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.hemant.resumemaker.adapters.FragmentAdapter;
import com.hemant.resumemaker.fragments.PersonalInfoFragment;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ImageView backBtn;
    Button saveBtn;
    //create progress dialog
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);
        backBtn = findViewById(R.id.backButton);
        saveBtn = findViewById(R.id.saveButton);

        //used PreferenceRef as sharedPreference for save data
        PreferanceRef preferanceRef = new PreferanceRef(this);

        //it use to insert , delete or update data from sqlite database
        DBHelper db = new DBHelper(this);

        getSupportActionBar().hide();

        FragmentAdapter adapter = new FragmentAdapter(this);
        viewPager2.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.white);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.black);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.white);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //show progress dialog
                progressDialog = new ProgressDialog(DetailActivity.this);
                progressDialog.setTitle("Saving..");
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                SharedPreferences sharedPreferences = getSharedPreferences("ResumeData", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String about = preferanceRef.getSharedPreferences("about");
                String achievements = preferanceRef.getSharedPreferences("achievements");
                String achievementDesc = preferanceRef.getSharedPreferences("achievementDesc");
                String collage = preferanceRef.getSharedPreferences("collage");
                String educationDegreeName = preferanceRef.getSharedPreferences("educationDegreeName");
                String educationDesc = preferanceRef.getSharedPreferences("educationDesc");
                String collageStartYear = preferanceRef.getSharedPreferences("collageStartYear");
                String collageEndYear = preferanceRef.getSharedPreferences("collageEndYear");
                int currentEducation = sharedPreferences.getInt("currentEducation", 0);
                String hobbyName = preferanceRef.getSharedPreferences("hobbyName");
                String languages = preferanceRef.getSharedPreferences("languages");
                String genderId = String.valueOf(sharedPreferences.getInt("genderId", 0));
                String gender = preferanceRef.getSharedPreferences("gender");
                String dob = preferanceRef.getSharedPreferences("dob");
                String profilePictureUri = sharedPreferences.getString("profilePictureUri", "android.resource://" + getPackageName() + "/drawable/resume_user");
                String firstName = preferanceRef.getSharedPreferences("firstName");
                String lastName = preferanceRef.getSharedPreferences("lastName");
                String phoneNumber = preferanceRef.getSharedPreferences("phoneNumber");
                String nationality = preferanceRef.getSharedPreferences("nationality");
                String emailAddress = preferanceRef.getSharedPreferences("emailAddress");
                String address = preferanceRef.getSharedPreferences("address");
                String profession = preferanceRef.getSharedPreferences("profession");
                String projectName = preferanceRef.getSharedPreferences("projectName");
                String projectCompanyName = preferanceRef.getSharedPreferences("projectCompanyName");
                String projectCompanyDesignation = preferanceRef.getSharedPreferences("projectCompanyDesignation");
                String projectDesc = preferanceRef.getSharedPreferences("projectDesc");
                String projectStartYear = preferanceRef.getSharedPreferences("projectStartYear");
                String projectEndYear = preferanceRef.getSharedPreferences("projectEndYear");
                int currentProject = sharedPreferences.getInt("currentProject", 0);
                String signaturePictureUri = sharedPreferences.getString("signaturePictureUri", "android.resource://" + getPackageName() + "/drawable/resume_signature");
                String skills = preferanceRef.getSharedPreferences("skills");
                String skillLevel = preferanceRef.getSharedPreferences("skillLevel");
                String skillLevelId = String.valueOf(sharedPreferences.getInt("skillLevelId", 0));
                String companyName = preferanceRef.getSharedPreferences("companyName");
                String companyDesignation = preferanceRef.getSharedPreferences("companyDesignation");
                String workExperienceDesc = preferanceRef.getSharedPreferences("workExperienceDesc");
                String workStartYear = preferanceRef.getSharedPreferences("workStartYear");
                String workEndYear = preferanceRef.getSharedPreferences("workEndYear");
                int currentWork = sharedPreferences.getInt("currentWork", 0);

                int dataNumber = sharedPreferences.getInt("dataNumber", 1);

                if (dataNumber == 1) {
                    boolean isInserted = db.insertData(about, achievements, achievementDesc, collage, educationDegreeName, educationDesc, collageStartYear, collageEndYear, currentEducation,
                            hobbyName, languages, gender, genderId, dob, profilePictureUri, firstName, lastName, phoneNumber, nationality, emailAddress, address, profession, projectName,
                            projectCompanyName, projectCompanyDesignation, projectDesc, projectStartYear, projectEndYear, currentProject, signaturePictureUri, skills, skillLevel, skillLevelId,
                            companyName, companyDesignation, workExperienceDesc, workStartYear, workEndYear, currentWork);

                    editor.putInt("dataNumber", 2);
                    editor.apply();
                }

                if (dataNumber == 2) {
                    boolean isUpdated = db.updateData(about, achievements, achievementDesc, collage, educationDegreeName, educationDesc, collageStartYear, collageEndYear, currentEducation,
                            hobbyName, languages, gender, genderId, dob, profilePictureUri, firstName, lastName, phoneNumber, nationality, emailAddress, address, profession, projectName,
                            projectCompanyName, projectCompanyDesignation, projectDesc, projectStartYear, projectEndYear, currentProject, signaturePictureUri, skills, skillLevel, skillLevelId,
                            companyName, companyDesignation, workExperienceDesc, workStartYear, workEndYear, currentWork);


                }
                startActivity(new Intent(DetailActivity.this, MainResumeActivity.class));
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}