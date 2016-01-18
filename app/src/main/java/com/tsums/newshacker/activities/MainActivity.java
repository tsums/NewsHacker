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
import android.util.Log;
import android.view.MenuItem;

import com.tsums.newshacker.NHApplication;
import com.tsums.newshacker.R;
import com.tsums.newshacker.adapters.ArticleListAdapter;
import com.tsums.newshacker.models.HNItem;
import com.tsums.newshacker.models.HNItemDateComparator;
import com.tsums.newshacker.models.HNItemScoreComparator;
import com.tsums.newshacker.network.HNConnector;
import com.tsums.newshacker.ui.DividerItemDecoration;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Bind;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Main Activity for the application which will hose the app drawer and the majority of the functionality.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ArticleListAdapter.ArticleClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Bind (R.id.activity_main_toolbar)       Toolbar            mToolbar;
    @Bind (R.id.activity_main_drawer_layout) DrawerLayout       mDrawerLayout;
    @Bind (R.id.activity_main_nav_view)      NavigationView     mNavigationView;
    @Bind (R.id.activity_main_recycler_view) RecyclerView       mRecyclerView;
    @Bind (R.id.activity_main_swipe_refresh) SwipeRefreshLayout mSwipeRefresh;

    @Inject HNConnector mConnector;

    private List<HNItem> articles = new ArrayList<>();
    private ArticleListAdapter    mAdapter;
    private ActionBarDrawerToggle mToggle;

    private int currentMenuItem;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((NHApplication) getApplication()).getmComponent().inject(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.setDrawerListener(mToggle);
        mDrawerLayout.setStatusBarBackgroundColor(R.color.primary_dark);

        mNavigationView.setNavigationItemSelectedListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha)));

        mAdapter = new ArticleListAdapter(this, articles);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefresh.setColorSchemeResources(R.color.primary, R.color.primary_dark);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh () {
                updateArticleIds();
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
        updateArticleIds();
    }

    @Override
    public boolean onNavigationItemSelected (MenuItem menuItem) {

        currentMenuItem = menuItem.getItemId();
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
        updateArticleIds();
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

    private void updateArticleIds () {

        articles.clear();
        mAdapter.notifyDataSetChanged();

        switch (currentMenuItem) {
            case R.id.nav_top_stories:
                mConnector.getTopStories(new ArticleIDListCallback(SORTING_STRATEGY.SORT_SCORE));
                break;
            case R.id.nav_new_stories:
                mConnector.getNewStories(new ArticleIDListCallback(SORTING_STRATEGY.SORT_SCORE));
                break;
            case R.id.nav_ask_stories:
                mConnector.getAskStories(new ArticleIDListCallback(SORTING_STRATEGY.SORT_SCORE));
                break;
            case R.id.nav_show_stories:
                mConnector.getShowStories(new ArticleIDListCallback(SORTING_STRATEGY.SORT_SCORE));
                break;
            case R.id.nav_job_stories:
                mConnector.getJobStories(new ArticleIDListCallback(SORTING_STRATEGY.SORT_SCORE));
                break;
        }
    }

    private void fetchArticles (List<Integer> ids, SORTING_STRATEGY strategy) {

        final Comparator<HNItem> itemComparator;

        switch (strategy) {
            case SORT_DATE:
                itemComparator = new HNItemDateComparator();
                break;
            case SORT_SCORE:
                itemComparator = new HNItemScoreComparator();
                break;
            default:
                itemComparator = new HNItemScoreComparator();
        }

        for (final Integer id : ids) {
            mConnector.getItem(id, new Callback<HNItem>() {
                @Override
                public void success (HNItem hnItem, Response response) {
                    articles.add(hnItem);
                    Collections.sort(articles, itemComparator);
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void failure (RetrofitError error) {
                    Log.e(TAG, "error getting article: " + id, error);
                }
            });
        }
    }

    private class ArticleIDListCallback implements Callback<List<Integer>> {

        private SORTING_STRATEGY strategy;

        public ArticleIDListCallback(SORTING_STRATEGY strategy) {
            this.strategy = strategy;
        }

        @Override
        public void success (List<Integer> integers, Response response) {
            integers = integers.subList(0, 15);
            fetchArticles(integers, strategy);
        }

        @Override
        public void failure (RetrofitError error) {
            Log.e(TAG, "failed to get article ids: " + error.getUrl(), error);
        }
    }

    enum SORTING_STRATEGY {
        SORT_SCORE,
        SORT_DATE
    }
}
