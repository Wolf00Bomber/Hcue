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
	private EditText edtEnterEmail,edtDomain;
	private Button btnConfirmMail,btnNoMailId;
	private TextView tvSymbol;

	@Override
	public void initializeControls() 
	{
		llEmail = (LinearLayout) inflater.inflate(R.layout.mail_more_details, null);
		llBody.addView(llEmail);
		

		
		edtEnterEmail	=	(EditText)		llEmail.findViewById(R.id.edtEnterEmail);
		edtDomain		=	(EditText)		llEmail.findViewById(R.id.edtDomain);
		
		tvSymbol		=	(TextView)		llEmail.findViewById(R.id.tvSymbol);
		
		btnConfirmMail	=	(Button)		llEmail.findViewById(R.id.btnConfirmMail);
		btnNoMailId		=	(Button)		llEmail.findViewById(R.id.btnNoMailId);
		

		btnConfirmMail.setOnClickListener(this);
		btnNoMailId.setOnClickListener(this);
		
		setSpecificTypeFace(llEmail, AppConstants.WALSHEIM_LIGHT);
		btnConfirmMail.setTypeface(AppConstants.WALSHEIM_BOLD);
		btnNoMailId.setTypeface(AppConstants.WALSHEIM_BOLD);
		
		tvTitle.setText("Enter Email Address");
		tvSymbol.setText("@");
		
		
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

			case R.id.btnConfirmMail:
				Intent intent = new Intent(EnterMailActivity.this,ConfirmationFullViewActivity.class);
				startActivity(intent);
				break;
			case R.id.btnNoMailId:
				break;
				
		}
	}

}
