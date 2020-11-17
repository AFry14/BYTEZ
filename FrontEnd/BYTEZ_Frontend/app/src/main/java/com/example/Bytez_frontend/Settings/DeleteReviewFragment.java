package com.example.Bytez_frontend.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

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
import com.android.volley.toolbox.Volley;
import com.example.Bytez_frontend.Features.HomeActivity;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Restaurant;
import com.example.Bytez_frontend.Review;
import com.example.Bytez_frontend.ReviewPackage.ReviewRecyclerAdapter;
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

public class DeleteReviewFragment extends Fragment
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


    public DeleteReviewFragment()
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_loading, container, false);
        reviewRecyclerView = view.findViewById(R.id.settingsReviewRecycler);

        Bundle bundle = getArguments();
        reviewIds = bundle.getIntegerArrayList("ids");
        ratings = (ArrayList<Double>) bundle.getSerializable("ratings");
//        reqQueue = Volley.newRequestQueue(ctx);


//        while(reviewIds.size()==0)
//        {
//
//            if(reviewIds.size() != 0)
//            {
//                break;
//            }
//        }


        for(int i = 0; i<reviewIds.size(); i++)
        {
            String pass1 = URLs.URL_REST_IN_REVIEW + reviewIds.get(i);
            JsonObjectRequest getRestRequest = new JsonObjectRequest(Request.Method.GET, pass1, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                locations.add(new Restaurant(response.getInt("id"), response.getString("restaurantName"), response.getString("address")));
                            }
                            catch(JSONException e)
                            {
                                System.out.println("RestRequestCatchError");
                                return;
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            System.out.println("RestRequestErrorResponse");
                            return;
                        }
                    }
            );

            SingletonVolley.getInstance(mCtx).addToRequestQueue(getRestRequest);
        }

        for(int i=0; i<reviewIds.size(); i++)
        {
            String pass1 = URLs.URL_AUTHOR_OF_REVIEW + reviewIds.get(i);
            JsonObjectRequest getUserRequest = new JsonObjectRequest(Request.Method.GET, pass1, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                reviewers.add(new User(response.getInt("id"), response.getString("userName"), response.getString("email")));
                            }
                            catch(JSONException e)
                            {
                                System.out.println("ReviewerRequestCatchError");
                                return;
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            System.out.println("ReviewerRequestErrorResponse");
                            return;
                        }
                    }
            );
            SingletonVolley.getInstance(mCtx).addToRequestQueue(getUserRequest);
        }


        this.view = view;
        return view;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.yesB).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                ArrayList<Integer> sendIds = reviewIds;
//                ArrayList<Restaurant> sendRest = locations;
//                ArrayList<User> sendUsers = reviewers;
//                ArrayList<Double> sendRatings = ratings;
                Bundle bundle = new Bundle();
                bundle.putSerializable("ids", reviewIds);
                bundle.putSerializable("rest", locations);
                bundle.putSerializable("user", reviewers);
                bundle.putSerializable("ratings", ratings);
                NavHostFragment.findNavController(DeleteReviewFragment.this)
                        .navigate(R.id.action_DeleteReviewFragment_to_ReviewShowFragment, bundle);


            }
        });
    }


}
