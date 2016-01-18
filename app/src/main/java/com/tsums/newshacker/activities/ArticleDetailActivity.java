package com.tsums.newshacker.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
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

public class ArticleDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ARTICLE = "extra_article";

    @Bind (R.id.activity_article_detail_toolbar) Toolbar toolbar;
    @Bind (R.id.activity_article_detail_webview) WebView webview;

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

        webview.loadUrl(article.url);

        getComments();
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
