package com.tsums.newshacker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tsums.newshacker.NHApplication;
import com.tsums.newshacker.R;
import com.tsums.newshacker.network.HNConnector;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ItemListFragment extends Fragment {

    @InjectView (R.id.fragment_item_list_recycler_view) RecyclerView mRecylerView;

    @Inject HNConnector mConnector;

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

        return parentView;
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
