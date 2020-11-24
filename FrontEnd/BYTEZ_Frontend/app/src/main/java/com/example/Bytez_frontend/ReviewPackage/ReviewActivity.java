package com.example.Bytez_frontend.ReviewPackage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Bytez_frontend.Features.HomeActivity;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Restaurant;
import com.example.Bytez_frontend.Review;
import com.example.Bytez_frontend.Settings.AdjustCriteriaFragment;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.SingletonVolley;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;
import com.example.Bytez_frontend.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity
{
    int cerror=-1;
    List<Restaurant> restArrayList = new ArrayList<Restaurant>();
    String restStringArray[];
    int restIDArray[];
    private Context ctx;
    private RecyclerView reviewRecyclerView;
    private ReviewRecyclerAdapter reviewRecyclerAdapter;
//    private RequestQueue reqQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ctx = this;
//        reviewRecyclerView = findViewById(R.id.businessRecycler);
//        reqQueue = Volley.newRequestQueue(this);

        //retrieve all the restaurants in the database
        String pass = URLs.URL_REST_LIST;
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, pass, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            Log.d("TAG", response.toString());
                            cerror =2;
                            restStringArray = new String[response.length()];
                            restIDArray = new int[response.length()];
                            for(int i =0; i<response.length(); i++)
                            {
                                JSONObject jresponse = response.getJSONObject(i);
                                restStringArray[i] = jresponse.getString("restaurantName") + ", " + jresponse.getString("address");
                                String restName = response.getJSONObject(i).getString("restaurantName");
                                String restAddress = response.getJSONObject(i).getString("address");
                                restArrayList.add(new Restaurant(restName, restAddress));
                                restIDArray[i] = jresponse.getInt("id");
                            }

                            //set the autocomplete text box with all the restaurants
                            AutoCompleteTextView BusinessSearch = findViewById(R.id.businessBar);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, restStringArray);
                            BusinessSearch.setAdapter(adapter);

//                            reviewRecyclerAdapter = new ReviewRecyclerAdapter(restArrayList, ctx);
//                            reviewRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
//
//                            reviewRecyclerView.setAdapter(reviewRecyclerAdapter);
//                            DividerItemDecoration restaurantDivider = new DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL);
//                            reviewRecyclerView.addItemDecoration(restaurantDivider);

                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
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



//        SingletonVolley.getInstance(ctx).addToRequestQueue(getRequest);
//        reqQueue.add(getRequest);
        SingletonVolley.getInstance(this).addToRequestQueue(getRequest);


        //when the submit button is pressed
        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                RatingBar foodBar = (RatingBar) findViewById(R.id.foodBar);
                RatingBar serviceBar = (RatingBar) findViewById(R.id.serviceBar);
                RatingBar cleanBar = (RatingBar) findViewById(R.id.cleanlinessBar);
                AutoCompleteTextView business = (AutoCompleteTextView) findViewById(R.id.businessBar);
                String rest = business.getText().toString();
                EditText commentText = (EditText) findViewById(R.id.commentSection);
                float foodS = foodBar.getRating();
                float serviceS = serviceBar.getRating();
                float cleanS = cleanBar.getRating();
                String comments = commentText.getText().toString();

                //if the restaurant box is empty
                if (TextUtils.isEmpty(rest)) {
                    business.setError("Please enter a restaurant");
                    business.requestFocus();
                    return;
                }

                //if the food rating is empty
                if (foodS == 0) {
                    String str = "Please enter a rating for how the food tasted";
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    return;
                }

                //if the service rating is empty
                if (serviceS == 0) {
                    String str = "Please enter a rating for how the service was";
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    return;
                }

                //if the clean rating is empty
                if (cleanS == 0) {
                    String str = "Please enter a rating for how the restaurant's cleanliness was";
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    return;
                }

                //set ids for the restaurant
                int restID = -1;
                for (int i = 0; i < restStringArray.length; i++) {
                    if (rest.equals(restStringArray[i])) {
                        restID = i + 1;
                    }
                }

                //body for review post request
                JSONObject jsonBody = new JSONObject();
                try {
                    AdjustCriteriaInReviewFragment criteriaFrag = (AdjustCriteriaInReviewFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
                    int foodCrit = criteriaFrag.getFood();
                    int serviceCrit = criteriaFrag.getService();
                    int cleanCrit = criteriaFrag.getClean();
                    jsonBody.put("foodQualityScore", foodS);
                    jsonBody.put("serviceScore", serviceS);
                    jsonBody.put("cleanlinessScore", cleanS);
                    jsonBody.put("overallScore", Review.getFinalRating(foodS, serviceS, cleanS, foodCrit, serviceCrit, cleanCrit));
                    jsonBody.put("authorName", SharedPrefManager.getInstance(ctx).getUser().getUsername());
                    jsonBody.put("restaurantName", business.getText().toString());
                    jsonBody.put("comment", comments);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                //post request for the review
                String pass = URLs.URL_REVIEW + SharedPrefManager.getInstance(ctx).getUser().getId() + "/" + restID;
                JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, pass, jsonBody,
                        new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
//                                  finish();
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                }
                            },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                SingletonVolley.getInstance(ctx).addToRequestQueue(postRequest);

            }

        });
//            }
//        });
    }

}