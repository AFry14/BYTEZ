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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MessageFriendsActivity extends AppCompatActivity {
    // Volley request queue
    private RequestQueue requestQueue;

    private RecyclerView messageFriendsRecycler;
    private MessageFriendsAdapter messageFriendsAdapter;

    // List of restaurant accounts
    private List<User> friendsList = new ArrayList<User>();

    // Current friends context
    private Context messageListContext;

    // Current user logged in
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_friends);

        Intent i = getIntent();
        currentUser = i.getParcelableExtra("user");
        setTitle("Message");

        // Find recycler view, copy current context, and set Volley request queue
        messageFriendsRecycler = findViewById(R.id.messageFriendsRecycler);
        messageListContext = this;
        requestQueue = Volley.newRequestQueue(this);

        final String url = URLs.URL_GET_USER_FRIENDS + currentUser.getId();

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
                    messageFriendsAdapter = new MessageFriendsAdapter(friendsList, messageListContext, currentUser);
                    messageFriendsRecycler.setLayoutManager(new LinearLayoutManager(messageListContext));

                    // Attach friendsRecyclerAdapter to friendsRecyclerView
                    messageFriendsRecycler.setAdapter(messageFriendsAdapter);
                    DividerItemDecoration usersDivider = new DividerItemDecoration(messageListContext, DividerItemDecoration.VERTICAL);
                    messageFriendsRecycler.addItemDecoration(usersDivider);




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
                messageFriendsAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}