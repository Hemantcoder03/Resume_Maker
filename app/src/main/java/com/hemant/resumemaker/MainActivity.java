package com.hemant.resumemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.start_btn);

        //hide the action bar
        getSupportActionBar().hide();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.progress_bar_dialog);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                new CountDownTimer(5000,1000){

                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        dialog.dismiss();
                    }
                };
                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }

}