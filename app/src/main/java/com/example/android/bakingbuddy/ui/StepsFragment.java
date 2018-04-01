package com.example.android.bakingbuddy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    // SimpleExoPlayerView
    @BindView(R.id.player_view_steps) SimpleExoPlayerView mPlayerView;

    // SimpleExoPlayer
    private SimpleExoPlayer mExoPlayer;

    // Video URL
    private String mVideoUrl;

    // Current position (for OnResume)
    private long mCurrentPosition;

    // Mandatory empty constructor
    public StepsFragment(){
    }

    // Inflates the fragment layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Context
        mContext = getContext();

        // Get the recipe from the intent that started the activity
        Intent intent = getActivity().getIntent();
        mRecipe = (Recipe) intent.getSerializableExtra("recipe");

        // Extract overview mVideoUrl
        mVideoUrl = mRecipe.getSteps().get(0).getVideoURL();

        // Inflate the Overview fragment layout
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        // Bind data
        ButterKnife.bind(this, rootView);

        // Create a linear layout manager and set it
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Setup click listener
        StepsAdapter.StepsAdapterClickListener listener = new StepsAdapter.StepsAdapterClickListener(){
            @Override
            public void onClick(View view, int position) {
                Class destinationClass = DetailActivity.class;
                Intent intent = new Intent(mContext, destinationClass);

                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe", mRecipe);
                bundle.putInt("position", position);

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

        // Initialize the player
        initializePlayer(mVideoUrl);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set the title bar
        ((OverviewActivity) getActivity()).setActionBarTitle(mRecipe.getName());

        // Initialize the player
        if(!mVideoUrl.isEmpty()){
            initializePlayer(mVideoUrl);
            mPlayerView.setVisibility(View.VISIBLE);
        }

        if(mCurrentPosition != 0){
            mExoPlayer.seekTo(mCurrentPosition);
        }
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
        if (null != mExoPlayer) {
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mExoPlayer != null){
            mCurrentPosition = mExoPlayer.getCurrentPosition();
        }
    }

}
