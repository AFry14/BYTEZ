package com.example.Bytez_frontend.Settings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.Bytez_frontend.Features.HomeReviewRecyclerAdapter;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Review;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.SingletonVolley;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class AdjustCriteriaFragment extends Fragment
{
    Context mCtx;
    User user = SharedPrefManager.getInstance(mCtx).getUser();
    int food, service, clean;
    boolean save = false;
    int clear;

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
        return inflater.inflate(R.layout.fragment_adjust_criteria, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);



        SeekBar foodScale = view.findViewById(R.id.foodSeek);
        foodScale.setProgress(user.getCritFood());
        food =  foodScale.getProgress();
        foodScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b==true)
                {
                    food = i;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar serviceScale = view.findViewById(R.id.serviceSeek);
        serviceScale.setProgress(user.getCritService());
        service =  serviceScale.getProgress();
        serviceScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b==true)
                {
                    service = i;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar cleanScale = view.findViewById(R.id.cleanSeek);
        cleanScale.setProgress(user.getCritClean());
        clean =  cleanScale.getProgress();
        cleanScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b==true)
                {
                    clean = i;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        view.findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear = 0;
                if(food+service+clean == 0)
                {
                    Toast.makeText(mCtx, "All weights cannot be 0", Toast.LENGTH_SHORT).show();
                }
                else {
                        String url = URLs.URL_CHANGE_ALL_CRITS + SharedPrefManager.getInstance(mCtx).getUser().getId() + "?critFood=" + food + "&critClean=" + clean + "&critService=" + service;
                    JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("TAG", response.toString());
                                    user.setCritFood(food);
                                    user.setCritService(service);
                                    user.setCritClean(clean);
                                    User newUser = new User(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), user.getFavFood(), user.getFavDrink(),
                                    user.getFavRestaurant(), user.getfName(), user.getlName(), user.getUserType(), user.getCritFood(), user.getCritService(), user.getCritClean());
                                    SharedPrefManager.getInstance(mCtx).loginInfo(newUser);
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    clear++;
                                    error.printStackTrace();
                                }
                            }
                    );
                    SingletonVolley.getInstance(mCtx).addToRequestQueue(putRequest);
////                    final JSONObject updatedCritFood = new JSONObject();
////                    try {
////                        updatedCritFood.put("value", food);
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
//                    String pass = URLs.URL_CHANGE_CRIT_FOOD + SharedPrefManager.getInstance(mCtx).getUser().getId() + "?value=" + food;
//                    JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, pass, null,
//                            new Response.Listener<JSONObject>() {
//                                @Override
//                                public void onResponse(JSONObject response) {
//                                    Log.d("TAG", response.toString());
//                                    user.setCritFood(food);
//                                    clear++;
//                                }
//                            },
//                            new Response.ErrorListener()
//                            {
//                                @Override
//                                public void onErrorResponse(VolleyError error)
//                                {
//                                    clear++;
//                                    error.printStackTrace();
//                                }
//                            }
//                    );
//                    SingletonVolley.getInstance(mCtx).addToRequestQueue(putRequest);
//                    final JSONObject updatedCritService = new JSONObject();
//                    try {
//                        updatedCritService.put("value", service);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String pass1 = URLs.URL_CHANGE_CRIT_SERVICE + SharedPrefManager.getInstance(mCtx).getUser().getId() + "?value=" + service;
//                    JsonObjectRequest put1Request = new JsonObjectRequest(Request.Method.PUT, pass1, null,
//                            new Response.Listener<JSONObject>() {
//                                @Override
//                                public void onResponse(JSONObject response) {
//                                    Log.d("TAG", response.toString());
//                                    user.setCritService(service);
//                                    clear++;
//                                }
//                            },
//                            new Response.ErrorListener()
//                            {
//                                @Override
//                                public void onErrorResponse(VolleyError error)
//                                {
//                                    error.printStackTrace();
//                                }
//                            }
//                    );
//                    SingletonVolley.getInstance(mCtx).addToRequestQueue(put1Request);
//                    final JSONObject updatedCritClean = new JSONObject();
//                    try {
//                        updatedCritClean.put("critClean", clean);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    String pass2 = URLs.URL_CHANGE_CRIT_CLEAN + SharedPrefManager.getInstance(mCtx).getUser().getId() + "?value=" + clean;
//                    JsonObjectRequest put2Request = new JsonObjectRequest(Request.Method.PUT, pass2, null,
//                            new Response.Listener<JSONObject>() {
//                                @Override
//                                public void onResponse(JSONObject response) {
//                                    Log.d("TAG", response.toString());
//                                    user.setCritClean(clean);
//                                    clear++;
//                                }
//                            },
//                            new Response.ErrorListener()
//                            {
//                                @Override
//                                public void onErrorResponse(VolleyError error)
//                                {
//                                    error.printStackTrace();
//                                    clear++;
//                                }
//                            }
//                    );
//                    SingletonVolley.getInstance(mCtx).addToRequestQueue(put2Request);
                }

            }
        });

        view.findViewById(R.id.backFCritTS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(AdjustCriteriaFragment.this).navigate(R.id.action_AdjustCriteriaFragment_to_SettingsMainFragment);
            }
        });

    }
}
