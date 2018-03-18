package com.example.android.bakingbuddy.data;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.bakingbuddy.R;


/**
 * Created by pkennedy on 3/18/18.
 */

public class MasterListAdapter extends RecyclerView.Adapter<MasterListAdapter.ViewHolder> {

    // I'll put some placeholder data here to use for testing
    private int[] images = { R.drawable.nutella_pie, R.drawable.brownies, R.drawable.yellow_cake, R.drawable.cheesecake };
    private String[] names = { "Nutella Pie", "Brownies", "Yellow Cake", "Cheesecake"};
    private String[] servings = { "Serves 8", "Serves 8", "Serves 8", "Serves 8" };

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemName;
        public TextView itemServes;

        public ViewHolder(View itemView){
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.iv_master_list_item);
            itemName = (TextView) itemView.findViewById(R.id.tv_master_list_item_recipe_name);
            itemServes = (TextView) itemView.findViewById(R.id.tv_master_list_item_recipe_serves);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout_master_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemImage.setImageResource(images[position]);
        holder.itemName.setText(names[position]);
        holder.itemServes.setText(servings[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }
}
