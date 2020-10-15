package com.example.Bytez_frontend.ReviewPackage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.Bytez_frontend.Map.HomeActivity;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Restaurant;
import com.example.Bytez_frontend.Review;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.SingletonVolley;
import com.example.Bytez_frontend.URLs;
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
    private Context ctx = this;
    private RecyclerView reviewRecyclerView;
    private ReviewRecyclerAdapter reviewRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
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
                                restArrayList.add(new Restaurant(jresponse.getString("restaurantName"), jresponse.getString("address")));
                                restIDArray[i] = jresponse.getInt("id");
                            }
                            AutoCompleteTextView BusinessSearch = (AutoCompleteTextView) findViewById(R.id.BusinessBar);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, restStringArray);
                            BusinessSearch.setAdapter(adapter);

                            reviewRecyclerAdapter = new ReviewRecyclerAdapter(restArrayList, ctx);
                            reviewRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));

                            reviewRecyclerView.setAdapter(reviewRecyclerAdapter);
                            DividerItemDecoration restaurantDivider = new DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL);
                            reviewRecyclerView.addItemDecoration(restaurantDivider);

                        }
                        catch(JSONException e)
                        {
                            cerror = 3;
                            return;
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        cerror = 1;
                        return;
                    }
                }
        );

        SingletonVolley.getInstance(this).addToRequestQueue(getRequest);

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                RatingBar foodBar = (RatingBar) findViewById(R.id.foodBar);
                RatingBar serviceBar = (RatingBar) findViewById(R.id.serviceBar);
                RatingBar cleanBar = (RatingBar) findViewById(R.id.cleanlinessBar);
                EditText commentText = (EditText) findViewById(R.id.commentSection);
                float foodS = foodBar.getRating();
                float serviceS = serviceBar.getRating();
                float cleanS = cleanBar.getRating();
                String comments = commentText.getText().toString();

                int restID=-1;
                AutoCompleteTextView business = (AutoCompleteTextView) findViewById(R.id.BusinessBar);
                String rest = business.getText().toString();
                for(int i = 0; i<restStringArray.length; i++)
                {
                    if(rest == restStringArray[i])
                    {
                       restID = i;
                    }
                }

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("restaurantId", restID);
                    jsonBody.put("userId", SharedPrefManager.getInstance(ctx).getUser().getId());
                    jsonBody.put("foodScore", foodS);
                    jsonBody.put("serviceScore", serviceS);
                    jsonBody.put("cleanlinessScore", cleanS);
                    jsonBody.put("overallScore", Review.getFinalRating(foodS, serviceS, cleanS));
                    jsonBody.put("comments", comments);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URLs.URL_REVIEW, jsonBody,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {
//                        finish();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                SingletonVolley.getInstance(ctx).addToRequestQueue(postRequest);

            }
        });
    }

}
