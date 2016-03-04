package com.appdest.hcue;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.AddPatientResponse;
import com.appdest.hcue.model.AdditionalInfoRequest;
import com.appdest.hcue.model.GetDoctorsResponse;
import com.appdest.hcue.services.RestCallback;
import com.appdest.hcue.services.RestClient;
import com.appdest.hcue.services.RestError;
import com.appdest.hcue.utils.Preference;

import retrofit.client.Response;

/**
 * Created by Admin on 29-02-2016.
 */
public class AdditionalInfoActivity extends BaseActivity{
    private LinearLayout llAdditional_info , llreferral_source ;
    private LinearLayout llKeyboard, llSpecilaKeyboard;
    private TextView tv_referral_source , tv_select , tv_marital_status , tv_single , tv_married;
    private EditText edt_educational , edt_occupation;
    private boolean isSingleSelected = true;
    private Button btn_confirm_addition_details ;
    private PopupWindow popupWindow ;
    private View focusedView;
    Animation slide_up, slide_down;
    Handler h;
    private Number phNumber;
    private String PhoneCode , Gender , FullName;
    private int age ;
    private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;
    private boolean isNoMobile;
    //  private Dialog dialog ;

    @Override
    public void initializeControls() {

        llAdditional_info = (LinearLayout) inflater.inflate(R.layout.additional_info, null);
        llBody.addView(llAdditional_info);

        tvTitle.setText("Enter Additional Details");
        slide_up 	= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slide_down  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

        llreferral_source  =  (LinearLayout)    llAdditional_info.findViewById(R.id.llreferral_source);
        llKeyboard		   =  (LinearLayout)	llAdditional_info.findViewById(R.id.llKeyBoard);
        llSpecilaKeyboard  =  (LinearLayout)	llAdditional_info.findViewById(R.id.llSpecialKeyBoard);
        tv_referral_source = (TextView) llAdditional_info.findViewById(R.id.tv_referral_source);
        tv_select          = (TextView) llAdditional_info.findViewById(R.id.tv_select);
        tv_marital_status  = (TextView) llAdditional_info.findViewById(R.id.tv_marital_status);
        tv_single          = (TextView) llAdditional_info.findViewById(R.id.tv_single);
        tv_married         = (TextView) llAdditional_info.findViewById(R.id.tv_married);
        edt_educational    = (EditText) llAdditional_info.findViewById(R.id.edt_educational);
        edt_occupation     = (EditText) llAdditional_info.findViewById(R.id.edt_occupation);
        btn_confirm_addition_details    = (Button) llAdditional_info.findViewById(R.id.btn_confirm_addition_details);

        h = new Handler(Looper.getMainLooper());

        tv_single.performClick();
        setSpecificTypeFace(llAdditional_info, AppConstants.WALSHEIM_LIGHT);

        tv_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isSingleSelected = true;
                tv_single.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_icon, 0, 0, 0);
                tv_married.setCompoundDrawablesWithIntrinsicBounds(R.drawable.un_check_icon, 0, 0, 0);

            }
        });
        tv_married.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isSingleSelected = false;
                tv_married.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_icon, 0, 0, 0);
                tv_single.setCompoundDrawablesWithIntrinsicBounds(R.drawable.un_check_icon, 0, 0, 0);

            }
        });

        llreferral_source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showSelectSourcePopup();
            }
        });


        btn_confirm_addition_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tv_select.getTag() == null) {
                    Toast.makeText(AdditionalInfoActivity.this, "Please select referral source.", Toast.LENGTH_LONG).show();
                } else {
                    showLoader("Loading");
                    callWebService();

                }
            }
        });

        edt_educational.clearFocus();
        edt_occupation.clearFocus();
        llKeyboard.setVisibility(View.GONE);
        llSpecilaKeyboard.setVisibility(View.GONE);

        edt_educational.setOnFocusChangeListener(new View.OnFocusChangeListener() {

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
                    if (llKeyboard.getVisibility() == View.GONE) {
//                        llKeyboard.startAnimation(slide_up);
                        llKeyboard.setVisibility(View.VISIBLE);
                    }

                    if (llSpecilaKeyboard.getVisibility() == View.VISIBLE)
//                        llSpecilaKeyboard.startAnimation(slide_down);
                        llSpecilaKeyboard.setVisibility(View.GONE);
                }
            }
        });

        edt_occupation.setOnFocusChangeListener(new View.OnFocusChangeListener() {

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
                    if (llKeyboard.getVisibility() == View.GONE) {
//                        llKeyboard.startAnimation(slide_up);
                        llKeyboard.setVisibility(View.VISIBLE);
                    }

                    if (llSpecilaKeyboard.getVisibility() == View.VISIBLE)
//                        llSpecilaKeyboard.startAnimation(slide_down);
                        llSpecilaKeyboard.setVisibility(View.GONE);
                }
            }
        });

        edt_occupation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edt_occupation.clearFocus();
                hideKeyBoard(v);
                return false;
            }
        });

        edt_educational.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edt_educational.clearFocus();
                hideKeyBoard(v);
                return false;
            }
        });
        tv_single.performClick();
        retrieveFromPreference();
    }

    private void retrieveFromPreference() {

        Preference preference = new Preference(AdditionalInfoActivity.this);
        if(!preference.getStringFromPreference("PreferenceSource", "").isEmpty())
        {
            if(!preference.getStringFromPreference("PreferenceSource", "").equalsIgnoreCase("Select"))
            tv_select.setTag(preference.getStringFromPreference("PreferenceSource", ""));
            tv_select.setText(preference.getStringFromPreference("PreferenceSource", ""));
        }
       if(preference.getStringFromPreference("MaritalStatus", "true").equalsIgnoreCase("true"))
       {
           tv_single.performClick();
       }else
       {
           tv_married.performClick();
       }
       edt_educational.setText(preference.getStringFromPreference("Education", ""));
       edt_occupation.setText(preference.getStringFromPreference("Ocupation",""));

    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_referral_source.requestFocus();
        edt_educational.clearFocus();

        llKeyboard.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {

        if(popupWindow != null) {
            popupWindow.dismiss();
        }
saveSharedValues();
        if((llSpecilaKeyboard != null && llSpecilaKeyboard.getVisibility()== View.VISIBLE)
                ||(llKeyboard != null && llKeyboard.getVisibility() == View.VISIBLE))  {
            try{
                llSpecilaKeyboard.setVisibility(View.GONE);
                llKeyboard.setVisibility(View.GONE);
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            super.onBackPressed();
        }

    }

    private void saveSharedValues() {
        Preference preference = new Preference(AdditionalInfoActivity.this);
        preference.saveStringInPreference("PreferenceSource",tv_select.getText().toString());
        preference.saveStringInPreference("MaritalStatus",isSingleSelected+"");
        preference.saveStringInPreference("Education",edt_educational.getText().toString());
        preference.saveStringInPreference("Ocupation",edt_occupation.getText().toString());

        preference.commitPreference();
    }

    private void callWebService() {

        final AdditionalInfoRequest additionalInfoRequest = new AdditionalInfoRequest();
        additionalInfoRequest.setUSRType("PATIENT");
        additionalInfoRequest.setUSRId(0);

        additionalInfoRequest.getPatientDetails().setFullName(FullName);
        additionalInfoRequest.getPatientDetails().setFirstName(FullName);
        additionalInfoRequest.getPatientDetails().setAge(age);
        additionalInfoRequest.getPatientDetails().setGender(Gender);
        additionalInfoRequest.getPatientDetails().setMobileID(phNumber);


        additionalInfoRequest.getPatientDetails().getPatientOtherDetails().setEducation(edt_educational.getText().toString());
        additionalInfoRequest.getPatientDetails().getPatientOtherDetails().setOccupation(edt_occupation.getText().toString());
        additionalInfoRequest.getPatientDetails().setGender(Gender);

        additionalInfoRequest.getPatientDetails().getPatientOtherDetails().setMaritalStatus(isSingleSelected ? "N" : "Y");
        if(tv_select.getText().toString().equalsIgnoreCase("Friends"))
            additionalInfoRequest.getPatientDetails().getPatientOtherDetails().getReferralSource().put("FRIEND", "friends");
        else  if(tv_select.getText().toString().equalsIgnoreCase("Relation"))
            additionalInfoRequest.getPatientDetails().getPatientOtherDetails().getReferralSource().put("RELATION","Relation");
        else  if(tv_select.getText().toString().equalsIgnoreCase("News Paper"))
            additionalInfoRequest.getPatientDetails().getPatientOtherDetails().getReferralSource().put("NEWSPAPER", "News Paper");
        else if(tv_select.getText().toString().equalsIgnoreCase("Flyer"))
            additionalInfoRequest.getPatientDetails().getPatientOtherDetails().getReferralSource().put("FLYER", "Flyer");
        else if(tv_select.getText().toString().equalsIgnoreCase("JustDial"))
            additionalInfoRequest.getPatientDetails().getPatientOtherDetails().getReferralSource().put("JUSTDIAL", "JustDial");
        else if(tv_select.getText().toString().equalsIgnoreCase("Others"))
            additionalInfoRequest.getPatientDetails().getPatientOtherDetails().getReferralSource().put("OTHERS", "Others");
        else
            additionalInfoRequest.getPatientDetails().getPatientOtherDetails().getReferralSource().put("WEBSITE","Web Site");
        String strphone = phNumber+"";
        additionalInfoRequest.getArrpatientPhone().get(0).setPhAreaCD(Integer.parseInt(strphone.substring(0,4)));
        additionalInfoRequest.getArrpatientPhone().get(0).setPhNumber(phNumber);
        additionalInfoRequest.getArrpatientPhone().get(0).setPhStateCD(Integer.parseInt(strphone.substring(4,10)));
        String url = "http://d1lmwj8jm5d3bc.cloudfront.net";
        RestClient.getAPI(url).additionalInfo(additionalInfoRequest, new RestCallback<AddPatientResponse>() {
            @Override
            public void success(AddPatientResponse additionalInfoResponse, Response response) {
                hideLoader();
                resetSharedValues();
                //Toast.makeText(AdditionalInfoActivity.this,"SUCCESS"+"",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AdditionalInfoActivity.this, ChooseAppointmentActivityNew.class);
                intent.putExtra("DoctorDetails", selectedDoctorDetails);
                intent.putExtra("PhoneNumber", phNumber);
                intent.putExtra("PatientInfo", additionalInfoResponse);
                intent.putExtra("isNoMobile", isNoMobile);
                intent.putExtra("isFromAdditionalInfo", true);
                startActivity(intent);
                finish();
            }

            @Override
            public void failure(RestError restError) {
                hideLoader();
                // Toast.makeText(AdditionalInfoActivity.this, restError.getErrorMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void resetSharedValues() {
        Preference preference = new Preference(AdditionalInfoActivity.this);
        preference.saveStringInPreference("PreferenceSource","");
        preference.saveStringInPreference("MaritalStatus","");
        preference.saveStringInPreference("Education","");
        preference.saveStringInPreference("Ocupation","");

        preference.commitPreference();
    }

    private void showSelectSourcePopup() {


        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.custom_dialog,null);
        if(popupWindow == null)
        {
            popupWindow = new PopupWindow(view, llreferral_source.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
            ListView lv_source_names = (ListView) view.findViewById(R.id.lv_source_names);

            String[] items = {"News Paper", "Relation" , "Web Site" , "Flyer" , "JustDial" , "Friends" , "Others"};
            ArrayAdapter<String> referral_names_adapter = new ArrayAdapter(this, R.layout.popup_cell, R.id.tv_pop_cell, items);
            lv_source_names.setAdapter(referral_names_adapter);

            tv_select.setTag(items[0]);
            tv_select.setText(items[0]);
            setSpecificTypeFace(lv_source_names, AppConstants.WALSHEIM_LIGHT);
            popupWindow.showAtLocation(llreferral_source, Gravity.CENTER_HORIZONTAL, 0, 0);
            lv_source_names.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.v("item", (String) parent.getItemAtPosition(position));
                    tv_select.setTag((String) parent.getItemAtPosition(position));
                    tv_select.setText((String) parent.getItemAtPosition(position));
                    popupWindow.dismiss();
                    popupWindow = null;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            lv_source_names.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.v("item", (String) parent.getItemAtPosition(position));
                    tv_select.setTag((String) parent.getItemAtPosition(position));
                    tv_select.setText((String) parent.getItemAtPosition(position));
                    popupWindow.dismiss();
                }
            });
        }else
        {
            popupWindow.dismiss();
            popupWindow = null;
        }


    }

    @Override
    public void bindControls() {

        Intent i = getIntent();
        if(i.hasExtra("FullName") && i.hasExtra("phNumber"))
        {
            FullName = i.getStringExtra("FullName");
            age = Integer.parseInt(i.getStringExtra("Age"));
            phNumber = (Number) i.getSerializableExtra("phNumber");
            PhoneCode = i.getStringExtra("PhoneCode");
            Gender = i.getStringExtra("Gender");
            selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail) i.getSerializableExtra("DoctorDetails");
            isNoMobile = i.getBooleanExtra("isNoMobile",false);
        }else
        {
            finish(); return;
        }

        tv_referral_source.setTypeface(AppConstants.WALSHEIM_LIGHT);
        tv_select.setTypeface(AppConstants.WALSHEIM_LIGHT);
        tv_marital_status.setTypeface(AppConstants.WALSHEIM_LIGHT);
        tv_single.setTypeface(AppConstants.WALSHEIM_LIGHT);
        tv_married.setTypeface(AppConstants.WALSHEIM_LIGHT);
        edt_educational.setTypeface(AppConstants.WALSHEIM_LIGHT);
        edt_occupation.setTypeface(AppConstants.WALSHEIM_LIGHT);
        btn_confirm_addition_details.setTypeface(AppConstants.WALSHEIM_LIGHT);

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
//                    llKeyboard.startAnimation(slide_down);
                    llKeyboard.setVisibility(View.GONE);
                    llSpecilaKeyboard.setVisibility(View.VISIBLE);
//                    llSpecilaKeyboard.startAnimation(slide_up);
                }
            }
            else if(button.getText().toString().equalsIgnoreCase("ABC"))
            {
                if(llKeyboard.getVisibility() == View.GONE)
                {
//                    llSpecilaKeyboard.startAnimation(slide_down);
                    llSpecilaKeyboard.setVisibility(View.GONE);
                    llKeyboard.setVisibility(View.VISIBLE);
//                    llKeyboard.startAnimation(slide_up);
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

    public class MyCustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
