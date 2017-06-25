package com.andesfit.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andesfit.android.R;
import com.andesfit.android.util.HealthSharedPreference;

/**
 * Created by Vampire on 2017-06-08.
 */

public class BodyStatsFragment extends Fragment
{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.body_stats_layout, container, false);
        HealthSharedPreference preference = HealthSharedPreference.getInstance(getActivity());
        ((TextView) view.findViewById(R.id.bs_user_name)).setText(preference.getFName());

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

    }
}
