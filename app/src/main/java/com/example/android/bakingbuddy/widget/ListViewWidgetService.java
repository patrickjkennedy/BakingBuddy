package com.example.android.bakingbuddy.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingbuddy.R;
import com.example.android.bakingbuddy.model.Ingredient;
import java.util.ArrayList;
import static com.example.android.bakingbuddy.widget.BakingWidgetProvider.ingredientsList;

public class ListViewWidgetService extends RemoteViewsService{
    ArrayList<Ingredient> remoteViewIngredients;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListViewRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class ListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

        Context mContext = null;

        public ListViewRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            remoteViewIngredients = ingredientsList;

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return remoteViewIngredients.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_view_item);

            views.setTextViewText(R.layout.widget_list_view_item, remoteViewIngredients.get(i).toString());

            Intent fillInIntent = new Intent();
            views.setOnClickFillInIntent(R.id.widget_list_view_item, fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
