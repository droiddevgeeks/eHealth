package com.andesfit.android.fragments.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.andesfit.android.R;
import com.andesfit.android.util.HealthSharedPreference;

/**
 * Created by Vampire on 2017-05-25.
 */

public class ProfileWeightFragment extends Fragment implements View.OnClickListener
{

    private EditText weightInput;
    private EditText waistInput;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.profile_setting_5,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init()
    {
        FrameLayout next = (FrameLayout)getView().findViewById(R.id.frameNext);
        FrameLayout previous = (FrameLayout)getView().findViewById(R.id.framePrevious);

        weightInput = (EditText) getView().findViewById(R.id.weightInput);
        waistInput = (EditText) getView().findViewById(R.id.waistInput);
        TextView skip = (TextView)getView().findViewById(R.id.profileSkip);
        skip.setVisibility(View.VISIBLE);

        next.setOnClickListener(this);
        previous.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.frameNext:
                saveUserData();
                profileCompleted();
                break;
            case R.id.framePrevious:
                getFragmentManager().popBackStack();
                break;
        }
    }

    private void saveUserData() {
        HealthSharedPreference preference = HealthSharedPreference.getInstance(getContext());
        preference.setWeight(weightInput.getText().toString());
        preference.setWaist(weightInput.getText().toString());
    }

    private void profileCompleted()
    {
        getFragmentManager().beginTransaction().add(R.id.container, new ProfileSelfieFragment()).addToBackStack(null).commit();
    }
}
