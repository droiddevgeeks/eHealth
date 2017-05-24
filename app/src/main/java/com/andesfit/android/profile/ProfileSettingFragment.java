package com.andesfit.android.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andesfit.android.R;

/**
 * Created by Vampire on 2017-05-24.
 */

public class ProfileSettingFragment extends Fragment implements View.OnClickListener
{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.profile_setting_1,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init()
    {
        TextView start = (TextView)getView().findViewById(R.id.btnStart);
        start.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnStart:
                createProfile();
                break;
        }
    }

    private void createProfile()
    {
        getFragmentManager().beginTransaction().add(R.id.container, new ProfileNameFragment()).addToBackStack(null).commit();
    }
}
