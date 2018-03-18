package com.example.android.bakingbuddy.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingbuddy.R;
import com.example.android.bakingbuddy.data.MasterListAdapter;

/**
 * Created by pkennedy on 3/18/18.
 */

public class MasterListFragment extends Fragment {

    // Context
    private Context mContext;

    // RecyclerView
    private RecyclerView mRecyclerView;
    private MasterListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    // TextView for Error Messaging
    private TextView mErrorTextView;

    // ProgressBar for Loading
    private ProgressBar mLoadingIndicator;

    // Mandatory empty constructor
    public MasterListFragment(){
    }

    // Inflates the RecyclerView containing the CardViews for each recipe
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Context
        mContext = getActivity();

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        // Recyclerview
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_master_list);

        // TextView for Error Messaging
        mErrorTextView = (TextView) rootView.findViewById(R.id.tv_error_message_display);

        // ProgressBar for Loading Indicator
        mLoadingIndicator = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator);

        mRecyclerView.setHasFixedSize(true);

        // Creating a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initialize the RecyclerView adapter, MasterListAdapter
        mAdapter = new MasterListAdapter();

        // Set the adapter
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
