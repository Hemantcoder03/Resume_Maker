package com.hemant.resumemaker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.hemant.resumemaker.adapters.PdfDocumentPrintAdapter;
import com.hemant.resumemaker.adapters.ResumeFragmentAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class MainResumeActivity extends AppCompatActivity {

    ViewPager2 resumeViewPager2;
    ImageButton downloadBtn,sendBtn,printBtn,shareBtn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_resume);

        getSupportActionBar().hide();  //hide the action bar

        resumeViewPager2 = findViewById(R.id.resumeViewPager2);
        downloadBtn = findViewById(R.id.download_btn);
        printBtn = findViewById(R.id.print_btn);
        shareBtn = findViewById(R.id.share_btn);
        sendBtn = findViewById(R.id.send_btn);

        //Sharedpreference is used to check which button was clicked
        SharedPreferences sharedPreferences= getSharedPreferences("Resume Maker",0); //0 means MODE_PRIVATE
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //it is used to set the data and layout of the resume
        ResumeFragmentAdapter resumeFragmentAdapter = new ResumeFragmentAdapter(this);
        resumeViewPager2.setAdapter(resumeFragmentAdapter);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(MainResumeActivity.this);
                progressDialog.setTitle("Saving...");
                progressDialog.show();
                progressDialog.setContentView(R.layout.download_progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                //if download button is clicked then sharedpreference is 1
                editor.putInt("button_no",1);   //it can set the button number
                editor.apply();

                //download button clicked
                // first request if not given
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //permission denied or requested
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                } else {
                    //permission granted
                    //call the method to create pdf
                    Toast.makeText(MainResumeActivity.this, "Resume Downloaded successfully", Toast.LENGTH_SHORT).show();
                    layoutToBitmap();
                }
            }
        });

        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if print button is clicked then sharedpreference is 2
                editor.putInt("button_no",2);   //it can set the button number
                editor.apply();

                printResume();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //if share button is clicked then sharedpreference is 3
                editor.putInt("button_no",3);   //it can set the button number
                editor.apply();

                sharePdf();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if sendEmail button is clicked then sharedpreference is 4
                editor.putInt("button_no",4);   //it can set the button number
                editor.apply();

                sendPdfByEmail();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            layoutToBitmap();
        }
    }

    public void layoutToBitmap(){
        LinearLayout mainLinearLayout = findViewById(R.id.mainLinearlayout);
        mainLinearLayout.setDrawingCacheEnabled(true);
        mainLinearLayout.buildDrawingCache();
        mainLinearLayout.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = mainLinearLayout.getDrawingCache();
        createPdf(bitmap);
    }

    public File createPdf(Bitmap bitmap){

        LinearLayout mainLinearLayout =(LinearLayout) findViewById(R.id.mainLinearlayout);
        //import pdf and paint class for draw or paint the page and convert to pdf
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        File directory = new File(Environment.getExternalStorageDirectory(),"/Resume Maker/");
        if(!directory.exists()){
            directory.mkdir();
        }
        directory.mkdirs();

        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(mainLinearLayout.getWidth(),mainLinearLayout.getHeight(),1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

        Rect rectangle = new Rect(0,0,mainLinearLayout.getWidth()-10,mainLinearLayout.getHeight()-15);

        Canvas myCanvas = myPage.getCanvas();
        myCanvas.drawBitmap(bitmap,null,rectangle,null);
        myPdfDocument.finishPage(myPage);

        File myFile = new File(directory,"/resume.pdf");

        try {
            FileOutputStream myFileOutputStream = new FileOutputStream(myFile);
            myPdfDocument.writeTo(myFileOutputStream);
        }
        catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        myPdfDocument.close();

        //Sharedpreference is used to check which button was clicked
        SharedPreferences sharedPreferences= getSharedPreferences("Resume Maker",0); //0 means MODE_PRIVATE

        int button_no = sharedPreferences.getInt("button_no",0);

        if(button_no == 1){
            //download button
            progressDialog.dismiss();
            Toast.makeText(this, "Pdf downloaded successfully", Toast.LENGTH_SHORT).show();
        }
        else if(button_no == 2){
            //send pdf as mail
            sendPdfByEmail();
        }
        else if(button_no == 3){
            //print the resume pdf
            printResume();
        }
        else if (button_no == 4) {
            //share the resume pdf
            sharePdf();
        }

        return myFile;
    }

    public void printResume(){

        String path = Environment.getExternalStorageDirectory()+"/Resume Maker/resume.pdf";
        File file = new File(path);

            PrintManager printManager = (PrintManager) MainResumeActivity.this.getSystemService(Context.PRINT_SERVICE);
        try{
            if(file.exists()){
                PrintDocumentAdapter printAdapter = new PdfDocumentPrintAdapter(this,path);
                PrintAttributes.Builder builder = new PrintAttributes.Builder();
                builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
                printManager.print("Document",printAdapter,builder.build());
            }
            else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //permission denied or requested
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                } else {
                    //permission granted
                    //call the method to create pdf
                    layoutToBitmap();
                }
            }
        }
        catch(Exception e){
            Log.d("printResume",e.getMessage());
        }
    }

    private void sharePdf() {

        String path = Environment.getExternalStorageDirectory()+"/Resume Maker/resume.pdf";
        File file = new File(path);
        try{
        if(file.exists()){

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM,FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName()+".provider",file));
            shareIntent.setType("application/pdf");
            startActivity(shareIntent);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //permission denied or requested
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                //permission granted
                //call the method to create pdf
                layoutToBitmap();
            }
        }
        }
        catch (Exception e){
            Log.d("error is",e.getMessage());
        }
    }

    private void sendPdfByEmail() {

        String path = Environment.getExternalStorageDirectory()+"/Resume Maker/resume.pdf";
        File file = new File(path);

        if(file.exists()){
            Intent emailIntent = new Intent();
            emailIntent.setAction(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_STREAM,FileProvider.getUriForFile(getApplicationContext(),getApplicationContext().getPackageName()+".provider",file));
            emailIntent.setType("application/pdf");
            emailIntent.setPackage("com.google.android.gm");
            startActivity(emailIntent);
        }
        else{
            Toast.makeText(this, "File Not Found", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //permission denied or requested
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                //permission granted
                //call the method to create pdf
                layoutToBitmap();
            }
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}