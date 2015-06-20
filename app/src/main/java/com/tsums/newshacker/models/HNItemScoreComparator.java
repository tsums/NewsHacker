package com.tsums.newshacker.models;

import java.util.Comparator;

/**
 * Created by Trevor on 6/20/2015.
 */
public class HNItemScoreComparator implements Comparator<HNItem> {

    @Override
    public int compare (HNItem lhs, HNItem rhs) {
        return rhs.score - lhs.score;
    }
}