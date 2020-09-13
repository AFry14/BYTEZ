package com.example.bytez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    RecyclerView mapRecyclerView;
    MapRecyclerAdapter mapRecyclerAdapter;

    // List of restaurant accounts
    List<RestaurantProfile> restaurantList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setTitle("");

        // Restaurant list
        restaurantList = new ArrayList<>();


        mapRecyclerView = findViewById(R.id.mapRecyclerView);


        restaurantList.add(new RestaurantProfile("Subway", "1234 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("Hickory Park", "5678 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("Jethro's", "9123 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("Buffalo Wild Wings", "4567 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("Taco Bell", "8912 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("Dairy Queen", "3456 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("Applebee's", "7891 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("Perkins", "2345 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("Panda Express", "6789 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("Cornbread Barbeque", "0101 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("Village Inn", "9237 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("West Towne Pub", "1678 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("Old Chicago", "9564 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("Boulder Tap House", "3819 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("Culver's", "7493 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("Red Lobster", "4739 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("Wendy's", "2640 Lincoln Way, Ames, IA"));
        restaurantList.add(new RestaurantProfile("McDonald's", "6942 Lincoln Way, Ames, IA"));


        mapRecyclerAdapter = new MapRecyclerAdapter(restaurantList);
        mapRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Attach mapRecyclerAdapter to mapRecyclerView
        mapRecyclerView.setAdapter(mapRecyclerAdapter);

        DividerItemDecoration restaurantDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mapRecyclerView.addItemDecoration(restaurantDivider);

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