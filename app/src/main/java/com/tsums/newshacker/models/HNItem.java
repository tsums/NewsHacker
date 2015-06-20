package com.tsums.newshacker.models;

import java.util.Date;

public class HNItem {

    public static final String TYPE_STORY   = "story";
    public static final String TYPE_COMMENT = "comment";
    public static final String TYPE_JOB     = "job";
    public static final String TYPE_POLL    = "poll";
    public static final String TYPE_POLLOPT = "pollopt";

    public String by;
    public int    descendents;
    public int    id;
    public int[]  kids;
    public int    score;
    public Date   time;
    public String title;
    public String type;
    public String URL;

}
