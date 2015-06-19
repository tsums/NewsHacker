package com.tsums.newshacker.dependencies;

import android.content.Context;

import com.tsums.newshacker.NHApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Trevor on 6/19/2015.
 */
@Module
public class NHModule {

    private Context context;

    public NHModule (NHApplication application) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideApplicationContext () {
        return context;
    }

}
