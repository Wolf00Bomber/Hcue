package com.hCue.Kiosk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class GetDoctorsResponse {
    @SerializedName("HospitalInfo")
    public HospitalInfo hospitalInfo;

    @SerializedName("DoctorDetails")
    public ArrayList<DoctorDetail> arrDoctorDetails;

    @SerializedName("DoctorCount")
    public int DoctorCount;

    public class HospitalInfo
    {
        @SerializedName("HospitalDetails")
        public HospitalDetail hospitalDetails;

        @SerializedName("HospitalAddress")
        public HospitalAddress hospitalAddress;
    }

    public class HospitalDetail
    {
        @SerializedName("HospitalID")
        public int HospitalID;
        @SerializedName("HospitalName")
        public String HospitalName;
        @SerializedName("ActiveIND")
        public String ActiveIND;
        @SerializedName("TermsAccepted")
        public String TermsAccepted;
    }

    public class HospitalAddress
    {
        @SerializedName("Address1")
        public String Address1;
        @SerializedName("Address2")
        public String Address2;
        @SerializedName("Location")
        public String Location;
        @SerializedName("CityTown")
        public String CityTown;
    }

    public class DoctorDetail implements Serializable
    {
        @SerializedName("DoctorID")
        public int DoctorID;
        @SerializedName("FullName")
        public String FullName;
        @SerializedName("SpecialityCD")
        public LinkedHashMap<Integer,String> specialityCD;
        @SerializedName("ImageURL")
        public String ImageURL;
        @SerializedName("AddressID")
        public int AddressID;
        @SerializedName("Available")
        public String Avaialble;
    }


}
