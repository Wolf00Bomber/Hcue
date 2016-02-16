package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

/*Sample Request URL :http://dct4avjn1lfw.cloudfront.net/doctors/getDoctors

Request Method :POST*/
public class AdminGetDoctorsRequest {
	@SerializedName("PageSize")
	private int PageSize;
	@SerializedName("PageNumber")
	private int PageNumber;
	@SerializedName("HospitalID")
	private int HospitalID;

	public int getPageSize() {
		return PageSize;
	}
	public void setPageSize(int PageSize) {
		this.PageSize = PageSize;
	}
	public int getPageNumber() {
		return PageNumber;
	}
	public void setPageNumber(int PageNumber) {
		this.PageNumber = PageNumber;
	}
	public int getHospitalID() {
		return HospitalID;
	}
	public void setHospitalID(int HospitalID) {
		this.HospitalID = HospitalID;
	}
}
