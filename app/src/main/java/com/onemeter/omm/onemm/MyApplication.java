package com.onemeter.omm.onemm;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.tsengvn.typekit.Typekit;

/**
 * Created by Tacademy on 2016-08-09.
 */
public class MyApplication extends Application {
    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NanumSquareR.ttf"))
                .addBold(Typekit.createFromAsset(this, "NanumSquareB.ttf"));

        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
