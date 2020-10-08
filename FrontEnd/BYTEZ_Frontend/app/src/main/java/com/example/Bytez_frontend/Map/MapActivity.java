package com.example.Bytez_frontend.Map;

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


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        //String url = "http://coms-309-vb-02.cs.iastate.edu:8080/restaurant/1";
        String url = "http://coms-309-vb-02.cs.iastate.edu:8080/restaurant/1";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                // Called if successful
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        // Add the restaurant to the restaurant list
                        String restName = response.getString("restaurantName");
                        String restAddress = response.getString("address");
                        restaurantList.add(new Restaurant(restName, restAddress));

                        // Other local restaurant tests
                        restaurantList.add(new Restaurant("Subway", "302 Lincoln Way Suite 101, Ames, IA"));
                        restaurantList.add(new Restaurant("Hickory Park", "1404 S Duff Ave, Ames, IA"));
                        restaurantList.add(new Restaurant("Jethro's", "1301 Buckeye Ave, Ames, IA"));
                        restaurantList.add(new Restaurant("Buffalo Wild Wings", "400 S Duff Ave, Ames, IA"));
                        restaurantList.add(new Restaurant("Taco Bell", "421 S Duff Ave, Ames, IA"));
                        restaurantList.add(new Restaurant("Boulder Tap House", "114 S Duff Ave, Ames, IA"));
                        restaurantList.add(new Restaurant("Culver's", "7493 Lincoln Way, Ames, IA"));
                        restaurantList.add(new Restaurant("Red Lobster", "4739 Lincoln Way, Ames, IA"));
                        restaurantList.add(new Restaurant("Wendy's", "2640 Lincoln Way, Ames, IA"));
                        restaurantList.add(new Restaurant("McDonald's", "6942 Lincoln Way, Ames, IA"));

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
            }
        );

        // Add Json request to queue
        reqQueue.add(getRequest);


//        Restaurant map testing
//        restaurantList.add(new Restaurant("Subway", "302 Lincoln Way Suite 101, Ames, IA"));
//        restaurantList.add(new Restaurant("Hickory Park", "1404 S Duff Ave, Ames, IA"));
//        restaurantList.add(new Restaurant("Jethro's", "1301 Buckeye Ave, Ames, IA"));
//        restaurantList.add(new Restaurant("Buffalo Wild Wings", "400 S Duff Ave, Ames, IA"));
//        restaurantList.add(new Restaurant("Taco Bell", "421 S Duff Ave, Ames, IA"));
//        restaurantList.add(new Restaurant("Dairy Queen", "3456 Lincoln Way, Ames, IA"));
//        restaurantList.add(new Restaurant("Applebee's", "7891 Lincoln Way, Ames, IA"));
//        restaurantList.add(new Restaurant("Perkins", "2345 Lincoln Way, Ames, IA"));
//        restaurantList.add(new Restaurant("Panda Express", "6789 Lincoln Way, Ames, IA"));
//        restaurantList.add(new Restaurant("Cornbread Barbeque", "0101 Lincoln Way, Ames, IA"));
//        restaurantList.add(new Restaurant("Village Inn", "9237 Lincoln Way, Ames, IA"));
//        restaurantList.add(new Restaurant("West Towne Pub", "1678 Lincoln Way, Ames, IA"));
//        restaurantList.add(new Restaurant("Old Chicago", "9564 Lincoln Way, Ames, IA"));
//        restaurantList.add(new Restaurant("Boulder Tap House", "114 S Duff Ave, Ames, IA"));
//        restaurantList.add(new Restaurant("Culver's", "7493 Lincoln Way, Ames, IA"));
//        restaurantList.add(new Restaurant("Red Lobster", "4739 Lincoln Way, Ames, IA"));
//        restaurantList.add(new Restaurant("Wendy's", "2640 Lincoln Way, Ames, IA"));
//        restaurantList.add(new Restaurant("McDonald's", "6942 Lincoln Way, Ames, IA"));


//        mapRecyclerAdapter = new MapRecyclerAdapter(restaurantList, this);
//        mapRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // Attach mapRecyclerAdapter to mapRecyclerView
//        mapRecyclerView.setAdapter(mapRecyclerAdapter);
//
//        DividerItemDecoration restaurantDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        mapRecyclerView.addItemDecoration(restaurantDivider);

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