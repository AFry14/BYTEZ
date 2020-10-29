package com.example.Bytez_frontend.Settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.login.LoginActivity;

public class SettingsActivity extends AppCompatActivity
{
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager().beginTransaction().add(new SettingsMainFragment(), "Tag").commit();

//        findViewById(R.id.logoutB).setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                SharedPrefManager.getInstance(ctx).logout();
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//            }
//        });
//
//        findViewById(R.id.bugReportB).setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(ctx, bugReportFragment.class);
//                startActivity(intent);
//            }
//        });
//
//        findViewById(R.id.deleteReviewB).setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                SharedPrefManager.getInstance(ctx).logout();
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//            }
//        });
    }

}
