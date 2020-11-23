package com.example.Bytez_frontend.Settings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.Bytez_frontend.Features.HomeReviewRecyclerAdapter;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Restaurant;
import com.example.Bytez_frontend.Review;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.SingletonVolley;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that shows all the reviews the logged in user has written and can delete
 */
public class ReviewShowFragment extends Fragment
{
    protected FragmentActivity mActivity;
    Context mCtx;
    View view;
    private SettingsReviewRecyclerAdapter SettingsReviewRecyclerAdapter;
    ArrayList<Integer> reviewIds = new ArrayList<Integer>();
    ArrayList<Restaurant> locations = new ArrayList<Restaurant>();
    ArrayList<User> reviewers = new ArrayList<User>();
    ArrayList<Double> ratings = new ArrayList<Double>();

    int cerror=-1;
    List<Review> reviewArrayList = new ArrayList<Review>();
    //    String restStringArray[];
//    int restIDArray[];
    private RecyclerView reviewRecyclerView;
    private SettingsReviewRecyclerAdapter settingsReviewRecyclerAdapter;
    private RequestQueue reqQueue;


    public ReviewShowFragment()
    {

    }

    /**
     * get the context of the activity
     */
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if(context instanceof Activity)
        {
            mCtx = context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_delete_review, container, false);
        reviewRecyclerView = view.findViewById(R.id.settingsReviewRecycler);

//        Bundle bundle = getArguments();
//        reviewIds = (ArrayList<Integer>) bundle.getSerializable("ids");
//        locations = (ArrayList<Restaurant>) bundle.getSerializable("rest");
//        reviewers = (ArrayList<User>) bundle.getSerializable("user");
//        ratings = (ArrayList<Double>) bundle.getSerializable("ratings");

        //get all reviews written by the user and insert into an ArrayList
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
                                int id = jresponse.getInt("id");
                                BigDecimal r = new BigDecimal(jresponse.getDouble("overallScore"));
                                float overallScore = r.floatValue();
                                String userName = jresponse.getString("authorName");
                                String restName = jresponse.getString("restaurantName");
//                                Review newReview = new Review(jresponse.getInt("id"), overallScore, jresponse.getString("userName"), jresponse.getString("restName"), FQScore, cleanScore,sScore, jresponse.getString("comments"));
                                Review newReview = new Review(id, overallScore, userName, restName);
//                                restStringArray[i] = jresponse.getString("restaurantName") + ", " + jresponse.getString("address");
                                reviewArrayList.add(newReview);
                            }

                            //insert arraylist into review recycler view
                            SettingsReviewRecyclerAdapter = new SettingsReviewRecyclerAdapter(reviewArrayList, mCtx);
                            reviewRecyclerView.setLayoutManager(new LinearLayoutManager(mCtx));

                            reviewRecyclerView.setAdapter(SettingsReviewRecyclerAdapter);
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

//        for(int i =0; i< reviewIds.size(); i++)
//        {
//            BigDecimal r = new BigDecimal(ratings.get(i));
//            float rating = r.floatValue();
//
//            reviewArrayList.add(new Review(reviewIds.get(i), rating, locations.get(i), reviewers.get(i)));
//        }

//        SettingsReviewRecyclerAdapter = new SettingsReviewRecyclerAdapter(reviewArrayList, mCtx);
//        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(mCtx));
//
//        reviewRecyclerView.setAdapter(SettingsReviewRecyclerAdapter);
//        DividerItemDecoration restaurantDivider = new DividerItemDecoration(mCtx, DividerItemDecoration.VERTICAL);
//        reviewRecyclerView.addItemDecoration(restaurantDivider);
//        this.view = view;

        //return to settings if back button hit
        view.findViewById(R.id.backDRTS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ReviewShowFragment.this).navigate(R.id.action_ReviewShowFragment_to_SettingsMainFragment);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {

    }


}
