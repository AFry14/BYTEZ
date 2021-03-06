package com.example.Bytez_frontend.Settings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.Bytez_frontend.Features.MapRecyclerAdapter;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Restaurant;
import com.example.Bytez_frontend.Review;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.SingletonVolley;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SettingsReviewRecyclerAdapter extends RecyclerView.Adapter<com.example.Bytez_frontend.Settings.SettingsReviewRecyclerAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "SettingReviewRecAdapter";

    // settings context, viewable restaurant list, list of all restaurants
    private Context context;
    private List<Review> reviewList;
    private List<Review> allReviewsList;

    /**
     * settings activity recycler adapter with a list of reviews
     * @param reviewList
     * @param context
     */
    public SettingsReviewRecyclerAdapter(List<Review> reviewList, Context context) {
        this.reviewList = reviewList;
        this.allReviewsList = new ArrayList<Review>(reviewList);
        this.context = context;
    }

    /**
     * ViewHolder for recycler view in ReviewShowFragment, Describes how each item in the recycler view should look and function
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public SettingsReviewRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.i(TAG, "onCreateViewHolder: ");

        // Each part of the recyclerView is one ReviewEntry
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View reviewEntry = layoutInflater.inflate(R.layout.row_review_item, parent, false);

        // ViewHolder that contains the views within each part of the recyclerView
        SettingsReviewRecyclerAdapter.ViewHolder reviewViewHolder = new SettingsReviewRecyclerAdapter.ViewHolder(reviewEntry);
        return reviewViewHolder;
    }

    /**
     * set all the values for each recycler position
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.userInfo.setText(reviewList.get(position).getReviewerString() + " reviewed " + reviewList.get(position).getRestString());
//        holder.comments.setText(reviewList.get(position).getComments());
        holder.rating.setIsIndicator(true);
        holder.rating.setRating(reviewList.get(position).getOverallR());
//        holder.reviewId.setText(reviewList.get(position).getId());
    }



    /**
     * Returns the total number of restaurants in recycler view
     * @return restaurantList.size
     */
    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    //delete the review from the arraylist
    public void delete(int position)
    {
        reviewList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * return the id of the review in the given position
     * @param position
     * @return
     */
    public int getID(int position)
    {
        return reviewList.get(position).getId();
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

        // Filters through the review list by an entered string
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            // Filtered list of reviews
            List<Review> filteredList = new ArrayList<>();

            // If there is nothing typed in the search bar, return all review
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(allReviewsList);
            } else {
                // For all elements in review list, check if charSequence is in the name, add to list if true; not case sensitive
                for (int i = 0; i < allReviewsList.size(); i++) {
                    if (allReviewsList.get(i).getReviewer().toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(allReviewsList.get(i));
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
            reviewList.clear();
            reviewList.addAll((Collection<? extends Review>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    /**
     * Class that keeps track of all views within each section of the recyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Views within recycler adapter
        ImageView profilePic;
        TextView userInfo, comments, reviewId;
        RatingBar rating;
        Button deleteB;


        /**
         * Class for each viewholder, sets button functionality for each viewholder and has proper review values set
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profileImage);
            userInfo = itemView.findViewById(R.id.userInfo);
            comments = itemView.findViewById(R.id.comments);
            rating = itemView.findViewById(R.id.ratingBar);
            reviewId = itemView.findViewById(R.id.reviewId);
            deleteB = itemView.findViewById(R.id.delete);

            // Allow for each viewholder to have button functionality
            itemView.setOnClickListener(this);

            //delete review from database if delete button is pressed
            deleteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = getID(getLayoutPosition());
                    String pass1 = URLs.URL_DELETE_REVIEW + id;
                    JsonObjectRequest deleteReviewRequest = new JsonObjectRequest(Request.Method.DELETE, pass1, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    delete(getAdapterPosition());
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    error.printStackTrace();
                                }
                            }
                    );
                    SingletonVolley.getInstance(context).addToRequestQueue(deleteReviewRequest);
                }
            });

        }

        /**
         * Function of clicking viewholder
         * Opens google maps with address from whatever restaurant was clicked
         * @param view
         */
        @Override
        public void onClick(View view)
        {
            

        }

    }

}
