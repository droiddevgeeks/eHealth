package com.andesfit.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vampire on 2017-05-25.
 */

public class HealthSharedPreference
{

    private static HealthSharedPreference  mInstance;
    private SharedPreferences mAppSharedPrefs;
    private SharedPreferences.Editor mPrefsEditor;

    private final String USER_PREFS  = "HEALTH_PREFS";
    private final  String MOBILE = "mobile";
    private final String PASSWORD = "password";

    public static HealthSharedPreference getInstance( Context context )
    {
        if ( mInstance == null )
        {
            mInstance = new HealthSharedPreference(context);
        }
        return mInstance;
    }

     private HealthSharedPreference( Context context )
    {
        this.mAppSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.mPrefsEditor = mAppSharedPrefs.edit();
    }



    public void setMobileNumber( String mob )
    {
        mPrefsEditor.putString(MOBILE, mob).commit();
    }

    public String getMobileNumber()
    {
        return mAppSharedPrefs.getString(MOBILE, null);
    }

    public void setPassword( String password )
    {
        mPrefsEditor.putString(PASSWORD, password).commit();
    }

    public String getPassword()
    {
        return mAppSharedPrefs.getString(PASSWORD, null);
    }
}
