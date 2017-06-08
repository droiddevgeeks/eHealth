package com.andesfit.android.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.andesfit.android.MainActivity;
import com.andesfit.android.R;
import com.andesfit.android.body_stats.BodyStatsFragment;

/**
 * Created by Vampire on 2017-05-25.
 */

public class ProfileSelfieFragment extends Fragment  implements View.OnClickListener
{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.profile_setting_6,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init()
    {
        TextView finish = (TextView)getView().findViewById(R.id.txtFinish);
        finish.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.txtFinish:
                profileCompleted();
                break;
        }
    }

    private void profileCompleted()
    {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
