package com.example.Bytez_frontend.Features;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {

    private User currentUser;
    private Context context;
    private RequestQueue reqQueue;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        i = getIntent();
        currentUser = i.getParcelableExtra("user");
        setTitle("Edit " + currentUser.getUsername() + "'s Profile");
        context = this;

        EditText userName = findViewById(R.id.editUsername);
        userName.setText(currentUser.getUsername());
        EditText email = findViewById(R.id.editEmail);
        email.setText(currentUser.getEmail());
        EditText passWord = findViewById(R.id.editPassword);
        passWord.setText(currentUser.getPassword());
        EditText conPassWord = findViewById(R.id.editConPass);
        conPassWord.setText(currentUser.getPassword());
        EditText favFood = findViewById(R.id.editFavFood);
        favFood.setText(currentUser.getFavFood());
        EditText favDrink = findViewById(R.id.editFavDrink);
        favDrink.setText(currentUser.getFavDrink());
        EditText favRest = findViewById(R.id.editFavRest);
        favRest.setText(currentUser.getFavRestaurant());
        EditText fName = findViewById(R.id.editFirstName);
        fName.setText(currentUser.getfName());
        EditText lName = findViewById(R.id.editLastName);
        lName.setText(currentUser.getlName());


    }

    public void dontSaveProfile(View view) {
        finish();
    }

    public void saveProfile(View view) {
        EditText userName = findViewById(R.id.editUsername);
        EditText email = findViewById(R.id.editEmail);
        EditText passWord = findViewById(R.id.editPassword);
        EditText conPassWord = findViewById(R.id.editConPass);
        EditText favFood = findViewById(R.id.editFavFood);
        EditText favDrink = findViewById(R.id.editFavDrink);
        EditText favRest = findViewById(R.id.editFavRest);
        EditText fName = findViewById(R.id.editFirstName);
        EditText lName = findViewById(R.id.editLastName);

        // Check if any field is empty
        // If username field is empty, give error
        if (TextUtils.isEmpty(userName.getText().toString())) {
            userName.setError("Please enter a username");
            userName.requestFocus();
            return;
        }

        // If email field is empty, give error
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Please enter an email");
            email.requestFocus();
            return;
        }

        // If password field is empty, give error
        if (TextUtils.isEmpty(passWord.getText().toString())) {
            passWord.setError("Please enter a password");
            passWord.requestFocus();
            return;
        }

        // If confirm password field is empty, give error
        if (TextUtils.isEmpty(conPassWord.getText().toString())) {
            conPassWord.setError("Please confirm your password");
            conPassWord.requestFocus();
            return;
        }

        // If favorite food field is empty, give error
        if (TextUtils.isEmpty(favFood.getText().toString())) {
            favFood.setError("Please enter your favorite food");
            favFood.requestFocus();
            return;
        }

        // If favorite drink field is empty, give error
        if (TextUtils.isEmpty(favDrink.getText().toString())) {
            favDrink.setError("Please enter your favorite drink");
            favDrink.requestFocus();
            return;
        }

        // If favorite restaurant field is empty, give error
        if (TextUtils.isEmpty(favRest.getText().toString())) {
            favRest.setError("Please enter your favorite restaurant");
            favRest.requestFocus();
            return;
        }

        // If first name field is empty, give error
        if (TextUtils.isEmpty(fName.getText().toString())) {
            fName.setError("Please enter your first name");
            fName.requestFocus();
            return;
        }

        // If last name field is empty, give error
        if (TextUtils.isEmpty(lName.getText().toString())) {
            lName.setError("Please enter your favorite restaurant");
            lName.requestFocus();
            return;
        }







        // If passwords match
        if (passWord.getText().toString().equals(conPassWord.getText().toString()) == true) {
            currentUser.setUsername(userName.getText().toString());
            currentUser.setEmail(email.getText().toString());
            currentUser.setPassword(passWord.getText().toString());
            currentUser.setFavFood(favFood.getText().toString());
            currentUser.setFavDrink(favDrink.getText().toString());
            currentUser.setFavRestaurant(favRest.getText().toString());
            currentUser.setFirstName(fName.getText().toString());
            currentUser.setLastName(lName.getText().toString());

            // Set other values, then a volley request to update server
            final JSONObject updatedUser = new JSONObject();
            try {
                updatedUser.put("id", currentUser.getId());
                updatedUser.put("firstName", currentUser.getfName());
                updatedUser.put("lastName", currentUser.getlName());
                updatedUser.put("userName", currentUser.getUsername());
                updatedUser.put("password", currentUser.getPassword());
                updatedUser.put("email", currentUser.getEmail());
                updatedUser.put("userType", currentUser.getUserType());
                updatedUser.put("favoriteFood", currentUser.getFavFood());
                updatedUser.put("favoriteRestaurant", currentUser.getFavRestaurant());
                updatedUser.put("favoriteDrink", currentUser.getFavDrink());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            reqQueue = Volley.newRequestQueue(this);


            String url = URLs.URL_UPDATE_USER_INFO + currentUser.getId();

            // Update user on server and start profile activity
            JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, updatedUser, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Log.d("Response", response);
                    User currentUser;
                    try {
                        currentUser = new User(updatedUser.getInt("id"), updatedUser.getString("userName"), updatedUser.getString("email"),
                                updatedUser.getString("password"), updatedUser.getString("favoriteFood"), updatedUser.getString("favoriteDrink"),
                                updatedUser.getString("favoriteRestaurant"), updatedUser.getString("firstName"), updatedUser.getString("lastName"),
                                updatedUser.getString("userType"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        currentUser = new User(-1, "null", "null", "null", "null", "null", "null", "null", "null", "null");
                    }

                    // Maybe need to change sharedprefmanager value here
                    SharedPrefManager.getInstance(context).loginInfo(currentUser);

                    // Create new profile activity with new changes
                    Intent profileActivity = new Intent(context, ProfileActivity.class);
                    profileActivity.putExtra("user", currentUser);
                    startActivity(profileActivity);

                    // Kill previous activity
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    // Deletes Edit Profile Activity
                    finish();


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Log.d("Error", error);
                    //Toast.makeText(context, "String Response : Uh oh, something went wrong").show();
                    String l = "";
                }
            });

            reqQueue.add(putRequest);

        } else {
            conPassWord.setError("Passwords do not match");
            conPassWord.requestFocus();
            return;
        }
    }
}