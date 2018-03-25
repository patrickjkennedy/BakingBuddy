package com.example.android.bakingbuddy.model;

import java.io.Serializable;

/**
 * Created by pkennedy on 3/18/18.
 */

public class Ingredient implements Serializable {

    private String quantity, measure, ingredient;

    public String getQuantity(){
        return quantity;
    }

    public String getMeasure(){
        return measure;
    }

    public String getIngredient() { return ingredient; }

    @Override
    public String toString() {
        return quantity + " " + measure + " " + ingredient;
    }

}
