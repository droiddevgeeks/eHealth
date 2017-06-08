package com.andesfit.android.login;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.andesfit.android.R;
import com.andesfit.android.body_stats.BodyStatsFragment;

/**
 * Created by Vampire on 2017-05-24.
 */

public class LoginActivity extends AppCompatActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                callLoginFragment();
            }
        }, 1000);
    }

    void callLoginFragment()
    {
        getSupportFragmentManager().beginTransaction().add(R.id.container, new LoginFragment()).addToBackStack(null).commit();
    }

}
