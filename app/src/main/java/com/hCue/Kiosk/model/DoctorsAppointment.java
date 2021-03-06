package com.hCue.Kiosk.model;

import com.google.gson.annotations.SerializedName;

public class DoctorsAppointment {

    @SerializedName("DoctorID")
    int mDoctorIDId;

    @SerializedName("DayCD")
    String DayCD;

    @SerializedName("USRType")
    String USRType;

    @SerializedName("VisitUserTypeID")
    String VisitUserTypeID;

    @SerializedName("EndTime")
    String EndTime;

    @SerializedName("ConsultationDt")
    String ConsultationDt;

    @SerializedName("AddressConsultID")
    int AddressConsultID;

    @SerializedName("PatientID")
    Number PatientID;

    @SerializedName("StartTime")
    String StartTime;

    @SerializedName("AppointmentStatus")
    String AppointmentStatus;

    @SerializedName("USRId")
    int USRId;

    @SerializedName("DoctorVisitRsnID")
    String DoctorVisitRsnID;

    public String getSendSMS() {
        return SendSMS;
    }

    public void setSendSMS(String sendSMS) {
        SendSMS = sendSMS;
    }

    @SerializedName("SendSMS")
    String SendSMS;

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

    public String getUSRType() {
        return USRType;
    }

    public void setUSRType(String USRType) {
        this.USRType = USRType;
    }

    public String getVisitUserTypeID() {
        return VisitUserTypeID;
    }

    public void setVisitUserTypeID(String visitUserTypeID) {
        VisitUserTypeID = visitUserTypeID;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
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

    public Number getPatientID() {
        return PatientID;
    }

    public void setPatientID(Number patientID) {
        PatientID = patientID;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
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
