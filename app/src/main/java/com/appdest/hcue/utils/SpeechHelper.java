package com.appdest.hcue.utils;

import java.util.Locale;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

public class SpeechHelper {
	private static Context mContext;
	private static SpeechHelper instance;
	private static TextToSpeech textToSpeech;

	private SpeechHelper(){

	}

	public static SpeechHelper getInstance(Context context) {
		mContext = context;
		if(instance == null)
			instance = new SpeechHelper();
		return instance;
	}

	public void initTextSpeaker() {
		if(instance != null && mContext != null) {
			textToSpeech = new TextToSpeech(mContext, new OnInitListener() {

				@Override
				public void onInit(int status) {
					if(status == TextToSpeech.SUCCESS) {
						textToSpeech.setLanguage(Locale.UK);
					} else {
						Log.e("Helper", "Error occured while initializing text speaker.");
					}
				}
			});
		} else {
			Log.e("Helper", "Not get an Instance or context is null");
		}
	}
	
	public void startSpeak(final String message){
		if(textToSpeech != null) {
			textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
		} else {
			if(mContext != null) {
				textToSpeech = new TextToSpeech(mContext, new OnInitListener() {

					@Override
					public void onInit(int status) {
						if(status == TextToSpeech.SUCCESS) {
							textToSpeech.setLanguage(Locale.UK);
							textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null);
						} else {
							Log.e("Helper", "Error occured while initializing text speaker.");
						}
					}
				});
			} else {
				Log.e("Helper", "Context is null");
			}
		}
	}
	
	public void stopSpeak() {
		if(textToSpeech !=null){
			textToSpeech.stop();
			textToSpeech.shutdown();
		}
	}
}
