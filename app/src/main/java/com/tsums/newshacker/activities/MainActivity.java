/*
 * NewsHacker - MainActivity.java
 * Last Modified: 1/23/16 9:28 AM
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

package com.tsums.newshacker.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.f2prateek.dart.Dart;
import com.tsums.newshacker.NHApplication;
import com.tsums.newshacker.R;
import com.tsums.newshacker.adapters.ArticleListAdapter;
import com.tsums.newshacker.models.CheeaunHNItem;
import com.tsums.newshacker.network.CheeaunAPIConnector;
import com.tsums.newshacker.network.HNConnector;
import com.tsums.newshacker.ui.DividerItemDecoration;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

/**
 * Main Activity for the application which will hose the app drawer and the majority of the functionality.
 */
//@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ArticleListAdapter.ArticleClickListener {

    @Bind (R.id.activity_main_toolbar)       Toolbar            mToolbar;
    @Bind (R.id.activity_main_drawer_layout) DrawerLayout       mDrawerLayout;
    @Bind (R.id.activity_main_nav_view)      NavigationView     mNavigationView;
    @Bind (R.id.activity_main_recycler_view) RecyclerView       mRecyclerView;
    @Bind (R.id.activity_main_swipe_refresh) SwipeRefreshLayout mSwipeRefresh;

    @Inject HNConnector mConnector;
    @Inject CheeaunAPIConnector cheeaunAPIConnector;

    private List<CheeaunHNItem>   articles = new ArrayList<>();
    private ArticleListAdapter    mAdapter;
    private ActionBarDrawerToggle mToggle;

    private int currentMenuItem;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((NHApplication) getApplication()).getmComponent().inject(this);
        Dart.inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.setDrawerListener(mToggle);

        //noinspection deprecation
        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.primary_dark));

        mNavigationView.setNavigationItemSelectedListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha)));

        mAdapter = new ArticleListAdapter(this, articles);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefresh.setColorSchemeResources(R.color.primary, R.color.primary_dark);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh () {
                fetchArticles();
                mSwipeRefresh.setRefreshing(false); //TODO better way of determining when data load is done.
            }
        });

        currentMenuItem = R.id.nav_top_stories;

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
    public void onStart () {
        super.onStart();
        fetchArticles();
    }

    @Override
    public boolean onNavigationItemSelected (MenuItem menuItem) {

        currentMenuItem = menuItem.getItemId();
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
        fetchArticles();
        getSupportActionBar().setTitle(getString(R.string.app_name) + " - " + menuItem.getTitleCondensed());
        return true;
    }

    @Override
    public void onClick (int position) {

        if (articles.get(position).url != null && !articles.get(position).url.isEmpty()) {
            Intent i = new Intent(this, ArticleDetailActivity.class);
            i.putExtra(ArticleDetailActivity.EXTRA_ARTICLE, Parcels.wrap(articles.get(position)));
            startActivity(i);
        }
    }

    private void fetchArticles() {

        articles.clear();
        mAdapter.notifyDataSetChanged();

        // Currently using Cheeaun API as a workaround to get the actual homepage order.
        // Methodology needs some cleanup.
        cheeaunAPIConnector.getHomepage(new Callback<List<CheeaunHNItem>>() {
            @Override
            public void success(List<CheeaunHNItem> cheeaunHNItems, Response response) {
                articles.addAll(cheeaunHNItems);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Timber.e(error, "Couldn't update articles!");
            }
        });
    }
}
