package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 29-02-2016.
 */
public class AdditionalInfoRequest {

    @SerializedName("USRType")
    public String USRType;

    @SerializedName("USRId")
    public String USRId;


    @SerializedName("patientDetails")
    public ArrayList<patientDetails> arrpatientDetails ;

    @SerializedName("patientEmail")
    public ArrayList<patientEmail> arrpatientEmail ;

    @SerializedName("patientAddress")
    public ArrayList<patientAddress> arrpatientAddress ;

    @SerializedName("patientPhone")
    public ArrayList<patientPhone> arrpatientPhone ;


    public String getUSRId() {
        return USRId;
    }

    public void setUSRId(String USRId) {
        this.USRId = USRId;
    }

    public String getUSRType() {
        return USRType;
    }

    public void setUSRType(String USRType) {
        this.USRType = USRType;
    }

    public class patientDetails
    {
        @SerializedName("FullName")
        public String FullName;

        @SerializedName("Gender")
        public String Gender;

        @SerializedName("Age")
        public int Age;

        @SerializedName("MobileID")
        public String MobileID;

        public ArrayList<PatientOtherDetails> getArrPatientOtherDetails() {
            return arrPatientOtherDetails;
        }

        public void setArrPatientOtherDetails(ArrayList<PatientOtherDetails> arrPatientOtherDetails) {
            this.arrPatientOtherDetails = arrPatientOtherDetails;
        }

        @SerializedName("PatientOtherDetails")
        public ArrayList<PatientOtherDetails> arrPatientOtherDetails;

        public String getFullName() {
            return FullName;
        }

        public void setFullName(String fullName) {
            FullName = fullName;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public int getAge() {
            return Age;
        }

        public void setAge(int age) {
            Age = age;
        }

        public String getMobileID() {
            return MobileID;
        }

        public void setMobileID(String mobileID) {
            MobileID = mobileID;
        }

        public class PatientOtherDetails
        {
            @SerializedName("MaritalStatus")
            public String MaritalStatus;

            @SerializedName("Occupation")
            public String Occupation;

            @SerializedName("Education")
            public String Education;

            @SerializedName("Gender")
            public String Gender;

            @SerializedName("ReferralSource")
            public ArrayList<ReferralSource> arrReferralSource;

            public String getMaritalStatus() {
                return MaritalStatus;
            }

            public void setMaritalStatus(String maritalStatus) {
                MaritalStatus = maritalStatus;
            }

            public String getOccupation() {
                return Occupation;
            }

            public void setOccupation(String occupation) {
                Occupation = occupation;
            }

            public String getEducation() {
                return Education;
            }

            public void setEducation(String education) {
                Education = education;
            }

            public String getGender() {
                return Gender;
            }

            public void setGender(String gender) {
                Gender = gender;
            }

            public class ReferralSource
            {
                @SerializedName("FRIEND")
                public String FRIEND;

                @SerializedName("RELATION")
                public String RELATION;

                @SerializedName("NEWSPAPER")
                public String NEWSPAPER;

                @SerializedName("WEBSITE")
                public String WEBSITE;

                public String getRELATION() {
                    return RELATION;
                }

                public void setRELATION(String RELATION) {
                    this.RELATION = RELATION;
                }

                public String getNEWSPAPER() {
                    return NEWSPAPER;
                }

                public void setNEWSPAPER(String NEWSPAPER) {
                    this.NEWSPAPER = NEWSPAPER;
                }

                public String getWEBSITE() {
                    return WEBSITE;
                }

                public void setWEBSITE(String WEBSITE) {
                    this.WEBSITE = WEBSITE;
                }

                public String getFRIEND() {
                    return FRIEND;
                }

                public void setFRIEND(String FRIEND) {
                    this.FRIEND = FRIEND;
                }
            }

        }

    }

    public class patientEmail
    {
        @SerializedName("EmailID")
        public String EmailID;

        @SerializedName("EmailIDType")
        public Character EmailIDType;

        @SerializedName("PrimaryIND")
        public Character PrimaryIND;
    }
    public class patientAddress
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

    public class patientPhone
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





}
