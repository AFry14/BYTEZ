package com.example.Bytez_frontend.Settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.SingletonVolley;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class bugReportFragment extends Fragment
{
//    Context ctx = getActivity();
    Context mCtx;

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
        View view = inflater.inflate(R.layout.fragment_bug_report, container, false);

        final EditText bugText = (EditText) view.findViewById(R.id.reportString);

        view.findViewById(R.id.bugSubmitB).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if(TextUtils.isEmpty(bugText.getText().toString()))
                {
                    bugText.setError("Please enter a bug report");
                    bugText.requestFocus();
                    return;
                }

                String report = bugText.getText().toString();

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("report", report);
                    jsonBody.put("author_id", SharedPrefManager.getInstance(mCtx).getUser().getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String pass = URLs.URL_BUG_ADD + SharedPrefManager.getInstance(mCtx).getUser().getId();
                JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, pass, jsonBody,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {
                                startActivity(new Intent(getActivity().getApplicationContext(), SettingsActivity.class));
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                return;
                            }
                        });

                SingletonVolley.getInstance(mCtx).addToRequestQueue(postRequest);

            }
        });
        view.findViewById(R.id.backFRTS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(bugReportFragment.this).navigate(R.id.action_bugReportFragment_to_SettingsMainFragment);
            }
        });
        return view;
    }
}
