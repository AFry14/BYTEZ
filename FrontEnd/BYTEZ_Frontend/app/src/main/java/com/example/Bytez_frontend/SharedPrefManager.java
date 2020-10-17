package com.example.Bytez_frontend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.Bytez_frontend.login.LoginActivity;

public class SharedPrefManager
{
    private static final String SHARED_PREF_NAME = "bytezsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_ID = "keyid";

    private static SharedPrefManager spm;
    private static Context ctx;


    private SharedPrefManager(Context context)
    {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context)
    {
        if(spm == null)
        {
            spm = new SharedPrefManager(context);
        }
        return spm;
    }

    public void loginInfo(User user)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.apply();
    }

    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_EMAIL, null) != null)
        {
            return false;
        }
        return false;
    }

    public User getUser()
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        User retUser = new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null)
        );
        return retUser;
    }

    public void logout()
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LoginActivity.class));
    }


}
