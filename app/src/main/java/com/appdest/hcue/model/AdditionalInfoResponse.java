package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 29-02-2016.
 */
public class AdditionalInfoResponse
{
    @SerializedName("Patient")
    private ArrayList<Patient> arrPatient;

    @SerializedName("PatientPhone")
    private ArrayList<PatientPhone> arrPatientPhone;

    @SerializedName("PatientAddress")
    private ArrayList<PatientAddress> arrPatientAddress;

    @SerializedName("PatientEmail")
    private ArrayList<PatientEmail> arrPatientEmail;

    public ArrayList<Patient> getArrPatient() {
        return arrPatient;
    }

    public void setArrPatient(ArrayList<Patient> arrPatient) {
        this.arrPatient = arrPatient;
    }

    public ArrayList<PatientPhone> getArrPatientPhone() {
        return arrPatientPhone;
    }

    public void setArrPatientPhone(ArrayList<PatientPhone> arrPatientPhone) {
        this.arrPatientPhone = arrPatientPhone;
    }

    public ArrayList<PatientAddress> getArrPatientAddress() {
        return arrPatientAddress;
    }

    public void setArrPatientAddress(ArrayList<PatientAddress> arrPatientAddress) {
        this.arrPatientAddress = arrPatientAddress;
    }

    public ArrayList<PatientEmail> getArrPatientEmail() {
        return arrPatientEmail;
    }

    public void setArrPatientEmail(ArrayList<PatientEmail> arrPatientEmail) {
        this.arrPatientEmail = arrPatientEmail;
    }

    public class PatientPhone
    {
        @SerializedName("PhAreaCD")
        public String PhAreaCD;

        @SerializedName("PhCntryCD")
        public String PhCntryCD;

        @SerializedName("PhNumber")
        public String PhNumber;

        @SerializedName("FirstName")
        public String FirstName;

        @SerializedName("PhStateCD")
        public String PhStateCD;

        @SerializedName("PhType")
        public String PhType;

        @SerializedName("PrimaryIND")
        public String PrimaryIND;

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

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            FirstName = firstName;
        }

        public String getPhStateCD() {
            return PhStateCD;
        }

        public void setPhStateCD(String phStateCD) {
            PhStateCD = phStateCD;
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

    public class PatientAddress
    {
        @SerializedName("Address1")
        public String Address1;

        @SerializedName("Address2")
        public String Address2;

        @SerializedName("AddressType")
        public String AddressType;

        @SerializedName("CityTown")
        public String CityTown;

        @SerializedName("Country")
        public String Country;

        @SerializedName("DistrictRegion")
        public String DistrictRegion;

        @SerializedName("Latitude")
        public String Latitude;

        @SerializedName("Location")
        public String Location;

        @SerializedName("Longitude")
        public String Longitude;

        @SerializedName("PinCode")
        public String PinCode;

        @SerializedName("PrimaryIND")
        public String PrimaryIND;

        @SerializedName("State")
        public String State;

        @SerializedName("Street")
        public String Street;

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

        public String getAddressType() {
            return AddressType;
        }

        public void setAddressType(String addressType) {
            AddressType = addressType;
        }

        public String getCityTown() {
            return CityTown;
        }

        public void setCityTown(String cityTown) {
            CityTown = cityTown;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String country) {
            Country = country;
        }

        public String getDistrictRegion() {
            return DistrictRegion;
        }

        public void setDistrictRegion(String districtRegion) {
            DistrictRegion = districtRegion;
        }

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String latitude) {
            Latitude = latitude;
        }

        public String getLocation() {
            return Location;
        }

        public void setLocation(String location) {
            Location = location;
        }

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String longitude) {
            Longitude = longitude;
        }

        public String getPinCode() {
            return PinCode;
        }

        public void setPinCode(String pinCode) {
            PinCode = pinCode;
        }

        public String getPrimaryIND() {
            return PrimaryIND;
        }

        public void setPrimaryIND(String primaryIND) {
            PrimaryIND = primaryIND;
        }

        public String getState() {
            return State;
        }

        public void setState(String state) {
            State = state;
        }

