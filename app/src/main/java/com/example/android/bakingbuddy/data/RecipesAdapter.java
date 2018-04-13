package com.example.android.bakingbuddy.data;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.bakingbuddy.R;
import com.example.android.bakingbuddy.model.Recipe;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pkennedy on 3/18/18.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private Context mContext;

    private MasterListAdapterClickListener mListener;

    // Hashmap for Recipe placeholder images
    private HashMap<String, Integer> placeholderMap = new HashMap<>();

    // ArrayList of Recipes
    private ArrayList<Recipe> mRecipes = new ArrayList<>();

    /**
     * The interface for custom RecyclerViewClickListener
     *
     */
    public interface MasterListAdapterClickListener {
        void onClick(View view, Recipe recipe);
    }

    public RecipesAdapter(MasterListAdapterClickListener listener, Context context){
        this.mListener = listener;
        this.mContext = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.iv_master_list_item) ImageView itemImage;
        @BindView(R.id.tv_master_list_item_recipe_name) TextView itemName;
        @BindView(R.id.tv_master_list_item_recipe_serves) TextView itemServes;

        public ViewHolder(View itemView, MasterListAdapterClickListener listener){
            super(itemView);
            ButterKnife.bind(this, itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, mRecipes.get(getAdapterPosition()));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        initPlaceHolderMap();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout_recipes_item, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // If there is no data in image json, display the placeholder image for the recipe
        int placeholder = placeholderMap.get(mRecipes.get(position).getName());

        Glide.with(mContext)
                .load(mRecipes.get(position).getImage())
                .placeholder(placeholder)
                .into(holder.itemImage);

        holder.itemName.setText(mRecipes.get(position).getName());
        holder.itemServes.setText(mRecipes.get(position).getServings());
    }

    @Override
    public int getItemCount() {
        if (null == mRecipes) return 0;
        return mRecipes.size();
    }

    public void setRecipes(ArrayList<Recipe> recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    private void initPlaceHolderMap(){
        placeholderMap.put("Nutella Pie", R.drawable.nutella_pie);
        placeholderMap.put("Brownies", R.drawable.brownies);
        placeholderMap.put("Yellow Cake", R.drawable.yellow_cake);
        placeholderMap.put("Cheesecake", R.drawable.cheesecake);
    }
}
