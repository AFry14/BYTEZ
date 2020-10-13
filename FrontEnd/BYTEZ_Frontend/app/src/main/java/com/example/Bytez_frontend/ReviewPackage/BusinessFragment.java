package com.example.Bytez_frontend.ReviewPackage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.Bytez_frontend.Map.HomeActivity;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Restaurant;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.SingletonVolley;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BusinessFragment extends Fragment
{
    String restArray[];
    int restIDArray[];
    int restID;
    int cerror = -1;
//    int restID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        String pass = URLs.URL_REST_LIST;
//        String fillArray[] = new String[2];
//        fillArray[0] = "hello";
//        fillArray[1] = "world";

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, pass, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            Log.d("TAG", response.toString());
                            cerror =2;
                            restArray = new String[response.length()];
                            restIDArray = new int[response.length()];
                            for(int i =0; i<response.length(); i++)
                            {
                                JSONObject jresponse = response.getJSONObject(i);
                                restArray[i] = jresponse.getString("restaurantName") + ", " + jresponse.getString("address");
                                restIDArray[i] = jresponse.getInt("id");
                            }


//                            finish();

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
        SingletonVolley.getInstance(getActivity()).addToRequestQueue(getRequest);
        View view = inflater.inflate(R.layout.fragment_business_search, container, false);
        AutoCompleteTextView BusinessSearch = (AutoCompleteTextView) view.findViewById(R.id.BusinessBar);
//        String fillArray[] = new String[restArray.length];
//        for(int i = 0; i<fillArray.length; i++)
//        {
//            String name_Addr = "";
//            try {
//                name_Addr = restArray[i].getString("restaurantName") + ", ";
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            try {
//                name_Addr = name_Addr +  restArray[i].getString("address");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            fillArray[i] = name_Addr;
//        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, restArray);
        BusinessSearch.setAdapter(adapter);
        return view;
    }

    public int getRestID()
    {
        AutoCompleteTextView business = (AutoCompleteTextView) getView().findViewById(R.id.BusinessBar);
        String rest = business.getText().toString();
        for(int i = 0; i<restArray.length; i++)
        {
            if(rest == restArray[i])
            {
                restID = i;
            }
        }
        return restID;
    }

}
