package com.example.dhanashri.evoting;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    public int splash_out_time=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final GlobalClass globalClass=(GlobalClass)getApplicationContext();
        globalClass.setconstr("http://192.168.0.106:8052/api/");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               Intent homeIntent= new Intent(SplashActivity.this, LoginActivity.class);
               startActivity(homeIntent);
               finish();

            }
        },splash_out_time);
    }
}
