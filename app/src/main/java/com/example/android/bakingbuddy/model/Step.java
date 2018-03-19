package com.example.android.bakingbuddy.model;

/**
 * Created by pkennedy on 3/18/18.
 */

public class Step {

    private String id, shortDescription, description, videoURL, thumbnailURL;

    public String getId(){
        return id;
    }

    public String getShortDescription(){
        return shortDescription;
    }

    public String getDescription(){
        return description;
    }

    public String getVideoURL(){
        return videoURL;
    }

    public String getThumbnailURL(){
        return thumbnailURL;
    }

}
