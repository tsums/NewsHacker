package com.tsums.newshacker.core;

import com.tsums.newshacker.activities.ArticleDetailActivity;
import com.tsums.newshacker.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger 2 whole-app dependency component.
 */
@Component (modules = NHModule.class)
@Singleton
public interface NHComponent {

    void inject (MainActivity activity);
    void inject (ArticleDetailActivity activity);

}
