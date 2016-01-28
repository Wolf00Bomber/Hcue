package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cvlhyd on 26-01-2016.
 */
public class GetDoctorAppointmentResponse {

    @SerializedName("count")
    public int count;

    @SerializedName("rows")
    public List<AppointmentRow> appointmentRows;

    public class TimeSlot
    {
        @SerializedName("StartTime")
        public String StartTime;
        @SerializedName("EndTime")
        public String EndTime;
        @SerializedName("Available")
        public String Available;
        @SerializedName("TokenNumber")
        public String TokenNumber;
        @SerializedName("PatientInfo")
        public PatientInfo patientInfo;

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

        public String getAvailable() {
            return Available;
        }

        public void setAvailable(String available) {
            Available = available;
        }

        public String getTokenNumber() {
            return TokenNumber;
        }

        public void setTokenNumber(String tokenNumber) {
            TokenNumber = tokenNumber;
        }

        public PatientInfo getPatientInfo() {
            return patientInfo;
        }

        public void setPatientInfo(PatientInfo patientInfo) {
            this.patientInfo = patientInfo;
        }
    }

    public class PatientInfo
    {
        @SerializedName("PatientID")
        public String PatientID;
        @SerializedName("FullName")
        public String FullName;
        @SerializedName("AppointmentID")
        public String AppointmentID;
        @SerializedName("AppointmentStatus")
        public String AppointmentStatus;
        @SerializedName("MobileNumber")
        public String MobileNumber;
        @SerializedName("Sex")
        public String Sex;

        public String getPatientID() {
            return PatientID;
        }

        public void setPatientID(String patientID) {
            PatientID = patientID;
        }

        public String getFullName() {
            return FullName;
        }

        public void setFullName(String fullName) {
            FullName = fullName;
        }

        public String getAppointmentID() {
            return AppointmentID;
        }

        public void setAppointmentID(String appointmentID) {
            AppointmentID = appointmentID;
        }

        public String getAppointmentStatus() {
            return AppointmentStatus;
        }

        public void setAppointmentStatus(String appointmentStatus) {
            AppointmentStatus = appointmentStatus;
        }

        public String getMobileNumber() {
            return MobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            MobileNumber = mobileNumber;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String sex) {
            Sex = sex;
        }
    }

    public class AppointmentRow
    {
        @SerializedName("ClinicName")
        public String ClinicName;
        @SerializedName("AddressConsultID")
        public String AddressConsultID;
        @SerializedName("AddressID")
        public String AddressID;
        @SerializedName("DoctorID")
        public String DoctorID;
        @SerializedName("ConsultationDate")
        public String ConsultationDate;
        @SerializedName("DayCD")
        public String DayCD;
        @SerializedName("StartTime")
        public String StartTime;
        @SerializedName("EndTime")
        public String EndTime;
        @SerializedName("MinPerCase")
        public String MinPerCase;
        @SerializedName("Fees")
        public String Fees;
        @SerializedName("SlotList")
        public List<TimeSlot> timeSlots;

        public String getClinicName() {
            return ClinicName;
        }

        public void setClinicName(String clinicName) {
            ClinicName = clinicName;
        }

        public String getAddressConsultID() {
            return AddressConsultID;
        }

        public void setAddressConsultID(String addressConsultID) {
            AddressConsultID = addressConsultID;
        }

        public String getAddressID() {
            return AddressID;
        }

        public void setAddressID(String addressID) {
            AddressID = addressID;
        }

        public String getDoctorID() {
            return DoctorID;
        }

        public void setDoctorID(String doctorID) {
            DoctorID = doctorID;
        }

        public String getConsultationDate() {
            return ConsultationDate;
        }

        public void setConsultationDate(String consultationDate) {
            ConsultationDate = consultationDate;
        }

        public String getDayCD() {
            return DayCD;
        }

        public void setDayCD(String dayCD) {
            DayCD = dayCD;
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

        public String getMinPerCase() {
            return MinPerCase;
        }

        public void setMinPerCase(String minPerCase) {
            MinPerCase = minPerCase;
        }

        public String getFees() {
            return Fees;
        }

        public void setFees(String fees) {
            Fees = fees;
        }

        public List<TimeSlot> getTimeSlots() {
            return timeSlots;
        }

        public void setTimeSlots(List<TimeSlot> timeSlots) {
            this.timeSlots = timeSlots;
        }
    }

}
