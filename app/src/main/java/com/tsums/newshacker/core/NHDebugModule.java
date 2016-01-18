package com.tsums.newshacker.core;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.tsums.newshacker.NHApplication;
import com.tsums.newshacker.R;
import com.tsums.newshacker.network.HNConnector;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Trevor on 6/20/2015.
 */
public class NHDebugModule extends NHModule {


    public NHDebugModule (NHApplication application) {
        super(application);
    }

    @Override
    HNConnector provideHNConnector (Context context, OkHttpClient okHttpClient) {
        RestAdapter.Builder mBuilder = new RestAdapter.Builder();
        mBuilder.setEndpoint(context.getString(R.string.hn_api_backend_path));
        mBuilder.setLogLevel(RestAdapter.LogLevel.FULL);
        mBuilder.setClient(new OkClient(okHttpClient));
        return mBuilder.build().create(HNConnector.class);
    }
}
