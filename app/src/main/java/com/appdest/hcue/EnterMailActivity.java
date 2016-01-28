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
	private LinearLayout llEmail,llGmail,llYahoo,llYahooIn,llHotmail,llRediff,llZoho,llOther;
	private EditText edtEnterEmail,edtDomain;
	private Button btnConfirmMail,btnNoMailId;
	private TextView tvSymbol;

	@Override
	public void initializeControls() 
	{
		llEmail = (LinearLayout) inflater.inflate(R.layout.mail_more_details, null);
		llBody.addView(llEmail);
		
		llGmail			=	(LinearLayout)	llEmail.findViewById(R.id.llGmail);
		llYahoo			=	(LinearLayout)	llEmail.findViewById(R.id.llYahoo);
		llYahooIn		=	(LinearLayout)	llEmail.findViewById(R.id.llYahooIn);
		llHotmail		=	(LinearLayout)	llEmail.findViewById(R.id.llHotmail);
		llRediff		=	(LinearLayout)	llEmail.findViewById(R.id.llRediff);
		llZoho			=	(LinearLayout)	llEmail.findViewById(R.id.llZoho);
		llOther			=	(LinearLayout)	llEmail.findViewById(R.id.llOther);
		
		edtEnterEmail	=	(EditText)		llEmail.findViewById(R.id.edtEnterEmail);
		edtDomain		=	(EditText)		llEmail.findViewById(R.id.edtDomain);
		
		tvSymbol		=	(TextView)		llEmail.findViewById(R.id.tvSymbol);
		
		btnConfirmMail	=	(Button)		llEmail.findViewById(R.id.btnConfirmMail);
		btnNoMailId		=	(Button)		llEmail.findViewById(R.id.btnNoMailId);
		
		llGmail.setOnClickListener(this);
		llYahoo.setOnClickListener(this);
		llYahooIn.setOnClickListener(this);
		llHotmail.setOnClickListener(this);
		llRediff.setOnClickListener(this);
		llZoho.setOnClickListener(this);
		llOther.setOnClickListener(this);
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
			case R.id.llGmail:
				edtDomain.setText("gmail.com");
				break;
			case R.id.llYahoo:
				edtDomain.setText("yahoo.com");
				break;
			case R.id.llYahooIn:
				edtDomain.setText("yahoo.in");
				break;
			case R.id.llHotmail:
				edtDomain.setText("hotmail.com");
				break;
			case R.id.llRediff:
				edtDomain.setText("rediff.com");
				break;
			case R.id.llZoho:
				edtDomain.setText("zoho.com");
				break;
			case R.id.llOther:
				break;
			case R.id.btnConfirmMail:
				Intent intent = new Intent(EnterMailActivity.this,ConfirmationFullViewActivity.class);
				startActivity(intent);
				break;
			case R.id.btnNoMailId:
				break;
				
		}
	}

}
