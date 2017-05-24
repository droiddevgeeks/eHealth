package com.andesfit.android.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andesfit.android.R;
import com.andesfit.android.profile.ProfileSettingFragment;

/**
 * Created by Vampire on 2017-05-24.
 */

public class CreateAccountFragment extends Fragment  implements View.OnClickListener
{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return  inflater.inflate(R.layout.create_account_layout, container, false);
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
        title.setText(getContext().getResources().getString(R.string.create_acc));

        ImageView backArrow = (ImageView)getView().findViewById(R.id.backArrow);
        backArrow.setVisibility(View.VISIBLE);
        TextView submit = (TextView)getView().findViewById(R.id.btnSubmit);
        submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnSubmit:
                createProfileFragment();
                break;
        }
    }

    private void createProfileFragment()
    {
        getFragmentManager().beginTransaction().add(R.id.container, new ProfileSettingFragment()).addToBackStack(null).commit();
    }
}
