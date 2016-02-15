package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sri Krishna on 14-02-2016.
 */
public class CancelAppointmentRequest {

    @SerializedName("FamilyHdID")
    public Number FamilyHdID;

    @SerializedName("PageSize")
    public int PageSize;

    @SerializedName("PageNumber")
    public int PageNumber;

    @SerializedName("PatientID")
    public Number PatientID;

    @SerializedName("Sort")
    public String Sort = "asc";

    @SerializedName("BaseDate")
    public String BaseDate;

    @SerializedName("Count")
    public int Count;

    @SerializedName("Indicator")
    public String Indicator = "F";
}
