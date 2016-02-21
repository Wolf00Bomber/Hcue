package com.appdest.hcue.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vinsan on 2/20/2016.
 */
public class GetDoctorRequest {
    @SerializedName("DoctorID")
    private List<Integer> DoctorID = new ArrayList<Integer>();
    @SerializedName("PageSize")
    public int PageSize;
    @SerializedName("PageNumber")
    public int PageNumber;
    @SerializedName("AddressID")
    private List<Integer> AddressID = new ArrayList<Integer>();

    public List<Integer> getDoctorID() {
        return DoctorID;
    }
    public void setDoctorID(List<Integer> DoctorID) {
        this.DoctorID = DoctorID;
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

    public List<Integer> getAddressID() {
        return AddressID;
    }
    public void setAddressID(List<Integer> AddressID) {
        this.AddressID = AddressID;
    }
}
