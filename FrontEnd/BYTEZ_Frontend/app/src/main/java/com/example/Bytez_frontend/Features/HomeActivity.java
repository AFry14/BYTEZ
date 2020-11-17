package com.example.Bytez_frontend.Features;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.User;

import java.util.Map;

import com.example.Bytez_frontend.ReviewPackage.ReviewActivity;
import com.example.Bytez_frontend.Settings.SettingsActivity;
import com.example.Bytez_frontend.login.SignUp;

public class HomeActivity extends AppCompatActivity {

    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        currentUser = SharedPrefManager.getInstance(this).getUser();

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

    public void launchReviewSubmit(View v) {
        Intent ReviewActivity = new Intent(this, ReviewActivity.class);
//        friendsActivity.putExtra("user", currentUser);
        startActivity(ReviewActivity);
    }

}
