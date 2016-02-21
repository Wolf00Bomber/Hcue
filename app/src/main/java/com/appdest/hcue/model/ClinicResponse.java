package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Vinsan on 2/21/2016.
 */
public class ClinicResponse {
    @SerializedName("count")
    public int count;
    @SerializedName("DoctorDetails")
    public ArrayList<GetDoctorsResponse.DoctorDetail> listDoctorDetails;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<GetDoctorsResponse.DoctorDetail> getListDoctorDetails() {
        return listDoctorDetails;
    }

    public void setListDoctorDetails(ArrayList<GetDoctorsResponse.DoctorDetail> listDoctorDetails) {
        this.listDoctorDetails = listDoctorDetails;
    }

   /* public class DoctorDetails {
        public boolean isSelected;
        @SerializedName("DoctorID")
        private int DoctorID;
        @SerializedName("FullName")
        private String FullName;
        @SerializedName("AddressID")
        private int AddressID;
        @SerializedName("Available")
        private String Available;
        @SerializedName("ImageURL")
        private String ImageURL;
        @SerializedName("SpecialityCD")
        private Map<String, String> mapSpecialityCD;


        public Map<String, String> getMapSpecialityCD() {
            return mapSpecialityCD;
        }
        public void setMapSpecialityCD(Map<String, String> mapSpecialityCD) {
            this.mapSpecialityCD = mapSpecialityCD;
        }
        public int getDoctorID() {
            return DoctorID;
        }
        public void setDoctorID(int DoctorID) {
            this.DoctorID = DoctorID;
        }
        public String getFullName() {
            return FullName;
        }
        public void setFullName(String FullName) {
            this.FullName = FullName;
        }
        public int getAddressID() {
            return AddressID;
        }
        public void setAddressID(int AddressID) {
            this.AddressID = AddressID;
        }
        public String getAvailable() {
            return Available;
        }
        public void setAvailable(String Available) {
            this.Available = Available;
        }
        public String getImageURL() {
            return ImageURL;
        }
        public void setImageURL(String ImageURL) {
            this.ImageURL = ImageURL;
        }
    }*/
}
