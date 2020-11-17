package com.example.Bytez_frontend.Features;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Review;
import com.example.Bytez_frontend.ReviewPackage.ShowFullReviewActivity;
import com.example.Bytez_frontend.Settings.SettingsReviewRecyclerAdapter;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HomeReviewRecyclerAdapter extends RecyclerView.Adapter<com.example.Bytez_frontend.Features.HomeReviewRecyclerAdapter.ViewHolder> implements Filterable
{
    private static final String TAG = "HomeReviewRecAdapter";

    // Map context, viewable restaurant list, list of all restaurants
    private Context context;
    private List<Review> reviewList;
    private List<Review> allReviewsList;


    /**
     * Map activity recycler adapter with a list of restaurants and the map context
     * @param reviewList
     * @param context
     */
    public HomeReviewRecyclerAdapter(List<Review> reviewList, Context context) {
        this.reviewList = reviewList;
        this.allReviewsList = new ArrayList<Review>(reviewList);
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
    public HomeReviewRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.i(TAG, "onCreateViewHolder: ");

        // Each part of the recyclerView is one mapEntry
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View reviewEntry = layoutInflater.inflate(R.layout.row_reviewbuttons_item, parent, false);

        // ViewHolder that contains the views within each part of the recyclerView
        HomeReviewRecyclerAdapter.ViewHolder reviewViewHolder = new HomeReviewRecyclerAdapter.ViewHolder(reviewEntry);
        return reviewViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
//        holder.userInfo.setText(reviewList.get(position).getReviewer().getUsername() + " reviewed " + reviewList.get(position).getRest().getName());
        holder.comments.setText(reviewList.get(position).getComments());
        holder.rating.setIsIndicator(true);
        holder.rating.setRating(reviewList.get(position).getOverallR());
        holder.reviewId.setText(String.valueOf(reviewList.get(position).getId()));
//        holder.helpfulValue.setText();
//        holder.agreeValue.setText();
//        holder.disagreeValue.setText();

    }

//    @Override
//    public void onBindViewHolder(@NonNull SettingsReviewRecyclerAdapter.ViewHolder holder, int position)
//    {
//        holder.userInfo.setText(reviewList.get(position).getReviewer().getUsername() + " reviewed " + reviewList.get(position).getRest().getName());
//        holder.comments.setText(reviewList.get(position).getComments());
//        holder.rating.setIsIndicator(true);
//        holder.rating.setRating(reviewList.get(position).getOverallR());
//    }



    /**
     * Returns the total number of restaurants in recycler view
     * @return restaurantList.size
     */
    @Override
    public int getItemCount() {
        return reviewList.size();
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
            List<Review> filteredList = new ArrayList<>();

            // If there is nothing typed in the search bar, return all restaurants
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(allReviewsList);
            } else {
                // For all elements in restaurant list, check if charSequence is in the name, add to list if true; not case sensitive
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
        TextView userInfo, comments, helpfulValue, agreeValue, disagreeValue, reviewId;
        ImageButton helpful, agree, disagree;
        RatingBar rating;
        int helpfulIntValue, agreeIntValue, disagreeIntValue;


        /**
         * Class for each viewholder, sets button functionality for each viewholder and has proper restaurant values set
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            profilePic = (ImageView) itemView.findViewById(R.id.profileImage);
            userInfo = (TextView) itemView.findViewById(R.id.userInfo);
            comments = (TextView) itemView.findViewById(R.id.comments);
            rating = (RatingBar) itemView.findViewById(R.id.ratingBar);
            helpful = (ImageButton) itemView.findViewById(R.id.helpfulButton);
            agree = (ImageButton) itemView.findViewById(R.id.agreeButton);
            disagree = (ImageButton) itemView.findViewById(R.id.disagreeButton);
            helpfulValue = (TextView) itemView.findViewById(R.id.helpAmount);
            agreeValue = (TextView) itemView.findViewById(R.id.agreeAmount);
            disagreeValue = (TextView) itemView.findViewById(R.id.disagreeAmount);
            reviewId = (TextView) itemView.findViewById(R.id.reviewId);

            String helpfulS = helpfulValue.getText().toString();
            String agreeS = agreeValue.getText().toString();
            String disagreeS = disagreeValue.getText().toString();

            helpfulIntValue = Integer.parseInt(helpfulS);
            agreeIntValue = Integer.parseInt(agreeS);
            disagreeIntValue = Integer.parseInt(disagreeS);

            // Allow for each viewholder to have button functionality
            itemView.setOnClickListener(this);

            helpful.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(helpful.isActivated())
                    {
                        helpful.setActivated(false);
                    }
                    else
                    {
                        helpful.setActivated(true);
                    }
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
            TextView rId = (TextView) view.findViewById(R.id.reviewId);
            String name = rId.getText().toString();
            int id = Integer.parseInt(name);

            Intent mIntent = new Intent(context, ShowFullReviewActivity.class);
            mIntent.putExtra("id", id);
            context.startActivity(mIntent);
        }

    }




}
