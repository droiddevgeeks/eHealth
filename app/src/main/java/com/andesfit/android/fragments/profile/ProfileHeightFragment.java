package com.andesfit.android.fragments.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.andesfit.android.R;
import com.andesfit.android.util.HealthSharedPreference;

/**
 * Created by Vampire on 2017-05-25.
 */

public class ProfileHeightFragment extends Fragment implements View.OnClickListener
{

    EditText heightInput;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.profile_setting_3,container,false);
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
        heightInput = (EditText) getView().findViewById(R.id.height);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
    }

    private void saveUserData() {
        HealthSharedPreference preference = HealthSharedPreference.getInstance(getContext());
        preference.setHeight(heightInput.getText().toString());
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.frameNext:
                saveUserData();
                createWeightProfile();
                break;
            case R.id.framePrevious:
                getFragmentManager().popBackStack();
                break;
        }
    }

    private void createWeightProfile()
    {
        getFragmentManager().beginTransaction().replace(R.id.container, new ProfileWeightFragment()).addToBackStack(null).commit();
    }
}
