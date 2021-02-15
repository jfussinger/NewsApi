package com.example.android.newsapi.activity;

//https://android.jlelse.eu/right-way-to-create-splash-screen-on-android-e7f1709ba154

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Start main activity
        startActivity(new Intent(this, MainActivity.class));
        // close splashScreen activity
        finish();
    }
}
