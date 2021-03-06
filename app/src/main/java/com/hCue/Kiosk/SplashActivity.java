package com.hCue.Kiosk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.hCue.Kiosk.common.AppConstants;
import com.hCue.Kiosk.model.Speciality;
import com.hCue.Kiosk.services.RestCallback;
import com.hCue.Kiosk.services.RestClient;
import com.hCue.Kiosk.services.RestError;
import com.hCue.Kiosk.utils.Connectivity;
import com.hCue.Kiosk.utils.Preference;
import com.google.gson.Gson;

import java.util.List;

import retrofit.client.Response;


public class SplashActivity extends Activity {
    private final int SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        initializeFonts();
    }

    private void initializeFonts() {
        AppConstants.WALSHEIM_BOLD 				= Typeface.createFromAsset(getApplicationContext().getAssets(), "GT-Walsheim-Bold.ttf");;
        AppConstants.WALSHEIM_BOLD_OBLIQUE 		= Typeface.createFromAsset(getApplicationContext().getAssets(), "GT-Walsheim-Bold-Oblique.ttf");;
        AppConstants.WALSHEIM_LIGHT_OBLIQUE 	= Typeface.createFromAsset(getApplicationContext().getAssets(), "GT-Walsheim-Light-Oblique.ttf");;
        AppConstants.WALSHEIM_LIGHT 			= Typeface.createFromAsset(getApplicationContext().getAssets(), "GT-Walsheim-Light.ttf");;
        AppConstants.WALSHEIM_MEDIUM_OBLIQUE	= Typeface.createFromAsset(getApplicationContext().getAssets(), "GT-Walsheim-Medium-Oblique.ttf");;
        AppConstants.WALSHEIM_MEDIUM			= Typeface.createFromAsset(getApplicationContext().getAssets(), "GT-Walsheim-Medium.ttf");;
        AppConstants.LATO 						= Typeface.createFromAsset(getApplicationContext().getAssets(), "Lato-Regular.ttf");;
        AppConstants.MYRAIDPRO_REGULAR 			= Typeface.createFromAsset(getApplicationContext().getAssets(), "MyriadPro-Regular.otf");;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void getSpecialityDetails() {
        String url = "http://dct4avjn1lfw.cloudfront.net";

        RestClient.getAPI(url).getSpecialityMap(new RestCallback<List<Speciality>>() {

            @Override
            public void success(List<Speciality> specialities, Response response) {

                String json = new Gson().toJson(specialities);
                if (!TextUtils.isEmpty(json)) {
                    Preference preference = new Preference(SplashActivity.this);
                    preference.saveStringInPreference(Preference.SPECIALITIES_MAP, json);
                    preference.commitPreference();
                }
                moveToNextScreen();
            }

            @Override
            public void failure(RestError restError) {
                Log.e("Specialities", "" + restError.getErrorMessage());
                Toast.makeText(SplashActivity.this, "Couldn't update the List of Specialities.", Toast.LENGTH_SHORT).show();
                moveToNextScreen();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Connectivity.isConnected(SplashActivity.this)) {
            getSpecialityDetails();
        } else {
            Toast.makeText(SplashActivity.this, getString(R.string.internet_unavailable), Toast.LENGTH_SHORT).show();
            moveToNextScreen();
        }
    }

    public void moveToNextScreen() {
        Preference preference = new Preference(SplashActivity.this);
        if(preference.getbooleanFromPreference(Preference.IS_LOGGEDIN, false)) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent splashActivity = new Intent(getApplicationContext(), SelectDoctorActivity.class);
                    startActivity(splashActivity);
                    finish();
                }
            }, SPLASH_TIME/2);
        } else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent splashActivity = new Intent(getApplicationContext(), AdminLoginActivity.class);
                    startActivity(splashActivity);
                    finish();
                }
            }, SPLASH_TIME/2);
        }
    }
}
