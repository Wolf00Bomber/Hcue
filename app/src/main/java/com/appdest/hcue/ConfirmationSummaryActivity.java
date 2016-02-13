package com.appdest.hcue;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.DoctorsAppointmentResponse;
import com.appdest.hcue.utils.TimeUtils;

import android.content.Intent;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConfirmationSummaryActivity extends BaseActivity implements OnClickListener
{
	private LinearLayout llConfirm;
	private TextView tvTime,tvToken,tvDoctorName;
	private Button btnProvideDetails,btnAskMe;
	private DoctorsAppointmentResponse bookingDetails;

	@Override
	public void initializeControls() 
	{
		Intent i = getIntent();
		if(!i.hasExtra("BookingDetails"))
		{
			finish();
			return;
		}
		bookingDetails = (DoctorsAppointmentResponse) i.getSerializableExtra("BookingDetails");


		llConfirm = (LinearLayout) inflater.inflate(R.layout.confirmation_summary, null);
		llBody.addView(llConfirm,layoutParams);
		
		tvTime				=	(TextView)	llConfirm.findViewById(R.id.tvTime);
		tvToken				=	(TextView)	llConfirm.findViewById(R.id.tvToken);
		tvDoctorName		=	(TextView)	llConfirm.findViewById(R.id.tvDoctorName);
		
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

		String dateString =
		DateUtils.isToday(bookingDetails.getConsultationDt())
				? "Today"
				: TimeUtils.format2DateProper(bookingDetails.getConsultationDt());
		dateString = dateString +" "+ TimeUtils.format2hhmm(bookingDetails.getStartTime());

		tvTime.setText(dateString);
		tvToken.setText(bookingDetails.getTokenNumber());
	}

	@Override
	public void bindControls() 
	{
		
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

}
