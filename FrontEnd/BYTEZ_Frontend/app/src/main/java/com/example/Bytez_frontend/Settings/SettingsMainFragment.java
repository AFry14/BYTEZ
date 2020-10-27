package com.example.Bytez_frontend.Settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.Bytez_frontend.R;
import com.example.Bytez_frontend.SharedPrefManager;
import com.example.Bytez_frontend.login.LoginActivity;

public class SettingsMainFragment extends Fragment
{
//    Context appCtx = getActivity().getApplicationContext();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInsanceState)
    {
        return inflater.inflate(R.layout.fragment_settingsmain, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.logoutB).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
            }
        });

        view.findViewById(R.id.bugReportB).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavHostFragment.findNavController(SettingsMainFragment.this)
                        .navigate(R.id.action_SettingsMainFragment_to_bugReportFragment);
            }
        });

        view.findViewById(R.id.deleteReviewB).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavHostFragment.findNavController(SettingsMainFragment.this)
                        .navigate(R.id.action_SettingsMainFragment_to_DeleteReviewFragment);
            }
        });

    }


}
