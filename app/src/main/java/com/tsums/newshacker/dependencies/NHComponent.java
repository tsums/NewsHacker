package com.tsums.newshacker.dependencies;

import com.tsums.newshacker.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger 2 whole-app dependency module. Will probably move to a multi-module architecture at a later date.
 */
@Component (modules = NHModule.class)
@Singleton
public interface NHComponent {

    void inject (MainActivity activity);

}
