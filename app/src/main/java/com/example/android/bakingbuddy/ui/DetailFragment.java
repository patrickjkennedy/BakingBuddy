package com.example.android.bakingbuddy.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.android.bakingbuddy.R;
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
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pkennedy on 3/24/18.
 */

public class DetailFragment extends Fragment implements View.OnClickListener{

    // Context
    private Context mContext;

    // Current Step
    private Step mStep;

    // Current Step arraylist position
    private int mPosition;

    // Recipe
    private Recipe mRecipe;

    // Steps in the Recipe
    private ArrayList<Step> mSteps = new ArrayList<>();

    // SimpleExoPlayerView
    @BindView(R.id.player_view) SimpleExoPlayerView mPlayerView;

    // SimpleExoPlayer
    private SimpleExoPlayer mExoPlayer;

    // Previous Button
    @BindView(R.id.btn_previous) Button mPreviousButton;

    // Next Button
    @BindView(R.id.btn_next) Button mNextButton;

    // Short Description
    @BindView(R.id.tv_detail_short_description) TextView shortDescription;

    // Description
    @BindView(R.id.tv_detail_description) TextView description;

    // Mandatory constructor for inflating the fragment
    public DetailFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Adding this allows the Fragment to call onOptionsItemSelected correctly
        setHasOptionsMenu(true);

        mContext = getContext();

        // Get the data from the intent that started this fragment
        Intent intent = getActivity().getIntent();
        mRecipe = (Recipe) intent.getSerializableExtra("recipe");

        if(getArguments() == null){
            mPosition = (Integer) intent.getIntExtra("position",0);
        }

        mSteps = mRecipe.getSteps();

        // Check to see if the fragment was started by a previous fragment
        if(getArguments()!=null){
            mPosition = getArguments().getInt("position");
        }

        mStep = mSteps.get(mPosition);

        // Inflate the fragment_detail layout
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // Bind the data
        ButterKnife.bind(this, rootView);

        // Set on click listener on buttons
        mPreviousButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);

        // Display buttons
        displayButtons(mPosition);

        // Initialize the player
        initializePlayer(mStep.getVideoURL());

        // Bind the description data to the respective textviews
        description.setText(mStep.getDescription());
        shortDescription.setText(mStep.getShortDescription());

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_previous:
                mPosition--;
                break;
            case R.id.btn_next:
                mPosition++;
                break;
        }

        DetailFragment detailFragment = new DetailFragment();

        Bundle args = new Bundle();
        args.putInt("position", mPosition);

        detailFragment.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.detail_fragment, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

    private void returnRecipe(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("recipe", mRecipe);
        getActivity().setResult(Activity.RESULT_OK, returnIntent);
        getActivity().finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                returnRecipe();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set the title bar
        ((DetailActivity) getActivity()).setActionBarTitle(mRecipe.getName());
    }

    private void displayButtons(int position){
        if(position == 0){
            mPreviousButton.setVisibility(View.INVISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
        } else if(position == mSteps.size() - 1){
            mPreviousButton.setVisibility(View.VISIBLE);
            mNextButton.setVisibility(View.INVISIBLE);
        } else {
            mPreviousButton.setVisibility(View.VISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
        }
    }
}
