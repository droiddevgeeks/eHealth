package com.andesfit.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Vampire on 2017-05-25.
 */

public class HealthSharedPreference {

    private static HealthSharedPreference mInstance;
    private final String USER_PREFS = "HEALTH_PREFS";
    private final String MOBILE = "mobile";
    private final String PASSWORD = "password";
    private final String LNAME = "lname";
    private final String FNAME = "fname";
    private final String HEIGHT = "height";
    private final String WEIGHT = "weight";
    private final String WAIST = "waist";
    private final String DOB = "dob";
    private SharedPreferences mAppSharedPrefs;
    private SharedPreferences.Editor mPrefsEditor;

    private HealthSharedPreference(Context context) {
        this.mAppSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.mPrefsEditor = mAppSharedPrefs.edit();
    }

    public static HealthSharedPreference getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HealthSharedPreference(context);
        }
        return mInstance;
    }

    public String getMobileNumber() {
        return mAppSharedPrefs.getString(MOBILE, null);
    }

    public void setMobileNumber(String mob) {
        mPrefsEditor.putString(MOBILE, mob).commit();
    }

    public String getPassword() {
        return mAppSharedPrefs.getString(PASSWORD, null);
    }

    public void setPassword(String password) {
        mPrefsEditor.putString(PASSWORD, password).commit();
    }

    public String getFName() {
        return mAppSharedPrefs.getString(FNAME, "EHealth");
    }

    public void setFName(String mob) {
        mPrefsEditor.putString(FNAME, mob).commit();
    }

    public String getLName() {
        return mAppSharedPrefs.getString(LNAME, "");
    }

    public void setLName(String mob) {
        mPrefsEditor.putString(LNAME, mob).commit();
    }

    public String getDob() {
        return mAppSharedPrefs.getString(DOB, "01/05/2017");
    }

    public void setDob(String dob) {
        mPrefsEditor.putString(DOB, dob).commit();
    }

    public String getHeight() {
        return mAppSharedPrefs.getString(HEIGHT, "");
    }

    public void setHeight(String dob) {
        mPrefsEditor.putString(HEIGHT, dob).commit();
    }

    public String getWeight() {
        return mAppSharedPrefs.getString(WEIGHT, "");
    }

    public void setWeight(String dob) {
        mPrefsEditor.putString(WEIGHT, dob).commit();
    }

    public String getWaist() {
        return mAppSharedPrefs.getString(WAIST, "");
    }

    public void setWaist(String dob) {
        mPrefsEditor.putString(WAIST, dob).commit();
    }
}
