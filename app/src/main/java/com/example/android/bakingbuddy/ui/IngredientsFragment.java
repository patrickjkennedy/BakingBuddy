package com.example.android.bakingbuddy.ui;

import android.app.Activity;
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
import com.example.android.bakingbuddy.data.IngredientsAdapter;
import com.example.android.bakingbuddy.model.Recipe;

/**
 * Created by pkennedy on 3/25/18.
 */

public class IngredientsFragment extends Fragment{

    // Context
    private Context mContext;

    // RecyclerView
    private RecyclerView mRecyclerView;
    private IngredientsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    // Recipe
    private Recipe mRecipe;

    // Mandatory empty constructor
    public IngredientsFragment(){
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
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        // Recyclerview
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_ingredients);

        // Create a linear layout manager and set it
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initialize the Recyclerview adapter, IngredientsAdapter
        mAdapter = new IngredientsAdapter(mContext);

        // Set the adapter
        mRecyclerView.setAdapter(mAdapter);

        // Pass in the recipe to extract the ingredients
        mAdapter.setIngredients(mRecipe);

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
}
