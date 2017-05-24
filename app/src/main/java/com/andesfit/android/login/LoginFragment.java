package com.andesfit.android.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andesfit.android.profile.CreateAccountFragment;
import com.andesfit.android.R;

/**
 * Created by Vampire on 2017-05-24.
 */

public class LoginFragment extends Fragment implements View.OnClickListener
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return  inflater.inflate(R.layout.common_login_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init()
    {
        TextView createAccount = (TextView)getView().findViewById(R.id.btnCreateAccount);
        createAccount.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnCreateAccount:
                createHealthAccount();
                break;
        }
    }

    private void createHealthAccount()
    {
        getFragmentManager().beginTransaction().add(R.id.container, new CreateAccountFragment()).addToBackStack(null).commit();
    }
}
