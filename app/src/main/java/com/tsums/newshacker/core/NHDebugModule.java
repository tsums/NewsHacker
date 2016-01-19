/*
 * NewsHacker - NHDebugModule.java
 * Last Modified: 1/18/16 4:56 PM
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

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Extension of core NHModule which gets loaded in debug build variant
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

    @Override
    CheeaunAPIConnector provideCheeaunConnector(Context context, OkHttpClient okHttpClient) {
        RestAdapter.Builder mBuilder = new RestAdapter.Builder();
        mBuilder.setEndpoint(context.getString(R.string.cheeaun_api_backend_path));
        mBuilder.setLogLevel(RestAdapter.LogLevel.FULL);
        mBuilder.setClient(new OkClient(okHttpClient));
        return mBuilder.build().create(CheeaunAPIConnector.class);
    }
}
