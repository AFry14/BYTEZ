package com.example.Bytez_frontend.ReviewPackage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Bytez_frontend.Features.HomeActivity;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Restaurant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ViewHolder> implements Filterable
{

    private static final String TAG = "ReviewRecyclerAdapter";

    // Review context, viewable restaurant list, list of all restaurants
    private Context ctx;
    private List<Restaurant> restaurantList;
    private List<Restaurant> allRestaurantList;

    /**
     * Review activity recycler adapter with a list of restaurants
     * @param restaurantList
     * @param context
     */
    public ReviewRecyclerAdapter(List<Restaurant> restaurantList, Context context)
    {
        this.restaurantList = restaurantList;
        this.allRestaurantList = new ArrayList<Restaurant>(restaurantList);
        this.ctx = context;
    }

    /**
     * ViewHolder for recycler view in ReviewActivity, Describes how each item in the recycler view should look and function
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        Log.i(TAG, "onCreateViewHolder: ");

        // Each part of the recyclerView is one mapEntry
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View mapEntry = layoutInflater.inflate(R.layout.row_map_item, parent, false);

        // ViewHolder that contains the views within each part of the recyclerView
        ViewHolder mapViewHolder = new ViewHolder(mapEntry);
        return mapViewHolder;
    }

    //set the information in the view position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.businessCityState.setText(restaurantList.get(position).getAddress());
        holder.businessName.setText(restaurantList.get(position).getName());
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
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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


//            TextView businessSearch = (TextView) findViewById(R.id.businessBar);
////            businessSearch.setText(name);

        }

    }

}