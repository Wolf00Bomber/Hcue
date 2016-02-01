package com.appdest.hcue.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by NNBS PRAKASH RAO on 2/1/2016.
 */
public class TimeUtils {

    private static SimpleDateFormat hhmmss, hhmm;

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

}
