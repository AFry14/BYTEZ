package com.example.Bytez_frontend.Settings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Restaurant;
import com.example.Bytez_frontend.Review;
import com.example.Bytez_frontend.SingletonVolley;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

        Bundle bundle = getArguments();
        reviewIds = (ArrayList<Integer>) bundle.getSerializable("ids");
        locations = (ArrayList<Restaurant>) bundle.getSerializable("rest");
        reviewers = (ArrayList<User>) bundle.getSerializable("user");
        ratings = (ArrayList<Double>) bundle.getSerializable("ratings");



        for(int i =0; i< reviewIds.size(); i++)
        {
            BigDecimal r = new BigDecimal(ratings.get(i));
            float rating = r.floatValue();

            reviewArrayList.add(new Review(reviewIds.get(i), rating, locations.get(i), reviewers.get(i)));
        }

        SettingsReviewRecyclerAdapter = new SettingsReviewRecyclerAdapter(reviewArrayList, mCtx);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(mCtx));

        reviewRecyclerView.setAdapter(SettingsReviewRecyclerAdapter);
        DividerItemDecoration restaurantDivider = new DividerItemDecoration(mCtx, DividerItemDecoration.VERTICAL);
        reviewRecyclerView.addItemDecoration(restaurantDivider);
        this.view = view;
        return view;
    }



}
