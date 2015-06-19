package com.tsums.newshacker;

import android.app.Application;

import com.tsums.newshacker.dependencies.DaggerNHComponent;
import com.tsums.newshacker.dependencies.NHComponent;
import com.tsums.newshacker.dependencies.NHModule;

/**
 * Created by Trevor on 6/19/2015.
 */
public class NHApplication extends Application {

    private NHComponent mComponent;

    public NHComponent getmComponent () {
        return mComponent;
    }

    @Override
    public void onCreate () {

        super.onCreate();

        mComponent = DaggerNHComponent.builder().nHModule(new NHModule(this)).build();

    }
}
