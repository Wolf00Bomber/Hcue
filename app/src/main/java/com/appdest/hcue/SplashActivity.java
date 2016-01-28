package com.appdest.hcue;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.utils.Preference;


public class SplashActivity extends Activity
{
    private final int SPLASH_TIME = 2000;
    private Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        initializeFonts();
        preference = new Preference(this);
        
    }

    private void initializeFonts()
    {
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

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                moveToNextScreen();
            }
        }, SPLASH_TIME);
    }


    public void moveToNextScreen() 
    {
          // New User
            Intent splashActivty = new Intent(getApplicationContext(), SelectDoctorActivity.class);
            startActivity(splashActivty);
        
        finish();

    }

}
