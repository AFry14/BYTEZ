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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
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
            startActivity(new Intent(this, SuccessActivity.class));
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
            textEmail.setError("Please enter your password");
            textEmail.requestFocus();
            return;
        }

        StringRequest strreq = new StringRequest(URLs.URL_LOGIN, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject obj = new JSONObject(response);

                    if(!obj.getBoolean("error"))
                    {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();


                        JSONObject JsonUser = obj.getJSONObject("user");

                        User user = new User(
                                JsonUser.getInt("id"),
                                JsonUser.getString("username"),
                                JsonUser.getString("email")
                        );

                        SharedPrefManager.getInstance(getApplicationContext()).loginInfo(user);

                        finish();
                        startActivity(new Intent(getApplicationContext(), SuccessActivity.class));
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
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
                    public void onErrorResponse(VolleyError err)
                    {
                        Toast.makeText(getApplicationContext(), err.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("email", userEmail);
                params.put("password", userPassword);
                return params;
            }
        };

        SingletonVolley.getInstance(this).addToRequestQueue(strreq);
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
