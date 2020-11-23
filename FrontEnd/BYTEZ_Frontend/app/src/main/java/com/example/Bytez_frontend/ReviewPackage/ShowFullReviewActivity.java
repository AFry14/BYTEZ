package com.example.Bytez_frontend.ReviewPackage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.Bytez_frontend.Features.HomeActivity;
import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.Restaurant;
import com.example.Bytez_frontend.SingletonVolley;
import com.example.Bytez_frontend.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class ShowFullReviewActivity extends AppCompatActivity
{
    Context ctx = this;
    TextView title;
    TextView comments;
    RatingBar overallBar;
    RatingBar foodBar;
    RatingBar serviceBar;
    RatingBar cleanBar;
    TextView helpfuls, agrees, disagrees;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_review);
        title = (TextView) findViewById(R.id.Title);
        comments = (TextView) findViewById(R.id.Comments);
        overallBar = (RatingBar) findViewById(R.id.oScore);
        foodBar = (RatingBar) findViewById(R.id.fScore);
        serviceBar = (RatingBar) findViewById(R.id.sScore);
        cleanBar = (RatingBar) findViewById(R.id.cScore);
//        helpfuls = (TextView) findViewById(R.id.helpfuls);
//        agrees = (TextView) findViewById(R.id.agrees);
//        disagrees = (TextView) findViewById(R.id.disagrees);

        //get the review id from a bundle passed by the last activity
        int id = getIntent().getExtras().getInt("id");

        //get the review from the database
        String pass1 = URLs.URL_REVIEW_LIST + id;
        JsonObjectRequest getReviewRequest = new JsonObjectRequest(Request.Method.GET, pass1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            //set all elements in the view with the information from the get request
//                            title.setText(response.getString("userName") + "reviewed " + response.getString("restName"));
                            title.setText(response.getString("authorName") + " reviewed " + response.getString("restaurantName"));
                            BigDecimal oScore = new BigDecimal(response.getDouble("overallScore"));
                            float oValue = oScore.floatValue();
                            overallBar.setRating(oValue);
                            BigDecimal fScore = new BigDecimal(response.getDouble("foodQualityScore"));
                            float fValue = fScore.floatValue();
                            foodBar.setRating(fValue);
                            BigDecimal sScore = new BigDecimal(response.getDouble("serviceScore"));
                            float sValue = sScore.floatValue();
                            serviceBar.setRating(sValue);
                            BigDecimal cScore = new BigDecimal(response.getDouble("cleanlinessScore"));
                            float cValue = cScore.floatValue();
                            cleanBar.setRating(cValue);
                            comments.setText(response.getString("comment"));
//                            int helpfulI = response.getInt("helpful");
//                            String helpfulS = String.valueOf(helpfulI);
//                            helpfuls.setText(helpfulS);
//                            int agreeI = response.getInt("agree");
//                            String agreeS = String.valueOf(agreeI);
//                            agrees.setText(agreeS);
//                            int disagreeI = response.getInt("disagree");
//                            String disagreeS = String.valueOf(disagreeI);
//                            disagrees.setText(disagreeS);
                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                    }
                }
        );

        SingletonVolley.getInstance(ctx).addToRequestQueue(getReviewRequest);

        //return to home from this activity if button is pressed
        findViewById(R.id.backFRevTH).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });


    }



}
