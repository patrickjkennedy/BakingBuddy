package com.example.android.bakingbuddy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingbuddy.R;
import com.example.android.bakingbuddy.data.StepsAdapter;
import com.example.android.bakingbuddy.model.Recipe;
import com.example.android.bakingbuddy.model.Step;
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

/**
 * Created by pkennedy on 3/22/18.
 */

public class StepsFragment extends Fragment {

    // Context
    private Context mContext;

    // RecyclerView
    private RecyclerView mRecyclerView;
    private StepsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    // Recipe
    private Recipe mRecipe;

    // SimpleExoPlayerView
    private SimpleExoPlayerView mPlayerView;

    // SimpleExoPlayer
    private SimpleExoPlayer mExoPlayer;

    // Mandatory empty constructor
    public StepsFragment(){
    }

    // Inflates the fragment layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get the recipe from the intent that started the activity
        Intent intent = getActivity().getIntent();
        mRecipe = (Recipe) intent.getSerializableExtra("recipe");

        // Context
        mContext = getActivity();

        // Inflate the Overview fragment layout
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        // Get a reference to the SimpleExoPlayerView
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.player_view_steps);

        // Initialize the player
        initializePlayer(mRecipe.getSteps().get(0).getVideoURL());

        // Recyclerview
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_steps);

        // Create a linear layout manager and set it
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Setup click listener
        StepsAdapter.OverviewAdapterClickListener listener = new StepsAdapter.OverviewAdapterClickListener(){
            @Override
            public void onClick(View view, Step step) {
                Class destinationClass = DetailActivity.class;
                Intent intent = new Intent(mContext, destinationClass);

                Bundle bundle = new Bundle();
                bundle.putSerializable("step", step);
                bundle.putSerializable("recipe", mRecipe);

                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
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

    private void initializePlayer(String mediaUrl) {
        if(mExoPlayer == null){
            // Create an instance of the ExoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the media source
            String userAgent = Util.getUserAgent(mContext, "BakingBuddy");
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mediaUrl),
                    new DefaultDataSourceFactory(mContext, userAgent),
                    new DefaultExtractorsFactory(),
                    null,
                    null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer(){
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }
}
