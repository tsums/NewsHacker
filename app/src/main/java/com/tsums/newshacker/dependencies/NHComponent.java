package com.tsums.newshacker.dependencies;

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

}
