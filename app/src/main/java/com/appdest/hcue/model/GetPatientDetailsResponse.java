package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shyamprasadg on 03/02/16.
 */
public class GetPatientDetailsResponse
{

    @SerializedName("Patient")
    public ArrayList<Patient> arrPatients;

    @SerializedName("PatientPhone")
    public ArrayList<PatientPhone> arrPatientPhones;

    @SerializedName("FamilyMenbers")
    public ArrayList<FamilyMenber> arrFamilyMenbers;

    @SerializedName("PatientAddress")
    public ArrayList<PatientAddres> arrPatientAddress;

    @SerializedName("PatientEmail")
    public ArrayList<PatientEmail> arrPatientEmails;

    @SerializedName("PatientOtherIds")
    public ArrayList<PatientOtherId> arrPatientOtherIds;

    public class Patient
    {
        @SerializedName("FirstName")
        public String FirstName;
        @SerializedName("LastName")
        public String LastName;
        @SerializedName("FullName")
        public String FullName;
        @SerializedName("PatientID")
        public int PatientID;
        @SerializedName("Gender")
        public String Gender;
        @SerializedName("DOB")
        public int DOB;
        @SerializedName("Age")
        public int Age;
        @SerializedName("FamilyHdIND")
        public String FamilyHdIND;
        @SerializedName("FamilyHdID")
        public int FamilyHdID;
    }
    public class FamilyMenber
    {

    }

    public class PatientPhone
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
    public class PatientAddres
    {

    }
    public class PatientEmail
    {

    }
    public class PatientOtherId
    {

    }
}
