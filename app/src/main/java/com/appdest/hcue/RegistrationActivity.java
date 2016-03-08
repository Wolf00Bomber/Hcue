package com.appdest.hcue;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.TextView;
import android.widget.Toast;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.AddPatientRequest;
import com.appdest.hcue.model.AddPatientResponse;
import com.appdest.hcue.model.GetDoctorsResponse;
import com.appdest.hcue.model.GetPatientDetailsResponse;
import com.appdest.hcue.model.GetPatientRequest;
import com.appdest.hcue.model.GetPatientResponse;
import com.appdest.hcue.services.RestCallback;
import com.appdest.hcue.services.RestClient;
import com.appdest.hcue.services.RestError;
import com.appdest.hcue.utils.Connectivity;
import com.appdest.hcue.utils.WordUtils;

import java.util.ArrayList;

import ademar.phasedseekbar.PhasedInteractionListener;
import ademar.phasedseekbar.PhasedListener;
import ademar.phasedseekbar.PhasedSeekBar;
import ademar.phasedseekbar.SimplePhasedAdapter;
import retrofit.client.Response;

public class RegistrationActivity extends BaseActivity implements OnClickListener
{
	EditText edtFirstName, edtAge;
	Button  btnDone,btnClearFields,btnAdditionalInfo;
	LinearLayout llUserDetails, llKeyboard,llNumbers,llSpecilaKeyboard;
	View focusedView;
	InputMethodManager im;
	Handler h;
	Animation slide_up, slide_down;
    private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;
    private Number phNumber;
    private String PhoneCode;
    private GetPatientResponse getPatientResponse;
    private PhasedSeekBar psbHorizontal;
    private boolean isNoMobile;


	@Override
	public void initializeControls()
	{
		llUserDetails = (LinearLayout) inflater.inflate(R.layout.user_details_activity, null);
		llBody.addView(llUserDetails);

		llUserDetails		=	(LinearLayout)	llUserDetails.findViewById(R.id.llUserDetails);
		llNumbers			=	(LinearLayout)	llUserDetails.findViewById(R.id.llNumbers);
		llKeyboard			=	(LinearLayout)	llUserDetails.findViewById(R.id.llKeyBoard);
		llSpecilaKeyboard	=	(LinearLayout)	llUserDetails.findViewById(R.id.llSpecialKeyBoard);
		edtFirstName 		= 	(EditText)		llUserDetails.findViewById(R.id.edtFirstName);

		edtAge 				= 	(EditText)		llUserDetails.findViewById(R.id.edtAge);
		btnDone 			=	(Button)		llUserDetails.findViewById(R.id.btnDone);
		btnAdditionalInfo 	=	(Button)		llUserDetails.findViewById(R.id.btnAdditionalInfo);
		btnClearFields 		=	(Button)		llUserDetails.findViewById(R.id.btnClearFields);

		if(AppConstants.Is_AdditionalInfo_On)
			btnDone.setVisibility(View.GONE);
		else
			btnAdditionalInfo.setVisibility(View.GONE);



		tvTitle.setText("Enter Patient Details");

		slide_up 	= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
		slide_down  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

		llNumbers.setVisibility(View.GONE);
		llSpecilaKeyboard.setVisibility(View.GONE);
		setSpecificTypeFace(llUserDetails, AppConstants.LATO);
		edtFirstName.setTypeface(AppConstants.WALSHEIM_LIGHT);

		edtAge.setOnClickListener(this);
		edtFirstName.setOnClickListener(this);
		btnClearFields.setOnClickListener(this);
		btnDone.setOnClickListener(this);
		btnAdditionalInfo.setOnClickListener(this);

        psbHorizontal = (PhasedSeekBar) findViewById(R.id.psb_hor);

        final Resources resources = getResources();

		psbHorizontal.setAdapter(new SimplePhasedAdapter(resources, new int[]{
				R.drawable.male_seek_thumb,
				R.drawable.female_seek_thumb}));

		psbHorizontal.setListener(new PhasedListener() {
			@Override
			public void onPositionSelected(int position) {
				if (position == 0) {

				} else if (position == 1) {

				}
			}
		});

		psbHorizontal.setInteractionListener(new PhasedInteractionListener() {
			@Override
			public void onInteracted(int x, int y, int position, MotionEvent motionEvent) {
				Log.d("PSB", String.format("onInteracted %d %d %d %d", x, y, position, motionEvent.getAction()));
			}
		});
        psbHorizontal.setPosition(0);
		setCapitalizeTextWatcher(edtFirstName);


    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		boolean isFromAdditionalInfo=intent.getBooleanExtra("isFromAdditionalInfo",false);
		if(isFromAdditionalInfo)
			finish();
	}