        public String getStreet() {
            return Street;
        }

        public void setStreet(String street) {
            Street = street;
        }
    }

    public class PatientEmail
    {
        @SerializedName("EmailID")
        public String EmailID;

        @SerializedName("EmailIDType")
        public String EmailIDType;

        @SerializedName("PrimaryIND")
        public String PrimaryIND;

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
    }
    public class Patient
    {
        @SerializedName("FirstName")
        public String FirstName;

        @SerializedName("LastName")
        public String LastName;

        @SerializedName("FullName")
        public String FullName;

        @SerializedName("PatientID")
        public String PatientID;

        @SerializedName("Gender")
        public String Gender;

        @SerializedName("DOB")
        public String DOB;

        @SerializedName("Age")
        public String Age;

        @SerializedName("FamilyHdIND")
        public String FamilyHdIND;

        @SerializedName("FamilyHdID")
        public String FamilyHdID;

        @SerializedName("PatientOtherDetails")
        public ArrayList<PatientOtherDetails> arrPatientOtherDetails;

        @SerializedName("EmergencyInfo")
        public ArrayList<EmergencyInfo> arrEmergencyInfo;

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

        public String getPatientID() {
            return PatientID;
        }

        public void setPatientID(String patientID) {
            PatientID = patientID;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public String getDOB() {
            return DOB;
        }

        public void setDOB(String DOB) {
            this.DOB = DOB;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String age) {
            Age = age;
        }

        public String getFamilyHdIND() {
            return FamilyHdIND;
        }

        public void setFamilyHdIND(String familyHdIND) {
            FamilyHdIND = familyHdIND;
        }

        public String getFamilyHdID() {
            return FamilyHdID;
        }

        public void setFamilyHdID(String familyHdID) {
            FamilyHdID = familyHdID;
        }

        public ArrayList<PatientOtherDetails> getArrPatientOtherDetails() {
            return arrPatientOtherDetails;
        }

        public void setArrPatientOtherDetails(ArrayList<PatientOtherDetails> arrPatientOtherDetails) {
            this.arrPatientOtherDetails = arrPatientOtherDetails;
        }

        public ArrayList<EmergencyInfo> getArrEmergencyInfo() {
            return arrEmergencyInfo;
        }

        public void setArrEmergencyInfo(ArrayList<EmergencyInfo> arrEmergencyInfo) {
            this.arrEmergencyInfo = arrEmergencyInfo;
        }

        public class EmergencyInfo
        {
            public String getAlternateNumber() {
                return AlternateNumber;
            }

            public void setAlternateNumber(String alternateNumber) {
                AlternateNumber = alternateNumber;
            }

            public String getEmailID() {
                return EmailID;
            }

            public void setEmailID(String emailID) {
                EmailID = emailID;
            }

            public String getMobileNumber() {
                return MobileNumber;
            }

            public void setMobileNumber(String mobileNumber) {
                MobileNumber = mobileNumber;
            }

            public String getName() {
                return Name;
            }

            public void setName(String name) {
                Name = name;
            }

            public String getRelationship() {
                return Relationship;
            }

            public void setRelationship(String relationship) {
                Relationship = relationship;
            }

            @SerializedName("AlternateNumber")
            public String AlternateNumber;

            @SerializedName("EmailID")
            public String EmailID;

            @SerializedName("MobileNumber")
            public String MobileNumber;

            @SerializedName("Name")
            public String Name;

            @SerializedName("Relationship")
            public String Relationship;
        }

    public class PatientOtherDetails
    {

        @SerializedName("MaritalStatus")
        public String MaritalStatus;

        @SerializedName("Education")
        public String Education;

        @SerializedName("MaritalStatus")
        public String Occupation;

        public String getMaritalStatus() {
            return MaritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            MaritalStatus = maritalStatus;
        }

        public String getEducation() {
            return Education;
        }

        public void setEducation(String education) {
            Education = education;
        }

        public String getOccupation() {
            return Occupation;
        }

        public void setOccupation(String occupation) {
            Occupation = occupation;
        }
    }
    }

}
