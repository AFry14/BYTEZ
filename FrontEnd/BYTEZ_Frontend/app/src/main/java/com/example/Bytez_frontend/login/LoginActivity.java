package com.example.Bytez_frontend.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.Bytez_frontend.Features.HomeActivity;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.SingletonVolley;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity
{
    EditText textEmail;
    EditText textPassword;
    private Context context;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
{
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    context = this;

    if(SharedPrefManager.getInstance(this).isLoggedIn())
    {
        finish();
        startActivity(new Intent(this, SuccessActivity.class));
    }

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

        //Intent to start home activity
//        Intent homeActivity = new Intent(context, HomeActivity.class);


        String pass = URLs.URL_LOGIN;
        pass = pass+"?email="+userEmail+"&password="+userPassword;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, pass, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            User JsonUser = new User(response.getInt("id"), response.getString("userName"), response.getString("email"),
                                    response.getString("password"), response.getString("favoriteFood"), response.getString("favoriteDrink"),
                                    response.getString("favoriteRestaurant"), response.getString("firstName"), response.getString("lastName"),
                                    response.getString("userType"));
                            SharedPrefManager.getInstance(context).loginInfo(JsonUser);

                            // Pass json into home activity
    //                        Intent homeActivity = new Intent(context, HomeActivity.class);
                            String user = response.toString();
                            Log.d("response", response.toString());
      //                      homeActivity.putExtra("json", user);
     //                       startActivity(homeActivity);
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


}
