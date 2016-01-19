/*
 * NewsHacker - NHApplication.java
 * Last Modified: 1/18/16 4:59 PM
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
