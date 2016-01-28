package com.appdest.hcue;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appdest.hcue.common.AppConstants;

public class RegistrationActivity extends BaseActivity implements OnClickListener
{
	EditText edtFirstName, edtLastName, edtAge;
	Button btnConfirm, btnNext;
	TextView tvMale,tvFeMale;
	LinearLayout llUserDetails, llKeyboard,llNumbers;
	ImageView ivMale,ivFeMale;
	View focusedView;
	InputMethodManager im;
	Handler h;
	Animation slide_up, slide_down;

	@Override
	public void initializeControls()
	{
		llUserDetails = (LinearLayout) inflater.inflate(R.layout.user_details_activity, null);
		llBody.addView(llUserDetails);

		llUserDetails	=	(LinearLayout)	llUserDetails.findViewById(R.id.llUserDetails);
		llNumbers		=	(LinearLayout)	llUserDetails.findViewById(R.id.llNumbers);
		llKeyboard		=	(LinearLayout)	llUserDetails.findViewById(R.id.llKeyBoard);
		edtFirstName 	= 	(EditText)		llUserDetails.findViewById(R.id.edtFirstName);
		edtLastName 	= 	(EditText)		llUserDetails.findViewById(R.id.edtLastName);

		tvMale			=	(TextView)		llUserDetails.findViewById(R.id.tvMale);
		tvFeMale		=	(TextView)		llUserDetails.findViewById(R.id.tvFeMale);
		edtAge 			= 	(EditText)		llUserDetails.findViewById(R.id.edtAge);
		btnConfirm 		= 	(Button)		llUserDetails.findViewById(R.id.btnConfirm);
		ivMale 			= 	(ImageView)		llUserDetails.findViewById(R.id.ivMale);
		ivFeMale 		= 	(ImageView)		llUserDetails.findViewById(R.id.ivFeMale);
		btnNext 		=	(Button)		llUserDetails.findViewById(R.id.btnNext);

		tvTitle.setText("Enter Patient Details");

		slide_up 	= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
		slide_down  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

		llNumbers.setVisibility(View.GONE);
//		llKeyboard.setVisibility(View.GONE);
		setSpecificTypeFace(llUserDetails, AppConstants.LATO);
		edtFirstName.setTypeface(AppConstants.WALSHEIM_LIGHT);
		edtLastName.setTypeface(AppConstants.WALSHEIM_LIGHT);
		btnConfirm.setTypeface(AppConstants.WALSHEIM_BOLD);

		ivMale.setTag(true);
		ivFeMale.setTag(false);

		ivMale.setOnClickListener(this);
		ivFeMale.setOnClickListener(this);
		tvFeMale.setOnClickListener(this);
		tvMale.setOnClickListener(this);
		edtAge.setOnClickListener(this);
		edtFirstName.setOnClickListener(this);
		edtLastName.setOnClickListener(this);
		btnConfirm.setOnClickListener(this);

	}

