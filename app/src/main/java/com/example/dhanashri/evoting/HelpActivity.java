package com.example.dhanashri.evoting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HelpActivity extends AppCompatActivity {

    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        final GlobalClass globalClass=(GlobalClass)getApplicationContext();
        path= globalClass.getconstr();
    }
}