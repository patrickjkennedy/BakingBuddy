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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pkennedy on 3/22/18.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder>{

    private Context mContext;

    private StepsAdapterClickListener mListener;

    //ArrayList of Steps
    private ArrayList<Step> mSteps = new ArrayList<>();

    // Two pane mode
    private boolean mTwoPane;

    /**
     * The interface for custom RecyclerViewClickListener
     *
     */
    public interface StepsAdapterClickListener {
        void onClick(View view, int position);
    }

    public StepsAdapter(StepsAdapterClickListener listener, Context context, Boolean twoPane){
        this.mListener = listener;
        this.mContext = context;
        this.mTwoPane = twoPane;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.tv_step_item_shortDescription) TextView shortDescription;

        public ViewHolder(View itemView, StepsAdapterClickListener listener){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_item, parent, false);
        return new ViewHolder(view, mListener);
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
        if(mTwoPane){
            mSteps = recipe.getSteps();
            notifyDataSetChanged();
        } else {
            mSteps = recipe.getSteps();
            mSteps.remove(0);
            notifyDataSetChanged();
        }
    }
}
