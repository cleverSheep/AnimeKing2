package com.murrayde.retrofittesting.application;

import android.app.Application;

import com.bumptech.glide.request.target.ViewTarget;
import com.murrayde.retrofittesting.R;

import timber.log.Timber;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        ViewTarget.setTagId(R.id.glide_custom_view_target_tag);
    }
}
