package com.andesfit.android.fragments.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

public class ProfileNameFragment extends Fragment implements View.OnClickListener

{
    private EditText fNameInput;
    private EditText lNameInput;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.profile_setting_2,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        fNameInput = (EditText) view.findViewById(R.id.fName);
        lNameInput = (EditText) view.findViewById(R.id.lName);
        init();

    }

    private void init()
    {
        FrameLayout next = (FrameLayout)getView().findViewById(R.id.frameNext);
        FrameLayout previous = (FrameLayout)getView().findViewById(R.id.framePrevious);

        next.setOnClickListener(this);
        previous.setOnClickListener(this);
     }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.frameNext:
                if (validateName()) {
                    saveUserData();
                    createBdayProfile();
                } else {
                    Snackbar.make(fNameInput, "First Name Should not be empty", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.framePrevious:
                getFragmentManager().popBackStack();
                break;
        }
    }

    private boolean validateName() {
        return fNameInput.getText().toString().length() > 0;
    }

    private void saveUserData() {
        HealthSharedPreference preference = HealthSharedPreference.getInstance(getContext());
        preference.setFName(fNameInput.getText().toString());
        preference.setLName(lNameInput.getText().toString());
    }

    private void createBdayProfile()
    {
        getFragmentManager().beginTransaction().replace(R.id.container, new ProfileBdayFragment()).addToBackStack(null).commit();
    }
}
