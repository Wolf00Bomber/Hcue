package com.appdest.hcue;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.DoctorsAppointmentResponse;
import com.appdest.hcue.model.GetDoctorsResponse;
import com.appdest.hcue.utils.SpeechHelper;
import com.appdest.hcue.utils.TimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfirmationSummaryActivity extends BaseActivity implements OnClickListener {
	private LinearLayout llConfirm;
	private TextView tvTime,tvToken,tvDoctorName, tvDownloadFooter;
	private Button btnProvideDetails,btnAskMe;
	private DoctorsAppointmentResponse bookingDetails;
    private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;
    boolean isActivityNeedsFinish = false;

	@Override
	public void initializeControls() {
        Intent i = getIntent();
		if(!i.hasExtra("BookingDetails") || !i.hasExtra("DoctorDetails")){
            isActivityNeedsFinish = true;
			finish();
			return;
		}
        selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail) i.getSerializableExtra("DoctorDetails");
		bookingDetails = (DoctorsAppointmentResponse) i.getSerializableExtra("BookingDetails");
		if(bookingDetails != null && !TextUtils.isEmpty(bookingDetails.getTokenNumber()))
			SpeechHelper.getInstance(ConfirmationSummaryActivity.this).startSpeak("Your token number is " + bookingDetails.getTokenNumber());

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

		tvBack.setVisibility(View.INVISIBLE);
		
		setSpecificTypeFace(llConfirm, AppConstants.WALSHEIM_LIGHT);
		
		tvTime.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		tvToken.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		tvDoctorName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		btnProvideDetails.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		btnAskMe.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		
		tvTitle.setText("Booking Confirmation");
        tvDoctorName.setText("Dr. "+selectedDoctorDetails.FullName);
        tvDownloadFooter.setText(Html.fromHtml("Download our <font color=\"#F57103\">hCue Patient App</font> from Google play store"));
        String dateString = DateUtils.isToday(bookingDetails.getConsultationDt()) ? "Today" : TimeUtils.format2DateProper(bookingDetails.getConsultationDt());
        String footer = "<b>" + dateString + ", " + /*TimeUtils.format2hhmm(bookingDetails.getStartTime())*/ bookingDetails.getStartTime() + "</b>" + " with";
		tvTime.setText(Html.fromHtml(footer));
		tvToken.setText(bookingDetails.getTokenNumber());
	}

	@Override
	public void bindControls() 
	{
		tvLogin.setEnabled(false);
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
				intent.putExtra("DoctorDetails", selectedDoctorDetails);
				startActivity(intent);
				break;
			case R.id.btnAskMe:
				Intent askme = new Intent(ConfirmationSummaryActivity.this,SelectDoctorActivity.class);
				askme.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(askme);
				break;
		}
	}

	private String getFormattedTime(String time) {
		String formattedTime = "";
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
			final Date dateObj = sdf.parse(time);
			formattedTime = new SimpleDateFormat("hh:mm").format(dateObj).toUpperCase();
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return formattedTime;
	}
}
