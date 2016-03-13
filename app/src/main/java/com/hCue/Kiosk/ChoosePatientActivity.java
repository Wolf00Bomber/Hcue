package com.hCue.Kiosk;

import com.hCue.Kiosk.common.AppConstants;
import com.hCue.Kiosk.model.GetDoctorsResponse;
import com.hCue.Kiosk.model.GetPatientResponse;
import com.hCue.Kiosk.utils.Preference;
import com.hCue.Kiosk.utils.TimeUtils;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
public class ChoosePatientActivity extends BaseActivity implements OnClickListener
{
	private LinearLayout llPatient,llAddPatient;
	private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;
	private Number phNumber;
	private String PhoneCode;
    private GetPatientResponse getPatientResponse;
	private TextView tvAdd;
	private GridView gridView;
	private GridAdapter adapter;
	private String fromActivity;
    private TextView tvChoosePatient ;

	@Override
	public void initializeControls() 
	{
		llPatient = (LinearLayout) inflater.inflate(R.layout.choose_patitent, null);
		llBody.addView(llPatient);

		tvAdd			         =	(TextView)	llPatient.findViewById(R.id.tvAdd);
        tvChoosePatient			 =	(TextView)	llPatient.findViewById(R.id.tvChoosePatient);

		llAddPatient	 =	(LinearLayout) llPatient.findViewById(R.id.llAddPatient);

		gridView = (GridView) llPatient.findViewById(R.id.gridView);

		setSpecificTypeFace(llPatient, AppConstants.WALSHEIM_LIGHT);

        tvAdd.setTypeface(AppConstants.WALSHEIM_LIGHT);
		
		llAddPatient.setOnClickListener(this);
        extractInfoFromIntent();
		tvTitle.setText("Book Appointment for");

        if("Feedback".equalsIgnoreCase(fromActivity) || "CancelAppointment".equalsIgnoreCase(fromActivity))
        {
            if("Feedback".equalsIgnoreCase(fromActivity))
        {
            tvTitle.setText("Choose patient to give feedback");
        }else
        {
            tvTitle.setText("Choose patient to cancel appointment");
        }
            llAddPatient.setVisibility(View.GONE);
        }else
        {
            llAddPatient.setVisibility(View.VISIBLE);
        }
	}

