package com.example.android.bakingbuddy.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import com.example.android.bakingbuddy.model.Recipe;

public class UpdateBakingService extends IntentService{

    public static String FROM_ACTIVITY_RECIPE ="FROM_ACTIVITY_RECIPE";

    public UpdateBakingService(){
        super("UpdateBakingService");
    }

    public static void startBakingService(Context context, Recipe recipe) {
        Intent intent = new Intent(context, UpdateBakingService.class);
        intent.putExtra(FROM_ACTIVITY_RECIPE, recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null) {
           Recipe recipe = (Recipe) intent.getSerializableExtra(FROM_ACTIVITY_RECIPE);
           handleActionUpdateBakingWidget(recipe);
        }
    }

    private void handleActionUpdateBakingWidget(Recipe recipe) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra(FROM_ACTIVITY_RECIPE, recipe);
        sendBroadcast(intent);
    }
}
