package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sri Krishna on 14-02-2016.
 */
public class CancelAppointmentResponse {

    @SerializedName("rows")
    public ArrayList<CancelAppointment> arrayList;

    @SerializedName("count")
    public int count;

    public class CancelAppointment implements Serializable
    {
        @SerializedName("AppointmentDetails")
        public DoctorsAppointmentResponse doctorsAppointmentResponse;
    }

}
