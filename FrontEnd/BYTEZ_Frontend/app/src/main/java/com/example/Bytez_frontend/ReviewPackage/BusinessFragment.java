package com.example.Bytez_frontend.ReviewPackage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


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
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BusinessFragment extends Fragment
{
    JSONObject restArray[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        String pass = URLs.URL_REST_LIST;

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, pass, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            Log.d("TAG", response.toString());
                            restArray = new JSONObject[response.length()];
                            for(int i =0; i<response.length(); i++)
                            {
                                JSONObject jresponse = response.getJSONObject(i);
                                restArray[i]=jresponse;
                            }


//                            finish();

                        }
                        catch(JSONException e)
                        {
                            System.out.println("fail");
                            return;
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        System.out.println("error");
                        return;
                    }
                }
        );
        View view = inflater.inflate(R.layout.fragment_business_search, container, false);
        AutoCompleteTextView BusinessSearch = (AutoCompleteTextView) view.findViewById(R.id.BusinessBar);
        String fillArray[] = new String[restArray.length];
        for(int i = 0; i<fillArray.length; i++)
        {
            String name_Addr = "";
            try {
                name_Addr = restArray[i].getString("restaurantName") + ", ";
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                name_Addr = name_Addr +  restArray[i].getString("address");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            fillArray[i] = name_Addr;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, fillArray);
        BusinessSearch.setAdapter(adapter);
        return view;
    }
}
