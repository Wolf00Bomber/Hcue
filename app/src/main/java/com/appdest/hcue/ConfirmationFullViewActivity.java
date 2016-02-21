package com.appdest.hcue;

import android.content.Intent;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.DoctorsAppointmentResponse;
import com.appdest.hcue.model.GetDoctorsResponse;
import com.appdest.hcue.utils.TimeUtils;

public class ConfirmationFullViewActivity extends BaseActivity implements OnClickListener
{
	private LinearLayout llConfirm;
	private TextView tvDoctorName,tvTime,tvTokenNumber;
	private Button btnOk;
	boolean isActivityNeedsFinish = false;
	private DoctorsAppointmentResponse bookingDetails;
	private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;

	@Override
	public void initializeControls() 
	{
		llConfirm = (LinearLayout) inflater.inflate(R.layout.confirmation_fullview, null);
		llBody.addView(llConfirm);

		tvTime				=	(TextView)	llConfirm.findViewById(R.id.tvTime);
		tvTokenNumber		=	(TextView)	llConfirm.findViewById(R.id.tvTokenNumber);
		tvDoctorName		=	(TextView)	llConfirm.findViewById(R.id.tvDoctorName);

		btnOk				=	(Button)	llConfirm.findViewById(R.id.btnOk);

		btnOk.setOnClickListener(this);

		setSpecificTypeFace(llConfirm, AppConstants.WALSHEIM_LIGHT);

		tvTime.setTypeface(AppConstants.WALSHEIM_BOLD);
		tvTokenNumber.setTypeface(AppConstants.WALSHEIM_BOLD);
		btnOk.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		tvDoctorName.setTypeface(AppConstants.WALSHEIM_BOLD);

		tvTitle.setText("Confirmation Summary");
	}

	@Override
	public void bindControls() 
	{
		tvLogin.setEnabled(false);
		Intent i = getIntent();
		if(!i.hasExtra("BookingDetails") || !i.hasExtra("DoctorDetails"))
		{
			isActivityNeedsFinish = true;
			finish();
			return;
		}
		selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail) i.getSerializableExtra("DoctorDetails");
		bookingDetails = (DoctorsAppointmentResponse) i.getSerializableExtra("BookingDetails");

		tvDoctorName.setText(selectedDoctorDetails.FullName);
		String dateString = DateUtils.isToday(bookingDetails.getConsultationDt()) ? "Today" : TimeUtils.format2DateProper(bookingDetails.getConsultationDt());
		String footer = "<b>" + dateString + ", " + TimeUtils.format2hhmmaa(bookingDetails.getStartTime()) + "</b>" + " with";
		tvTime.setText(Html.fromHtml(footer));
        tvTokenNumber.setText(bookingDetails.getTokenNumber());
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnOk:
			Intent intent = new Intent(ConfirmationFullViewActivity.this,SelectDoctorActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		}
	}

}
