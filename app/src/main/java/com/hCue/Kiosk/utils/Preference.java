package com.hCue.Kiosk.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

@SuppressLint("CommitPrefEdits")
public class Preference {

	private SharedPreferences preferences;
	private SharedPreferences.Editor edit;
	public static String SPECIALITIES_MAP = "Specialities";
	public static String IS_INSTALLED = "isInstalled";
	public static String SYNC_STATUS = "SYNCSTATUS";
	public static String USER_ROLE = "user_role";
	public static String USER_ID = "user_id";
	public static String LAST_USER_ID = "last_user_id";
	public static String LAST_PASSWORD = "last_password";
	public static String REMEMBER_ME = "REMEMBER_ME";
	public static final String SQLITE_DATE = "SQLITE_DATE";
	public static final String ORG_CODE = "ORG_CODE";
	public static String DEVICE_DISPLAY_WIDTH = "DEVICE_DISPLAY_WIDTH";
	public static String DEVICE_DISPLAY_HEIGHT = "DEVICE_DISPLAY_HEIGHT";
	public static final String gcmId = "gcmId";
	
	public static String USER_NAME = "USERNAME";
	public static String FIRST_NAME = "FIRSTNAME";
	public static String LAST_NAME = "LASTNAME";
	public static String EMAIL_ID = "EMAIL_ID";
	public static String PASSWORD = "PASSWORD";
	public static String GENDER = "GENDER";
	public static String CITY = "CITY";
	public static String DOB = "DOB";
	public static String PHONE_NUMBER = "PHONE_NUMBER";
	
/** Profile Image Url as obtained by Social Media APIs*/
	public static String PROFILE_PIC_URL = "PROFILE_PIC_URL";

	
	
	public static final String BUILD_INSTALLATIONDATE = "BUILD_INSTALLATION_DATE";
	
	public static String INT_USER_ID 		= "int_user_id";

	public static String IS_APP_FIRSTTIME_LAUNCH 		= "IS_APP_FIRSTTIME_LAUNCH";

	public static String IS_LOGGEDIN = "IS_LOGGEDIN"; //boolean
	public static String IS_CLINIC = "IS_CLINIC"; //boolean
	public static String LOGIN_DOCTOR_ID = "LOGIN_DOCTOR_ID"; //integer
	public static String SELECTED_CLINIC_ADDRESS_ID = "SELECTED_CLINIC_ADDRESS_ID"; //integer
	public static String SELECTED_HOSPITAL_ID = "SELECTED_HOSPITAL_ID"; //integer
	public static String SELECTED_DOCTORS = "SELECTED_DOCTORS"; //string
	public static String CLINIC_NAME = "CLINIC_NAME"; //string
	public static String ADMIN_ID = "ADMIN_ID"; //string
	public static String ADMIN_PASSWORD = "ADMIN_PASSWORD"; //string
	public static String ADDITIONAL_INFO = "ADDITIONAL_INFO"; //string

	
	public Preference(Context context) {
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
		edit = preferences.edit();
	}

	public void saveStringInPreference(String strKey, String strValue) {
		edit.putString(strKey, strValue);
	}

	public void saveIntInPreference(String strKey, int value) {
		edit.putInt(strKey, value);
	}

	public void saveBooleanInPreference(String strKey, boolean value) {
		edit.putBoolean(strKey, value);
	}

	public void saveLongInPreference(String strKey, Long value) {
		edit.putLong(strKey, value);
	}

	public void saveDoubleInPreference(String strKey, String value) {
		edit.putString(strKey, value);
	}

	public void removeFromPreference(String strKey) {
		edit.remove(strKey);
	}

	public void commitPreference() {
		edit.commit();
	}

	public String getStringFromPreference(String strKey, String defaultValue) {
		return preferences.getString(strKey, defaultValue);
	}

	public boolean getbooleanFromPreference(String strKey, boolean defaultValue) {
		return preferences.getBoolean(strKey, defaultValue);
	}

	public int getIntFromPreference(String strKey, int defaultValue) {
		return preferences.getInt(strKey, defaultValue);
	}

	public double getDoubleFromPreference(String strKey, double defaultValue) {
		return Double.parseDouble(preferences.getString(strKey, ""
				+ defaultValue));
	}

	public long getLongInPreference(String strKey) {
		return preferences.getLong(strKey, 0);
	}

	public void clearPreferences() {
		edit.clear();
		edit.commit();
	}
}
