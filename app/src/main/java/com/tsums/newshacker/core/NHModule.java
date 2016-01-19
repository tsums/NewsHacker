/*
 * NewsHacker - NHModule.java
 * Last Modified: 1/18/16 7:58 PM
 *
 * Copyright (c) 2016 Trevor Summerfield
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.tsums.newshacker.core;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.tsums.newshacker.NHApplication;
import com.tsums.newshacker.R;
import com.tsums.newshacker.network.CheeaunAPIConnector;
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
        mBuilder.setClient(new OkClient(okHttpClient));
        return mBuilder.build().create(HNConnector.class);
    }

    @Provides
    @Singleton
    CheeaunAPIConnector provideCheeaunConnector(Context context, OkHttpClient okHttpClient) {
        RestAdapter.Builder mBuilder = new RestAdapter.Builder();
        mBuilder.setEndpoint(context.getString(R.string.cheeaun_api_backend_path));
        mBuilder.setClient(new OkClient(okHttpClient));
        return mBuilder.build().create(CheeaunAPIConnector.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkClient () {
        OkHttpClient client = new OkHttpClient();
        return client;
    }

}
