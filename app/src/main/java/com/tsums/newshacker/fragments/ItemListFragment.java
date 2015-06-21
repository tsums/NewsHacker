package com.tsums.newshacker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tsums.newshacker.NHApplication;
import com.tsums.newshacker.R;
import com.tsums.newshacker.activities.ArticleDetailActivity;
import com.tsums.newshacker.adapters.ArticleListAdapter;
import com.tsums.newshacker.models.HNItem;
import com.tsums.newshacker.models.HNItemScoreComparator;
import com.tsums.newshacker.network.HNConnector;
import com.tsums.newshacker.ui.DividerItemDecoration;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ItemListFragment extends Fragment implements ArticleListAdapter.ArticleClickListener {

    private static final String TAG = ItemListFragment.class.getSimpleName();

    @InjectView (R.id.fragment_item_list_recycler_view) RecyclerView       mRecyclerView;
    @InjectView (R.id.fragment_item_list_swipe_refresh) SwipeRefreshLayout mSwipeRefresh;

    @Inject HNConnector mConnector;

    private List<HNItem> articles = new ArrayList<>();
    private ArticleListAdapter mAdapter;

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((NHApplication) getActivity().getApplication()).getmComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_item_list, container);
        ButterKnife.inject(this, parentView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha)));

        mAdapter = new ArticleListAdapter(this, articles);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefresh.setColorSchemeResources(R.color.primary, R.color.primary_dark);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh () {
                updateArticles();
                mSwipeRefresh.setRefreshing(false); //TODO better way of determining when data load is done.
            }
        });

        return parentView;
    }

    @Override
    public void onStart () {
        super.onStart();
        updateArticles();
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onClick (int position) {

        Intent i = new Intent(getActivity(), ArticleDetailActivity.class);
        i.putExtra(ArticleDetailActivity.EXTRA_ARTICLE, Parcels.wrap(articles.get(position)));
        startActivity(i);
    }

    private void updateArticles () {
        articles.clear();
        mAdapter.notifyDataSetChanged();
        mConnector.getTopStories(new Callback<List<Integer>>() {
            @Override
            public void success (List<Integer> integers, Response response) {
                integers = integers.subList(0, 15);
                for (final Integer id : integers) {
                    mConnector.getItem(id, new Callback<HNItem>() {
                        @Override
                        public void success (HNItem hnItem, Response response) {
                            articles.add(hnItem);
                            Collections.sort(articles, new HNItemScoreComparator());
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void failure (RetrofitError error) {
                            Log.e(TAG, "error getting article: " + id, error);
                        }
                    });
                }

            }

            @Override
            public void failure (RetrofitError error) {
                Log.e(TAG, "erorr getting top articles", error);
            }
        });
    }
}
