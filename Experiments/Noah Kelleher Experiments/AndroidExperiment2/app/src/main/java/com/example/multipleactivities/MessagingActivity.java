package com.example.multipleactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MessagingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        setTitle("Messaging");

        //Take data from homepage
        Intent i = getIntent();
        String data = i.getStringExtra("DATA");
        ((TextView) findViewById(R.id.toText)).setText(data);
    }
}