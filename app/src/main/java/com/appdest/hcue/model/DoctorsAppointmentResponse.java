package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DoctorsAppointmentResponse implements Serializable{

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public long getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(long appointmentID) {
        AppointmentID = appointmentID;
    }

    public int getAddressConsultID() {
        return AddressConsultID;
    }

    public void setAddressConsultID(int addressConsultID) {
        AddressConsultID = addressConsultID;
    }

    public String getDayCD() {
        return DayCD;
    }

    public void setDayCD(String dayCD) {
        DayCD = dayCD;
    }

    public long getConsultationDt() {
        return ConsultationDt;
    }

    public void setConsultationDt(long consultationDt) {
        ConsultationDt = consultationDt;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public Number getPatientID() {
        return PatientID;
    }

    public void setPatientID(Number patientID) {
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

    public String getDoctorVisitRsnID() {
        return DoctorVisitRsnID;
    }

    public void setDoctorVisitRsnID(String doctorVisitRsnID) {
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

    @SerializedName("SeqNo")
    public int seqNo;

    @SerializedName("AppointmentID")
    long AppointmentID;

    @SerializedName("AddressConsultID")
    int AddressConsultID;

    @SerializedName("DayCD")
    String DayCD;

    @SerializedName("ConsultationDt")
    long ConsultationDt;

    @SerializedName("StartTime")
    String StartTime;

    @SerializedName("EndTime")
    String EndTime;

    @SerializedName("PatientID")
    Number PatientID;

    @SerializedName("VisitUserTypeID")
    String VisitUserTypeID;

    @SerializedName("DoctorID")
    int DoctorID;

    @SerializedName("FirstTimeVisit")
    String FirstTimeVisit;

    @SerializedName("DoctorVisitRsnID")
    String DoctorVisitRsnID;

    @SerializedName("AppointmentStatus")
    String AppointmentStatus;

    @SerializedName("TokenNumber")
    String TokenNumber;

}
