package com.andesfit.android.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andesfit.android.R;
import com.andesfit.android.util.HealthSharedPreference;

/**
 * Created by Vampire on 2017-05-24.
 */

public class CreateAccountFragment extends Fragment implements View.OnClickListener {

    private EditText mobile, password, cnfPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_account_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        TextView title = (TextView) getView().findViewById(R.id.profileSetting);
        title.setText(getContext().getResources().getString(R.string.create_acc));

        mobile = (EditText) getView().findViewById(R.id.edtMobile);
        password = (EditText) getView().findViewById(R.id.edtPassword);
        cnfPassword = (EditText) getView().findViewById(R.id.edtCnfPassword);

        ImageView backArrow = (ImageView) getView().findViewById(R.id.backArrow);
        backArrow.setVisibility(View.VISIBLE);
        backArrow.setOnClickListener(this);
        TextView submit = (TextView) getView().findViewById(R.id.btnSubmit);
        submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                if (checkInputField()) {
                    createProfileFragment();
                }
                break;
            case R.id.backArrow:
                getFragmentManager().popBackStack();
                break;
        }
    }

    boolean checkInputField() {
        if (mobile.getText().length() < 10 || cnfPassword.getText().length() == 0 || password.getText().length() == 0) {
            Snackbar.make(cnfPassword, "Please enter correct value", Snackbar.LENGTH_LONG).show();
//            Toast.makeText(getContext() , "Please enter correct value", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.getText().toString().equalsIgnoreCase(cnfPassword.getText().toString())) {
            Toast.makeText(getContext(), "Please enter same password", Toast.LENGTH_SHORT).show();
            return false;
        }
        saveUserData();
        return true;
    }

    private void saveUserData() {
        HealthSharedPreference preference = HealthSharedPreference.getInstance(getContext());
        preference.setMobileNumber(mobile.getText().toString());
        preference.setPassword(password.getText().toString());
    }

    private void createProfileFragment() {
        getFragmentManager().beginTransaction().add(R.id.container, new ProfileSettingFragment()).addToBackStack(null).commit();
    }
}
