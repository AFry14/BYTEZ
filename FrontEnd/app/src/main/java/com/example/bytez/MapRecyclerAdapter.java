package com.example.bytez;

import android.icu.text.Transliterator;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is the recycler adapter for the recycle view in MapActivity
 */
public class MapRecyclerAdapter extends RecyclerView.Adapter<MapRecyclerAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "MapRecyclerAdapter";


    private List<RestaurantProfile> restaurantList;
    private List<RestaurantProfile> allRestaurantList;


    public MapRecyclerAdapter(List<RestaurantProfile> restaurantList) {
        this.restaurantList = restaurantList;
        this.allRestaurantList = new ArrayList<>(restaurantList);
    }

    /**
     * ViewHolder for recycler view in MapActivity
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.i(TAG, "onCreateViewHolder: ");

        // Each part of the recyclerView is one mapEntry
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View mapEntry = layoutInflater.inflate(R.layout.row_map_item, parent, false);

        // ViewHolder that contains the views within each part of the recyclerView
        ViewHolder mapViewHolder = new ViewHolder(mapEntry);
        return mapViewHolder;
    }


    /**
     * Takes in data and binds it into the viewholder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.businessCityState.setText(restaurantList.get(position).getAddress());
        holder.businessName.setText(restaurantList.get(position).getName());
        // CHANGE THIS to set image to a restaurant logo
        //holder.businessLogo.setImage
    }

    /**
     * Returns the total number of restaurants in recycler view
     * @return restaurantList.size
     */
    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    /**
     * Get searchFilter
     * @return searchFilter
     */
    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    /**
     * Filter system by name
     */
    Filter searchFilter = new Filter() {

        // Filters through the restaurant list by name
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<RestaurantProfile> filteredList = new ArrayList<>();

            // If there is nothing typed in the search bar, return all restaurants
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(allRestaurantList);
            } else {
                // For all elements in restaurant list, check if charSequence is in the name, add to list if true; not case sensitive
                for (int i = 0; i < allRestaurantList.size(); i++) {
                    if (allRestaurantList.get(i).getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(allRestaurantList.get(i));
                    }
                }
            }

            // Return filtered list to publishResults
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        // Updates UI with filtered results
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            restaurantList.clear();
            restaurantList.addAll((Collection<? extends RestaurantProfile>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    /**
     * Class that keeps track of all views within each section of the recyclerView
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView businessLogo;
        TextView businessName, businessCityState;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            businessLogo = itemView.findViewById(R.id.businessLogo);
            businessName = itemView.findViewById(R.id.businessName);
            businessCityState = itemView.findViewById(R.id.businessCityState);

            // Allow for each viewholder to have button functionality
            itemView.setOnClickListener(this);

        }

        // Function of clicking each viewholder
        @Override
        public void onClick(View view) {
            // CHANGE THIS for google maps functionality
            Toast.makeText(view.getContext(), restaurantList.get(getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
        }

    }

}
