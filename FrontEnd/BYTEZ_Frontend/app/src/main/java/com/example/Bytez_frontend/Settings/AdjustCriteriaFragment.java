package com.example.Bytez_frontend.Settings;

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

public class AdjustCriteriaFragment extends Fragment
{
    Context mCtx;
    User user = SharedPrefManager.getInstance(mCtx).getUser();
    int food, service, clean;

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
        return inflater.inflate(R.layout.fragment_adjust_criteria, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);



        SeekBar foodScale = view.findViewById(R.id.foodSeek);
        foodScale.setProgress(user.getCritFood());
        food =  foodScale.getProgress();
        foodScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b==true)
                {
                    food = i;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar serviceScale = view.findViewById(R.id.serviceSeek);
        serviceScale.setProgress(user.getCritService());
        service =  serviceScale.getProgress();
        serviceScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b==true)
                {
                    service = i;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar cleanScale = view.findViewById(R.id.cleanSeek);
        cleanScale.setProgress(user.getCritClean());
        clean =  cleanScale.getProgress();
        cleanScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b==true)
                {
                    clean = i;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        view.findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(food+service+clean == 0)
                {
                    Toast.makeText(mCtx, "All weights cannot be 0", Toast.LENGTH_SHORT).show();
                }
                else {
                    user.setCritFood(food);
                    user.setCritService(service);
                    user.setCritClean(clean);
                    NavHostFragment.findNavController(AdjustCriteriaFragment.this)
                            .navigate(R.id.action_AdjustCriteriaFragment_to_SettingsMainFragment);
                }

            }
        });

    }
}
