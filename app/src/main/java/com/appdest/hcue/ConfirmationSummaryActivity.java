package com.appdest.hcue;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.DoctorsAppointmentResponse;
import com.appdest.hcue.utils.TimeUtils;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConfirmationSummaryActivity extends BaseActivity implements OnClickListener
{
	private LinearLayout llConfirm;
	private TextView tvTime,tvToken;
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
		llBody.addView(llConfirm);
		
		tvTime				=	(TextView)	llConfirm.findViewById(R.id.tvTime);
		tvToken				=	(TextView)	llConfirm.findViewById(R.id.tvToken);
		
		btnProvideDetails	=	(Button)	llConfirm.findViewById(R.id.btnProvideDetails);
		btnAskMe			=	(Button)	llConfirm.findViewById(R.id.btnAskMe);
		
		btnProvideDetails.setOnClickListener(this);
		btnAskMe.setOnClickListener(this);
		
		setSpecificTypeFace(llConfirm, AppConstants.WALSHEIM_LIGHT);
		
		tvTime.setTypeface(AppConstants.WALSHEIM_BOLD);
		tvToken.setTypeface(AppConstants.WALSHEIM_BOLD);
		btnProvideDetails.setTypeface(AppConstants.WALSHEIM_BOLD);
		btnAskMe.setTypeface(AppConstants.WALSHEIM_BOLD);
		
		tvTitle.setText("Confirmation Summary");

		tvTime.setText(TimeUtils.format2Date(bookingDetails.getConsultationDt()));
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
				break;
		}
	}

}
