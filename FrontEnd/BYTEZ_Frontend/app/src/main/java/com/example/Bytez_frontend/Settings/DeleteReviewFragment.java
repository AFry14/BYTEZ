package com.example.Bytez_frontend.Settings;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Restaurant;
import com.example.Bytez_frontend.Review;
import com.example.Bytez_frontend.ReviewPackage.ReviewRecyclerAdapter;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.SingletonVolley;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DeleteReviewFragment extends Fragment
{
    Context ctx = getActivity();

    int cerror=-1;
    List<Review> reviewArrayList = new ArrayList<Review>();
//    String restStringArray[];
//    int restIDArray[];
    private RecyclerView reviewRecyclerView;
    private SettingsReviewRecyclerAdapter settingsReviewRecyclerAdapter;
    private RequestQueue reqQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_delete_review, container, false);
        reviewRecyclerView = view.findViewById(R.id.settingsReviewRecycler);
//        reqQueue = Volley.newRequestQueue(ctx);
        String pass = URLs.URL_AUTHOR + SharedPrefManager.getInstance(ctx).getUser().getId();

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
//                                restStringArray[i] = jresponse.getString("restaurantName") + ", " + jresponse.getString("address");
                                int reviewId = response.getJSONObject(i).getInt("id");
                                Restaurant location = (Restaurant) response.getJSONObject(i).get("restaurant");
                                int reviewer = response.getJSONObject(i).getInt("userId");
                                String comments = response.getJSONObject(i).getString("comments");
                                reviewArrayList.add(new Review(reviewId, location, reviewer, comments));
//                                restIDArray[i] = jresponse.getInt("id");
                            }

//                            AutoCompleteTextView BusinessSearch = view.findViewById(R.id.businessBar);
//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, restStringArray);
//                            BusinessSearch.setAdapter(adapter);

                            settingsReviewRecyclerAdapter = new SettingsReviewRecyclerAdapter(reviewArrayList, ctx);
                            reviewRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));

                            reviewRecyclerView.setAdapter(settingsReviewRecyclerAdapter);
                            DividerItemDecoration restaurantDivider = new DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL);
                            reviewRecyclerView.addItemDecoration(restaurantDivider);

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

        SingletonVolley.getInstance(ctx).addToRequestQueue(getRequest);

        return view;
    }
}
