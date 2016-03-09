package com.hCue.Kiosk.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by shyamprasadg on 03/02/16.
 */
public class GetPatientDetailsRequest
{

    @SerializedName("USRType")
    public String USRType;

    @SerializedName("patientDetails")
    public PatientDetail patientDetail;

    @SerializedName("USRId")
    public int USRId;

    @SerializedName("patientPhone")
    public ArrayList<PatientPhone> arrPatientPhones;

    public class PatientDetail
    {

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
            public int MobileID;
            @SerializedName("Age")
            public int Age;
        }


    public class PatientPhone
    {

        @SerializedName("PhAreaCD")
        public int PhAreaCD;
        @SerializedName("PhCntryCD")
        public int PhCntryCD;
        @SerializedName("PhNumber")
        public Number PhNumber;
        @SerializedName("PhType")
        public String PhType;
        @SerializedName("PrimaryIND")
        public String PrimaryIND;
        @SerializedName("PhStateCD")
        public int PhStateCD;
    }

    }

