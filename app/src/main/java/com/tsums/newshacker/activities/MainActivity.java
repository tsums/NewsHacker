package com.tsums.newshacker.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.tsums.newshacker.NHApplication;
import com.tsums.newshacker.R;
import com.tsums.newshacker.models.HNItem;
import com.tsums.newshacker.network.HNConnector;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Main Activity for the application which will hose the app drawer and the majority of the functionality.
 */
public class MainActivity extends AppCompatActivity {

    @InjectView (R.id.activity_main_toolbar)       Toolbar      mToolbar;
    @InjectView (R.id.activity_main_drawer_layout) DrawerLayout mDrawerLayout;

    @Inject HNConnector mConnector;

    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((NHApplication) getApplication()).getmComponent().inject(this);

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.setDrawerListener(mToggle);

    }

    @Override
    protected void onPostCreate (Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged (Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart () {
        super.onStart();
        mConnector.getNewStories(new Callback<List<HNItem>>() {
            @Override
            public void success (List<HNItem> hnItems, Response response) {

            }

            @Override
            public void failure (RetrofitError error) {

            }
        });
    }
}
