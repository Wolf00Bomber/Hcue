package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sri Krishna on 13-02-2016.
 */
public class GetPatientRequest {
    @SerializedName("PageSize")
    int PageSize;
    @SerializedName("PageNumber")
    int PageNumber;
    @SerializedName("Sort")
    String Sort;
    @SerializedName("PhoneNumber")
    String PhoneNumber;

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

    public String getSort() {
        return Sort;
    }

    public void setSort(String sort) {
        Sort = sort;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
