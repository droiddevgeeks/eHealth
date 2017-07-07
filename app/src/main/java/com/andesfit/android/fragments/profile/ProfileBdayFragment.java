package com.andesfit.android.fragments.profile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.andesfit.android.R;
import com.andesfit.android.util.HealthSharedPreference;

/**
 * Created by Vampire on 2017-05-25.
 */

public class ProfileBdayFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener
{

    EditText dobInput;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.profile_setting_4, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init()
    {
        FrameLayout next = (FrameLayout) getView().findViewById(R.id.frameNext);
        FrameLayout previous = (FrameLayout) getView().findViewById(R.id.framePrevious);
        dobInput = (EditText) getView().findViewById(R.id.dobInput);
        dobInput.setOnClickListener(this);
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
                createHeightProfile();
                break;
            case R.id.framePrevious:
                getFragmentManager().popBackStack();
                break;
            case R.id.dobInput:
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), ProfileBdayFragment.this, 2017, 05, 01);
                datePickerDialog.show();
                break;
        }
    }

    private void saveUserData()
    {
        HealthSharedPreference preference = HealthSharedPreference.getInstance(getContext());
        preference.setDob(dobInput.getText().toString());
    }


    private void createHeightProfile()
    {
        getFragmentManager().beginTransaction().replace(R.id.container, new ProfileHeightFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day)
    {
        dobInput.setText(day + "/" + month + "/" + year);
    }
}
