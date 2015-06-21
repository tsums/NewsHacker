package com.tsums.newshacker.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tsums.newshacker.NHApplication;
import com.tsums.newshacker.R;
import com.tsums.newshacker.network.HNConnector;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Main Activity for the application which will hose the app drawer and the majority of the functionality.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @InjectView (R.id.activity_main_toolbar)       Toolbar        mToolbar;
    @InjectView (R.id.activity_main_drawer_layout) DrawerLayout   mDrawerLayout;
    @InjectView (R.id.activity_main_nav_view)      NavigationView mNavigationView;

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
        mDrawerLayout.setStatusBarBackgroundColor(R.color.primary_dark);

        mNavigationView.setNavigationItemSelectedListener(this);

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
    public boolean onNavigationItemSelected (MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            // TODO implement switching of feed in fragment.
            case R.id.nav_top_stories:
            case R.id.nav_new_stories:
            case R.id.nav_show_stories:
            case R.id.nav_ask_stories:
            case R.id.nav_job_stories:
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
        }

        return false;
    }
}
