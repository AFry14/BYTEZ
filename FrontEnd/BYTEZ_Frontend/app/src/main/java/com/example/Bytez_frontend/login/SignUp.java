package com.example.Bytez_frontend.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.SingletonVolley;
import com.example.Bytez_frontend.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity
{
    EditText textEmail;
    EditText textName;
    EditText textPassword;
    EditText textCPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        if(SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this, SuccessActivity.class));
            return;
        }

        textEmail = (EditText) findViewById(R.id.TextEmail);
        textPassword = (EditText) findViewById(R.id.TextPassword);
        textCPassword = (EditText) findViewById(R.id.TextCPassword);
        textName = (EditText) findViewById(R.id.TextName);

        findViewById(R.id.registerB).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                register();
            }
        });


    }

    private void register()
    {
        final String userEmail = textEmail.getText().toString();
        final String userPassword = textPassword.getText().toString();
        final String userCPassword = textCPassword.getText().toString();
        final String userName = textName.getText().toString();

        if(TextUtils.isEmpty(userEmail))
        {
            textEmail.setError("Please enter an email");
            textEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
        {
            textEmail.setError("Enter a valid email address");
            return;
        }

        if(TextUtils.isEmpty(userPassword))
        {
            textPassword.setError("Please enter your password");
            textPassword.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(userCPassword))
        {
            textCPassword.setError("Please confirm your password");
            textCPassword.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(userName))
        {
            textName.setError("Please enter your Name");
            textName.requestFocus();
            return;
        }

        if(!userPassword.equals(userCPassword))
        {
            textCPassword.setError("Your passwords do not match");
            textCPassword.requestFocus();
            return;
        }

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", userEmail);
            jsonBody.put("password", userPassword);
            jsonBody.put("userName", userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, URLs.URL_REGISTER, jsonBody,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
//                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        SingletonVolley.getInstance(this).addToRequestQueue(postRequest);
    }

}
