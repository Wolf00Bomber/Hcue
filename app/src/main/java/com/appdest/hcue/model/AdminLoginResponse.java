package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Vinsan on 2/14/2016.
 */
public class AdminLoginResponse implements Serializable {

    public ArrayList<Doctor> getArrDoctor() {
        return arrDoctor;
    }

    public void setArrDoctor(ArrayList<Doctor> arrDoctor) {
        this.arrDoctor = arrDoctor;
    }

    public ArrayList<DoctorPhone> getArrDoctorPhone() {
        return arrDoctorPhone;
    }

    public void setArrDoctorPhone(ArrayList<DoctorPhone> arrDoctorPhone) {
        this.arrDoctorPhone = arrDoctorPhone;
    }

    public ArrayList<DoctorAddress> getArrDoctorAddress() {
        return arrDoctorAddress;
    }

    public void setArrDoctorAddress(ArrayList<DoctorAddress> arrDoctorAddress) {
        this.arrDoctorAddress = arrDoctorAddress;
    }

    public ArrayList<DoctorEmail> getArrDoctorEmail() {
        return arrDoctorEmail;
    }

    public void setArrDoctorEmail(ArrayList<DoctorEmail> arrDoctorEmail) {
        this.arrDoctorEmail = arrDoctorEmail;
    }

    public ArrayList<DoctorConsultation> getArrDoctorConsultation() {
        return arrDoctorConsultation;
    }

    public void setArrDoctorConsultation(ArrayList<DoctorConsultation> arrDoctorConsultation) {
        this.arrDoctorConsultation = arrDoctorConsultation;
    }

    public ArrayList<DoctorEducation> getArrDoctorEducation() {
        return arrDoctorEducation;
    }

    public void setArrDoctorEducation(ArrayList<DoctorEducation> arrDoctorEducation) {
        this.arrDoctorEducation = arrDoctorEducation;
    }

    public ArrayList<DoctorPublishing> getArrDoctorPublishing() {
        return arrDoctorPublishing;
    }

    public void setArrDoctorPublishing(ArrayList<DoctorPublishing> arrDoctorPublishing) {
        this.arrDoctorPublishing = arrDoctorPublishing;
    }

    public ArrayList<DoctorAchievements> getArrDoctorAchievements() {
        return arrDoctorAchievements;
    }

    public void setArrDoctorAchievements(ArrayList<DoctorAchievements> arrDoctorAchievements) {
        this.arrDoctorAchievements = arrDoctorAchievements;
    }

    @SerializedName("doctor")
    public ArrayList<Doctor> arrDoctor;

    @SerializedName("doctorPhone")
    public ArrayList<DoctorPhone> arrDoctorPhone;

    @SerializedName("doctorAddress")
    public ArrayList<DoctorAddress> arrDoctorAddress;

    @SerializedName("doctorEmail")
    public ArrayList<DoctorEmail> arrDoctorEmail;

    @SerializedName("doctorConsultation")
    public ArrayList<DoctorConsultation> arrDoctorConsultation;

    @SerializedName("doctorEducation")
    public ArrayList<DoctorEducation> arrDoctorEducation;

    @SerializedName("DoctorPublishing")
    public ArrayList<DoctorPublishing> arrDoctorPublishing;

    @SerializedName("DoctorAchievements")
    public ArrayList<DoctorAchievements> arrDoctorAchievements;

    public class Doctor implements Serializable {
        @SerializedName("FirstName")
        public String FirstName;
        @SerializedName("LastName")
        public String LastName;
        @SerializedName("FullName")
        public String FullName;
        @SerializedName("DoctorID")
        public int DoctorID;
        @SerializedName("Gender")
        public String Gender;
        @SerializedName("DOB")
        public Number DOB;
        @SerializedName("Exp")
        public Number Exp;
        @SerializedName("DoctorLoginID")
        public String DoctorLoginID;
        @SerializedName("HcueScore")
        public Number HcueScore;
        @SerializedName("Prospect")
        public String Prospect;
        @SerializedName("TermsAccepted")
        public String TermsAccepted;

        public LinkedHashMap<String, String> getSpecialityCD() {
            return specialityCD;
        }

        public void setSpecialityCD(LinkedHashMap<String, String> specialityCD) {
            this.specialityCD = specialityCD;
        }

        @SerializedName("SpecialityCD")
        public LinkedHashMap<String,String> specialityCD;

        public LinkedHashMap<String, String> getMemberID() {
            return MemberID;
        }

        public void setMemberID(LinkedHashMap<String, String> memberID) {
            MemberID = memberID;
        }

        public LinkedHashMap<String, String> getQualification() {
            return Qualification;
        }

        public void setQualification(LinkedHashMap<String, String> qualification) {
            Qualification = qualification;
        }

        public LinkedHashMap<String, String> getAbout() {
            return About;
        }

        public void setAbout(LinkedHashMap<String, String> about) {
            About = about;
        }

        public LinkedHashMap<String, String> getAwards() {
            return Awards;
        }

        public void setAwards(LinkedHashMap<String, String> awards) {
            Awards = awards;
        }

        public LinkedHashMap<String, String> getPractice() {
            return Practice;
        }

