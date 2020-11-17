package com.example.Bytez_frontend.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.SingletonVolley;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SettingsMainFragment extends Fragment
{
    Context mCtx;
    final ArrayList<Integer> reviewIds = new ArrayList<Integer>();
    final ArrayList<Double> ratings = new ArrayList<Double>();

    public SettingsMainFragment()
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
//                                restStringArray[i] = jresponse.getString("restaurantName") + ", " + jresponse.getString("address");
                                reviewIds.add(response.getJSONObject(i).getInt("id"));
                                ratings.add(response.getJSONObject(i).getDouble("overallScore"));
//                                String comments = response.getJSONObject(i).getString("comments");
//                                reviewArrayList.add(new Review(reviewId, locations.get(i), reviewers.get(i)));
//                                restIDArray[i] = jresponse.getInt("id");
                            }

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
        return inflater.inflate(R.layout.fragment_settingsmain, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.logoutB).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
            }
        });

        view.findViewById(R.id.bugReportB).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavHostFragment.findNavController(SettingsMainFragment.this)
                        .navigate(R.id.action_SettingsMainFragment_to_bugReportFragment);
            }
        });

        view.findViewById(R.id.deleteReviewB).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                ArrayList<Integer> sendIds = reviewIds;
//                ArrayList<Double> sendRatings = ratings;
//                reviewIds.clear();
//                ratings.clear();
                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList("ids", reviewIds);
                bundle.putSerializable("ratings", ratings);
                NavHostFragment.findNavController(SettingsMainFragment.this)
                        .navigate(R.id.action_SettingsMainFragment_to_DeleteReviewFragment, bundle);
            }
        });

    }


}
