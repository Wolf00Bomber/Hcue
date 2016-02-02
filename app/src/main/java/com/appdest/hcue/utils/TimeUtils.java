package com.appdest.hcue.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by NNBS PRAKASH RAO on 2/1/2016.
 */
public class TimeUtils {

    private static SimpleDateFormat hhmmss, hhmm, normalDate;

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

}
