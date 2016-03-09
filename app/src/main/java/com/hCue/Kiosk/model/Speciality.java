package com.hCue.Kiosk.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sri Krishna on 12-02-2016.
 */
public class Speciality implements Comparable
{
    @SerializedName("DoctorSpecialityID")
    public String DoctorSpecialityID;
    @SerializedName("DoctorSpecialityDesc")
    public String DoctorSpecialityDesc;
    @SerializedName("SpecialityDesc")
    public String SpecialityDesc;
    @SerializedName("ActiveIND")
    public String ActiveIND;

    @Override
    public int compareTo(Object another) {
        String obj1 = this.DoctorSpecialityDesc;
        String obj2 = ((Speciality)another).DoctorSpecialityDesc;
        if (obj1 == obj2) {
            return 0;
        }
        if (obj1 == null) {
            return -1;
        }
        if (obj2 == null) {
            return 1;
        }
        return obj1.compareTo(obj2);
    }
}