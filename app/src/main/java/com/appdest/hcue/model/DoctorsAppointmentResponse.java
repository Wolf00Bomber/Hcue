package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

public class DoctorsAppointmentResponse {

    @SerializedName("AppointmentID")
    int AppointmentID;

    @SerializedName("AddressConsultID")
    String AddressConsultID;

    @SerializedName("DayCD")
    int DayCD;

    @SerializedName("ConsultationDt")
    String ConsultationDt;

    @SerializedName("StartTime")
    int StartTime;

    @SerializedName("EndTime")
    String EndTime;

    @SerializedName("PatientID")
    int PatientID;

    @SerializedName("VisitUserTypeID")
    String VisitUserTypeID;

    @SerializedName("DoctorID")
    int DoctorID;

    @SerializedName("FirstTimeVisit")
    String FirstTimeVisit;

    @SerializedName("DoctorVisitRsnID")
    int DoctorVisitRsnID;

    @SerializedName("AppointmentStatus")
    String AppointmentStatus;

    @SerializedName("TokenNumber")
    String TokenNumber;

    @SerializedName("CrtUSR")
    int CrtUSR;

    @SerializedName("CrtUSRType")
    String CrtUSRType;

    public int getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        AppointmentID = appointmentID;
    }

    public String getAddressConsultID() {
        return AddressConsultID;
    }

    public void setAddressConsultID(String addressConsultID) {
        AddressConsultID = addressConsultID;
    }

    public int getDayCD() {
        return DayCD;
    }

    public void setDayCD(int dayCD) {
        DayCD = dayCD;
    }

    public String getConsultationDt() {
        return ConsultationDt;
    }

    public void setConsultationDt(String consultationDt) {
        ConsultationDt = consultationDt;
    }

    public int getStartTime() {
        return StartTime;
    }

    public void setStartTime(int startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public int getPatientID() {
        return PatientID;
    }

    public void setPatientID(int patientID) {
        PatientID = patientID;
    }

    public String getVisitUserTypeID() {
        return VisitUserTypeID;
    }

    public void setVisitUserTypeID(String visitUserTypeID) {
        VisitUserTypeID = visitUserTypeID;
    }

    public int getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(int doctorID) {
        DoctorID = doctorID;
    }

    public String getFirstTimeVisit() {
        return FirstTimeVisit;
    }

    public void setFirstTimeVisit(String firstTimeVisit) {
        FirstTimeVisit = firstTimeVisit;
    }

    public int getDoctorVisitRsnID() {
        return DoctorVisitRsnID;
    }

    public void setDoctorVisitRsnID(int doctorVisitRsnID) {
        DoctorVisitRsnID = doctorVisitRsnID;
    }

    public String getAppointmentStatus() {
        return AppointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        AppointmentStatus = appointmentStatus;
    }

    public String getTokenNumber() {
        return TokenNumber;
    }

    public void setTokenNumber(String tokenNumber) {
        TokenNumber = tokenNumber;
    }

    public int getCrtUSR() {
        return CrtUSR;
    }

    public void setCrtUSR(int crtUSR) {
        CrtUSR = crtUSR;
    }

    public String getCrtUSRType() {
        return CrtUSRType;
    }

    public void setCrtUSRType(String crtUSRType) {
        CrtUSRType = crtUSRType;
    }
}
