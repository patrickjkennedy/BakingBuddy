package com.example.android.bakingbuddy.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakingbuddy.R;
import com.example.android.bakingbuddy.data.OverviewFragmentPagerAdapter;
import com.example.android.bakingbuddy.model.Ingredient;
import com.example.android.bakingbuddy.model.Recipe;
import com.example.android.bakingbuddy.widget.UpdateBakingService;

import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private Recipe mRecipe;

    private ArrayList<Ingredient> mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Get the recipe from the intent that started the activity
        Intent intent = getIntent();
        mRecipe = (Recipe) intent.getSerializableExtra("recipe");

        if(findViewById(R.id.overview_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            if(savedInstanceState == null) {

                // Add the Steps Fragment to the screen
                FragmentManager fragmentManager = getSupportFragmentManager();
                StepsFragment stepsFragment = new StepsFragment();

                // Pass mTwoPane = true to the StepsFragment in an arguments bundle
                Bundle args = new Bundle();
                args.putBoolean("mTwoPane", mTwoPane);
                stepsFragment.setArguments(args);

                // Set the data
                stepsFragment.setRecipe(mRecipe);

                // Add the fragment to its container using a transaction
                fragmentManager.beginTransaction().add(R.id.overview_steps_container, stepsFragment).commit();

            }

        } else{
            mTwoPane = false;

            // Find the view pager that will allow the user to swipe between fragments
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

            // Create an adapter that knows which fragment should be shown on each page
            OverviewFragmentPagerAdapter adapter = new OverviewFragmentPagerAdapter(getSupportFragmentManager());

            // Set the adapter onto the view pager
            viewPager.setAdapter(adapter);

            // Give the TabLayout the ViewPager
            TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
            tabLayout.setupWithViewPager(viewPager);
        }

       UpdateBakingService.startBakingService(this, mRecipe);

    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
