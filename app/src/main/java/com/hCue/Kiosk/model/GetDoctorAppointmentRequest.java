package com.hCue.Kiosk.model;

import com.google.gson.annotations.SerializedName;

public class GetDoctorAppointmentRequest {

    public int getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(int doctorID) {
        DoctorID = doctorID;
    }

    public String getFilterByDate() {
        return filterByDate;
    }

    public void setFilterByDate(String filterByDate) {
        this.filterByDate = filterByDate;
    }

    public int getAddressID() {
        return AddressID;
    }

    public void setAddressID(int addressID) {
        AddressID = addressID;
    }

    @SerializedName("DoctorID")
    public int DoctorID;
    @SerializedName("filterByDate")
    public String filterByDate;
    @SerializedName("AddressID")
    public int AddressID;
}