    private boolean extractInfoFromIntent()
    {
        boolean isValid = false;
        Intent i = getIntent();

        if(i.hasExtra("PhoneNumber") && i.hasExtra("GetPatientResponse"))
        {
            phNumber = (Number) i.getSerializableExtra("PhoneNumber");
            PhoneCode = i.getStringExtra("PhoneCode");
            getPatientResponse = (GetPatientResponse) i.getSerializableExtra("GetPatientResponse");
            isValid = true;
        }
        fromActivity = i.hasExtra("From") ? i.getStringExtra("From") : "";

        if(!"CancelAppointment".equalsIgnoreCase(fromActivity))
        {
            if(i.hasExtra("DoctorDetails"))
                selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail) i.getSerializableExtra("DoctorDetails");
            else
                isValid = false;
        }
        return isValid;
    }


    @Override
    protected void onResume() {
        super.onResume();
        resetSharedValues();
    }

    private void resetSharedValues() {

        Preference preference = new Preference(ChoosePatientActivity.this);
        preference.saveStringInPreference("PreferenceSource","");
        preference.saveStringInPreference("MaritalStatus","");
        preference.saveStringInPreference("Education","");
        preference.saveStringInPreference("Ocupation","");

        preference.commitPreference();
    }

    @Override
	public void bindControls() 
	{
		tvLogin.setEnabled(false);
       /* if(!extractInfoFromIntent()){
            finish();  return;
        }*/
        if(selectedDoctorDetails == null && getPatientResponse == null)
        {
            tvChoosePatient.setText("No patients found");
            gridView.setVisibility(View.GONE);
            llAddPatient.setVisibility(View.GONE);
        }
		adapter = new GridAdapter();
		gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if("Feedback".equalsIgnoreCase(fromActivity) || "CancelAppointment".equalsIgnoreCase(fromActivity))
                {
                    GetPatientResponse.PatientInfo patientInfo = getPatientResponse.rows.get(position);
                    Intent summary;
                    if("Feedback".equalsIgnoreCase(fromActivity))
                    {
                        summary = new Intent(ChoosePatientActivity.this, ChoosePatientAppointmentActivity.class);
                        summary.putExtra("DoctorDetails", selectedDoctorDetails);
                    }
                    else
                    {
                        summary = new Intent(ChoosePatientActivity.this, CancelAppointmentActivity.class);
                    }

                    summary.putExtra("From", fromActivity);
                    summary.putExtra("PatientInfo", patientInfo);
                    startActivity(summary);
                }
                else
                {
                    GetPatientResponse.PatientInfo patientInfo = getPatientResponse.rows.get(position);
                    Intent summary = new Intent(ChoosePatientActivity.this, ChooseAppointmentActivityNew.class);
                    summary.putExtra("PhoneNumber", phNumber);
                    summary.putExtra("PhoneCode", PhoneCode);
                    summary.putExtra("DoctorDetails", selectedDoctorDetails);
                    summary.putExtra("PatientInfo", patientInfo);
                    if((patientInfo.patientEmail == null || patientInfo.patientEmail.size()== 0)&&(patientInfo.patientAddress == null || patientInfo.patientAddress.size()== 0))
                        summary.putExtra("ShowProvideMoreDetails",true);
                    else
                        summary.putExtra("ShowProvideMoreDetails", false);
                    startActivity(summary);
                }

            }
        });
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		 	case R.id.llAddPatient:
		 		Intent intent = new Intent(ChoosePatientActivity.this,RegistrationActivity.class);
                intent.putExtra("PhoneNumber", phNumber);
                intent.putExtra("PhoneCode", PhoneCode);
                intent.putExtra("DoctorDetails", selectedDoctorDetails);
                intent.putExtra("GetPatientResponse", getPatientResponse);
		 		startActivity(intent);
			 break;
		}
	}

	//Custom Adapter for GridView
	private class GridAdapter extends BaseAdapter {

        private String AGE_SEX_FORMAT = "%s, %d years";

		@Override
		public int getCount() {
            if(getPatientResponse != null && getPatientResponse.rows != null && getPatientResponse.rows.size() > 0)
			    return getPatientResponse.rows.size();
            else
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
			convertView = LayoutInflater.from(ChoosePatientActivity.this).inflate(R.layout.choose_patient_grid_cell, parent, false);
			TextView tvPatientName = (TextView) convertView.findViewById(R.id.tvPatientName);
			TextView tvGenderAndAge = (TextView) convertView.findViewById(R.id.tvGenderAndAge);
			LinearLayout llPatientDetails =	(LinearLayout) convertView.findViewById(R.id.llPatientDetails);

            GetPatientResponse.PatientInfo patientInfo = getPatientResponse.rows.get(position);
            GetPatientResponse.Patient patient = patientInfo.patients.get(0);
            String sex;
            if("M".equalsIgnoreCase(patient.Gender))
            {
                sex= "Male";
                llPatientDetails.setBackgroundResource(R.drawable.added_patient_male);
            }
            else if("F".equalsIgnoreCase(patient.Gender))
            {
                sex= "Female";
                llPatientDetails.setBackgroundResource(R.drawable.added_patient_female);
            }
            else
                sex = "NA";

            int age = TimeUtils.getDiffYears(new Date(patient.getDOB().longValue()), new Date());

            tvGenderAndAge.setText(String.format(AGE_SEX_FORMAT, sex, age));
            tvPatientName.setText(patient.FullName);
            tvPatientName.setTypeface(AppConstants.WALSHEIM_BOLD);
            tvGenderAndAge.setTypeface(AppConstants.WALSHEIM_LIGHT);
            return convertView;
		}
	}

}
