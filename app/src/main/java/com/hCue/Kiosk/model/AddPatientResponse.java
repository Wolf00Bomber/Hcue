package com.hCue.Kiosk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sri Krishna on 13-02-2016.
 */
public class AddPatientResponse implements Serializable{

    @SerializedName("Patient")
    public ArrayList<Patient> arrPatients;

    @SerializedName("FamilyMenbers")
    public ArrayList<FamilyMenbers> arrFamilyMenbers;

    @SerializedName("PatientPhone")
    public ArrayList<PatientPhone> arrPatientPhone;

    @SerializedName("PatientAddress")
    public ArrayList<PatientAddress> arrPatientAddress;

    @SerializedName("PatientEmail")
    public ArrayList<PatientEmail> arrPatientEmail;

    @SerializedName("PatientOtherIds")
    public ArrayList<PatientOtherIds> arrPatientOtherIds;

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

    class FamilyMenbers {}
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


    class PatientAddress {}
    class PatientEmail {}
    class PatientOtherIds {}

}
