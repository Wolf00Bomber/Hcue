package com.appdest.hcue.common;

import android.app.Application;
import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by Vinsan on 2/25/2016.
 */
public class HCueApplication extends Application {
    private TextToSpeech t1;
    private static HCueApplication hCueApplication;

    public HCueApplication() {

    }

    public static HCueApplication getInstance(Context c) {
        if(hCueApplication == null)
        {
            hCueApplication = new HCueApplication(c);
        }
        return hCueApplication;
    }

    private HCueApplication(Context c) {
//        if(t1 == null)
        {
            t1 = new TextToSpeech(c, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        t1.setLanguage(Locale.UK);
                    }
                }
            });
        }
    }

    public void startSpeak(String message){
        if(message != null && !message.isEmpty() && t1 != null){
            t1.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void stopSpeak(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
    }
}
