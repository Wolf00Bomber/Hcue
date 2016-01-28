package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cvlhyd on 26-01-2016.
 */
public class AddPatientRequest {

    @SerializedName("USRType")
    int USRType;

    @SerializedName("patientDetails")
    PatientDetails patientDetails;

    @SerializedName("USRId")
    int USRId;

    @SerializedName("patientPhone")
    List<PatientPhone> listPatientPhone;

    class PatientDetails
    {
        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            FirstName = firstName;
        }

        public String getTermsAccepted() {
            return TermsAccepted;
        }

        public void setTermsAccepted(String termsAccepted) {
            TermsAccepted = termsAccepted;
        }

        public String getFullName() {
            return FullName;
        }

        public void setFullName(String fullName) {
            FullName = fullName;
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String lastName) {
            LastName = lastName;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public String getMobileID() {
            return MobileID;
        }

        public void setMobileID(String mobileID) {
            MobileID = mobileID;
        }

        public int getAge() {
            return Age;
        }

        public void setAge(int age) {
            Age = age;
        }

        @SerializedName("FirstName")
        public String FirstName;
        @SerializedName("TermsAccepted")
        public String TermsAccepted;
        @SerializedName("FullName")
        public String FullName;
        @SerializedName("LastName")
        public String LastName;
        @SerializedName("Gender")
        public String Gender;
        @SerializedName("MobileID")
        public String MobileID;
        @SerializedName("Age")
        public int Age;

    }

    class PatientPhone
    {
        public String getPhAreaCD() {
            return PhAreaCD;
        }

        public void setPhAreaCD(String phAreaCD) {
            PhAreaCD = phAreaCD;
        }

        public String getPhCntryCD() {
            return PhCntryCD;
        }

        public void setPhCntryCD(String phCntryCD) {
            PhCntryCD = phCntryCD;
        }

        public String getPhNumber() {
            return PhNumber;
        }

        public void setPhNumber(String phNumber) {
            PhNumber = phNumber;
        }

        public String getPhType() {
            return PhType;
        }

        public void setPhType(String phType) {
            PhType = phType;
        }

        public String getPrimaryIND() {
            return PrimaryIND;
        }

        public void setPrimaryIND(String primaryIND) {
            PrimaryIND = primaryIND;
        }

        public String getPhStateCD() {
            return PhStateCD;
        }

        public void setPhStateCD(String phStateCD) {
            PhStateCD = phStateCD;
        }

        @SerializedName("PhAreaCD")
        public String PhAreaCD;
        @SerializedName("PhCntryCD")
        public String PhCntryCD;
        @SerializedName("PhNumber")
        public String PhNumber;
        @SerializedName("PhType")
        public String PhType;
        @SerializedName("PrimaryIND")
        public String PrimaryIND;
        @SerializedName("PhStateCD")
        public String PhStateCD;
    }

}
