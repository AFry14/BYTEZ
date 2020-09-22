package com.example.login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity
{
    EditText textEmail;
    EditText textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if(SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
//        View v = findViewById(R.id.SignupB);
//        v.setOnClickListener(this);

        textEmail = (EditText) findViewById(R.id.editEmail);
        textPassword = (EditText) findViewById(R.id.editPassword);

        findViewById(R.id.LoginButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                login();
            }
        });

        findViewById(R.id.SignupB).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });
    }

    private void login()
    {
        final String userEmail = textEmail.getText().toString();
        final String userPassword = textPassword.getText().toString();

        if(TextUtils.isEmpty(userEmail))
        {
            textEmail.setError("Please enter an email");
            textEmail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(userPassword))
        {
            textPassword.setError("Please enter your password");
            textPassword.requestFocus();
            return;
        }

        String pass = URLs.URL_LOGIN;
        pass = pass+"?email="+userEmail+"&password="+userPassword;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, pass, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            String w ="";
                            User JsonUser = new User(response.getInt("id"), response.getString("userName"), response.getString("email"));
                            SharedPrefManager.getInstance(getApplicationContext()).loginInfo(JsonUser);
//                            finish();
                            startActivity(new Intent(getApplicationContext(), SignUp.class));
                        }
                        catch(JSONException e)
                        {
                            textEmail.setError("Incorrect password or username");
                            textEmail.requestFocus();
                            return;
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        textPassword.setError("Incorrect password or username");
                        textPassword.requestFocus();
                        return;
                    }
                }
        );

        SingletonVolley.getInstance(this).addToRequestQueue(getRequest);
    }


//    public void onClick(View view)
//    {
//        if(view.getId() == R.id.SignupB)
//        {
//            Intent intent = new Intent(this, SignUp.class);
//            this.startActivity(intent);
//        }
//
//    }
}
