package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sri Krishna on 18-02-2016.
 */
public class GetPatientAppointmentsRequest {

    @SerializedName("FamilyHdID")
    public Number FamilyHdID;
    @SerializedName("PageSize")
    public int PageSize;
    @SerializedName("PageNumber")
    public int PageNumber;
    @SerializedName("PatientID")
    public Number PatientID;
    @SerializedName("Sort")
    public String Sort =  "asc";
    @SerializedName("BaseDate")
    public String BaseDate;
    @SerializedName("Count")
    public int Count;
    @SerializedName("Indicator")
    public String Indicator = "P";

    public Number getFamilyHdID() {
        return FamilyHdID;
    }

    public void setFamilyHdID(Number familyHdID) {
        FamilyHdID = familyHdID;
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

    public Number getPatientID() {
        return PatientID;
    }

    public void setPatientID(Number patientID) {
        PatientID = patientID;
    }

    public String getSort() {
        return Sort;
    }

    public void setSort(String sort) {
        Sort = sort;
    }

    public String getBaseDate() {
        return BaseDate;
    }

    public void setBaseDate(String baseDate) {
        BaseDate = baseDate;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String getIndicator() {
        return Indicator;
    }

    public void setIndicator(String indicator) {
        Indicator = indicator;
    }
}
