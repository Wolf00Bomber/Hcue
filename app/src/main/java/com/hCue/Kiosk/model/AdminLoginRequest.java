package com.hCue.Kiosk.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vinsan on 2/14/2016.
 */
public class AdminLoginRequest {
    @SerializedName("DoctorPassword")
    public String DoctorPassword;
    @SerializedName("DoctorLoginID")
    public String DoctorLoginID;

    public String getDoctorPassword() {
        return DoctorPassword;
    }

    public void setDoctorPassword(String doctorPassword) {
        DoctorPassword = doctorPassword;
    }

    public String getDoctorLoginID() {
        return DoctorLoginID;
    }

    public void setDoctorLoginID(String doctorLoginID) {
        DoctorLoginID = doctorLoginID;
    }
}