	@Override
	public void bindControls()
	{
		h = new Handler(Looper.getMainLooper());

		edtFirstName.setOnFocusChangeListener(new OnFocusChangeListener() {

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
					btnNext.setText("Next");


					if(llKeyboard.getVisibility() == View.GONE)
						llNumbers.setVisibility(View.GONE);
//						llKeyboard.startAnimation(slide_up);

					llKeyboard.setVisibility(View.VISIBLE);

				}
			}
		});
		edtLastName.setOnFocusChangeListener(new OnFocusChangeListener() {

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
					btnNext.setText("Next");
					if(llKeyboard.getVisibility() == View.GONE)
						llNumbers.setVisibility(View.GONE);
//						llKeyboard.startAnimation(slide_up);
					llKeyboard.setVisibility(View.VISIBLE);
				}
			}
		});
		edtAge.setOnFocusChangeListener(new OnFocusChangeListener() {

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
					btnNext.setText("Done");

					if(llNumbers.getVisibility() == View.GONE)
						llNumbers.startAnimation(slide_up);
					llNumbers.setVisibility(View.VISIBLE);
					llKeyboard.startAnimation(slide_down);
					llKeyboard.setVisibility(View.GONE);

				}
			}
		});

		hideKeyBoard(edtAge);
		hideKeyBoard(edtFirstName);
		hideKeyBoard(edtLastName);

		edtAge.clearFocus();
		edtFirstName.clearFocus();
		edtLastName.clearFocus();
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
				if(llNumbers.getVisibility() == View.GONE)
					llKeyboard.startAnimation(slide_down);
				llKeyboard.setVisibility(View.GONE);
				llNumbers.startAnimation(slide_up);
				llNumbers.setVisibility(View.VISIBLE);
				Toast.makeText(RegistrationActivity.this, "Need To Implement.", Toast.LENGTH_SHORT).show();
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
				Toast.makeText(RegistrationActivity.this, "Need To Implement.", Toast.LENGTH_SHORT).show();
			}
			else if(button.getText().toString().equalsIgnoreCase("Next"))
			{
				if(edtFirstName.isFocused())
					edtLastName.requestFocus();
				else if(edtLastName.isFocused())
				{
					edtAge.requestFocus();
					button.setText("Done");
				}
				else{

					if(llKeyboard.getVisibility() == View.VISIBLE)
						llKeyboard.startAnimation(slide_down);

					llKeyboard.setVisibility(View.GONE);
					llNumbers.setVisibility(View.VISIBLE);
				}
				//	Toast.makeText(MainActivity.this, "Need To Implement.", Toast.LENGTH_SHORT).show();
			}
			else if(button.getText().toString().equalsIgnoreCase("Done"))
			{
				if(llKeyboard.getVisibility() == View.VISIBLE)
					llKeyboard.startAnimation(slide_down);

				llKeyboard.setVisibility(View.GONE);
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
	protected void onResume() {
		super.onResume();

		h.postDelayed(new Runnable() {

			@Override
			public void run() {
				llKeyboard.setVisibility(View.VISIBLE);
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
		switch (v.getId()) {
			case R.id.tvMale:
			case R.id.ivMale:
				if((Boolean) ivFeMale.getTag())
				{
					ivFeMale.setBackgroundResource(R.drawable.un_check_icon);
					ivFeMale.setTag(false);
				}
				ivMale.setBackgroundResource(R.drawable.check_icon);
				ivMale.setTag(true);
				break;
			case R.id.tvFeMale:
			case R.id.ivFeMale:
				if((Boolean) ivMale.getTag())
				{
					ivMale.setBackgroundResource(R.drawable.un_check_icon);
					ivMale.setTag(false);
				}
				ivFeMale.setBackgroundResource(R.drawable.check_icon);
				ivFeMale.setTag(true);
				break;
			case R.id.edtFirstName:
			case R.id.edtLastName:
				v.requestFocus();
				hideKeyBoard(v);
				h.postDelayed(new Runnable() {

					@Override
					public void run() {
						hideKeyBoard(v);
					}
				}, 50);


				if(llKeyboard.getVisibility() == View.GONE)
					llNumbers.setVisibility(View.GONE);
//				llKeyboard.startAnimation(slide_up);

				llKeyboard.setVisibility(View.VISIBLE);
				if(v.getId() == R.id.edtAge)
				{
					btnNext.setText("Done");
				}
				break;
			case R.id.edtAge:
				v.requestFocus();
				hideKeyBoard(v);
				h.postDelayed(new Runnable() {

					@Override
					public void run() {
						hideKeyBoard(v);
					}
				}, 50);


				if(llNumbers.getVisibility() == View.GONE)
				{
					llKeyboard.startAnimation(slide_down);
					llNumbers.setVisibility(View.VISIBLE);
					llNumbers.startAnimation(slide_up);
				}

				break;
			case R.id.btnConfirm:
				Intent intent = new Intent(RegistrationActivity.this,ChoosePatientActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}



	}



}

