package com.tsums.newshacker.dependencies;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.tsums.newshacker.NHApplication;
import com.tsums.newshacker.R;
import com.tsums.newshacker.network.HNConnector;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Dagger 2 starter module. Will most likely expand to a multi-module architecture later in development.
 */
@Module
public class NHModule {

    private Context context;

    /**
     * Strictly enforce that only the NHApplication should be the source of context for this module.
     *
     * @param application to provide context
     */
    public NHModule (NHApplication application) {
        this.context = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext () {
        return context;
    }

    @Provides
    @Singleton
    HNConnector provideHNConnector (Context context, OkHttpClient okHttpClient) {
        RestAdapter.Builder mBuilder = new RestAdapter.Builder();
        mBuilder.setEndpoint(context.getString(R.string.hn_api_backend_path));
        mBuilder.setLogLevel(RestAdapter.LogLevel.FULL); //TODO disable this in a release build
        mBuilder.setClient(new OkClient(okHttpClient));
        return mBuilder.build().create(HNConnector.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkClient () {
        OkHttpClient client = new OkHttpClient();
        return client;
    }

}
