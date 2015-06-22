package com.tsums.newshacker.models;

import java.util.Comparator;

/**
 * Sort HNItems in descending order by score.
 */
public class HNItemScoreComparator implements Comparator<HNItem> {

    @Override
    public int compare (HNItem lhs, HNItem rhs) {
        return rhs.score - lhs.score;
    }
}