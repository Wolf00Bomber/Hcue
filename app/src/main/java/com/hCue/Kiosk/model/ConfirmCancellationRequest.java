package com.hCue.Kiosk.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cvlhyd on 18-02-2016.
 */
public class ConfirmCancellationRequest {

    @SerializedName("USRType")
    public String USRType = "KIOSK";
    @SerializedName("AppointmentID")
    public Number AppointmentID;
    @SerializedName("AppointmentStatus")
    public String AppointmentStatus = "C";
    @SerializedName("USRId")
    public int USRId = 0;

    public String getUSRType() {
        return USRType;
    }

    public void setUSRType(String USRType) {
        this.USRType = USRType;
    }

    public Number getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(Number appointmentID) {
        AppointmentID = appointmentID;
    }

    public String getAppointmentStatus() {
        return AppointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        AppointmentStatus = appointmentStatus;
    }

    public int getUSRId() {
        return USRId;
    }

    public void setUSRId(int USRId) {
        this.USRId = USRId;
    }
}
