/*
 * NewsHacker - BlubberKnife.java
 * Last Modified: 1/23/16 10:08 AM
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

package com.tsums.blubberknife;

import android.app.Activity;

import com.tsums.blubberknife.annotation.ContentView;
import com.tsums.blubberknife.exception.MissingContentViewException;

/**
 * BlubberKnife cuts through the fat in Android development.
 */
public class BlubberKnife {

    public static void setContentView(Activity activity) {
        ContentView contentView = activity.getClass().getAnnotation(ContentView.class);
        if (contentView != null) {
            activity.setContentView(contentView.value());
        } else {
            throw new MissingContentViewException(activity);
        }
    }

    public static void blubber(Activity activity) {

    }
}
