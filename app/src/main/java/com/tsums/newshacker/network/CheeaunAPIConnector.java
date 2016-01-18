package com.tsums.newshacker.network;

import com.tsums.newshacker.models.CheeaunHNItem;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by trevor on 1/18/16.
 */
public interface CheeaunAPIConnector {

    @GET("/news")
    void getHomepage(Callback<List<CheeaunHNItem>> callback);

    @GET("/item/{id}")
    void getItemDetail(@Path("id") long id, Callback<CheeaunHNItem> callback);

}
