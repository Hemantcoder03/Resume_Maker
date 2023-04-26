package com.hemant.resumemaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "ResumeData.db", null, 1);
    }

    private final String TABLE_NAME = "ResumeDataTable";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+TABLE_NAME+" (" +
                "id INTEGER Primary key autoincrement," +
                "about TEXT," +
                "achievements TEXT,"+
                "achievementDesc TEXT,"+
                "collage TEXT,"+
                "educationDegreeName TEXT,"+
                "educationDesc TEXT," +
                "collageStartYear TEXT," +
                "collageEndYear TEXT," +
                "currentEducation int,"+
                "hobbyName TEXT,"+
                "languages TEXT,"+
                "gender TEXT," +
                "genderId TEXT,"+
                "dob TEXT,"+
                "profilePictureUri TEXT,"+
                "firstName TEXT,"+
                "lastName TEXT,"+
                "phoneNumber TEXT,"+
                "nationality TEXT,"+
                "emailAddress TEXT,"+
                "address TEXT,"+
                "profession TEXT,"+
                "projectName TEXT,"+
                "projectCompanyName TEXT,"+
                "ProjectCompanyDesignation TEXT,"+
                "projectDesc TEXT," +
                "projectStartYear TEXT," +
                "projectEndYear TEXT," +
                "currentProject int,"+
                "signaturePictureUri TEXT,"+
                "skills TEXT,"+
                "skillLevel TEXT," +
                "skillLevelId TEXT," +
                "companyName TEXT," +
                "companyDesignation TEXT," +
                "workExperienceDesc TEXT," +
                "workStartYear TEXT," +
                "workEndYear TEXT," +
                "currentWork int"+
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String query = "drop table if exists "+TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean insertData(String about,String achievements,String achievementDesc,String collage,String educationDegreeName,String educationDesc,
                              String collageStartYear,String collageEndYear,int currentEducation, String hobbyName,String languages, String gender,String genderId,String dob,
                              String profilePictureUri,String firstName,String lastName, String phoneNumber,String nationality,String emailAddress,
                              String address, String profession,String projectName, String projectCompanyName,String projectCompanyDesignation,
                              String projectDesc,String projectStartYear,String projectEndYear,int currentProject, String signaturePictureUri,String skills,
                              String skillLevel, String skillLevelId,String companyName,String companyDesignation,String workExperienceDesc,String workStartYear,
                              String workEndYear,int currentWork){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("about", about);
        values.put("achievements", achievements);
        values.put("achievementDesc", achievementDesc);
        values.put("collage", collage);
        values.put("educationDegreeName", educationDegreeName);
        values.put("educationDesc", educationDesc);
        values.put("collageStartYear", collageStartYear);
        values.put("collageEndYear", collageEndYear);
        values.put("currentEducation", currentEducation);
        values.put("hobbyName", hobbyName);
        values.put("languages", languages);
        values.put("gender", gender);
        values.put("genderId", genderId);
        values.put("dob", dob);
        values.put("profilePictureUri", profilePictureUri);
        values.put("firstName", firstName);
        values.put("lastName", lastName);
        values.put("phoneNumber", phoneNumber);
        values.put("nationality", nationality);
        values.put("emailAddress", emailAddress);
        values.put("address", address);
        values.put("profession", profession);
        values.put("projectName", projectName);
        values.put("projectCompanyName", projectCompanyName);
        values.put("projectCompanyDesignation", projectCompanyDesignation);
        values.put("projectDesc", projectDesc);
        values.put("projectStartYear", projectStartYear);
        values.put("projectEndYear", projectEndYear);
        values.put("currentProject", currentProject);
        values.put("signaturePictureUri", signaturePictureUri);
        values.put("skills", skills);
        values.put("skillLevel", skillLevel);
        values.put("skillLevelId", skillLevelId);
        values.put("companyName", companyName);
        values.put("companyDesignation", companyDesignation);
        values.put("workExperienceDesc", workExperienceDesc);
        values.put("workStartYear", workStartYear);
        values.put("workEndYear", workEndYear);
        values.put("currentWork", currentWork);
        int isInserted = (int) db.insert(TABLE_NAME,null,values);
        return isInserted!=-1;
    }

    public boolean updateData(String about,String achievements,String achievementDesc,String collage,String educationDegreeName,String educationDesc,
                              String collageStartYear,String collageEndYear,int currentEducation, String hobbyName,String languages, String gender,String genderId,String dob,
                              String profilePictureUri,String firstName,String lastName, String phoneNumber,String nationality,String emailAddress,
                              String address, String profession,String projectName, String projectCompanyName,String projectCompanyDesignation,
                              String projectDesc,String projectStartYear,String projectEndYear,int currentProject, String signaturePictureUri,String skills,
                              String skillLevel, String skillLevelId,String companyName,String companyDesignation,String workExperienceDesc,String workStartYear,
                              String workEndYear,int currentWork){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("about", about);
        values.put("achievements", achievements);
        values.put("achievementDesc", achievementDesc);
        values.put("collage", collage);
        values.put("educationDegreeName", educationDegreeName);
        values.put("educationDesc", educationDesc);
        values.put("collageStartYear", collageStartYear);
        values.put("collageEndYear", collageEndYear);
        values.put("currentEducation", currentEducation);
        values.put("hobbyName", hobbyName);
        values.put("languages", languages);
        values.put("gender", gender);
        values.put("genderId", genderId);
        values.put("dob", dob);
        values.put("profilePictureUri", profilePictureUri);
        values.put("firstName", firstName);
        values.put("lastName", lastName);
        values.put("phoneNumber", phoneNumber);
        values.put("nationality", nationality);
        values.put("emailAddress", emailAddress);
        values.put("address", address);
        values.put("profession", profession);
        values.put("projectName", projectName);
        values.put("projectCompanyName", projectCompanyName);
        values.put("projectCompanyDesignation", projectCompanyDesignation);
        values.put("projectDesc", projectDesc);
        values.put("projectStartYear", projectStartYear);
        values.put("projectEndYear", projectEndYear);
        values.put("currentProject", currentProject);
        values.put("signaturePictureUri", signaturePictureUri);
        values.put("skills", skills);
        values.put("skillLevel", skillLevel);
        values.put("skillLevelId", skillLevelId);
        values.put("companyName", companyName);
        values.put("companyDesignation", companyDesignation);
        values.put("workExperienceDesc", workExperienceDesc);
        values.put("workStartYear", workStartYear);
        values.put("workEndYear", workEndYear);
        values.put("currentWork", currentWork);

        int isUpdated = db.update(TABLE_NAME,values,"id = 1",null);
        return isUpdated!=-1;
    }

    public String getData(String columnName){   //It used to retrieve the data from database

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor dataCursor = db.rawQuery("Select "+columnName+" from "+TABLE_NAME,null);

        String data = null;

        if(dataCursor.moveToFirst()){
            data = dataCursor.getString(0);
        }

        return data;
    }

    public int count(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dataCursor = db.rawQuery("Select * from "+TABLE_NAME,null);

        int dataCount = 0;

        if(dataCursor.moveToFirst()){
            dataCount = dataCursor.getCount();
        }
        return dataCount;
    }
}
