package com.appdest.hcue.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vinsan on 2/20/2016.
 */
public class GetDoctorRequest {
    @SerializedName("DoctorID")
    private Integer DoctorID;
    @SerializedName("USRType")
    private String USRType;
    @SerializedName("USRId")
    private Integer USRId;
    @SerializedName("AddressID")
    private List<Integer> AddressID = new ArrayList<Integer>();

    public Integer getDoctorID() {
        return DoctorID;
    }
    public void setDoctorID(Integer DoctorID) {
        this.DoctorID = DoctorID;
    }
    public String getUSRType() {
        return USRType;
    }
    public void setUSRType(String USRType) {
        this.USRType = USRType;
    }
    public Integer getUSRId() {
        return USRId;
    }
    public void setUSRId(Integer USRId) {
        this.USRId = USRId;
    }
    public List<Integer> getAddressID() {
        return AddressID;
    }
    public void setAddressID(List<Integer> AddressID) {
        this.AddressID = AddressID;
    }
}
