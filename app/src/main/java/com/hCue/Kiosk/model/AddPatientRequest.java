package com.hCue.Kiosk.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by cvlhyd on 26-01-2016.
 */
public class AddPatientRequest {

    @SerializedName("USRType")
    public String USRType;

    @SerializedName("patientDetails")
    public PatientDetails patientDetails = new PatientDetails();

    @SerializedName("USRId")
    public int USRId;

    @SerializedName("patientPhone")
    public ArrayList<PatientPhone> listPatientPhone = new ArrayList<>();

    public void addPatientPhone(PatientPhone patientPhone)
    {
        listPatientPhone.add(patientPhone);
    }

    public void addAllPatientPhone(ArrayList<PatientPhone> arrPatientPhone)
    {
        listPatientPhone.addAll(arrPatientPhone);
    }

    public PatientPhone getEmptypatientPhone(){
        return new PatientPhone();
    }

    public String getUSRType() {
        return USRType;
    }

    public void setUSRType(String USRType) {
        this.USRType = USRType;
    }

    public PatientDetails getPatientDetails() {
        return patientDetails;
    }

    public void setPatientDetails(PatientDetails patientDetails) {
        this.patientDetails = patientDetails;
    }

    public int getUSRId() {
        return USRId;
    }

    public void setUSRId(int USRId) {
        this.USRId = USRId;
    }

    public ArrayList<PatientPhone> getListPatientPhone() {
        return listPatientPhone;
    }

    public void setListPatientPhone(ArrayList<PatientPhone> listPatientPhone) {
        this.listPatientPhone = listPatientPhone;
    }

    public class PatientDetails
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

        public Number getMobileID() {
            return MobileID;
        }

        public void setMobileID(Number mobileID) {
            MobileID = mobileID;
        }

        public int getAge() {
            return Age;
        }

        public void setAge(int age) {
            Age = age;
        }

        public int getUSRId() {
            return USRId;
        }

        public void setUSRId(int USRId) {
            this.USRId = USRId;
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
        public Number MobileID;
        @SerializedName("Age")
        public int Age;
        @SerializedName("USRId")
        public int USRId;


    }

    public class PatientPhone
    {
        public int getPhAreaCD() {
            return PhAreaCD;
        }

        public void setPhAreaCD(int phAreaCD) {
            PhAreaCD = phAreaCD;
        }

        public int getPhCntryCD() {
            return PhCntryCD;
        }

        public void setPhCntryCD(int phCntryCD) {
            PhCntryCD = phCntryCD;
        }

        public Number getPhNumber() {
            return PhNumber;
        }

        public void setPhNumber(Number phNumber) {
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

        public int getPhStateCD() {
            return PhStateCD;
        }

        public void setPhStateCD(int phStateCD) {
            PhStateCD = phStateCD;
        }

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
