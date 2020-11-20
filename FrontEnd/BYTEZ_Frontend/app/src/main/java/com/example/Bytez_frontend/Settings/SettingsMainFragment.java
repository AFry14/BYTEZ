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
import com.example.Bytez_frontend.Features.HomeActivity;
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
                NavHostFragment.findNavController(SettingsMainFragment.this)
                        .navigate(R.id.action_SettingsMainFragment_to_ReviewShowFragment);
            }
        });

        view.findViewById(R.id.adjustCriteriaB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SettingsMainFragment.this).navigate(R.id.action_SettingsMainFragment_to_AdjustCriteriaFragment);
            }
        });

        view.findViewById(R.id.backFSTH).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HomeActivity.class));
            }
        });

    }


}
