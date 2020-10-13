package com.example.Bytez_frontend.ReviewPackage;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Bytez_frontend.R;

public class ReviewActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                RatingBar foodBar = (RatingBar) findViewById(R.id.foodBar);
                RatingBar serviceBar = (RatingBar) findViewById(R.id.serviceBar);
                RatingBar cleanBar = (RatingBar) findViewById(R.id.cleanlinessBar);
                EditText commentText = (EditText) findViewById(R.id.commentSection);
                float foodS = foodBar.getRating();
                float serviceS = serviceBar.getRating();
                float cleanS = cleanBar.getRating();
                String comments = commentText.getText().toString();

                //to do: post request for review
            }
        });
    }

}