	/*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==506)
		{
			boolean isFromAdditionalInfo=data.getBooleanExtra("isFromAdditionalInfo",false);
			if(isFromAdditionalInfo)
			finish();
		}
	}*/

	@Override
	public void bindControls()
	{
		tvLogin.setEnabled(false);
		Intent i = getIntent();
		if(i.hasExtra("DoctorDetails") && i.hasExtra("PhoneNumber"))
		{
			selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail) i.getSerializableExtra("DoctorDetails");
			phNumber = (Number) i.getSerializableExtra("PhoneNumber");
			PhoneCode = i.getStringExtra("PhoneCode");
			if(i.hasExtra("GetPatientResponse"))
				getPatientResponse = (GetPatientResponse) i.getSerializableExtra("GetPatientResponse");
			if(i.hasExtra("NoMobile"))
			{
				isNoMobile = true;
				btnDone.setVisibility(View.VISIBLE);
				btnAdditionalInfo.setVisibility(View.GONE);
			}
		}
		else{
            finish(); return;
		}

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
				if (hasFocus) {

					if (llNumbers.getVisibility() == View.GONE)
						llNumbers.startAnimation(slide_up);
					llNumbers.setVisibility(View.VISIBLE);

					if (llKeyboard.getVisibility() == View.VISIBLE)
						/*llKeyboard.startAnimation(slide_down);*/
						llKeyboard.setVisibility(View.GONE);

					if (llSpecilaKeyboard.getVisibility() == View.VISIBLE)
						/*llSpecilaKeyboard.startAnimation(slide_down);*/
						llSpecilaKeyboard.setVisibility(View.GONE);

				}
			}
		});

		hideKeyBoard(edtAge);
		hideKeyBoard(edtFirstName);

		edtAge.clearFocus();
		edtFirstName.clearFocus();

		edtAge.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hideKeyBoard(v);
			}
		});
		edtFirstName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hideKeyBoard(v);
			}
		});

		edtAge.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				edtAge.clearFocus();
				hideKeyBoard(v);
				llKeyboard.setVisibility(View.GONE);
				llSpecilaKeyboard.setVisibility(View.GONE);
				return false;
			}
		});

		edtFirstName.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				edtFirstName.clearFocus();
				hideKeyBoard(v);
				return false;
			}
		});


		/*edtFirstName.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s != null && s.length() > 0) {
					String res = getCapWordString(s.toString());
					Log.e("cap word", res);
//					((TextView) edtFirstName).setText(res);
				}
			}
		});*/
	}

	private String getCapWordString(String string){
		String str = string;
		String[] strArray = str.split(" ");
		StringBuilder builder = new StringBuilder();
		for (String s : strArray) {
			String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
			builder.append(cap + " ");
		}
		return builder.toString();
	}

	public void keyboardClick(View v)
	{
		hideKeyBoard(v);
		focusedView = getCurrentFocus();

		Button button = (Button)v;

		if(focusedView != null && focusedView instanceof EditText)
		{
			String str = ((Button)v).getText().toString();

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
					/*str = str.substring(0, str.length()-1);
					((EditText)focusedView).setText(str);
					((EditText)focusedView).setSelection(((EditText) focusedView).length());*/

					int remove_index_position = ((EditText)focusedView).getSelectionStart() -1 ;  //getting cursor starting position
					StringBuilder dialled_nos_builder = new StringBuilder(((EditText)focusedView).getText().toString());
					if(remove_index_position>=0) {
						dialled_nos_builder.deleteCharAt(remove_index_position);
						((EditText)focusedView).setText(dialled_nos_builder.toString());
						((EditText)focusedView).setSelection(remove_index_position);
					}

				}
			}
			else if(button.getText().toString().equalsIgnoreCase("SPACE"))
			{
				/*str = str + " ";
				((EditText)focusedView).setText(str);
				((EditText)focusedView).setSelection(((EditText) focusedView).length());*/
				int start =(((EditText) focusedView).getSelectionStart()); //this is to get the the cursor position
				//String s = "Some string";
				//editText.getText().insert(start, s);
				//str = str + button.getText().toString() ;
				((EditText)focusedView).getText().insert(start, " ");
				((EditText)focusedView).setSelection((start+1));
			}


			else if(button.getText().toString().equalsIgnoreCase("Done"))
			{
				if(llKeyboard.getVisibility() == View.VISIBLE)
					llKeyboard.startAnimation(slide_down);

				llKeyboard.setVisibility(View.GONE);
			}
			else
			{
				int start =(((EditText) focusedView).getSelectionStart()); //this is to get the the cursor position
				//String s = "Some string";
				//editText.getText().insert(start, s);
				//str = str + button.getText().toString() ;
				((EditText)focusedView).getText().insert(start, str);
				((EditText)focusedView).setSelection((start+1));
			}
		}

	}

	@Override
	protected void onResume() {
		super.onResume();

		//edtFirstName.setText("");
		//edtAge.setText("");

		h.postDelayed(new Runnable() {

			@Override
			public void run() {
				llKeyboard.setVisibility(View.VISIBLE);
				llNumbers.setVisibility(View.GONE);
			}
		}, 50);

		try{
			edtAge.clearFocus();
			edtFirstName.setFocusable(true);
		}catch(Exception e){

		}
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
					/*llKeyboard.startAnimation(slide_down);*/
					/*llSpecilaKeyboard.startAnimation(slide_down);*/
					llKeyboard.setVisibility(View.GONE);
					llSpecilaKeyboard.setVisibility(View.GONE);
					llNumbers.setVisibility(View.VISIBLE);
					llNumbers.startAnimation(slide_up);
				}

				break;
			case R.id.btnDone:
                if(TextUtils.isEmpty(edtFirstName.getText().toString().trim()))
                {
                    showToast("Please enter Name.");
                }
                else if(TextUtils.isEmpty(edtAge.getText().toString().trim()))
                {
                    showToast("Please enter Age.");
                }
                else if(!TextUtils.isDigitsOnly(edtAge.getText().toString().trim()))
                {
                    showToast("Please enter a number for Age.");
                }
                else
                {
                    callService();
                }

				break;
			case R.id.btnClearFields:
				edtFirstName.getText().clear();
				edtAge.getText().clear();
				break;
			case R.id.btnAdditionalInfo:
				if(TextUtils.isEmpty(edtFirstName.getText().toString().trim()))
				{
					showToast("Please enter Name.");
				}
				else if(TextUtils.isEmpty(edtAge.getText().toString().trim()))
				{
					showToast("Please enter Age.");
				}
				else if(!TextUtils.isDigitsOnly(edtAge.getText().toString().trim()))
				{
					showToast("Please enter a number for Age.");
				}
				else
				{
					Intent intent = new Intent(RegistrationActivity.this,AdditionalInfoActivity.class);
					intent.putExtra("FullName",edtFirstName.getText().toString());
					intent.putExtra("phNumber",phNumber);
					intent.putExtra("PhoneCode",PhoneCode);
					intent.putExtra("Age",edtAge.getText().toString());
					intent.putExtra("Gender",psbHorizontal.getCurrentItem() == 0 ? "M" : "F");
					intent.putExtra("DoctorDetails", selectedDoctorDetails);
					intent.putExtra("isNoMobile", isNoMobile);
					startActivity(intent);
					//finish();
				}

				break;
			default:
				break;
		}
	}

    private void callService()
    {
        if (Connectivity.isConnected(RegistrationActivity.this)) {
            registerNewPatient();
        } else {
            Toast.makeText(RegistrationActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

	private void registerNewPatient()
	{
        showLoader("Please wait...");
        try
        {
            AddPatientRequest addPatientRequest = new AddPatientRequest();
            addPatientRequest.setUSRType("KIOSK");
            addPatientRequest.setUSRId(0);

            String firstName = edtFirstName.getText().toString();
            addPatientRequest.patientDetails.setFirstName(firstName);
            addPatientRequest.patientDetails.setAge(Integer.parseInt(edtAge.getText().toString()));
            addPatientRequest.patientDetails.setTermsAccepted("Y");
            addPatientRequest.patientDetails.setFullName(firstName);
            addPatientRequest.patientDetails.setGender(psbHorizontal.getCurrentItem() == 0 ? "M" : "F");
            addPatientRequest.patientDetails.setMobileID(phNumber);

            if(isNoMobile)
            {
                addPatientRequest.patientDetails.setUSRId(0);
            }
            else
            {
                AddPatientRequest.PatientPhone patientPhone = addPatientRequest.getEmptypatientPhone();
                String phoneNumber = phNumber.toString();
                patientPhone.setPhAreaCD(Integer.parseInt(phoneNumber.substring(4, 9)));
                patientPhone.setPhStateCD(Integer.parseInt(phoneNumber.substring(0, 3)));
                patientPhone.setPhNumber(phNumber);
                patientPhone.setPhType("M");
                patientPhone.setPrimaryIND("Y");
                String phoneCodeString = PhoneCode != null && PhoneCode.contains("+") ? PhoneCode.replace("+","") : "0";
                int IntPhoneCode = Integer.parseInt(phoneCodeString);
                patientPhone.setPhCntryCD(IntPhoneCode);

                addPatientRequest.addPatientPhone(patientPhone);
            }


            String url = "http://d1lmwj8jm5d3bc.cloudfront.net";
            RestClient.getAPI(url).addPatient(addPatientRequest, new RestCallback<AddPatientResponse>() {
                @Override
                public void failure(RestError restError) {
                    Log.e("Patient Registration", "" + restError.getErrorMessage());
                    Toast.makeText(RegistrationActivity.this, "Couldn't get the List of Patients, Try again later...", Toast.LENGTH_LONG).show();
                    hideLoader();
                }

                @Override
                public void success(AddPatientResponse addPatientResponse, Response response) {
                    if (addPatientResponse != null) {
                        goToNextActivity(addPatientResponse);
                    } else {
                        // Patient Doesn't Exist.
                    }
                    hideLoader();
                }
            });
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Toast.makeText(RegistrationActivity.this, "Patient Registration Failed...", Toast.LENGTH_LONG).show();
            hideLoader();
        }
	}

    private void goToNextActivity(AddPatientResponse addPatientResponse)
    {
        Intent intent = new Intent(RegistrationActivity.this, ChooseAppointmentActivityNew.class);
        intent.putExtra("DoctorDetails", selectedDoctorDetails);
        intent.putExtra("PhoneNumber", phNumber);
        intent.putExtra("PatientInfo", addPatientResponse);
        intent.putExtra("isNoMobile", isNoMobile);
        startActivity(intent);
		finish();
    }

	private void setCapitalizeTextWatcher(final EditText editText) {
		final TextWatcher textWatcher = new TextWatcher() {

			int mStart = 0;

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mStart = start + count;
			}

			@Override
			public void afterTextChanged(Editable s) {
				//Use WordUtils.capitalizeFully if you only want the first letter of each word to be capitalized
				String capitalizedText = WordUtils.capitalize(editText.getText().toString().toLowerCase());
				if (!capitalizedText.equals(editText.getText().toString())) {
					editText.addTextChangedListener(new TextWatcher() {
						@Override
						public void beforeTextChanged(CharSequence s, int start, int count, int after) {

						}

						@Override
						public void onTextChanged(CharSequence s, int start, int before, int count) {

						}

						@Override
						public void afterTextChanged(Editable s) {
							editText.setSelection(mStart);
							editText.removeTextChangedListener(this);
						}
					});
					editText.setText(capitalizedText);
				}
			}
		};

		editText.addTextChangedListener(textWatcher);
	}
}

