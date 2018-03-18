package com.example.android.bakingbuddy.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pkennedy on 3/18/18.
 */

public class Recipe implements Serializable {

    private String mId, mName, mServings, mImage;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<Step> mSteps;

    public Recipe(String mId, String mName, ArrayList<Ingredient> mIngredients,
                  ArrayList<Step> mSteps, String mServings, String mImage){
        this.mId = mId;
        this.mName = mName;
        this.mIngredients = mIngredients;
        this.mSteps = mSteps;
        this.mServings = mServings;
        this.mImage = mImage;
    }

    public String getmId(){
        return mId;
    }

    public String getmName(){
        return mName;
    }

    public ArrayList<Ingredient> getmIngredients(){
        return mIngredients;
    }

    public ArrayList<Step> getmSteps(){
        return mSteps;
    }

    public String getmServings(){
        return "Serves: " + mServings;
    }

    public String getmImage(){
        return mImage;
    }

}
