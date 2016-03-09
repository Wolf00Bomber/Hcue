package com.hCue.Kiosk;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hCue.Kiosk.common.AppConstants;


public class WelcomeActivity extends BaseActivity
{

	private LinearLayout llMain;
	private ImageView ivImage;
	private TextView tvName,tvSpecality,tvDay,tvTime;
	private Button btnQuickAppointment,btnLaterAppointment;
	
	@Override
	public void initializeControls() 
	{
		llMain = (LinearLayout) inflater.inflate(R.layout.welcome_screen, null);
		
		llBody.addView(llMain);
		
		
		tvBack.setVisibility(View.GONE);
		tvHome.setVisibility(View.GONE);
		
		ivImage = (ImageView) llMain.findViewById(R.id.ivImage);
		tvName = (TextView) llMain.findViewById(R.id.tvName);
		tvSpecality = (TextView) llMain.findViewById(R.id.tvSpecality);
		tvDay = (TextView) llMain.findViewById(R.id.tvDay);
		tvTime = (TextView) llMain.findViewById(R.id.tvTime);
		btnQuickAppointment = (Button) llMain.findViewById(R.id.btnQuickAppointment);
		btnLaterAppointment = (Button) llMain.findViewById(R.id.btnLaterAppointment);
		
		setSpecificTypeFace(llMain, AppConstants.WALSHEIM_LIGHT);
		
		tvName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		btnQuickAppointment.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		btnLaterAppointment.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		tvTime.setTypeface(AppConstants.WALSHEIM_BOLD);
		tvName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		
		tvTitle.setText("Welcome to VHSL PhysioPoint");
		tvTitle.setTypeface(AppConstants.MYRAIDPRO_REGULAR);
	}

	@Override
	public void bindControls() 
	{
		tvLogin.setEnabled(false);
		btnQuickAppointment.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				//Need to change based on requirement
				Intent intent = new Intent(WelcomeActivity.this,EnterContactNumberActivity.class);
				startActivity(intent);
			}
		});
		
		btnLaterAppointment.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				Intent intent = new Intent(WelcomeActivity.this,ChooseAppointmentActivityNew.class);
				startActivity(intent);
				
			}
		});
	}
	
}
