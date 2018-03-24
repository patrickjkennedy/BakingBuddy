package com.example.android.bakingbuddy.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.android.bakingbuddy.R;

public class StepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
