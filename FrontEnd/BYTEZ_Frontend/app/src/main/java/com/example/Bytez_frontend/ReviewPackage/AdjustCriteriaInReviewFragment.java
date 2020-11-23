package com.example.Bytez_frontend.ReviewPackage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.User;

/**
 * Fragment that adjusts the criteria for the review in the review activity
 */
public class AdjustCriteriaInReviewFragment extends Fragment
{
    Context mCtx;
    User user = SharedPrefManager.getInstance(mCtx).getUser();
    private int food, service, clean;
    private SeekBar foodScale, serviceScale, cleanScale;

    /**
     * get the context form the activity
     * @param context
     */
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if(context instanceof Activity)
        {
            mCtx = context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_adjust_criteria_in_review, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        foodScale = view.findViewById(R.id.foodSeek);
        foodScale.setProgress(user.getCritFood());

        food =  user.getCritFood();
        service =  user.getCritService();
        clean =  user.getCritClean();

        //if the bar is changed update the criteria so it can be used to calculate the overall score
        foodScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                User user = SharedPrefManager.getInstance(mCtx).getUser();
                if(b==true)
                {
                    //make sure that all 3 bars cannot be set to 0, if not update the bar
                    if(service+clean!=0)
                    {
                        user.setCritFood(i);
                        food = i;
                    }
                    else
                    {
                        //set the bar back to the crit value saved in user
                        if(seekBar.getProgress() == 0)
                        {
                            seekBar.setProgress(user.getCritFood());
                            Toast.makeText(mCtx, "All weights cannot be 0", Toast.LENGTH_SHORT);
                        }
                    }

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        serviceScale = view.findViewById(R.id.serviceSeek);
        serviceScale.setProgress(user.getCritService());

        //if the bar is changed update the criteria so it can be used to calculate the overall score
        serviceScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b==true)
                {
                    //make sure that all 3 bars cannot be set to 0, if not update the bar
                    if(food+clean!=0)
                    {
                        user.setCritService(i);
                        service = i;
                    }
                    else
                    {
                        //set the bar back to the crit value saved in user
                        if(seekBar.getProgress() == 0)
                        {
                            seekBar.setProgress(user.getCritService());
                            Toast.makeText(mCtx, "All weights cannot be 0", Toast.LENGTH_SHORT);
                        }
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cleanScale = view.findViewById(R.id.cleanSeek);
        cleanScale.setProgress(user.getCritClean());

        //if the bar is changed update the criteria so it can be used to calculate the overall score
        cleanScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b==true)
                {
                    //make sure that all 3 bars cannot be set to 0, if not update the bar
                    if(service+food!=0)
                    {
                        user.setCritClean(i);
                        clean = i;
                    }
                    else
                    {
                        //set the bar back to the crit value saved in user
                        if(seekBar.getProgress() == 0)
                        {
                            seekBar.setProgress(user.getCritClean());
                            Toast.makeText(mCtx, "All weights cannot be 0", Toast.LENGTH_SHORT);
                        }
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    protected int getFood()
    {
        return foodScale.getProgress();
    }

    protected int getService()
    {
        return serviceScale.getProgress();
    }

    protected int getClean()
    {
        return cleanScale.getProgress();
    }
}

