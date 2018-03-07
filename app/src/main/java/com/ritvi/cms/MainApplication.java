package com.ritvi.cms;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by sunil on 04-02-2018.
 */

public class MainApplication extends Application {
    LocalizationApplicationDelegate localizationDelegate = new LocalizationApplicationDelegate(this);

    @Override
    public void onCreate() {
        super.onCreate();
        final TwitterAuthConfig authConfig = new TwitterAuthConfig("odTUxR2y7jhDIb1ImhiGE4VDY", "FFKtAo7BeyDoEoUeRXZUq1FwHAjCHutOXZc4gcimEmG4cOMWKV");

        Fabric.with(this, new Twitter(authConfig));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(localizationDelegate.attachBaseContext(base));
        MultiDex.install(this);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        localizationDelegate.onConfigurationChanged(this);
    }

    @Override
    public Context getApplicationContext() {
        return localizationDelegate.getApplicationContext(super.getApplicationContext());
    }

}
