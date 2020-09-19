package com.example.login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View v = findViewById(R.id.SignupB);
        v.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.SignupB)
        {
            Intent intent = new Intent(this, SignUp.class);
            this.startActivity(intent);
        }

    }
}
