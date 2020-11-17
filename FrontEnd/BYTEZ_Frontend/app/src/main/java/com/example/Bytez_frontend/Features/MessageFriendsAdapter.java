package com.example.Bytez_frontend.Features;

import android.content.Context;
import android.content.Intent;
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
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

public class MessageFriendsAdapter extends RecyclerView.Adapter<MessageFriendsAdapter.ViewHolder> implements Filterable {

    private static final String TAG = "MessageFriendsAdapter";

    // FriendRequest context, viewable user list, list of all users
    private Context context;
    private List<User> usersList;
    private List<User> allUsersList;
    private User user;

    public MessageFriendsAdapter(List<User> usersList, Context context, User user) {
        this.usersList = usersList;
        this.allUsersList = new ArrayList<User>(usersList);
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public MessageFriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ");

        // Each part of the recyclerView is one userEntry
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View userEntry = layoutInflater.inflate(R.layout.row_map_item, parent, false);

        // ViewHolder that contains the views within each part of the recyclerView
        ViewHolder friendsViewHolder = new ViewHolder(userEntry);
        return friendsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageFriendsAdapter.ViewHolder holder, int position) {
        holder.email.setText(usersList.get(position).getEmail());
        holder.username.setText(usersList.get(position).getUsername());
        holder.friendPosition.setText(String.valueOf(position));
        holder.addButton.setVisibility(View.GONE);
        holder.unfriendButton.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    /**
     * Filter recycler view by a string
     */
    Filter searchFilter = new Filter() {

        // Filters through the user list by an entered string
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            // Filtered list of users
            List<User> filteredList = new ArrayList<>();

            // If there is nothing typed in the search bar, return all users
            if (charSequence.toString().isEmpty()) {
                filteredList.addAll(allUsersList);
            } else {
                // For all elements in user list, check if charSequence is in the name, add to list if true; not case sensitive
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
            usersList.clear();
            usersList.addAll((Collection<? extends User>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    /**
     * Class that keeps track of all views within each section of the recyclerView
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Views within recycler adapter
        ImageView userProilePic;
        TextView username, email, friendPosition;
        Button addButton, unfriendButton;

        /**
         * Class for each viewholder, sets button functionality for each viewholder and has proper user values set
         *
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userProilePic = itemView.findViewById(R.id.businessLogo);
            username = itemView.findViewById(R.id.businessName);
            email = itemView.findViewById(R.id.businessCityState);
            friendPosition = itemView.findViewById(R.id.position);
            addButton = itemView.findViewById(R.id.addFriend);
            unfriendButton = itemView.findViewById(R.id.undfriendButton);

            // Allow for each viewholder to have button functionality
            itemView.setOnClickListener(this);
        }

        /**
         * Function of clicking viewholder
         * Opens profile activity of selected user
         *
         * @param view
         */
        @Override
        public void onClick(View view) {
            //Open profile activity with selected user
            Intent chatActivity = new Intent(context, ChatActivity.class);
            TextView userPositionView = view.findViewById(R.id.position);
            int userPosition = Integer.parseInt(userPositionView.getText().toString());
            User clickedUser = usersList.get(userPosition);
            chatActivity.putExtra("user", clickedUser);
            chatActivity.putExtra("currentUser", user);
            context.startActivity(chatActivity);
        }

    }

}
