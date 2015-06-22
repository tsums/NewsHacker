package com.tsums.newshacker.models;

import java.util.Comparator;

/**
 * Sort HNItems in descending order by time submitted.
 */
public class HNItemDateComparator implements Comparator<HNItem> {

    @Override
    public int compare (HNItem lhs, HNItem rhs) {
        return rhs.time - lhs.time;
    }
}
