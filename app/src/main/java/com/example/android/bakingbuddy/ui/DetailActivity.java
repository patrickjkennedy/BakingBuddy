package com.example.android.bakingbuddy.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingbuddy.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
