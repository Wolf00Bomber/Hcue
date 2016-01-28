package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cvlhyd on 26-01-2016.
 */
public class DoctorsAppointment {

    @SerializedName("DoctorID")
    int mDoctorIDId;

    @SerializedName("DayCD")
    String DayCD;

    @SerializedName("USRType")
    int USRType;

    @SerializedName("VisitUserTypeID")
    String VisitUserTypeID;

    @SerializedName("EndTime")
    int EndTime;

    @SerializedName("ConsultationDt")
    String ConsultationDt;

    @SerializedName("AddressConsultID")
    int AddressConsultID;

    @SerializedName("PatientID")
    String PatientID;

    @SerializedName("StartTime")
    int StartTime;

    @SerializedName("AppointmentStatus")
    String AppointmentStatus;

    @SerializedName("USRId")
    int USRId;

    @SerializedName("DoctorVisitRsnID")
    String DoctorVisitRsnID;

    public int getmDoctorIDId() {
        return mDoctorIDId;
    }

    public void setmDoctorIDId(int mDoctorIDId) {
        this.mDoctorIDId = mDoctorIDId;
    }

    public String getDayCD() {
        return DayCD;
    }

    public void setDayCD(String dayCD) {
        DayCD = dayCD;
    }

    public int getUSRType() {
        return USRType;
    }

    public void setUSRType(int USRType) {
        this.USRType = USRType;
    }

    public String getVisitUserTypeID() {
        return VisitUserTypeID;
    }

    public void setVisitUserTypeID(String visitUserTypeID) {
        VisitUserTypeID = visitUserTypeID;
    }

    public int getEndTime() {
        return EndTime;
    }

    public void setEndTime(int endTime) {
        EndTime = endTime;
    }

    public String getConsultationDt() {
        return ConsultationDt;
    }

    public void setConsultationDt(String consultationDt) {
        ConsultationDt = consultationDt;
    }

    public int getAddressConsultID() {
        return AddressConsultID;
    }

    public void setAddressConsultID(int addressConsultID) {
        AddressConsultID = addressConsultID;
    }

    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String patientID) {
        PatientID = patientID;
    }

    public int getStartTime() {
        return StartTime;
    }

    public void setStartTime(int startTime) {
        StartTime = startTime;
    }

    public String getAppointmentStatus() {
        return AppointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        AppointmentStatus = appointmentStatus;
    }

    public int getUSRId() {
        return USRId;
    }

    public void setUSRId(int USRId) {
        this.USRId = USRId;
    }

    public String getDoctorVisitRsnID() {
        return DoctorVisitRsnID;
    }

    public void setDoctorVisitRsnID(String doctorVisitRsnID) {
        DoctorVisitRsnID = doctorVisitRsnID;
    }
}
