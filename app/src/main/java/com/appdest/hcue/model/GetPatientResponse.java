package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sri Krishna on 13-02-2016.
 */
public class GetPatientResponse implements Serializable{

    @SerializedName("rows")
    public ArrayList<PatientInfo> rows;

    @SerializedName("count")
    public int count;


    public class PatientInfo implements Serializable
    {
        @SerializedName("Patient")
        public ArrayList<Patient> patients;
        @SerializedName("FamilyMenbers")
        public ArrayList<FamilyMenbers> familyMenbers;
        @SerializedName("PatientPhone")
        public ArrayList<PatientPhone> patientPhone;
        @SerializedName("PatientAddress")
        public ArrayList<PatientAddress> patientAddress;
        @SerializedName("PatientEmail")
        public ArrayList<PatientEmail> patientEmail;
        @SerializedName("PatientOtherIds")
        public ArrayList<PatientOtherIds> patientOtherIds;
    }

    public class PatientPhone implements Serializable
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

    public class FamilyMenbers implements Serializable
    {}

    public class PatientAddress implements Serializable
    {
        @SerializedName("EmailID")
        public String EmailID;
        @SerializedName("EmailIDType")
        public String EmailIDType;
        @SerializedName("PrimaryIND")
        public String PrimaryIND;
    }

    public class PatientEmail implements Serializable
    {

    }

    public class PatientOtherIds implements Serializable
    {

    }

    public class Patient implements Serializable
    {
        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            FirstName = firstName;
        }

        public String getFullName() {
            return FullName;
        }

        public void setFullName(String fullName) {
            FullName = fullName;
        }

        public Number getPatientID() {
            return PatientID;
        }

        public void setPatientID(Number patientID) {
            PatientID = patientID;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public Number getDOB() {
            return DOB;
        }

        public void setDOB(Number DOB) {
            this.DOB = DOB;
        }

        public String getFamilyHdIND() {
            return FamilyHdIND;
        }

        public void setFamilyHdIND(String familyHdIND) {
            FamilyHdIND = familyHdIND;
        }

        public Number getFamilyHdID() {
            return FamilyHdID;
        }

        public void setFamilyHdID(Number familyHdID) {
            FamilyHdID = familyHdID;
        }

        @SerializedName("FirstName")
        public String FirstName;

        @SerializedName("FullName")
        public String FullName;

        @SerializedName("PatientID")
        public Number PatientID;

        @SerializedName("Gender")
        public String Gender;

        @SerializedName("DOB")
        public Number DOB;

        @SerializedName("FamilyHdIND")
        public String FamilyHdIND;

        @SerializedName("FamilyHdID")
        public Number FamilyHdID;
    }

}
