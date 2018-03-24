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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.bakingbuddy.R;
import com.example.android.bakingbuddy.data.RecipesAdapter;
import com.example.android.bakingbuddy.model.Recipe;
import com.example.android.bakingbuddy.service.RecipeClient;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pkennedy on 3/18/18.
 */

public class RecipesFragment extends Fragment{

    // Context
    private Context mContext;

    // RecyclerView
    private RecyclerView mRecyclerView;
    private RecipesAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    // TextView for Error Messaging
    private TextView mErrorTextView;

    // ProgressBar for Loading
    private ProgressBar mLoadingIndicator;

    // Mandatory empty constructor
    public RecipesFragment(){
    }

    // Inflates the RecyclerView containing the CardViews for each recipe
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Context
        mContext = getActivity();

        final View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);

        // Recyclerview
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_master_list);

        // TextView for Error Messaging
        mErrorTextView = (TextView) rootView.findViewById(R.id.tv_error_message_display);

        // ProgressBar for Loading Indicator
        mLoadingIndicator = (ProgressBar) rootView.findViewById(R.id.pb_loading_indicator);

        mRecyclerView.setHasFixedSize(true);

        // Creating a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Setup click listener
        RecipesAdapter.MasterListAdapterClickListener listener = new RecipesAdapter.MasterListAdapterClickListener() {
            @Override
            public void onClick(View view, Recipe recipe) {
                Class destinationClass = OverviewActivity.class;
                Intent intent = new Intent(mContext, destinationClass);

                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe", recipe);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        };

        // Initialize the RecyclerView adapter, RecipesAdapter
        mAdapter = new RecipesAdapter(listener, mContext);

        // Set the adapter
        mRecyclerView.setAdapter(mAdapter);


        // Construct the Retrofit builder
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create());

        // Create the retrofit objects
        Retrofit retrofit = builder.build();

        // Make the request and return the call object
        RecipeClient client = retrofit.create(RecipeClient.class);
        Call<ArrayList<Recipe>> call = client.getRecipes();

        // As we're in UI thread, we need to make the network call asynchronously, we do this using enqueue
        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                ArrayList<Recipe> recipes = response.body();

                // Pass the recipes from the response into the adapter
                mAdapter.setRecipes(recipes);

            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error in Network call", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

}