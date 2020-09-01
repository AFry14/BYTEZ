package com.example.helloworldexperiment;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void helloWorld(View v) {
        View button = findViewById(R.id.button);
        button.setEnabled(false);

        button = findViewById(R.id.hello);
        TextView hello = (TextView) button;
        hello.setTextSize(64);
        hello.setTextColor(Color.BLUE);
        hello.setText("Hello world!");
    }
}