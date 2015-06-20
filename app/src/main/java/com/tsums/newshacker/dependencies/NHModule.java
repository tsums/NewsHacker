package com.tsums.newshacker.dependencies;

import android.content.Context;

import com.tsums.newshacker.NHApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger 2 starter module. Will most likely expand to a multi-module architecture later in development.
 */
@Module
public class NHModule {

    private Context context;

    public NHModule (NHApplication application) {
        this.context = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext () {
        return context;
    }

}
