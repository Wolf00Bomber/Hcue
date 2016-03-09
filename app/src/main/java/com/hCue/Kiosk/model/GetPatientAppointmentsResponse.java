package com.hCue.Kiosk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Sri Krishna on 18-02-2016.
 */
public class GetPatientAppointmentsResponse {
    @SerializedName("rows")
    public ArrayList<AppointmentRow> rows;
    @SerializedName("count")
    public int count;


    public class AppointmentRow implements Serializable
    {
        public boolean isSelected;
        @SerializedName("AppointmentDetails")
        public AppointmentDetails appointmentDetails;
        @SerializedName("DoctorDetails")
        public DoctorDetail doctorDetail;
        @SerializedName("RatingEntered")
        public boolean RatingEntered ;

        public boolean isRatingEntered() {
            return RatingEntered;
        }

        public void setRatingEntered(boolean ratingEntered) {
            RatingEntered = ratingEntered;
        }
    }


    public class AppointmentDetails implements Serializable
    {
        @SerializedName("AppointmentID")
        public Number AppointmentID;
        @SerializedName("AddressConsultID")
        public int AddressConsultID;
        @SerializedName("ClinicName")
        public String ClinicName;
        @SerializedName("DayCD")
        public String DayCD;
        @SerializedName("ConsultationDt")
        public Number ConsultationDt;
        @SerializedName("StartTime")
        public String StartTime;
        @SerializedName("EndTime")
        public String EndTime;
        @SerializedName("PatientID")
        public Number PatientID;
        @SerializedName("VisitUserTypeID")
        public String VisitUserTypeID = "OPATIENT";
        @SerializedName("DoctorID")
        public int DoctorID;
        @SerializedName("FirstTimeVisit")
        public String FirstTimeVisit;
        @SerializedName("DoctorVisitRsnID")
        public String DoctorVisitRsnID = "ALLMRDRR";
        @SerializedName("AppointmentStatus")
        public String AppointmentStatus = "B";
        @SerializedName("TokenNumber")
        public String TokenNumber;
    }

    public class DoctorDetail implements Serializable
    {
        @SerializedName("doctorFullName")
        public String doctorFullName;
        @SerializedName("doctorSpecialization")
        public LinkedHashMap<Integer,String> doctorSpecialization;
        @SerializedName("doctorQualification")
        public LinkedHashMap<Integer,String> doctorQualification;
        @SerializedName("doctorPhone")
        public ArrayList<DoctorPhone> doctorPhone;
        @SerializedName("doctorAddress")
        public ArrayList<DoctorAddress> doctorAddress;
        @SerializedName("doctorEmail")
        public ArrayList<DoctorEmail> arrDoctorEmail;
        @SerializedName("ProfileImage")
        public String ImageURL;
    }

    public class DoctorAddress implements Serializable{}

    public class DoctorEmail implements Serializable
    {

        @SerializedName("EmailID")
        public String EmailID;
        @SerializedName("EmailIDType")
        public String EmailIDType = "P";
        @SerializedName("PrimaryIND")
        public String PrimaryIND;
        @SerializedName("CrtUSR")
        public int CrtUSR;
        @SerializedName("CrtUSRType")
        public String CrtUSRType = "ADMIN";
    }

    public class DoctorPhone implements Serializable
    {
        @SerializedName("PhCntryCD")
        public int PhCntryCD;
        @SerializedName("PhStateCD")
        public int PhStateCD;
        @SerializedName("PhAreaCD")
        public int PhAreaCD;
        @SerializedName("PhNumber")
        public Number PhNumber;
        @SerializedName("PhType")
        public String PhType;
        @SerializedName("PrimaryIND")
        public String PrimaryIND;
    }

}