        public void setPractice(LinkedHashMap<String, String> practice) {
            Practice = practice;
        }

        public LinkedHashMap<String, String> getMembership() {
            return Membership;
        }

        public void setMembership(LinkedHashMap<String, String> membership) {
            Membership = membership;
        }

        public LinkedHashMap<String, String> getServices() {
            return Services;
        }

        public void setServices(LinkedHashMap<String, String> services) {
            Services = services;
        }

        @SerializedName("MemberID")
        public LinkedHashMap<String,String> MemberID;
        @SerializedName("Qualification")
        public LinkedHashMap<String,String> Qualification;
        @SerializedName("About")
        public LinkedHashMap<String,String> About;
        @SerializedName("Awards")
        public LinkedHashMap<String,String> Awards;
        @SerializedName("Practice")
        public LinkedHashMap<String,String> Practice;
        @SerializedName("Membership")
        public LinkedHashMap<String,String> Membership;
        @SerializedName("Services")
        public LinkedHashMap<String,String> Services;

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            FirstName = firstName;
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String lastName) {
            LastName = lastName;
        }

        public String getFullName() {
            return FullName;
        }

        public void setFullName(String fullName) {
            FullName = fullName;
        }

        public int getDoctorID() {
            return DoctorID;
        }

        public void setDoctorID(int doctorID) {
            DoctorID = doctorID;
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

        public Number getExp() {
            return Exp;
        }

        public void setExp(Number exp) {
            Exp = exp;
        }

        public String getDoctorLoginID() {
            return DoctorLoginID;
        }

        public void setDoctorLoginID(String doctorLoginID) {
            DoctorLoginID = doctorLoginID;
        }

        public Number getHcueScore() {
            return HcueScore;
        }

        public void setHcueScore(Number hcueScore) {
            HcueScore = hcueScore;
        }

        public String getProspect() {
            return Prospect;
        }

        public void setProspect(String prospect) {
            Prospect = prospect;
        }

        public String getTermsAccepted() {
            return TermsAccepted;
        }

        public void setTermsAccepted(String termsAccepted) {
            TermsAccepted = termsAccepted;
        }
    }

    public class DoctorPhone implements Serializable {
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

        public int getPhCntryCD() {
            return PhCntryCD;
        }

        public void setPhCntryCD(int phCntryCD) {
            PhCntryCD = phCntryCD;
        }

        public int getPhStateCD() {
            return PhStateCD;
        }

        public void setPhStateCD(int phStateCD) {
            PhStateCD = phStateCD;
        }

        public int getPhAreaCD() {
            return PhAreaCD;
        }

        public void setPhAreaCD(int phAreaCD) {
            PhAreaCD = phAreaCD;
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
    }

    public class DoctorAddress implements Serializable {
        public boolean isSelected; //kvk
        @SerializedName("AddressID")
        public int AddressID;
        @SerializedName("ClinicName")
        public String ClinicName;
        @SerializedName("Address1")
        public String Address1;
        @SerializedName("Address2")
        public String Address2;
        @SerializedName("Location")
        public String Location;
        @SerializedName("CityTown")
        public String CityTown;
        @SerializedName("State")
        public String State;
        @SerializedName("PinCode")
        public Number PinCode;
        @SerializedName("Country")
        public String Country;
        @SerializedName("AddressType")
        public String AddressType;
        @SerializedName("Active")
        public String Active;
        @SerializedName("ExtDetails")
        public ExtDetails ExtDetails;

        public int getAddressID() {
            return AddressID;
        }

        public void setAddressID(int addressID) {
            AddressID = addressID;
        }

        public String getClinicName() {
            return ClinicName;
        }

        public void setClinicName(String clinicName) {
            ClinicName = clinicName;
        }

        public String getAddress1() {
            return Address1;
        }

        public void setAddress1(String address1) {
            Address1 = address1;
        }

        public String getAddress2() {
            return Address2;
        }

        public void setAddress2(String address2) {
            Address2 = address2;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
        }

        public String getCityTown() {
            return CityTown;
        }

        public void setCityTown(String cityTown) {
            CityTown = cityTown;
        }

        public String getState() {
            return State;
        }

        public void setState(String state) {
            State = state;
        }

        public Number getPinCode() {
            return PinCode;
        }

        public void setPinCode(Number pinCode) {
            PinCode = pinCode;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String country) {
            Country = country;
        }

        public String getAddressType() {
            return AddressType;
        }

        public void setAddressType(String addressType) {
            AddressType = addressType;
        }

        public String getActive() {
            return Active;
        }

        public void setActive(String active) {
            Active = active;
        }

        public AdminLoginResponse.ExtDetails getExtDetails() {
            return ExtDetails;
        }

        public void setExtDetails(AdminLoginResponse.ExtDetails extDetails) {
            ExtDetails = extDetails;
        }
    }

    public class ExtDetails implements Serializable{
        @SerializedName("HospitalID")
        public int HospitalID;
        @SerializedName("PrimaryIND")
        public String PrimaryIND;
        @SerializedName("Latitude")
        public String Latitude;
        @SerializedName("Longitude")
        public String Longitude;

