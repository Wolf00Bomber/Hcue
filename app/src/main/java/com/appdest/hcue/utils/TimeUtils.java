package com.appdest.hcue.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.google.gson.internal.$Gson$Preconditions.checkArgument;

public class TimeUtils {

    private static SimpleDateFormat hhmmss, hhmm, hhmma, normalDate, properDate;

    public static String getDay(long mils)
    {
        return new SimpleDateFormat("EEEE").format(new Date(mils));
    }

    private static SimpleDateFormat getSDFHHMMA()
    {
        if(hhmma == null)
            hhmma = new SimpleDateFormat("HH:mm a");
        return hhmma;
    }

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

    public static String format2hhmmaa(String hhmm)
    {
        try {
            Date d = getSDFHHMM().parse(hhmm);
            return getSDFHHMMA().format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hhmm;
    }

    public static long getLongForHHMMSS(String hhmmss)
    {
        try {
            Date d = getSDFHHMMSS().parse(hhmmss);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    public static String format2Date(Date date)
    {
        return getSDNormalDate().format(date);
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static String format2Date(long milliseconds)
    {
        Date d = new Date(milliseconds);
        return getSDNormalDate().format(d);
    }

    public static String format2DateProper(Date date)
    {
        return getSDProperDate().format(date);
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
