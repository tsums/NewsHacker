package com.tsums.newshacker.network;

import com.tsums.newshacker.models.HNChanged;
import com.tsums.newshacker.models.HNItem;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Retrofit interface to connect to the Hacker News API v0.
 */
public interface HNConnector {

    @GET ("/item/{id}.json")
    void getItem (@Path ("id") int item, Callback<HNItem> itemCallback);

    @GET ("/topstories.json")
    void getTopStories (Callback<List<HNItem>> callback);

    @GET ("/newstories.json")
    void getNewStories (Callback<List<HNItem>> callback);

    @GET ("/showstories.json")
    void getShowStories (Callback<List<HNItem>> callback);

    @GET ("/askstories.json")
    void getAskStories (Callback<List<HNItem>> callback);

    @GET ("/jobstories.json")
    void getJobStories (Callback<List<HNItem>> callback);

    @GET ("/maxitem.json")
    void getMaxItem (Callback<Integer> callback);

    @GET ("/updates.json")
    void getUpdates (Callback<HNChanged> callback);

}
