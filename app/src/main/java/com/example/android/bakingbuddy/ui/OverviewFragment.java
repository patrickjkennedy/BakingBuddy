package com.example.android.bakingbuddy.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingbuddy.R;
import com.example.android.bakingbuddy.data.OverviewAdapter;
import com.example.android.bakingbuddy.model.Recipe;

/**
 * Created by pkennedy on 3/22/18.
 */

public class OverviewFragment extends Fragment {

    // Context
    private Context mContext;

    // RecyclerView
    private RecyclerView mRecyclerView;
    private OverviewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    // Mandatory empty constructor
    public OverviewFragment(){
    }

    // Inflates the fragment layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get the recipe from the intent that started the activity
        Intent intent = getActivity().getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("recipe");

        // Context
        mContext = getActivity();

        // Inflate the Overview fragment layout
        View rootView = inflater.inflate(R.layout.fragment_overview, container, false);

        // Recyclerview
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_overview);

        // Create a linear layout manager and set it
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initialize the Recyclerview adapter, OverviewAdapter
        mAdapter = new OverviewAdapter(mContext);

        // Set the adapter
        mRecyclerView.setAdapter(mAdapter);

        // Pass in the recipe to extract the steps
        mAdapter.setSteps(recipe);

        return rootView;
    }
}
