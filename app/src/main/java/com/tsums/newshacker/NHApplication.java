package com.tsums.newshacker;

import android.app.Application;

import com.tsums.newshacker.core.DaggerNHComponent;
import com.tsums.newshacker.core.NHComponent;
import com.tsums.newshacker.core.NHDebugModule;
import com.tsums.newshacker.core.NHModule;

import timber.log.Timber;

/**
 * Application class where we can instantiate runtime needs like Dagger injection.
 */
public class NHApplication extends Application {

    private NHComponent mComponent;

    public NHComponent getmComponent () {
        return mComponent;
    }

    @Override
    public void onCreate () {

        super.onCreate();

        if (BuildConfig.DEBUG) {
            mComponent = DaggerNHComponent.builder().nHModule(new NHDebugModule(this)).build();
            Timber.plant(new Timber.DebugTree());
        } else {
            mComponent = DaggerNHComponent.builder().nHModule(new NHModule(this)).build();
        }


    }
}
