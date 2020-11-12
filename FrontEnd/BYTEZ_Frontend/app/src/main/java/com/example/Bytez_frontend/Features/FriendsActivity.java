package com.example.Bytez_frontend.Features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity {
    // Volley request queue
    private RequestQueue requestQueue;

    private RecyclerView usersRecyclerView;
    private FriendsRecyclerAdapter friendsRecyclerAdapter;

    // List of restaurant accounts
    private List<User> friendsList = new ArrayList<User>();

    // Current friends context
    private Context friendsContext;

    // Current user logged in
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Log.i("Friends Activity", "onCreate: ");

        Intent i = getIntent();
        user = i.getParcelableExtra("user");


        // If there is no user data, or the user is the logged in user, use the current logged in user
        if (user == null || user.getId() == (SharedPrefManager.getInstance(this).getUser().getId())) {
            user = SharedPrefManager.getInstance(this).getUser();
        } else {
            // If the user is not the current user, disable the friend request button
            Button friendRequestButton = findViewById(R.id.friendRequestButton);
            friendRequestButton.setEnabled(false);
        }
        setTitle(user.getUsername() + "'s Friends");

        // Find recycler view, copy current friends context, and set Volley request queue
        usersRecyclerView = findViewById(R.id.friendsRecyclerView);
        friendsContext = this;
        requestQueue = Volley.newRequestQueue(this);

        final String url = URLs.URL_GET_USER_FRIENDS + user.getId();
        //Request for friends of current user
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            // Called if successful
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for(int i = 0; i < response.length(); i++) {
                        int id = response.getJSONObject(i).getInt("id");
                        String username = response.getJSONObject(i).getString("userName");
                        String email = response.getJSONObject(i).getString("email");
                        String favFood = response.getJSONObject(i).getString("favoriteFood");
                        String favDrink = response.getJSONObject(i).getString("favoriteDrink");
                        String favRest = response.getJSONObject(i).getString("favoriteRestaurant");
                        String firstName = response.getJSONObject(i).getString("firstName");
                        String lastName = response.getJSONObject(i).getString("lastName");

                        friendsList.add(new User(id, username, email, favFood, favDrink, favRest, firstName, lastName));
                    }

                    // Set restaurant list in recycler view with each item as a restaurant in the restaurant list
                    friendsRecyclerAdapter = new FriendsRecyclerAdapter(friendsList, friendsContext, user);
                    usersRecyclerView.setLayoutManager(new LinearLayoutManager(friendsContext));

                    // Attach friendsRecyclerAdapter to friendsRecyclerView
                    usersRecyclerView.setAdapter(friendsRecyclerAdapter);
                    DividerItemDecoration usersDivider = new DividerItemDecoration(friendsContext, DividerItemDecoration.VERTICAL);
                    usersRecyclerView.addItemDecoration(usersDivider);




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // Called if there is an error
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(getRequest);

    }

    /**
     * Add a friend
     * @param view
     */
//    public void addFriend(View view) {
//        //If user = current logged in user, add button does this
//        if (user.getId() == SharedPrefManager.getInstance(this).getUser().getId()) {
//            // Accept Friend Request
//            int loggedInID = SharedPrefManager.getInstance(this).getUser().getId();
//            Button addButton = findViewById(R.id.addFriend);
//            int friendID = Integer.parseInt(addButton.getText().toString());
//            String postReqURL = URLs.URL_SEND_FRIEND_REQUEST + loggedInID + "/" + friendID;
//            //Request for friends of current user
//            JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.POST, postReqURL, null, new Response.Listener<JSONArray>() {
//                // Called if successful
//                @Override
//                public void onResponse(JSONArray response) {
//                    try {
//
//                        for(int i = 0; i < response.length(); i++) {
//                            int id = response.getJSONObject(i).getInt("id");
//                            String username = response.getJSONObject(i).getString("userName");
//                            String email = response.getJSONObject(i).getString("email");
//                            String favFood = response.getJSONObject(i).getString("favoriteFood");
//                            String favDrink = response.getJSONObject(i).getString("favoriteDrink");
//                            String favRest = response.getJSONObject(i).getString("favoriteRestaurant");
//                            String firstName = response.getJSONObject(i).getString("firstName");
//                            String lastName = response.getJSONObject(i).getString("lastName");
//
//                            friendsList.add(new User(id, username, email, favFood, favDrink, favRest, firstName, lastName));
//                        }
//
//                        // Set restaurant list in recycler view with each item as a restaurant in the restaurant list
//                        friendsRecyclerAdapter = new FriendsRecyclerAdapter(friendsList, friendsContext, user);
//                        usersRecyclerView.setLayoutManager(new LinearLayoutManager(friendsContext));
//
//                        // Attach friendsRecyclerAdapter to friendsRecyclerView
//                        usersRecyclerView.setAdapter(friendsRecyclerAdapter);
//                        DividerItemDecoration usersDivider = new DividerItemDecoration(friendsContext, DividerItemDecoration.VERTICAL);
//                        usersRecyclerView.addItemDecoration(usersDivider);
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                // Called if there is an error
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                }
//            });
//
//            requestQueue.add(getRequest);
//
//
//            // Start friends activity
////            Intent friendsActivity = getIntent();
////            finish();
////            startActivity(friendsActivity);
//        } else {
//            // Send friend request
//            // Post request
//
//
//
//
//
//            // Restart friends activity
////            Intent friendsActivity = getIntent();
////            finish();
////            startActivity(friendsActivity);
//        }
//    }

    public void launchFriendRequests(View view) {
        Intent friendRequestActivity = new Intent(this, FriendRequestActivity.class);
        friendRequestActivity.putExtra("user", user);
        startActivity(friendRequestActivity);
    }


    /**
     * When search menu item is clicked
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                friendsRecyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}