/*
 * NewsHacker - ArticleDetailActivity.java
 * Last Modified: 1/23/16 10:02 AM
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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.tsums.blubberknife.annotation.OptionsMenu;
import com.tsums.newshacker.NHApplication;
import com.tsums.newshacker.R;
import com.tsums.newshacker.models.CheeaunHNItem;
import com.tsums.newshacker.network.CheeaunAPIConnector;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

/**
 * Detail page showing WebView of link contents.
 */
@OptionsMenu(R.menu.activity_detail_toolbar)
public class ArticleDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ARTICLE = "extra_article";

    @Bind (R.id.activity_article_detail_toolbar) Toolbar toolbar;
//    @Bind (R.id.activity_article_detail_webview) WebView webview;

    @InjectExtra (EXTRA_ARTICLE) CheeaunHNItem article;

    @Inject CheeaunAPIConnector cheeaunAPIConnector;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((NHApplication) getApplication()).getmComponent().inject(this);

        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        Dart.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(article.title);

//        webview.loadUrl(article.url);

        getComments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_detail_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getComments() {
        cheeaunAPIConnector.getItemDetail(article.id, new Callback<CheeaunHNItem>() {
            @Override
            public void success(CheeaunHNItem cheeaunHNItem, Response response) {
                article = cheeaunHNItem;
                Timber.d("Got the item!");
                // TODO notify changed!
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
