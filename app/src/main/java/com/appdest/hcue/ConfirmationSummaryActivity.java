package com.appdest.hcue;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.DoctorsAppointmentResponse;
import com.appdest.hcue.model.GetDoctorsResponse;
import com.appdest.hcue.utils.TimeUtils;

import android.content.Intent;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ConfirmationSummaryActivity extends BaseActivity implements OnClickListener, TextToSpeech.OnInitListener
{
	private LinearLayout llConfirm;
	private TextView tvTime,tvToken,tvDoctorName, tvDownloadFooter;
	private Button btnProvideDetails,btnAskMe;
	private DoctorsAppointmentResponse bookingDetails;
    private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;

    //TTS object
    private TextToSpeech myTTS;
    //status check code
    private int MY_DATA_CHECK_CODE = 0;

    boolean isActivityNeedsFinish = false;

	@Override
	public void initializeControls() 
	{
        //check for TTS data
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

        Intent i = getIntent();
		if(!i.hasExtra("BookingDetails") || !i.hasExtra("DoctorDetails"))
		{
            isActivityNeedsFinish = true;
			finish();
			return;
		}
        selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail) i.getSerializableExtra("DoctorDetails");
		bookingDetails = (DoctorsAppointmentResponse) i.getSerializableExtra("BookingDetails");

		llConfirm = (LinearLayout) inflater.inflate(R.layout.confirmation_summary, null);
		llBody.addView(llConfirm,layoutParams);
		
		tvTime				=	(TextView)	llConfirm.findViewById(R.id.tvTime);
		tvToken				=	(TextView)	llConfirm.findViewById(R.id.tvToken);
		tvDoctorName		=	(TextView)	llConfirm.findViewById(R.id.tvDoctorName);
        tvDownloadFooter    =   (TextView)  llConfirm.findViewById(R.id.tvDownloadFooter);
		btnProvideDetails	=	(Button)	llConfirm.findViewById(R.id.btnProvideDetails);
		btnAskMe			=	(Button)	llConfirm.findViewById(R.id.btnAskMe);
		
		btnProvideDetails.setOnClickListener(this);
		btnAskMe.setOnClickListener(this);
		
		setSpecificTypeFace(llConfirm, AppConstants.WALSHEIM_LIGHT);
		
		tvTime.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		tvToken.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		tvDoctorName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		btnProvideDetails.setTypeface(AppConstants.WALSHEIM_BOLD);
		btnAskMe.setTypeface(AppConstants.WALSHEIM_BOLD);
		
		tvTitle.setText("Booking Confirmation");
        tvDoctorName.setText(selectedDoctorDetails.FullName);
        tvDownloadFooter.setText(Html.fromHtml("Download our <font color=\"#F57103\">hCue Patient App</font> from Google play store"));
        String dateString = DateUtils.isToday(bookingDetails.getConsultationDt()) ? "Today" : TimeUtils.format2DateProper(bookingDetails.getConsultationDt());
        String footer = "<b>" + dateString + ", " + TimeUtils.format2hhmmaa(bookingDetails.getStartTime()) + "</b>" + " with";
		tvTime.setText(Html.fromHtml(footer));
		tvToken.setText(bookingDetails.getTokenNumber());
	}

    //act on result of TTS data check
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                //the user has the necessary data - create the TTS
                myTTS = new TextToSpeech(this, this);
                if(bookingDetails != null && !TextUtils.isEmpty(bookingDetails.getTokenNumber()))
                    speakWords("Your token number is " + bookingDetails.getTokenNumber());
            }
            else {
                //no data - install it now
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    //speak the user text
    private void speakWords(final String speech) {

        //speak straight away
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
            }
        }, 400);

    }

	@Override
	public void bindControls() 
	{
        if(isActivityNeedsFinish)
            return;
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
			case R.id.btnProvideDetails:
				Intent intent = new Intent(ConfirmationSummaryActivity.this,EnterMailActivity.class);
				intent.putExtra("BookingDetails", bookingDetails);
				startActivity(intent);
				break;
			case R.id.btnAskMe:
				Intent askme = new Intent(ConfirmationSummaryActivity.this,SelectDoctorActivity.class);
				askme.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(askme);
				break;
		}
	}

    @Override
    public void onInit(int initStatus) {
        if (initStatus == TextToSpeech.SUCCESS) {
            if(myTTS.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.US);
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }
}
