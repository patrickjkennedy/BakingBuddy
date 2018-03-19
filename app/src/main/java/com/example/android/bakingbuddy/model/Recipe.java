package com.example.android.bakingbuddy.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pkennedy on 3/18/18.
 */

public class Recipe implements Serializable {

    private String id, name, servings, image;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public ArrayList<Ingredient> getIngredients(){
        return ingredients;
    }

    public ArrayList<Step> getSteps(){
        return steps;
    }

    public String getServings(){
        return "Serves: " + servings;
    }

    public String getImage(){
        return image;
    }

}
