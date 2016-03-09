package com.hCue.Kiosk.model;

import com.google.gson.annotations.SerializedName;

public class GetHospitalsRequest {
    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public int getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(int pageNumber) {
        PageNumber = pageNumber;
    }

    public int getHospitalID() {
        return HospitalID;
    }

    public void setHospitalID(int hospitalID) {
        HospitalID = hospitalID;
    }

    @SerializedName("DoctorPageSize")
    public int PageSize;

    @SerializedName("DoctorPageNumber")
    public int PageNumber;

    @SerializedName("HospitalID")
    public int HospitalID;

}
