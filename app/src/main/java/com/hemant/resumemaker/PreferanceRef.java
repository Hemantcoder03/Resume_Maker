package com.hemant.resumemaker;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PreferanceRef {

    Context context;

    public PreferanceRef(Context context) {
        this.context = context;
    }

    public static final String PREF_NAME = "ResumeData";
    public void setSharedPreferences(String key,String data){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,data);
        editor.apply();
    }

    public String getSharedPreferences(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,0);
        String value = sharedPreferences.getString(key,"");
        return value;
    }

}
