package com.appdest.hcue;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdest.hcue.common.AppConstants;

public class ConfirmationFullViewActivity extends BaseActivity implements OnClickListener
{
	private LinearLayout llConfirm;
	private TextView tvDoctorName,tvTime,tvTokenNumber;
	private Button btnOk;

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
		btnOk.setTypeface(AppConstants.WALSHEIM_BOLD);
		tvDoctorName.setTypeface(AppConstants.WALSHEIM_BOLD);

		tvTitle.setText("Confirmation Summary");
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
		case R.id.btnOk:
			Intent intent = new Intent(ConfirmationFullViewActivity.this,SelectDoctorForFeedbackActivity.class);
			startActivity(intent);
			break;
		}
	}

}
