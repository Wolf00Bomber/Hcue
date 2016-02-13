package com.appdest.hcue;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
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
import com.appdest.hcue.model.GetDoctorsResponse;
import com.appdest.hcue.model.GetPatientRequest;
import com.appdest.hcue.model.GetPatientResponse;
import com.appdest.hcue.services.RestCallback;
import com.appdest.hcue.services.RestClient;
import com.appdest.hcue.services.RestError;
import com.appdest.hcue.utils.Connectivity;

import java.math.BigDecimal;

import retrofit.client.Response;


public class EnterContactNumberActivity extends BaseActivity implements OnClickListener
{

	private LinearLayout llMain,llNumbers;
	private TextView tvMobile,tvLandLine;
	private EditText edtCode,edtNumber;
	private Button btnConfirmNumber,btnNoNumber;
	private boolean isMobile=true,isLandline=false;
	private View focusedView;
	private InputMethodManager im;
	private Handler h;
	private Animation slide_up, slide_down;
	private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;
    private Number phNumber;
	boolean isActivityNeedsFinish = false;
	
	@Override
	public void initializeControls() 
	{
		Intent i = getIntent();
		if(i.hasExtra("DoctorDetails"))
		{
			selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail) i.getSerializableExtra("DoctorDetails");
		}
		else
		{
			isActivityNeedsFinish = true;
			finish();
			return;
		}

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
		if(isActivityNeedsFinish)
			return;
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
			else if(button.getText().toString().equalsIgnoreCase("  "))
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
		if(isActivityNeedsFinish)
			return;

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
					showLoader("Please wait...");
                    phNumber = new BigDecimal(edtNumber.getText().toString().trim());
                    callService(phNumber);
				}
				break;
			case R.id.btnNoNumber:
				Intent intent	= new Intent(EnterContactNumberActivity.this,RegistrationActivity.class);
                intent.putExtra("PhoneNumber", selectedDoctorDetails.DoctorID);
                intent.putExtra("PhoneCode", edtCode.getVisibility() == View.VISIBLE ?  getPhoneCode() : "");
                intent.putExtra("DoctorDetails", selectedDoctorDetails);
                intent.putExtra("NoMobile", true);
				startActivity(intent);
				break;
				
			case R.id.tvMobile:
				tvMobile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_icon, 0, R.drawable.mobile_icon, 0);
				tvLandLine.setCompoundDrawablesWithIntrinsicBounds(R.drawable.un_check_icon, 0, R.drawable.landline_icon, 0);
				isMobile = true;
				isLandline = false;
				edtCode.setVisibility(View.VISIBLE);
				break;
			case R.id.tvLandLine:
				tvMobile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.un_check_icon, 0, R.drawable.mobile_icon, 0);
				tvLandLine.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_icon, 0, R.drawable.landline_icon, 0);
				isMobile = false;
				isLandline = true;
				edtCode.setVisibility(View.GONE);
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

    private String getPhoneCode()
    {
        String text = edtCode.getText().toString();
        if(TextUtils.isEmpty(text))
        {
            text = edtCode.getHint().toString();
        }
        return text;
    }

    private void goToRegistrationActivity()
    {
        Intent intent	= new Intent(EnterContactNumberActivity.this,RegistrationActivity.class);
        intent.putExtra("PhoneNumber", phNumber);
        intent.putExtra("PhoneCode", edtCode.getVisibility() == View.VISIBLE ?  getPhoneCode() : "");
        intent.putExtra("DoctorDetails", selectedDoctorDetails);
        startActivity(intent);
    }

    private void goToPatientListActivity(GetPatientResponse getPatientResponse)
    {
        Intent intent	= new Intent(EnterContactNumberActivity.this, ChoosePatientActivity.class);
        intent.putExtra("PhoneNumber", phNumber);
        intent.putExtra("PhoneCode", edtCode.getVisibility() == View.VISIBLE ? getPhoneCode() : "");
        intent.putExtra("DoctorDetails", selectedDoctorDetails);
        intent.putExtra("GetPatientResponse", getPatientResponse);
        startActivity(intent);
    }

    private void callService(Number phNumber)
    {
        if (Connectivity.isConnected(EnterContactNumberActivity.this)) {
            searchForPatient(phNumber);
        } else {
            Toast.makeText(EnterContactNumberActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void searchForPatient(Number phNumber)
    {
        final GetPatientRequest getDoctorsRequest = new GetPatientRequest();
        getDoctorsRequest.setPageNumber(1);
        getDoctorsRequest.setPageSize(10);
        getDoctorsRequest.setPhoneNumber(phNumber.toString());
        getDoctorsRequest.setSort("asc");

        String url = "http://d1lmwj8jm5d3bc.cloudfront.net";
        RestClient.getAPI(url).getPatients(getDoctorsRequest, new RestCallback<GetPatientResponse>() {
            @Override
            public void failure(RestError restError) {
                Log.e("Doctor Appointement", "" + restError.getErrorMessage());
                Toast.makeText(EnterContactNumberActivity.this, "Couldn't get the List of Patients, Try again later...", Toast.LENGTH_LONG).show();
                hideLoader();
            }

            @Override
            public void success(GetPatientResponse getPatientResponse, Response response) {
                if (getPatientResponse != null && getPatientResponse.count > 0) {
                    // Patient Exists.
                    goToPatientListActivity(getPatientResponse);
                } else {
                     //Patient Doesn't Exist.
                    goToRegistrationActivity();
                }
                hideLoader();
            }
        });
    }
	
}
