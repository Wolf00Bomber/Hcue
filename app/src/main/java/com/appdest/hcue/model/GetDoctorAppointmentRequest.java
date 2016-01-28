package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cvlhyd on 26-01-2016.
 */
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
