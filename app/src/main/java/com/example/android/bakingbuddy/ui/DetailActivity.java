package com.example.android.bakingbuddy.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.android.bakingbuddy.R;

public class DetailActivity extends AppCompatActivity {

    //Orientation
    private int ORIENTATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Determine orientation of device
        ORIENTATION = getResources().getConfiguration().orientation;

        if(ORIENTATION == 2){
            setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
