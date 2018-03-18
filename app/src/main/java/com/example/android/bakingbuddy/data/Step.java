package com.example.android.bakingbuddy.data;

import android.widget.ProgressBar;

/**
 * Created by pkennedy on 3/18/18.
 */

public class Step {

    private String mId, mShortDescription, mDescription, mVideoUrl, mthumbnailUrl;

    public Step(String mId, String mShortDescription, String mDescription, String mVideoUrl, String mthumbnailUrl){
        this.mId = mId;
        this.mShortDescription = mShortDescription;
        this.mDescription = mDescription;
        this.mVideoUrl = mVideoUrl;
        this.mthumbnailUrl = mthumbnailUrl;
    }

    public String getmId(){
        return mId;
    }

    public String getmShortDescription(){
        return mShortDescription;
    }

    public String getmDescription(){
        return mDescription;
    }

    public String getmVideoUrl(){
        return mVideoUrl;
    }

    public String getmthumbnailUrl(){
        return mthumbnailUrl;
    }

}
