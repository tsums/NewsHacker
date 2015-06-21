package com.tsums.newshacker.util;

import com.ocpsoft.pretty.time.PrettyTime;

import java.util.Date;

/**
 * Static wrapper for PrettyTime for use in data bindings.
 */
public final class TimeUtil {

    public static String format (int time) {

        return format(new Date((long) time * 1000));

    }

    public static String format (Date time) {

        PrettyTime p = new PrettyTime();
        return p.format(time);

    }

}
