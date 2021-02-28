package com.murrayde.animekingandroid.application;

import android.app.Application;
import android.content.res.Resources;

import com.bumptech.glide.request.target.ViewTarget;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.murrayde.animekingandroid.R;

import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;

@HiltAndroidApp
public class BaseApplication extends Application {
    private static BaseApplication mInstance;
    private static Resources res;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        res = getResources();
        Timber.plant(new Timber.DebugTree());
        ViewTarget.setTagId(R.id.glide_custom_view_target_tag);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

    public static Resources getResourses() {
        return res;
    }
}
