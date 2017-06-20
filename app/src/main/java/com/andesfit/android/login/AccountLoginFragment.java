package com.andesfit.android.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andesfit.android.activities.MainActivity;
import com.andesfit.android.R;
import com.andesfit.android.util.HealthSharedPreference;

/**
 * Created by Vampire on 2017-05-25.
 */

public class AccountLoginFragment extends Fragment implements  View.OnClickListener
{

    private EditText mobile, password;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return  inflater.inflate(R.layout.account_login_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init()
    {
        TextView title = (TextView)getView().findViewById(R.id.profileSetting);
        title.setText(getContext().getResources().getString(R.string.login));

        mobile = (EditText)getView().findViewById(R.id.edtMobile);
        password = (EditText)getView().findViewById(R.id.edtPassword);


        ImageView backArrow = (ImageView)getView().findViewById(R.id.backArrow);
        backArrow.setVisibility(View.VISIBLE);
        backArrow.setOnClickListener(this);

        TextView login = (TextView)getView().findViewById(R.id.btnLogin);
        login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnLogin:
                if(login())
                {
                    openMainActivity();
                }
                else
                {
                    Toast.makeText(getContext() , "Please enter correct credentials", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.backArrow:
                getFragmentManager().popBackStack();
                break;
        }
    }

    private boolean login()
    {
        String mob = mobile.getText().toString();
        String pass = password.getText().toString();

        HealthSharedPreference preference = HealthSharedPreference.getInstance(getContext());

        String saveMobile = preference.getMobileNumber();
        String savePass = preference.getPassword();

        if( mob.length() <10 || pass.length() ==0)
        {
            Toast.makeText(getContext(), "Please enter correct value",Toast.LENGTH_SHORT).show();
            return  false;
        }

        if( mob.equalsIgnoreCase(saveMobile) && pass.equalsIgnoreCase(savePass))
        {
            return  true;
        }
        return  false;

    }

    private void openMainActivity()
    {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
