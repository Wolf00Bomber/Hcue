package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 29-02-2016.
 */
public class AdditionalInfoResponse {

    @SerializedName("USRType")
    public String USRType;

    @SerializedName("USRId")
    public int USRId;


    @SerializedName("patientDetails")
    public patientDetails patientDetails = new patientDetails();

    public AdditionalInfoResponse.patientDetails getPatientDetails() {
        return patientDetails;
    }

    public void setPatientDetails(AdditionalInfoResponse.patientDetails patientDetails) {
        this.patientDetails = patientDetails;
    }

    public ArrayList<patientEmail> getArrpatientEmail() {
        /*if(arrpatientEmail.isEmpty())
        {
            arrpatientEmail.add(new patientEmail());
        }*/
        return arrpatientEmail;
    }

    public void setArrpatientEmail(ArrayList<patientEmail> arrpatientEmail) {
        this.arrpatientEmail = arrpatientEmail;
    }

    public ArrayList<patientAddress> getArrpatientAddress() {
        /*if(arrpatientAddress.isEmpty())
        {
            arrpatientAddress.add(new patientAddress());
        }*/
        return arrpatientAddress;
    }

    public void setArrpatientAddress(ArrayList<patientAddress> arrpatientAddress) {
        this.arrpatientAddress = arrpatientAddress;
    }

    public ArrayList<patientPhone> getArrpatientPhone() {
        /*if(arrpatientPhone.isEmpty())
        {
            arrpatientPhone.add(new patientPhone());
        }*/
        return arrpatientPhone;
    }

    public void setArrpatientPhone(ArrayList<patientPhone> arrpatientPhone) {
        this.arrpatientPhone = arrpatientPhone;
    }

    @SerializedName("patientEmail")
    public ArrayList<patientEmail> arrpatientEmail = new ArrayList<>();

    @SerializedName("patientAddress")
    public ArrayList<patientAddress> arrpatientAddress = new ArrayList<>();

    @SerializedName("patientPhone")
    public ArrayList<patientPhone> arrpatientPhone = new ArrayList<>();


    public int getUSRId() {
        return USRId;
    }

    public void setUSRId(int USRId) {
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
        public String FullName = "";

        @SerializedName("Gender")
        public String Gender ="";

        @SerializedName("Age")
        public int Age = 0;

        @SerializedName("MobileID")
        public Number MobileID = 0;

        @SerializedName("TermsAccepted")
        public String TermsAccepted = "Y";

        @SerializedName("AadhaarID")
        public int AadhaarID = 0;

        @SerializedName("FirstName")
        public String FirstName = "";

        public int getAadhaarID() {
            return AadhaarID;
        }

        public void setAadhaarID(int aadhaarID) {
            AadhaarID = aadhaarID;
        }

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

        public String getPatientLoginID() {
            return PatientLoginID;
        }

        public void setPatientLoginID(String patientLoginID) {
            PatientLoginID = patientLoginID;
        }

        public String getPatientPassword() {
            return PatientPassword;
        }

        public void setPatientPassword(String patientPassword) {
            PatientPassword = patientPassword;
        }

        @SerializedName("LastName")
        public String LastName = "";

        @SerializedName("PatientLoginID")
        public String PatientLoginID = "0";

        @SerializedName("PatientPassword")
        public String PatientPassword = "";

        public String getTermsAccepted() {
            return TermsAccepted;
        }

        public void setTermsAccepted(String termsAccepted) {
            TermsAccepted = termsAccepted;
        }



        public AdditionalInfoResponse.patientDetails.PatientOtherDetails getPatientOtherDetails() {
            return PatientOtherDetails;
        }

        public void setPatientOtherDetails(AdditionalInfoResponse.patientDetails.PatientOtherDetails patientOtherDetails) {
            PatientOtherDetails = patientOtherDetails;
        }

        @SerializedName("EmergencyInfo")
        public EmergencyInfo EmergencyInfo = new EmergencyInfo();

        public AdditionalInfoResponse.patientDetails.EmergencyInfo getEmergencyInfo() {
            return EmergencyInfo;
        }

        public void setEmergencyInfo(AdditionalInfoResponse.patientDetails.EmergencyInfo emergencyInfo) {
            EmergencyInfo = emergencyInfo;
        }

        @SerializedName("PatientOtherDetails")
        public PatientOtherDetails PatientOtherDetails = new PatientOtherDetails();

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

        public Number getMobileID() {
            return MobileID;
        }

        public void setMobileID(Number mobileID) {
            MobileID = mobileID;
        }

        public class EmergencyInfo
        {
            @SerializedName("AlternateNumber")
            public String AlternateNumber = "";

            @SerializedName("EmailID")
            public String EmailID = "";

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

            @SerializedName("MobileNumber")
            public String MobileNumber = "0";

            @SerializedName("Name")
            public String Name = "";

            @SerializedName("Relationship")
            public String Relationship = "";
        }

        public class PatientOtherDetails
        {
            @SerializedName("MaritalStatus")
            public String MaritalStatus = "N";

            @SerializedName("Occupation")
            public String Occupation = "";

            @SerializedName("Education")
            public String Education = "";

            public String getPaymentMode() {
                return PaymentMode;
            }

            public void setPaymentMode(String paymentMode) {
                PaymentMode = paymentMode;
            }

            @SerializedName("PaymentMode")

            public String PaymentMode = "";

            @SerializedName("ReferralSource")
            public ReferralSource ReferralSource = new ReferralSource();

            public AdditionalInfoResponse.patientDetails.PatientOtherDetails.ReferralSource getReferralSource() {
                return ReferralSource;
            }

            public void setReferralSource(AdditionalInfoResponse.patientDetails.PatientOtherDetails.ReferralSource referralSource) {
                ReferralSource = referralSource;
            }

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



            public class ReferralSource
            {
                @SerializedName("FRIEND")
                public String FRIEND = "";

                @SerializedName("RELATION")
                public String RELATION = "";

                @SerializedName("NEWSPAPER")
                public String NEWSPAPER = "";

                @SerializedName("WEBSITE")
                public String WEBSITE = "";

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
