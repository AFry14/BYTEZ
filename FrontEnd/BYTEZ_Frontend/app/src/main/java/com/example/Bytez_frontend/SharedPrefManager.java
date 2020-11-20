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
    private static final String KEY_ID = "id";
    private static final String KEY_PASSWORD = "keypassword";
    private static final String KEY_FAVFOOD = "keyfavfood";
    private static final String KEY_FAVDRINK = "keyfavdrink";
    private static final String KEY_FAVREST = "keyfavrest";
    private static final String KEY_FNAME = "keyfname";
    private static final String KEY_LNAME = "keylname";
    private static final String KEY_USERTYPE = "keyusertype";
    private static final String KEY_CRIT_FOOD = "keycritfood";
    private static final String KEY_CRIT_SERVICE = "keycritservice";
    private static final String KEY_CRIT_CLEAN = "keycritclean";
    private static final String KEY_HELPFULS = "keyhelpful";
    private static final String KEY_AGREES = "keyagree";
    private static final String KEY_DISAGREES = "keydisagree";


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

//    public void updateWeights(User user)
//    {
//        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt(KEY_CRIT_FOOD, user.getCritFood());
//        editor.putInt(KEY_CRIT_SERVICE, user.getCritService());
//        editor.putInt(KEY_CRIT_CLEAN, user.getCritClean());
//        editor.apply();
//    }

    public void loginInfo(User user)
    {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_FAVFOOD, user.getFavFood());
        editor.putString(KEY_FAVDRINK, user.getFavDrink());
        editor.putString(KEY_FAVREST, user.getFavRestaurant());
        editor.putString(KEY_FNAME, user.getfName());
        editor.putString(KEY_LNAME, user.getlName());
        editor.putString(KEY_USERTYPE, user.getUserType());
        editor.putInt(KEY_CRIT_FOOD, user.getCritFood());
        editor.putInt(KEY_CRIT_SERVICE, user.getCritService());
        editor.putInt(KEY_CRIT_CLEAN, user.getCritClean());
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
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PASSWORD, null),
                sharedPreferences.getString(KEY_FAVFOOD, null),
                sharedPreferences.getString(KEY_FAVDRINK, null),
                sharedPreferences.getString(KEY_FAVREST, null),
                sharedPreferences.getString(KEY_FNAME, null),
                sharedPreferences.getString(KEY_LNAME, null),
                sharedPreferences.getString(KEY_USERTYPE, null),
                sharedPreferences.getInt(KEY_CRIT_FOOD, 1),
                sharedPreferences.getInt(KEY_CRIT_SERVICE, 1),
                sharedPreferences.getInt(KEY_CRIT_CLEAN, 1)
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