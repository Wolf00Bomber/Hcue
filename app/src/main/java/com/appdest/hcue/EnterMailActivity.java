package com.appdest.hcue;

import com.appdest.hcue.common.AppConstants;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EnterMailActivity extends BaseActivity implements OnClickListener
{
	private LinearLayout llEmail;
	private EditText edtEnterAddress,edtLandmark;
	private Button btnConfirm,btnSkip;


	@Override
	public void initializeControls() 
	{
		llEmail = (LinearLayout) inflater.inflate(R.layout.mail_more_details, null);
		llBody.addView(llEmail);



		edtEnterAddress	=	(EditText)		llEmail.findViewById(R.id.edtEnterAddress);
		edtLandmark		=	(EditText)		llEmail.findViewById(R.id.edtLandmark);



		btnConfirm		=	(Button)		llEmail.findViewById(R.id.btnConfirm);
		btnSkip			=	(Button)		llEmail.findViewById(R.id.btnSkip);
		

		btnConfirm.setOnClickListener(this);
		btnSkip.setOnClickListener(this);
		
		setSpecificTypeFace(llEmail, AppConstants.WALSHEIM_LIGHT);
		btnConfirm.setTypeface(AppConstants.WALSHEIM_BOLD);
		btnSkip.setTypeface(AppConstants.WALSHEIM_BOLD);
		
		tvTitle.setText("Enter Email Address");

		
		
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

			case R.id.btnConfirm:
				Intent intent = new Intent(EnterMailActivity.this,ConfirmationFullViewActivity.class);
				startActivity(intent);
				break;
			case R.id.btnSkip:
				break;
				
		}
	}

}
