package com.appdest.hcue;

import com.appdest.hcue.common.AppConstants;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChoosePatientActivity extends BaseActivity implements OnClickListener
{
	private LinearLayout llPatient,llAddPatient,llPatientDetails;
	private TextView tvPat,tvMat,tvGender,tvAge,tvAdd,tvChoosePatient;

	@Override
	public void initializeControls() 
	{
		llPatient = (LinearLayout) inflater.inflate(R.layout.choose_patitent, null);
		llBody.addView(llPatient);
		
		tvPat			 =	(TextView)	llPatient.findViewById(R.id.tvPat);
		tvMat			 =	(TextView)	llPatient.findViewById(R.id.tvMat);
		tvGender		 =	(TextView)	llPatient.findViewById(R.id.tvGender);
		tvAge			 =	(TextView)	llPatient.findViewById(R.id.tvAge);
		tvAdd			 =	(TextView)	llPatient.findViewById(R.id.tvAdd);
		tvChoosePatient	 =	(TextView)	llPatient.findViewById(R.id.tvChoosePatient);
		
		llAddPatient	 =	(LinearLayout) llPatient.findViewById(R.id.llAddPatient);
		llPatientDetails =	(LinearLayout) llPatient.findViewById(R.id.llPatientDetails);
		
		setSpecificTypeFace(llPatient, AppConstants.WALSHEIM_LIGHT);
		
		tvPat.setTypeface(AppConstants.WALSHEIM_BOLD);
		tvMat.setTypeface(AppConstants.WALSHEIM_BOLD);
		tvAdd.setTypeface(AppConstants.WALSHEIM_BOLD);
		
		llAddPatient.setOnClickListener(this);
		llPatientDetails.setOnClickListener(this);
		
		
		tvTitle.setText("Book Appointment for");
		
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
		 	case R.id.llAddPatient:
		 		Intent intent = new Intent(ChoosePatientActivity.this,RegistrationActivity.class);
		 		startActivity(intent);
			 break;
		 	case R.id.llPatientDetails:
		 		Intent summary = new Intent(ChoosePatientActivity.this,ConfirmationSummaryActivity.class);
		 		startActivity(summary);
			 break;
		}
	}

}
