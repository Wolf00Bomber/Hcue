package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetDoctors {
    @SerializedName("DoctorID")
    private List<Integer> DoctorID = new ArrayList<Integer>();
    @SerializedName("PageSize")
    public int PageSize;
    @SerializedName("PageNumber")
    public int PageNumber;
    @SerializedName("HospitalID")
    public int HospitalID;


    public List<Integer> getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(List<Integer> doctorID) {
        DoctorID = doctorID;
    }

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

}
