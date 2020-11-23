package com.example.Bytez_frontend.Features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Restaurant;
import com.example.Bytez_frontend.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Map Activity for Bytez software
 * Shows a list of current restaurants that the user can click on to open google maps
 */
public class MapActivity extends AppCompatActivity {
    private static final String TAG = "MapActivity";

    // Volley request queue
    private RequestQueue reqQueue;

    // Recycler map view and adapter
    private RecyclerView mapRecyclerView;
    private MapRecyclerAdapter mapRecyclerAdapter;

    // List of restaurant accounts
    private List<Restaurant> restaurantList = new ArrayList<Restaurant>();

    // Current map context
    private Context mapContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setTitle("");
        Log.i(TAG, "onCreate: ");

        // Find recycler view, copy current map context, and set Volley request queue
        mapRecyclerView = findViewById(R.id.mapRecyclerView);
        mapContext = this;
        reqQueue = Volley.newRequestQueue(this);

// Jsonobject GET request, getting a list of restaurant users from database for display in recycler view
        //String url = "http://coms-309-vb-02.cs.iastate.edu:8080/restaurant/";
        String url = URLs.URL_REST_LIST;

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            // Called if successful
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for(int i = 0; i < response.length(); i++) {
                        String restName = response.getJSONObject(i).getString("restaurantName");
                        String restAddress = response.getJSONObject(i).getString("address");
                        restaurantList.add(new Restaurant(restName, restAddress));
                    }

                    // Set restaurant list in recycler view with each item as a restaurant in the restaurant list
                    mapRecyclerAdapter = new MapRecyclerAdapter(restaurantList, mapContext);
                    mapRecyclerView.setLayoutManager(new LinearLayoutManager(mapContext));

                    // Attach mapRecyclerAdapter to mapRecyclerView
                    mapRecyclerView.setAdapter(mapRecyclerAdapter);


                    DividerItemDecoration restaurantDivider = new DividerItemDecoration(mapContext, DividerItemDecoration.VERTICAL);
                    mapRecyclerView.addItemDecoration(restaurantDivider);


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

        // Add Json request to queue
        reqQueue.add(getRequest);
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
                mapRecyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}