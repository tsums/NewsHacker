package com.tsums.newshacker.models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by trevor on 1/18/16.
 */
@Parcel
public class CheeaunHNComment {

    public long id;
    public int level;
    public String user;
    public int time;
    public String time_ago;
    public String content;
    public List<CheeaunHNComment> comments;

}
