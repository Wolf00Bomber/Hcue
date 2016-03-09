package com.hCue.Kiosk.model;

/**
 * Created by cvlhyd on 26-01-2016.
 */
public class TimeObject {

    public TimeObject(String state, String timeValue)
    {
        this.state = state;
        this.timeValue = timeValue;
    }

    public String state = "NORMAL";
    public String timeValue;
}
