package com.appdest.hcue.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.google.gson.internal.$Gson$Preconditions.checkArgument;

public class TimeUtils {

    private static SimpleDateFormat hhmmss, hhmm, normalDate, properDate;

    private static SimpleDateFormat getSDFHHMMSS()
    {
        if(hhmmss == null)
            hhmmss = new SimpleDateFormat("HH:mm:ss");
        return hhmmss;
    }
    private static SimpleDateFormat getSDFHHMM()
    {
        if(hhmm == null)
            hhmm = new SimpleDateFormat("HH:mm");
        return hhmm;
    }

    private static SimpleDateFormat getSDNormalDate()
    {
        if(normalDate == null)/*"2016-01-05"*/
            normalDate = new SimpleDateFormat("yyyy-MM-dd");
        return normalDate;
    }

    private static SimpleDateFormat getSDProperDate()
    {
        if(properDate == null)/*"2016-01-05"*/
            properDate = new SimpleDateFormat("dd MMM yyyy");
        return properDate;
    }

    public static String format2hhmm(String hhmmss)
    {
        try {
            Date d = getSDFHHMMSS().parse(hhmmss);
            return getSDFHHMM().format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hhmmss;
    }

    public static String format2Date(long milliseconds)
    {
        Date d = new Date(milliseconds);
        return getSDNormalDate().format(d);
    }

    public static String format2DateProper(long milliseconds)
    {
        Date d = new Date(milliseconds);
        return getSDProperDate().format(d);
    }

    String getDayOfMonthSuffix(final int n) {
        assert (n >= 1 && n <= 31);
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }

}
