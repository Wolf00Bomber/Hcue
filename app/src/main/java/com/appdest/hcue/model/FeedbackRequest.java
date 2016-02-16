package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sri Krishna on 16-02-2016.
 */
public class FeedbackRequest implements Serializable{
    public int getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(int doctorID) {
        DoctorID = doctorID;
    }

    public String getUSRType() {
        return USRType;
    }

    public void setUSRType(String USRType) {
        this.USRType = USRType;
    }

    public String getRatingComments() {
        return RatingComments;
    }

    public void setRatingComments(String ratingComments) {
        RatingComments = ratingComments;
    }

    public Number getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(Number appointmentID) {
        AppointmentID = appointmentID;
    }

    public Number getPatientID() {
        return PatientID;
    }

    public void setPatientID(Number patientID) {
        PatientID = patientID;
    }

    public float getStarValue() {
        return StarValue;
    }

    public void setStarValue(float starValue) {
        StarValue = starValue;
    }

    public Number getUSRId() {
        return USRId;
    }

    public void setUSRId(Number USRId) {
        this.USRId = USRId;
    }

    @SerializedName("DoctorID")
    public int DoctorID;
    @SerializedName("USRType")
    public String USRType = "PATIENT";
    @SerializedName("RatingComments")
    public String RatingComments;
    @SerializedName("AppointmentID")
    public Number AppointmentID;
    @SerializedName("PatientID")
    public Number PatientID;
    @SerializedName("StarValue")
    public float StarValue;
    @SerializedName("USRId")
    public Number USRId;

}
