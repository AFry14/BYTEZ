package com.example.Bytez_frontend.Map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Settings.SettingsActivity;
import com.example.Bytez_frontend.login.SignUp;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");

        findViewById(R.id.settingsB).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            }
        });
    }

    /**
     * Launch map activity
     * @param v
     */
    public void launchMap(View v) {
        Intent mapActivity = new Intent(this, MapActivity.class);
        startActivity(mapActivity);
    }
}