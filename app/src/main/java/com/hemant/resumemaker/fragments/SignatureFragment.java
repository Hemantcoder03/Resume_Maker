package com.hemant.resumemaker.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.hemant.resumemaker.DBHelper;
import com.hemant.resumemaker.PreferanceRef;
import com.hemant.resumemaker.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SignatureFragment extends Fragment {

    public SignatureFragment(){

    }

    ImageView signatureImage;
    Button uploadPictureBtn,removePictureBtn;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signatureImage = view.findViewById(R.id.signatureImage);
        uploadPictureBtn = (Button)getView().findViewById(R.id.uploadPictureButton);
        removePictureBtn = (Button)getView().findViewById(R.id.removePictureButton);

        PreferanceRef preferanceRef = new PreferanceRef(getContext());

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("RemoveData",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //set the data from database
        DBHelper db = new DBHelper(getContext());

        if(db.count()!=0) {
            signatureImage.setImageURI(Uri.parse(db.getData("signaturePictureUri")));
        }

        uploadPictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(SignatureFragment.this)
                        .crop()
                        .cropSquare()
                        .compress(1024)
                        .start(101);
            }
        });

        removePictureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("android.resource://"+getContext().getPackageName()+"/drawable/resume_signature");
                signatureImage.setImageURI(uri);

                //set dafault image
                editor.putString("signaturePictureUri","android.resource://"+getContext().getPackageName()+"/drawable/resume_signature");
                editor.apply();
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101){
            Uri uri = data.getData();
            signatureImage.setImageURI(uri);

            PreferanceRef preferanceRef = new PreferanceRef(getContext());
            preferanceRef.setSharedPreferences("signaturePictureUri",uri.toString());

            SharedPreferences sharedPreferences = getContext().getSharedPreferences("RemoveData",0);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("signaturePictureUri","android.resource://"+getContext().getPackageName()+"/drawable/resume_signature");
            editor.apply();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signature, container, false);
    }
}