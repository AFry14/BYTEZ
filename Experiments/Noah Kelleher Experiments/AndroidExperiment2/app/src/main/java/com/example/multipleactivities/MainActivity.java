package com.example.multipleactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Activity");
    }

    public void toSettings (View v) {
        Intent newActivity = new Intent(this, SettingsActivity.class);

        String data = ((EditText) findViewById(R.id.editText)).getText().toString();
        newActivity.putExtra("DATA", data);
        startActivity(newActivity);
    }

    public void toProfile (View v) {
        Intent newActivity = new Intent(this, ProfileActivity.class);

        String data = ((EditText) findViewById(R.id.editText)).getText().toString();
        newActivity.putExtra("DATA", data);
        startActivity(newActivity);
    }

    public void toMessaging(View v) {
        Intent newActivity = new Intent(this, MessagingActivity.class);

        String data = ((EditText) findViewById(R.id.editText)).getText().toString();
        newActivity.putExtra("DATA", data);
        startActivity(newActivity);
    }
}