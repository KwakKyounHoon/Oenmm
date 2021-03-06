package com.onemeter.omm.onemm.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.onemeter.omm.onemm.MyApplication;


/**
 * Created by Tacademy on 2016-08-10.
 */
public class PropertyManager {
    private static PropertyManager instance;
    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REGISTRATION_ID ="regid";
    private static final String KEY_FACEBOOK_ID = "facebookid";
    private static final String KEY_MY_ID = "myid";
    private static final String KEY_SWITCH_ANSWER = "answer";
    private static final String KEY_SWITCH_QUESTION = "question";
    private static final String KEY_SWITCH_like = "like";

    private PropertyManager() {
        Context context = MyApplication.getContext();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPrefs.edit();
    }


    public void setMyId(String facebookid) {
        mEditor.putString(KEY_MY_ID, facebookid);
        mEditor.commit();
    }

    public String getMyId() {
        return mPrefs.getString(KEY_MY_ID, "");
    }

    public void setRegistrationId(String regid) {
        mEditor.putString(KEY_REGISTRATION_ID, regid);
        mEditor.commit();
    }

    public String getRegistrationId() {
        return mPrefs.getString(KEY_REGISTRATION_ID, "");
    }

    public void setFacebookId(String facebookid) {
        mEditor.putString(KEY_FACEBOOK_ID, facebookid);
        mEditor.commit();
    }

    public String getFacebookId() {
        return mPrefs.getString(KEY_FACEBOOK_ID, "");
    }

    public void setAnswerSwitch(String onOff) {
        mEditor.putString(KEY_SWITCH_ANSWER, onOff);
        mEditor.commit();
    }

    public String getAnswerSwitch() {
        return mPrefs.getString(KEY_SWITCH_ANSWER, "1");
    }

    public void setQuestionSwitch(String onOff) {
        mEditor.putString(KEY_SWITCH_QUESTION, onOff);
        mEditor.commit();
    }

    public String getQuestionSwitch() {
        return mPrefs.getString(KEY_SWITCH_QUESTION, "1");
    }

    public void setLikeSwitch(String onOff) {
        mEditor.putString(KEY_SWITCH_like, onOff);
        mEditor.commit();
    }

    public String getLikeSwitch() {
        return mPrefs.getString(KEY_SWITCH_like, "1");
    }


}
