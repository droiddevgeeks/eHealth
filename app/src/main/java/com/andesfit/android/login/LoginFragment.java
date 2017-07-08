package com.andesfit.android.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andesfit.android.activities.MainActivity;
import com.andesfit.android.fragments.profile.CreateAccountFragment;
import com.andesfit.android.R;
import com.andesfit.android.util.HealthSharedPreference;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by Vampire on 2017-05-24.
 */

public class LoginFragment extends Fragment implements View.OnClickListener
{
    private CallbackManager callbackManager;
    private LoginButton fbLogin;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.common_login_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init()
    {
        fbLogin = (LoginButton) getView().findViewById(R.id.login_button);
        fbLogin.setReadPermissions("public_profile email");
        fbLogin.setFragment(this);
        fbLogin.registerCallback(callbackManager, facebookCallback);

        TextView createAccount = (TextView) getView().findViewById(R.id.btnCreateAccount);
        createAccount.setOnClickListener(this);

        TextView btnLogin = (TextView) getView().findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnCreateAccount:
                createHealthAccount();
                break;
            case R.id.btnLogin:
                login();
        }
    }

    private FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>()
    {
        @Override
        public void onSuccess(LoginResult loginResult)
        {
            if(AccessToken.getCurrentAccessToken() !=null)
            {
                Toast.makeText(getContext(), "Name: " + loginResult.getAccessToken().getUserId(), Toast.LENGTH_LONG).show();
            }

            HealthSharedPreference preference = HealthSharedPreference.getInstance(getContext());
            preference.setFbProfileID(loginResult.getAccessToken().getUserId());
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        @Override
        public void onCancel()
        {

        }

        @Override
        public void onError(FacebookException e)
        {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void login()
    {
        getFragmentManager().beginTransaction().replace(R.id.container, new AccountLoginFragment()).addToBackStack(null).commit();
    }

    private void createHealthAccount()
    {
        getFragmentManager().beginTransaction().replace(R.id.container, new CreateAccountFragment()).addToBackStack(null).commit();
    }
}
