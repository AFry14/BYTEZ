package com.example.Bytez_frontend;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * class that creates a single request queue to be used by volley throughout the app
 */
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

    /**
     * return the instance if one has been created, if not create one and return it
     * @param context
     * @return
     */
    public static synchronized SingletonVolley getInstance(Context context)
    {
        if(svin == null)
        {
            svin = new SingletonVolley(context);
        }
        return svin;
    }

    /**
     * return the request queue
     * @return
     */
    public RequestQueue getReqque()
    {
        if(reqque ==null)
        {
            reqque = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return reqque;
    }

    /**
     * add to request queue
     * @param req
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req)
    {
        getReqque().add(req);
    }

}
