package com.example.login_signup;

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
import com.android.volley.toolbox.StringRequest;

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
//        View v = findViewById(R.id.SignupB);
//        v.setOnClickListener(this);

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

//        findViewById(R.id.SignupB).setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                startActivity(new Intent(getApplicationContext(), SignUp.class));
//            }
//        });
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

        StringRequest strreq = new StringRequest(Request.Method.POST, URLs.URL_REGISTER, new Response.Listener<String>()
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
                params.put("userName", userName);
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
