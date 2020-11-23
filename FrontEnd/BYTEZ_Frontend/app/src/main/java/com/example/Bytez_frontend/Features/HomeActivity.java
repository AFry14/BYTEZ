package com.example.Bytez_frontend.Features;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Review;
import com.example.Bytez_frontend.Settings.SettingsReviewRecyclerAdapter;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.SingletonVolley;
import com.example.Bytez_frontend.URLs;
import com.example.Bytez_frontend.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import com.example.Bytez_frontend.ReviewPackage.ReviewActivity;
import com.example.Bytez_frontend.Settings.SettingsActivity;
import com.example.Bytez_frontend.login.SignUp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    Context ctx = this;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        currentUser = SharedPrefManager.getInstance(this).getUser();

        /**
         * Launch settings activity
         * @param v
         */
        findViewById(R.id.settingsB).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            }
        });

//        findViewById(R.id.reviewButton).setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                startActivity(new Intent(getApplicationContext(), ReviewActivity.class));
//            }
//        });

    }

    /**
     * Launch map activity
     * @param v
     */
    public void launchMap(View v) {
        Intent mapActivity = new Intent(this, MapActivity.class);
        startActivity(mapActivity);
    }

    /**
     * Launch profile activity
     * @param v
     */
    public void launchProfile(View v) {
        Intent profileActivity = new Intent(this, ProfileActivity.class);
        startActivity(profileActivity);
    }

    /**
     * Launch friends activity
     * @param v
     */
    public void launchFriends(View v) {
        Intent friendsActivity = new Intent(this, FriendsActivity.class);
        friendsActivity.putExtra("user", currentUser);
        startActivity(friendsActivity);
    }

    /**
     * Launch review activity
     * @param v
     */
    public void launchReviewSubmit(View v) {
        Intent ReviewActivity = new Intent(this, ReviewActivity.class);
        startActivity(ReviewActivity);
    }

    public void launchMessageFriends(View v) {
        Intent messageFriendsActivity = new Intent(this, MessageFriendsActivity.class);
        messageFriendsActivity.putExtra("user", currentUser);
        startActivity(messageFriendsActivity);
    }

}