package com.example.Bytez_frontend.Features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.Bytez_frontend.Restaurant;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FriendRequestActivity extends AppCompatActivity {

    // Volley request queue
    private RequestQueue reqQueue;

    private RequestAdapter friendRequestAdapter;

    private RecyclerView requestsRecyclerView;

    // List of restaurant accounts
//    private List<User> friendRequestList = new ArrayList<User>();

    private List<User> friendRequestList = new ArrayList<User>();

    User user;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        Intent i = getIntent();
        user = i.getParcelableExtra("user");
        setTitle(user.getUsername() + "'s Friend Requests");
        context = this;


        // Find recycler view, copy current map context, and set Volley request queue
        requestsRecyclerView = findViewById(R.id.friendRequestRecycler);
        reqQueue = Volley.newRequestQueue(this);

        final String url = URLs.URL_GET_USER_FRIEND_REQUESTS + user.getId();
        // Request for friends of current user
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

                        friendRequestList.add(new User(id, username, email, favFood, favDrink, favRest, firstName, lastName));
                    }

                    // Set restaurant list in recycler view with each item as a restaurant in the restaurant list
                    friendRequestAdapter = new RequestAdapter(friendRequestList, context);
                    requestsRecyclerView.setLayoutManager(new LinearLayoutManager(context));

                    // Attach mapRecyclerAdapter to mapRecyclerView
                    requestsRecyclerView.setAdapter(friendRequestAdapter);


                    DividerItemDecoration restaurantDivider = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
                    requestsRecyclerView.addItemDecoration(restaurantDivider);


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

        reqQueue.add(getRequest);
    }

    public void backToFriends(View view) {
        finish();
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
                friendRequestAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}