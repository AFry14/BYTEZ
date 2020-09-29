package com.example.bytez;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.media.Image;
import android.net.Uri;
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

    // Map context, viewable restaurant list, list of all restaurants
    private Context context;
    private List<Restaurant> restaurantList;
    private List<Restaurant> allRestaurantList;

    /**
     * Map activity recycler adapter with a list of restaurants and the map context
     * @param restaurantList
     * @param context
     */
    public MapRecyclerAdapter(List<Restaurant> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.allRestaurantList = new ArrayList<Restaurant>(restaurantList);
        this.context = context;
    }

    /**
     * ViewHolder for recycler view in MapActivity, Describes how each item in the recycler view should look and function
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
     * Takes in data and binds it into the viewholder, sets the proper restaurant values in each view holder
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
     * Filter recycler view by a string
     */
    Filter searchFilter = new Filter() {

        // Filters through the restaurant list by an entered string
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            // Filtered list of restaurants
            List<Restaurant> filteredList = new ArrayList<>();

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
            restaurantList.addAll((Collection<? extends Restaurant>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    /**
     * Class that keeps track of all views within each section of the recyclerView
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Views within recycler adapter
        ImageView businessLogo;
        TextView businessName, businessCityState;

        /**
         * Class for each viewholder, sets button functionality for each viewholder and has proper restaurant values set
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            businessLogo = itemView.findViewById(R.id.businessLogo);
            businessName = itemView.findViewById(R.id.businessName);
            businessCityState = itemView.findViewById(R.id.businessCityState);

            // Allow for each viewholder to have button functionality
            itemView.setOnClickListener(this);

        }

        /**
         * Function of clicking viewholder
         * Opens google maps with address from whatever restaurant was clicked
         * @param view
         */
        @Override
        public void onClick(View view) {
            TextView businessAddress = (TextView) view.findViewById(R.id.businessCityState);
            String address = businessAddress.getText().toString();

            // Open Google Maps with address obtained from clicked restaurant
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

//            if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
//            }

        }

    }

}
