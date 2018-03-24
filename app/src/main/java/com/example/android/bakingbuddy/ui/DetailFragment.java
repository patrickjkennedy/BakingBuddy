package com.example.android.bakingbuddy.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.bakingbuddy.R;
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
 * Created by pkennedy on 3/24/18.
 */

public class DetailFragment extends Fragment {

    // Context
    private Context mContext;

    // Step
    private Step mStep;

    // SimpleExoPlayerView
    private SimpleExoPlayerView mPlayerView;

    // SimpleExoPlayer
    private SimpleExoPlayer mExoPlayer;

    // Mandatory constructor for inflating the fragment
    public DetailFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = getContext();

        // Get the data from the intent that started this activity
        Intent intent = getActivity().getIntent();
        mStep = (Step) intent.getSerializableExtra("step");

        // Inflate the fragment_detail layout
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // Get a reference to the SimpleExoPlayerView
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.player_view);

        // Initialize the player
        initializePlayer(mStep.getVideoURL());

        // Get a reference to the short description and bind the data
        TextView shortDescription = (TextView) rootView.findViewById(R.id.tv_detail_short_description);
        shortDescription.setText(mStep.getShortDescription());

        // Get a reference to the description and bind the data
        TextView description = (TextView) rootView.findViewById(R.id.tv_detail_description);
        description.setText(mStep.getDescription());

        return rootView;
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