        public int getHospitalID() {
            return HospitalID;
        }

        public void setHospitalID(int hospitalID) {
            HospitalID = hospitalID;
        }

        public String getPrimaryIND() {
            return PrimaryIND;
        }

        public void setPrimaryIND(String primaryIND) {
            PrimaryIND = primaryIND;
        }

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String latitude) {
            Latitude = latitude;
        }

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String longitude) {
            Longitude = longitude;
        }
    }

    public class DoctorEmail implements Serializable{
        @SerializedName("EmailID")
        public String EmailID;
        @SerializedName("EmailIDType")
        public String EmailIDType;
        @SerializedName("PrimaryIND")
        public String PrimaryIND;
        @SerializedName("CrtUSR")
        public int CrtUSR;
        @SerializedName("CrtUSRType")
        public String CrtUSRType;

        public String getEmailID() {
            return EmailID;
        }

        public void setEmailID(String emailID) {
            EmailID = emailID;
        }

        public String getEmailIDType() {
            return EmailIDType;
        }

        public void setEmailIDType(String emailIDType) {
            EmailIDType = emailIDType;
        }

        public String getPrimaryIND() {
            return PrimaryIND;
        }

        public void setPrimaryIND(String primaryIND) {
            PrimaryIND = primaryIND;
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

    public class DoctorConsultation implements Serializable{
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

        public int getFees() {
            return Fees;
        }

        public void setFees(int fees) {
            Fees = fees;
        }

        public String getActive() {
            return Active;
        }

        public void setActive(String active) {
            Active = active;
        }

        /*
        * {
      "AddressConsultID": 2145,
      "AddressID": 267,
      "DoctorID": 85,
      "DayCD": "SAT",
      "StartTime": "00:00",
      "EndTime": "17:00",
      "MinPerCase": 15,
      "Fees": 0,
      "Active": "Y"
    }*/
        @SerializedName("AddressConsultID")
        public int AddressConsultID;
        @SerializedName("AddressID")
        public int AddressID;
        @SerializedName("DoctorID")
        public int DoctorID;
        @SerializedName("DayCD")
        public String DayCD;
        @SerializedName("StartTime")
        public String StartTime;
        @SerializedName("EndTime")
        public String EndTime;
        @SerializedName("MinPerCase")
        public int MinPerCase;
        @SerializedName("Fees")
        public int Fees;
        @SerializedName("Active")
        public String Active;
    }

    public class DoctorEducation implements Serializable{
        @SerializedName("RowID")
        public int RowID;
        @SerializedName("EducationName")
        public String EducationName;
        @SerializedName("InstituteName")
        public String InstituteName;
        @SerializedName("FullName")
        public String FullName;

        public int getRowID() {
            return RowID;
        }

        public void setRowID(int rowID) {
            RowID = rowID;
        }

        public String getEducationName() {
            return EducationName;
        }

        public void setEducationName(String educationName) {
            EducationName = educationName;
        }

        public String getInstituteName() {
            return InstituteName;
        }

        public void setInstituteName(String instituteName) {
            InstituteName = instituteName;
        }

        public String getFullName() {
            return FullName;
        }

        public void setFullName(String fullName) {
            FullName = fullName;
        }
    }

    public class DoctorPublishing implements Serializable{
        @SerializedName("RowID")
        public int RowID;
        @SerializedName("Name")
        public String Name;
        @SerializedName("Year")
        public String Year;
        @SerializedName("Indicator")
        public String Indicator;
        @SerializedName("Details")
        public Details Details;

        public int getRowID() {
            return RowID;
        }

        public void setRowID(int rowID) {
            RowID = rowID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getYear() {
            return Year;
        }

        public void setYear(String year) {
            Year = year;
        }

        public String getIndicator() {
            return Indicator;
        }

        public void setIndicator(String indicator) {
            Indicator = indicator;
        }

        public AdminLoginResponse.Details getDetails() {
            return Details;
        }

        public void setDetails(AdminLoginResponse.Details details) {
            Details = details;
        }
    }

    public class Details implements Serializable {
        @SerializedName("publishedBy")
        public String publishedBy;
        @SerializedName("publishedBooks")
        public String publishedBooks;

        public String getPublishedBy() {
            return publishedBy;
        }

        public void setPublishedBy(String publishedBy) {
            this.publishedBy = publishedBy;
        }

        public String getPublishedBooks() {
            return publishedBooks;
        }

        public void setPublishedBooks(String publishedBooks) {
            this.publishedBooks = publishedBooks;
        }
    }

    public class DoctorAchievements implements Serializable{
        @SerializedName("RowID")
        public int RowID;
        @SerializedName("Name")
        public String Name;
        @SerializedName("Year")
        public String Year;
        @SerializedName("Indicator")
        public String Indicator;

        public int getRowID() {
            return RowID;
        }

        public void setRowID(int rowID) {
            RowID = rowID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getYear() {
            return Year;
        }

        public void setYear(String year) {
            Year = year;
        }

        public String getIndicator() {
            return Indicator;
        }

        public void setIndicator(String indicator) {
            Indicator = indicator;
        }
    }
}