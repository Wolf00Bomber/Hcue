package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetDoctorAppointmentResponse {

    @SerializedName("count")
    public int count;

    @SerializedName("rows")
    public ArrayList<AppointmentRow> appointmentRows;

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
    }

    public class AppointmentRow
    {
        @SerializedName("ClinicName")
        public String ClinicName;
        @SerializedName("AddressConsultID")
        public int AddressConsultID;
        @SerializedName("AddressID")
        public int AddressID;
        @SerializedName("DoctorID")
        public int DoctorID;
        @SerializedName("ConsultationDate")
        public long ConsultationDate;
        @SerializedName("DayCD")
        public String DayCD;
        @SerializedName("StartTime")
        public String StartTime;
        @SerializedName("EndTime")
        public String EndTime;
        @SerializedName("MinPerCase")
        public int MinPerCase;
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

        public int getAddressConsultID() {
            return AddressConsultID;
        }

        public void setAddressConsultID(int addressConsultID) {
            AddressConsultID = addressConsultID;
        }

        public int getAddressID() {
            return AddressID;
        }

        public void setAddressID(int addressID) {
            AddressID = addressID;
        }

        public int getDoctorID() {
            return DoctorID;
        }

        public void setDoctorID(int doctorID) {
            DoctorID = doctorID;
        }

        public long getConsultationDate() {
            return ConsultationDate;
        }

        public void setConsultationDate(long consultationDate) {
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

        public int getMinPerCase() {
            return MinPerCase;
        }

        public void setMinPerCase(int minPerCase) {
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
