package com.tsums.newshacker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    @InjectView (R.id.fragment_item_list_recycler_view) RecyclerView mRecylerView;

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

        mRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new ArticleListAdapter(this, articles);
        mRecylerView.setAdapter(mAdapter);

        return parentView;
    }

    @Override
    public void onStart () {
        super.onStart();

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

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onClick (int position) {

//        String url = articles.get(position).url;
//
//        if (url != null && !url.isEmpty()) {
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(url));
//            if (!getActivity().getPackageManager().queryIntentActivities(i, 0).isEmpty()) {
//                getActivity().startActivity(i);
//            }
//        }

        Intent i = new Intent(getActivity(), ArticleDetailActivity.class);
        i.putExtra(ArticleDetailActivity.EXTRA_ARTICLE, Parcels.wrap(articles.get(position)));
        startActivity(i);
    }
}
