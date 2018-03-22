package com.example.android.bakingbuddy.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.bakingbuddy.R;
import com.example.android.bakingbuddy.model.Recipe;
import com.example.android.bakingbuddy.model.Step;
import java.util.ArrayList;

/**
 * Created by pkennedy on 3/22/18.
 */

public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.ViewHolder>{

    private Context mContext;

    //ArrayList of Steps
    private ArrayList<Step> mSteps = new ArrayList<>();

    public OverviewAdapter(Context context){
        this.mContext = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView shortDescription;

        public ViewHolder(View itemView){
            super(itemView);
            shortDescription = (TextView) itemView.findViewById(R.id.tv_overview_item_shortDescription);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.overview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.shortDescription.setText(mSteps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (null == mSteps) return 0;
        return mSteps.size();
    }

    public void setSteps(Recipe recipe){
        mSteps = recipe.getSteps();
        notifyDataSetChanged();
    }
}
