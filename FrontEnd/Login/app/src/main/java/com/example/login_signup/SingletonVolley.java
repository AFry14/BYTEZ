package com.example.login_signup;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonVolley
{
    private static SingletonVolley svin;
    private RequestQueue reqque;
    private static Context ctx;

    private SingletonVolley(Context context)
    {
        ctx = context;
        reqque = getReqque();
    }

    public static synchronized SingletonVolley getInstance(Context context)
    {
        if(svin == null)
        {
            svin = new SingletonVolley(context);
        }
        return svin;
    }

    public RequestQueue getReqque()
    {
        if(reqque ==null)
        {
            reqque = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return reqque;
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        getReqque().add(req);
    }

}
