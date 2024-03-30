package com.example.proyectointegradorv1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.proyectointegradorv1.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Log.d("SplashScreen", "onCreate");

        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    Log.d("SplashScreen", "Sleeping for 2000 milliseconds");
                    sleep(2000);
                    Log.d("SplashScreen", "Starting LoginActivity");
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        };

        splashThread.start();
    }
}