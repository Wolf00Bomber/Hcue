package com.hCue.Kiosk;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hCue.Kiosk.common.AppConstants;
import com.hCue.Kiosk.model.AdminLoginResponse;
import com.hCue.Kiosk.utils.Preference;

/**
 * Created by Vinsan on 13/02/16.
 */
//kvk
public class AdminConfirmation extends BaseActivity implements View.OnClickListener {
    private LinearLayout layout;
    private Button btnGoBack, btnConfirm;
    private int hospitalID, loginDoctorID, clinicID;
    private AdminLoginResponse.DoctorAddress hospitalData;
    private boolean isClinic;
    private String selectedDoctors , selected_clinic_name;
    @Override
    public void initializeControls() {
        layout = (LinearLayout) inflater.inflate(R.layout.admin_confirmation, null);
        llBody.addView(layout,layoutParams);

        btnGoBack           = (Button) layout.findViewById(R.id.btnGoBack);
        btnConfirm           = (Button) layout.findViewById(R.id.btnConfirm);

        btnGoBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        tvBack.setOnClickListener(this);

        setSpecificTypeFace(layout, AppConstants.WALSHEIM_LIGHT);
    }

    @Override
    public void bindControls() {
        Intent intent = getIntent();
        if(intent != null) {
            String from = intent.getStringExtra("from");
            if(from.equalsIgnoreCase("AdminHospital")) {
                hospitalData = (AdminLoginResponse.DoctorAddress) intent.getSerializableExtra("hospitalData");
                loginDoctorID = intent.getIntExtra("doctorId",0);
                selectedDoctors="";
                clinicID = hospitalData.getAddressID();
                hospitalID = 0;
                isClinic = true;
                selected_clinic_name = hospitalData.getClinicName();
            } else if(from.equalsIgnoreCase("AdminDoctor")) {
                hospitalData = (AdminLoginResponse.DoctorAddress) intent.getSerializableExtra("hospitalData");
                loginDoctorID = intent.getIntExtra("doctorId",0);
                selectedDoctors = intent.getStringExtra("selectedDoctors");
                clinicID = 0;
                hospitalID = hospitalData.getExtDetails().getHospitalID();
                isClinic = false;
                selected_clinic_name = "";
            }
        }

        tvLogin.setEnabled(false);
        tvHome.setVisibility(View.GONE);
        tvBack.setVisibility(View.GONE);
        tvBack.setText("Previous Page");
        tvTitle.setText("Configuration confirmation");
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvBack:
            case R.id.btnGoBack:
                finish();
                break;
            case R.id.btnConfirm:
                // Saving the Hospital ID of the Login.
                Preference preference = new Preference(AdminConfirmation.this);
                preference.saveBooleanInPreference(Preference.IS_LOGGEDIN, true);
                preference.saveBooleanInPreference(Preference.IS_CLINIC, isClinic);
                preference.saveIntInPreference(Preference.LOGIN_DOCTOR_ID, loginDoctorID);
                preference.saveIntInPreference(Preference.SELECTED_CLINIC_ADDRESS_ID, clinicID);
                preference.saveIntInPreference(Preference.SELECTED_HOSPITAL_ID, hospitalID);
                preference.saveStringInPreference(Preference.SELECTED_DOCTORS, selectedDoctors);
                preference.saveStringInPreference(Preference.CLINIC_NAME, selected_clinic_name);
                preference.commitPreference();

                Log.e("isClinic", ""+isClinic);
                Log.e("loginDoctorID",""+loginDoctorID);
                Log.e("clinicID",""+clinicID);
                Log.e("hospitalID",""+hospitalID);
                Log.e("selectedDoctors",""+selectedDoctors);

                showToast("Configuration is successfully updated.");
                Intent intent = new Intent(AdminConfirmation.this, SelectDoctorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }

}
