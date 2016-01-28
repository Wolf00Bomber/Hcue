package com.appdest.hcue;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appdest.hcue.common.AppConstants;


public class EnterContactNumberActivity extends BaseActivity implements OnClickListener
{

	private LinearLayout llMain,llNumbers;
	private TextView tvMobile,tvLandLine;
	private EditText edtCode,edtNumber;
	private Button btnConfirmNumber,btnNoNumber;
	private boolean isMobile=true,isLandline=false;
	private String PhoneNumber = "";
	private View focusedView;
	private InputMethodManager im;
	private Handler h;
	private Animation slide_up, slide_down;
	
	@Override
	public void initializeControls() 
	{
		llMain = (LinearLayout) inflater.inflate(R.layout.enter_contact_number, null);
		
		llBody.addView(llMain);
		
		tvMobile 			= (TextView) 	llMain.findViewById(R.id.tvMobile);
		tvLandLine 			= (TextView) 	llMain.findViewById(R.id.tvLandLine);
		btnConfirmNumber	= (Button) 		llMain.findViewById(R.id.btnConfirmNumber);
		btnNoNumber 		= (Button) 		llMain.findViewById(R.id.btnNoNumber);
		edtCode 			= (EditText)	llMain.findViewById(R.id.edtCode);
		edtNumber 			= (EditText) 	llMain.findViewById(R.id.edtNumber);
		
		llNumbers 			= (LinearLayout) llMain.findViewById(R.id.llNumbers);
		
		slide_up 	= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
		slide_down  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
		
		setSpecificTypeFace(llMain, AppConstants.WALSHEIM_MEDIUM);
		tvMobile.setTypeface(AppConstants.WALSHEIM_LIGHT);
		tvLandLine.setTypeface(AppConstants.WALSHEIM_LIGHT);
		
		tvTitle.setText("Enter Contact Number");
		
		llNumbers.setVisibility(View.GONE);
		
		tvMobile.setOnClickListener(this);
		tvLandLine.setOnClickListener(this);
		btnConfirmNumber.setOnClickListener(this);
		btnNoNumber.setOnClickListener(this);
	}

	@Override
	public void bindControls() 
	{

		h = new Handler(Looper.getMainLooper());

		edtCode.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(final View v, boolean hasFocus) {
				hideKeyBoard(v);
				h.postDelayed(new Runnable() {

					@Override
					public void run() {
						hideKeyBoard(v);
					}
				}, 50);
				if(hasFocus){
										
					if(llNumbers.getVisibility() == View.GONE)
						llNumbers.startAnimation(slide_up);

					llNumbers.setVisibility(View.VISIBLE);

				}
			}
		});
		edtNumber.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(final View v, boolean hasFocus) {
				hideKeyBoard(v);
				h.postDelayed(new Runnable() {

					@Override
					public void run() {
						hideKeyBoard(v);
					}
				}, 50);
				if(hasFocus){
					if(llNumbers.getVisibility() == View.GONE)
						llNumbers.startAnimation(slide_up);
					llNumbers.setVisibility(View.VISIBLE);
				}
			}
		});
		

		hideKeyBoard(edtCode);
		hideKeyBoard(edtNumber);

		edtCode.clearFocus();
		edtNumber.clearFocus();
		
	}
	
	public void keyboardClick(View v)
	{
		hideKeyBoard(v);
		focusedView = getCurrentFocus();

		Button button = (Button)v;

		if(focusedView != null && focusedView instanceof EditText)
		{
			String str = ((EditText)focusedView).getText().toString();

			if(button.getText().toString().equalsIgnoreCase("123"))
			{
				Toast.makeText(EnterContactNumberActivity.this, "Need To Implement.", Toast.LENGTH_SHORT).show();
			}
			else if(button.getText().toString().equalsIgnoreCase("Delete"))
			{
				if(str.length()>0)
				{
					str = str.substring(0, str.length()-1);
					((EditText)focusedView).setText(str);
					((EditText)focusedView).setSelection(((EditText)focusedView).length());
				}
			}
			else if(button.getText().toString().equalsIgnoreCase(""))
			{
				str = str + " ";
				((EditText)focusedView).setText(str);
				((EditText)focusedView).setSelection(((EditText)focusedView).length());
			}
			else if(button.getText().toString().equalsIgnoreCase("Shift"))
			{
				Toast.makeText(EnterContactNumberActivity.this, "Need To Implement.", Toast.LENGTH_SHORT).show();
			}
			else if(button.getText().toString().equalsIgnoreCase("Next"))
			{
				if(edtCode.isFocused())
					edtNumber.requestFocus();
				
				else{

					if(llNumbers.getVisibility() == View.VISIBLE)
						llNumbers.startAnimation(slide_down);

					llNumbers.setVisibility(View.GONE);
				}
				//					Toast.makeText(MainActivity.this, "Need To Implement.", Toast.LENGTH_SHORT).show();
			}
			
			else
			{
				str = str + button.getText().toString() ;
				((EditText)focusedView).setText(str);
				((EditText)focusedView).setSelection(((EditText)focusedView).length());
			}
		}

	}
	@Override
	protected void onResume() 
	{
		super.onResume();

		h.postDelayed(new Runnable() {

			@Override
			public void run() {
				llNumbers.setVisibility(View.GONE);
			}
		}, 50);
	}

	public void hideKeyBoard(View view)
	{
		im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	@Override
	public void onClick(final View v) 
	{
		switch(v.getId())
		{
			case R.id.btnConfirmNumber:
				if(TextUtils.isEmpty(edtNumber.getText().toString().trim()))
				{
					showToast("Please enter Mobile/Phone number.");
				}
				else
				{
					PhoneNumber = edtNumber.getText().toString().trim();
					Intent intent	= new Intent(EnterContactNumberActivity.this,RegistrationActivity.class);
					startActivity(intent);
				}
				break;
			case R.id.btnNoNumber:
				Intent intent	= new Intent(EnterContactNumberActivity.this,RegistrationActivity.class);
				startActivity(intent);
				break;
				
				case R.id.tvMobile:
					tvMobile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_icon, 0, R.drawable.mobile_icon, 0);
					tvLandLine.setCompoundDrawablesWithIntrinsicBounds(R.drawable.un_check_icon, 0, R.drawable.landline_icon, 0);
					isMobile = true;
					isLandline = false;
					break;
				case R.id.tvLandLine:
					tvMobile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.un_check_icon, 0, R.drawable.mobile_icon, 0);
					tvLandLine.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_icon, 0, R.drawable.landline_icon, 0);
					isMobile = false;
					isLandline = true;
					break;
					
				case R.id.edtCode:
				case R.id.edtNumber:
					v.requestFocus();
					hideKeyBoard(v);
					h.postDelayed(new Runnable() {

						@Override
						public void run() {
							hideKeyBoard(v);
						}
					}, 50);


					if(llNumbers.getVisibility() == View.GONE)
						llNumbers.startAnimation(slide_up);
					break;
				
		}
		
		
	}
	
}
