package com.tsums.newshacker.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tsums.newshacker.NHApplication;

/**
 * Main Activity for the application which will hose the app drawer and the majority of the functionality.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((NHApplication) getApplication()).getmComponent().inject(this);
    }
}
