package com.example.Bytez_frontend.Features;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Review;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.SingletonVolley;
import com.example.Bytez_frontend.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MyReviewsFragment extends Fragment
{
    Context mCtx;
    ArrayList<Review> allReviews = new ArrayList<Review>();
    HomeReviewRecyclerAdapter HomeReviewRecyclerAdapter;
    private RecyclerView reviewRecyclerView;

    public MyReviewsFragment()
    {

    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if(context instanceof Activity)
        {
            mCtx = context;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInsanceState)
    {
        String pass = URLs.URL_AUTHORS_WORK + SharedPrefManager.getInstance(mCtx).getUser().getId();
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, pass, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            Log.d("TAG", response.toString());
//                            restStringArray = new String[response.length()];
//                            restIDArray = new int[response.length()];
                            for(int i =0; i<response.length(); i++)
                            {
                                JSONObject jresponse = response.getJSONObject(i);
                                BigDecimal r = new BigDecimal(jresponse.getDouble("overallScore"));
                                float overallScore = r.floatValue();
                                BigDecimal r1 = new BigDecimal(jresponse.getDouble("foodQualityScore"));
                                float FQScore = r1.floatValue();
                                BigDecimal r2 = new BigDecimal(jresponse.getDouble("cleanlinessScore"));
                                float cleanScore = r2.floatValue();
                                BigDecimal r3 = new BigDecimal(jresponse.getDouble("serviceScore"));
                                float sScore = r3.floatValue();
                                String comments;
                                if(jresponse.getString("comment") == null)
                                {
                                    comments = "";
                                }
                                else
                                {
                                    comments = jresponse.getString("comment");
                                }
                                ArrayList<Integer> agrees = new ArrayList<Integer>();
                                for(int j = 0; j<jresponse.getJSONArray("likes").length(); j++)
                                {
                                    agrees.add(jresponse.getJSONArray("likes").getInt(j));
                                }
                                ArrayList<Integer> disagrees = new ArrayList<Integer>();
                                for(int j = 0; j<jresponse.getJSONArray("dislikes").length(); j++)
                                {
                                    disagrees.add(jresponse.getJSONArray("dislikes").getInt(j));
                                }
                                ArrayList<Integer> helpfuls = new ArrayList<Integer>();
                                for(int j = 0; j<jresponse.getJSONArray("helpfuls").length(); j++)
                                {
                                    helpfuls.add(jresponse.getJSONArray("helpfuls").getInt(j));
                                }
//                                Review newReview = new Review(jresponse.getInt("id"), overallScore, jresponse.getString("userName"), jresponse.getString("restName"), FQScore, cleanScore,sScore, jresponse.getString("comments"));
                                Review newReview = new Review(jresponse.getInt("id"), overallScore, jresponse.getString("authorName"), jresponse.getString("restaurantName"), FQScore, cleanScore, sScore, comments, agrees, disagrees, helpfuls);
//                                restStringArray[i] = jresponse.getString("restaurantName") + ", " + jresponse.getString("address");
                                allReviews.add(newReview);
                            }
                            HomeReviewRecyclerAdapter = new HomeReviewRecyclerAdapter(allReviews, mCtx);
                            reviewRecyclerView.setLayoutManager(new LinearLayoutManager(mCtx));

                            reviewRecyclerView.setAdapter(HomeReviewRecyclerAdapter);
                            DividerItemDecoration reviewDivider = new DividerItemDecoration(mCtx, DividerItemDecoration.VERTICAL);
                            reviewRecyclerView.addItemDecoration(reviewDivider);

//                            AutoCompleteTextView BusinessSearch = view.findViewById(R.id.businessBar);
//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, restStringArray);
//                            BusinessSearch.setAdapter(adapter);
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

        SingletonVolley.getInstance(mCtx).addToRequestQueue(getRequest);
        return inflater.inflate(R.layout.fragment_my_reviews, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        reviewRecyclerView = (RecyclerView) view.findViewById(R.id.myReviews);
    }
}

