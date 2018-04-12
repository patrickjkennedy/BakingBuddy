package com.example.android.bakingbuddy.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.android.bakingbuddy.R;
import com.example.android.bakingbuddy.data.StepsAdapter;
import com.example.android.bakingbuddy.model.Recipe;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pkennedy on 3/22/18.
 */

public class StepsFragment extends Fragment {

    // Context
    private Context mContext;

    // RecyclerView
    @BindView(R.id.rv_steps) RecyclerView mRecyclerView;

    // StepsAdapter
    private StepsAdapter mAdapter;

    // LinearLayoutManager
    private LinearLayoutManager mLayoutManager;

    // Recipe
    private Recipe mRecipe;

    // Ingredients TextView for Two Pane mode
    @Nullable
    @BindView(R.id.tv_steps_ingredients) TextView mIngredientsTextView;

    // Two Pane Mode Boolean
    private boolean mTwoPane = false;

    // Mandatory empty constructor
    public StepsFragment(){
    }

    // Inflates the fragment layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Check to see if the fragment was started in Two Pane Mode
        if(getArguments()!=null){
            mTwoPane = getArguments().getBoolean("mTwoPane");
        }

        // Context
        mContext = getContext();

        // Get the recipe from the intent that started the activity
        Intent intent = getActivity().getIntent();
        mRecipe = (Recipe) intent.getSerializableExtra("recipe");

        // Declare rootView
        View rootView;

        if(mTwoPane){
            // Inflate the Steps fragment tablet layout
            rootView = inflater.inflate(R.layout.fragment_steps_tablet, container, false);

            // Bind data
            ButterKnife.bind(this, rootView);

            // Set an onclick listener on the Ingredients Textview
            mIngredientsTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Create an Ingredients Fragment and add it to the screen
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    IngredientsFragment ingredientsFragment = new IngredientsFragment();

                    // Pass the required data to the IngredientsFragment in an arguments bundle
                    Bundle args = new Bundle();
                    args.putSerializable("mRecipe", mRecipe);
                    ingredientsFragment.setArguments(args);

                    // Add the fragment to its container using a transaction
                    fragmentManager.beginTransaction().replace(R.id.overview_detail_container, ingredientsFragment).commit();
                }
            });

        } else {
            // Inflate the Steps fragment layout
            rootView = inflater.inflate(R.layout.fragment_steps, container, false);

            // Bind data
            ButterKnife.bind(this, rootView);

        }

        // Create a linear layout manager and set it
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Setup click listener for steps
        StepsAdapter.StepsAdapterClickListener listener = new StepsAdapter.StepsAdapterClickListener(){
            @Override
            public void onClick(View view, int position) {
                if (mTwoPane){
                    // Create the correct detail fragment and add it to the screen
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    DetailFragment detailFragment = new DetailFragment();

                    // Pass required data to the DetailFragment in an arguments bundle
                    Bundle args = new Bundle();
                    args.putBoolean("mTwoPane", mTwoPane);
                    args.putInt("mPosition", position);
                    args.putSerializable("mRecipe", mRecipe);
                    detailFragment.setArguments(args);

                    // Add the fragment to its container using a transaction
                    fragmentManager.beginTransaction().replace(R.id.overview_detail_container, detailFragment).commit();

                } else {
                    Class destinationClass = DetailActivity.class;
                    Intent intent = new Intent(mContext, destinationClass);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("recipe", mRecipe);
                    bundle.putInt("position", position);

                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1);
                }
            }
        };

        // Initialize the Recyclerview adapter, StepsAdapter
        mAdapter = new StepsAdapter(listener, mContext);

        // Set the adapter
        mRecyclerView.setAdapter(mAdapter);

        // Pass in the recipe to extract the steps
        mAdapter.setSteps(mRecipe);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set the title bar
        ((OverviewActivity) getActivity()).setActionBarTitle(mRecipe.getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                mRecipe = (Recipe) data.getSerializableExtra("recipe");
            }
        }
    }

    // Setter method for the recipe
    public void setRecipe(Recipe recipe){
        mRecipe = recipe;
    }

}
