package com.appdest.hcue.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class AdminGetDoctorsResponse {
	@SerializedName("DoctorCount")
	public int DoctorCount;
	@SerializedName("HospitalInfo")
	public HospitalInfo hospitalInfo;
	@SerializedName("DoctorDetails")
	public ArrayList<DoctorDetails> listDoctorDetails;
	
	public int getDoctorCount() {
		return DoctorCount;
	}

	public void setDoctorCount(int doctorCount) {
		DoctorCount = doctorCount;
	}

	public HospitalInfo getHospitalInfo() {
		return hospitalInfo;
	}

	public void setHospitalInfo(HospitalInfo hospitalInfo) {
		this.hospitalInfo = hospitalInfo;
	}

	public ArrayList<DoctorDetails> getListDoctorDetails() {
		return listDoctorDetails;
	}

	public void setListDoctorDetails(ArrayList<DoctorDetails> listDoctorDetails) {
		this.listDoctorDetails = listDoctorDetails;
	}
	
	public class HospitalInfo {
		@SerializedName("HospitalDetails")
		public HospitalDetails hospitalDetails;
		@SerializedName("HospitalAddress")
		public HospitalAddress hospitalAddress;
		
		public HospitalDetails getHospitalDetails() {
			return hospitalDetails;
		}
		public void setHospitalDetails(HospitalDetails hospitalDetails) {
			this.hospitalDetails = hospitalDetails;
		}
		public HospitalAddress getHospitalAddress() {
			return hospitalAddress;
		}
		public void setHospitalAddress(HospitalAddress hospitalAddress) {
			this.hospitalAddress = hospitalAddress;
		}
	}
	

	public class HospitalDetails {
		@SerializedName("HospitalID")
		private int HospitalID;
		@SerializedName("ParentHospitalID")
		private int ParentHospitalID;
		@SerializedName("HospitalName")
		private String HospitalName;
		@SerializedName("ContactName")
		private String ContactName;
		@SerializedName("TINNumber")
		private String TINNumber;
		@SerializedName("MobileNumber")
		private int MobileNumber;
		@SerializedName("EmailAddress")
		private String EmailAddress;
		@SerializedName("WebSite")
		private String WebSite;
		@SerializedName("About")
		private String About;
		@SerializedName("RegsistrationDate")
		private int RegsistrationDate;
		@SerializedName("LicenseNumber")
		private String LicenseNumber;
		@SerializedName("ActiveIND")
		private String ActiveIND;
		@SerializedName("TermsAccepted")
		private String TermsAccepted;

		public int getHospitalID() {
			return HospitalID;
		}
		public void setHospitalID(int HospitalID) {
			this.HospitalID = HospitalID;
		}
		public int getParentHospitalID() {
			return ParentHospitalID;
		}
		public void setParentHospitalID(int ParentHospitalID) {
			this.ParentHospitalID = ParentHospitalID;
		}
		public String getHospitalName() {
			return HospitalName;
		}
		public void setHospitalName(String HospitalName) {
			this.HospitalName = HospitalName;
		}
		public String getContactName() {
			return ContactName;
		}
		public void setContactName(String ContactName) {
			this.ContactName = ContactName;
		}
		public String getTINNumber() {
			return TINNumber;
		}
		public void setTINNumber(String TINNumber) {
			this.TINNumber = TINNumber;
		}
		public int getMobileNumber() {
			return MobileNumber;
		}
		public void setMobileNumber(int MobileNumber) {
			this.MobileNumber = MobileNumber;
		}
		public String getEmailAddress() {
			return EmailAddress;
		}
		public void setEmailAddress(String EmailAddress) {
			this.EmailAddress = EmailAddress;
		}
		public String getWebSite() {
			return WebSite;
		}
		public void setWebSite(String WebSite) {
			this.WebSite = WebSite;
		}
		public String getAbout() {
			return About;
		}
		public void setAbout(String About) {
			this.About = About;
		}
		public int getRegsistrationDate() {
			return RegsistrationDate;
		}
		public void setRegsistrationDate(int RegsistrationDate) {
			this.RegsistrationDate = RegsistrationDate;
		}
		public String getLicenseNumber() {
			return LicenseNumber;
		}
		public void setLicenseNumber(String LicenseNumber) {
			this.LicenseNumber = LicenseNumber;
		}
		public String getActiveIND() {
			return ActiveIND;
		}
		public void setActiveIND(String ActiveIND) {
			this.ActiveIND = ActiveIND;
		}
		public String getTermsAccepted() {
			return TermsAccepted;
		}
		public void setTermsAccepted(String TermsAccepted) {
			this.TermsAccepted = TermsAccepted;
		}
	}

	public class HospitalAddress {
		@SerializedName("Address1")
		private String Address1;
		@SerializedName("Address2")
		private String Address2;
		@SerializedName("Street")
		private String Street;
		@SerializedName("Location")
		private String Location;
		@SerializedName("CityTown")
		private String CityTown;
		@SerializedName("DistrictRegion")
		private String DistrictRegion;
		@SerializedName("State")
		private String State;
		@SerializedName("PinCode")
		private int PinCode;
		@SerializedName("Country")
		private String Country;
		@SerializedName("LandMark")
		private String LandMark;
		@SerializedName("Latitude")
		private String Latitude;
		@SerializedName("Longitude")
		private String Longitude;

		public String getAddress1() {
			return Address1;
		}
		public void setAddress1(String Address1) {
			this.Address1 = Address1;
		}
		public String getAddress2() {
			return Address2;
		}
		public void setAddress2(String Address2) {
			this.Address2 = Address2;
		}
		public String getStreet() {
			return Street;
		}
		public void setStreet(String Street) {
			this.Street = Street;
		}
		public String getLocation() {
			return Location;
		}
		public void setLocation(String Location) {
			this.Location = Location;
		}
		public String getCityTown() {
			return CityTown;
		}
		public void setCityTown(String CityTown) {
			this.CityTown = CityTown;
		}
		public String getDistrictRegion() {
			return DistrictRegion;
		}
		public void setDistrictRegion(String DistrictRegion) {
			this.DistrictRegion = DistrictRegion;
		}
		public String getState() {
			return State;
		}
		public void setState(String State) {
			this.State = State;
		}
		public int getPinCode() {
			return PinCode;
		}
		public void setPinCode(int PinCode) {
			this.PinCode = PinCode;
		}
		public String getCountry() {
			return Country;
		}
		public void setCountry(String Country) {
			this.Country = Country;
		}
		public String getLandMark() {
			return LandMark;
		}
		public void setLandMark(String LandMark) {
			this.LandMark = LandMark;
		}
		public String getLatitude() {
			return Latitude;
		}
		public void setLatitude(String Latitude) {
			this.Latitude = Latitude;
		}
		public String getLongitude() {
			return Longitude;
		}
		public void setLongitude(String Longitude) {
			this.Longitude = Longitude;
		}
	}

	public class DoctorDetails {
		@SerializedName("DoctorID")
		private int DoctorID;
		@SerializedName("FullName")
		private String FullName;
		@SerializedName("AddressID")
		private int AddressID;
		@SerializedName("Available")
		private String Available;
		@SerializedName("ImageURL")
		private String ImageURL;
		@SerializedName("SpecialityCD")
		private Map<String, String> mapSpecialityCD;
		

		public Map<String, String> getMapSpecialityCD() {
			return mapSpecialityCD;
		}
		public void setMapSpecialityCD(Map<String, String> mapSpecialityCD) {
			this.mapSpecialityCD = mapSpecialityCD;
		}
		public int getDoctorID() {
			return DoctorID;
		}
		public void setDoctorID(int DoctorID) {
			this.DoctorID = DoctorID;
		}
		public String getFullName() {
			return FullName;
		}
		public void setFullName(String FullName) {
			this.FullName = FullName;
		}
		public int getAddressID() {
			return AddressID;
		}
		public void setAddressID(int AddressID) {
			this.AddressID = AddressID;
		}
		public String getAvailable() {
			return Available;
		}
		public void setAvailable(String Available) {
			this.Available = Available;
		}
		public String getImageURL() {
			return ImageURL;
		}
		public void setImageURL(String ImageURL) {
			this.ImageURL = ImageURL;
		}
	}
}
