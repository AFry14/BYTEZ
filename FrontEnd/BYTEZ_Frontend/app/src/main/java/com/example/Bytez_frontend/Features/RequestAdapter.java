package com.example.Bytez_frontend.Features;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Restaurant;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "RequestRecyclerAdapter";

    // FriendRequest context, viewable restaurant list, list of all restaurants
    private Context context;
    private List<User> userList;
    private List<User> allUsersList;

    public RequestAdapter(List<User> restaurantList, Context context) {
        this.userList = restaurantList;
        this.allUsersList = new ArrayList<User>(restaurantList);
        this.context = context;
    }

    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");

        // Each part of the recyclerView is one userEntry
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View userEntry = layoutInflater.inflate(R.layout.row_map_item, parent, false);

        // ViewHolder that contains the views within each part of the recyclerView
        ViewHolder requestViewHolder = new ViewHolder(userEntry);
        return requestViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapter.ViewHolder holder, int position) {
        holder.businessCityState.setText(userList.get(position).getEmail());
        holder.businessName.setText(userList.get(position).getUsername());
        holder.restaurantPosition.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

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
            List<User> filteredList = new ArrayList<>();

            // If there is nothing typed in the search bar, return all restaurants
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(allUsersList);
            } else {
                // For all elements in restaurant list, check if charSequence is in the name, add to list if true; not case sensitive
                for (int i = 0; i < allUsersList.size(); i++) {
                    if (allUsersList.get(i).getUsername().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(allUsersList.get(i));
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
            userList.clear();
            userList.addAll((Collection<? extends User>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    /**
     * Class that keeps track of all views within each section of the recyclerView
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Views within recycler adapter
        ImageView businessLogo;
        TextView businessName, businessCityState, restaurantPosition;
        Button addButton, unfriendButton;

        /**
         * Class for each viewholder, sets button functionality for each viewholder and has proper user values set
         *
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            businessLogo = itemView.findViewById(R.id.businessLogo);
            businessName = itemView.findViewById(R.id.businessName);
            businessCityState = itemView.findViewById(R.id.businessCityState);
            restaurantPosition = itemView.findViewById(R.id.position);
            addButton = itemView.findViewById(R.id.addFriend);
            unfriendButton = itemView.findViewById(R.id.undfriendButton);

            // Allow for each viewholder to have button functionality
            itemView.setOnClickListener(this);

            // Click functionality of addButton
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Adds a friend
                    int position = getAdapterPosition();
                    int friendID = userList.get(position).getId();
                    int loggedInID = SharedPrefManager.getInstance(context).getUser().getId();

                    RequestQueue reqQueue = Volley.newRequestQueue(context);
                    // Post Request
                    String postReqURL = URLs.URL_SEND_FRIEND_REQUEST + loggedInID + "/" + friendID;

                    final JSONObject friendRequest = new JSONObject();
                    try {
                        friendRequest.put("id", SharedPrefManager.getInstance(context).getUser().getId());
                        friendRequest.put("firstName", SharedPrefManager.getInstance(context).getUser().getfName());
                        friendRequest.put("lastName", SharedPrefManager.getInstance(context).getUser().getlName());
                        friendRequest.put("userName", SharedPrefManager.getInstance(context).getUser().getUsername());
                        friendRequest.put("password", SharedPrefManager.getInstance(context).getUser().getPassword());
                        friendRequest.put("email", SharedPrefManager.getInstance(context).getUser().getEmail());
                        friendRequest.put("userType", SharedPrefManager.getInstance(context).getUser().getUserType());
                        friendRequest.put("favoriteFood", SharedPrefManager.getInstance(context).getUser().getFavFood());
                        friendRequest.put("favoriteRestaurant", SharedPrefManager.getInstance(context).getUser().getFavRestaurant());
                        friendRequest.put("favoriteDrink", SharedPrefManager.getInstance(context).getUser().getFavDrink());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //Request for friends of current user
                    JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, postReqURL, friendRequest, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Log.d("Response", response);
                            String l ="";

                            Intent friendRequestActivity = new Intent(context, FriendRequestActivity.class);
                            context.startActivity(friendRequestActivity);
                            //context.finish();

                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Log.d("Error", error);
                            //Toast.makeText(context, "String Response : Uh oh, something went wrong").show();
                            String l = "";
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    reqQueue.add(postRequest);
                }
            });

            // Click functionality of unfriendButton
            unfriendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //removeFriend delete request
                    int position = getAdapterPosition();
                    int friendID = userList.get(position).getId();
                    int loggedInID = SharedPrefManager.getInstance(context).getUser().getId();

                    RequestQueue reqQueue = Volley.newRequestQueue(context);
                    // Post Request
                    String deleteReqURL = URLs.URL_REMOVE_FRIEND + loggedInID + "/" + friendID;

                    JsonObjectRequest removeFriend = new JsonObjectRequest(Request.Method.DELETE, deleteReqURL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Log.d("Response", response);
                            String l ="";
                            Intent friendRequestActivity = new Intent(context, FriendRequestActivity.class);
                            context.startActivity(friendRequestActivity);

                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Log.d("Error", error);
                            //Toast.makeText(context, "String Response : Uh oh, something went wrong").show();
                            String l = "";
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    reqQueue.add(removeFriend);

                }
            });

        }

        /**
         * Function of clicking viewholder
         * Opens google maps with address from whatever restaurant was clicked
         *
         * @param view
         */
        @Override
        public void onClick(View view) {
            //Open profile activity with selected user

        }

    }

}