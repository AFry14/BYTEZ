package com.example.bytez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
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