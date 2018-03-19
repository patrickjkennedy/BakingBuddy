package com.example.android.bakingbuddy.service;

import com.example.android.bakingbuddy.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by pkennedy on 3/19/18.
 */

public interface RecipeClient {

    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipes();
}
