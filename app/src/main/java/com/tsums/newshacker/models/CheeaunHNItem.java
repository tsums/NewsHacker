package com.tsums.newshacker.models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by trevor on 1/18/16.
 */
@Parcel
public class CheeaunHNItem {

    public long id;
    public String title;
    public long points;
    public String user;
    public int time;
    public String time_ago;
    public int comments_count;
    public String type;
    public String url;
    public String domain;
    public List<CheeaunHNComment> comments;


}
