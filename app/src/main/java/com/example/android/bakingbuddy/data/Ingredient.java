package com.example.android.bakingbuddy.data;

/**
 * Created by pkennedy on 3/18/18.
 */

public class Ingredient {

    private String mQuantity, mMeasure, mIngredient;

    public Ingredient(String mQuantity, String mMeasure, String mIngredient){
        this.mQuantity = mQuantity;
        this.mMeasure = mMeasure;
        this.mIngredient = mIngredient;
    }

    public String getmQuantity(){
        return mQuantity;
    }

    public String getmMeasure(){
        return mMeasure;
    }

    public String getmIngredient(){
        return mIngredient;
    }

}
