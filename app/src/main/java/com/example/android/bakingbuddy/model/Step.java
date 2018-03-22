package com.example.android.bakingbuddy.model;

import java.io.Serializable;

/**
 * Created by pkennedy on 3/18/18.
 */

public class Step implements Serializable{

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
