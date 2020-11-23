package com.example.Bytez_frontend.Features;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.User;

public class MyReviewsActivity extends AppCompatActivity
{
    Context ctx = this;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reviews);
        setTitle("MyReviews");

        currentUser = SharedPrefManager.getInstance(this).getUser();
    }
}
