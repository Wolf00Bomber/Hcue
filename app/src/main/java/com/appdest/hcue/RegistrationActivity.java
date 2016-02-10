package com.appdest.hcue;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.GetHospitalsResponse;

import ademar.phasedseekbar.PhasedInteractionListener;
import ademar.phasedseekbar.PhasedListener;
import ademar.phasedseekbar.PhasedSeekBar;
import ademar.phasedseekbar.SimplePhasedAdapter;

public class RegistrationActivity extends BaseActivity implements OnClickListener
{
	EditText edtFirstName, edtLastName, edtAge;
	Button  btnDone,btnClearFields;
	LinearLayout llUserDetails, llKeyboard,llNumbers,llSpecilaKeyboard;
	View focusedView;
	InputMethodManager im;
	Handler h;
	Animation slide_up, slide_down;
    private GetHospitalsResponse.DoctorDetail selectedDoctorDetails;
    private Number phNumber;
    boolean isActivityNeedsFinish = false;


	@Override
	public void initializeControls()
	{
        Intent i = getIntent();
        if(i.hasExtra("DoctorDetails") && i.hasExtra("PhoneNumber"))
        {
            selectedDoctorDetails = (GetHospitalsResponse.DoctorDetail) i.getSerializableExtra("DoctorDetails");
            phNumber = (Number) i.getSerializableExtra("PhoneNumber");
        }
        else
        {
            isActivityNeedsFinish = true;
            finish();
            return;
        }

		llUserDetails = (LinearLayout) inflater.inflate(R.layout.user_details_activity, null);
		llBody.addView(llUserDetails);

		llUserDetails		=	(LinearLayout)	llUserDetails.findViewById(R.id.llUserDetails);
		llNumbers			=	(LinearLayout)	llUserDetails.findViewById(R.id.llNumbers);
		llKeyboard			=	(LinearLayout)	llUserDetails.findViewById(R.id.llKeyBoard);
		llSpecilaKeyboard	=	(LinearLayout)	llUserDetails.findViewById(R.id.llSpecialKeyBoard);
		edtFirstName 		= 	(EditText)		llUserDetails.findViewById(R.id.edtFirstName);
		edtLastName 		= 	(EditText)		llUserDetails.findViewById(R.id.edtLastName);

		edtAge 				= 	(EditText)		llUserDetails.findViewById(R.id.edtAge);
		btnDone 			=	(Button)		llUserDetails.findViewById(R.id.btnDone);
		btnClearFields 		=	(Button)		llUserDetails.findViewById(R.id.btnClearFields);

		tvTitle.setText("Enter Patient Details");

		slide_up 	= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
		slide_down  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

		llNumbers.setVisibility(View.GONE);
		llSpecilaKeyboard.setVisibility(View.GONE);
		setSpecificTypeFace(llUserDetails, AppConstants.LATO);
		edtFirstName.setTypeface(AppConstants.WALSHEIM_LIGHT);
		edtLastName.setTypeface(AppConstants.WALSHEIM_LIGHT);

		edtAge.setOnClickListener(this);
		edtFirstName.setOnClickListener(this);
		edtLastName.setOnClickListener(this);
		btnClearFields.setOnClickListener(this);
		btnDone.setOnClickListener(this);

        PhasedSeekBar psbHorizontal = (PhasedSeekBar) findViewById(R.id.psb_hor);

        final Resources resources = getResources();

        psbHorizontal.setAdapter(new SimplePhasedAdapter(resources, new int[]{
                R.drawable.male_seek_thumb,
                R.drawable.female_seek_thumb}));

        psbHorizontal.setListener(new PhasedListener() {
            @Override
            public void onPositionSelected(int position) {
                /*if (position == 0) {

                } else if (position == 1) {

                }*/
            }
        });

        psbHorizontal.setInteractionListener(new PhasedInteractionListener() {
            @Override
            public void onInteracted(int x, int y, int position, MotionEvent motionEvent) {
                Log.d("PSB", String.format("onInteracted %d %d %d %d", x, y, position, motionEvent.getAction()));
            }
        });
        psbHorizontal.setPosition(0);
    }

    @Override
	public void bindControls()
	{
        if(isActivityNeedsFinish)
            return;

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
				if(hasFocus)
                {
					if(llKeyboard.getVisibility() == View.GONE)
						llNumbers.setVisibility(View.GONE);
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
					if(llKeyboard.getVisibility() == View.GONE)
						llNumbers.setVisibility(View.GONE);
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

					if(llNumbers.getVisibility() == View.GONE)
						llNumbers.startAnimation(slide_up);
					llNumbers.setVisibility(View.VISIBLE);
					llKeyboard.startAnimation(slide_down);
					llKeyboard.setVisibility(View.GONE);
					llSpecilaKeyboard.startAnimation(slide_down);
					llSpecilaKeyboard.setVisibility(View.GONE);

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
				if(llSpecilaKeyboard.getVisibility() == View.GONE)
				{
					llKeyboard.startAnimation(slide_down);
					llKeyboard.setVisibility(View.GONE);
					llSpecilaKeyboard.setVisibility(View.VISIBLE);
					llSpecilaKeyboard.startAnimation(slide_up);
				}
			}
			else if(button.getText().toString().equalsIgnoreCase("ABC"))
			{
				if(llKeyboard.getVisibility() == View.GONE)
				{
					llSpecilaKeyboard.startAnimation(slide_down);
					llSpecilaKeyboard.setVisibility(View.GONE);
					llKeyboard.setVisibility(View.VISIBLE);
					llKeyboard.startAnimation(slide_up);
				}
			}
			else if(button.getText().toString().equalsIgnoreCase("DEL"))
			{
				if(str.length()>0)
				{
					str = str.substring(0, str.length()-1);
					((EditText)focusedView).setText(str);
					((EditText)focusedView).setSelection(((EditText)focusedView).length());
				}
			}
			else if(button.getText().toString().equalsIgnoreCase("SPACE"))
			{
				str = str + " ";
				((EditText)focusedView).setText(str);
				((EditText)focusedView).setSelection(((EditText) focusedView).length());
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

				llKeyboard.setVisibility(View.VISIBLE);
				if(v.getId() == R.id.edtAge)
				{
					btnDone.setText("Done");
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
					llSpecilaKeyboard.startAnimation(slide_down);
					llNumbers.setVisibility(View.VISIBLE);
					llNumbers.startAnimation(slide_up);
				}

				break;
			case R.id.btnDone:
				Intent intent = new Intent(RegistrationActivity.this,ChooseAppointmentActivity.class);
				intent.putExtra("DoctorDetails", selectedDoctorDetails);
                intent.putExtra("PhoneNumber", phNumber);
				startActivity(intent);
				break;
			case R.id.btnClearFields:
				edtFirstName.getText().clear();
				edtLastName.getText().clear();
				edtAge.getText().clear();
				break;
			default:
				break;
		}



	}



}

