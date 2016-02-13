package com.appdest.hcue;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.GetDoctorsResponse;
import com.appdest.hcue.model.GetPatientResponse;

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

public class ChoosePatientActivity extends BaseActivity implements OnClickListener
{
	private LinearLayout llPatient,llAddPatient;
	private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;
	private Number phNumber;
	private String PhoneCode;
    private GetPatientResponse getPatientResponse;
	boolean isActivityNeedsFinish = false;
	private TextView tvAdd;
	private GridView gridView;
	private GridAdapter adapter;

	@Override
	public void initializeControls() 
	{
		Intent i = getIntent();
		if(i.hasExtra("DoctorDetails") && i.hasExtra("PhoneNumber") && i.hasExtra("GetPatientResponse"))
		{
			selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail) i.getSerializableExtra("DoctorDetails");
			phNumber = (Number) i.getSerializableExtra("PhoneNumber");
			PhoneCode = i.getStringExtra("PhoneCode");
            getPatientResponse = (GetPatientResponse) i.getSerializableExtra("GetPatientResponse");
		}
		else
		{
			isActivityNeedsFinish = true;
			finish();
			return;
		}

		llPatient = (LinearLayout) inflater.inflate(R.layout.choose_patitent, null);
		llBody.addView(llPatient);

		tvAdd			 =	(TextView)	llPatient.findViewById(R.id.tvAdd);

		llAddPatient	 =	(LinearLayout) llPatient.findViewById(R.id.llAddPatient);

		gridView = (GridView) llPatient.findViewById(R.id.gridView);

		setSpecificTypeFace(llPatient, AppConstants.WALSHEIM_LIGHT);

        tvAdd.setTypeface(AppConstants.WALSHEIM_BOLD);
		
		llAddPatient.setOnClickListener(this);

		tvTitle.setText("Book Appointment for");
	}

	@Override
	public void bindControls() 
	{
		if(isActivityNeedsFinish)
			return;
		adapter = new GridAdapter();
		gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetPatientResponse.PatientInfo patientInfo = getPatientResponse.rows.get(position);
                Intent summary = new Intent(ChoosePatientActivity.this, ChooseAppointmentActivity.class);
                summary.putExtra("PhoneNumber", phNumber);
                summary.putExtra("PhoneCode", PhoneCode);
                summary.putExtra("DoctorDetails", selectedDoctorDetails);
                summary.putExtra("PatientInfo", patientInfo);
                startActivity(summary);
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
        private long YEAR = 365 * 24 * 60 * 60 * 1000;

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
            tvGenderAndAge.setText(String.format(AGE_SEX_FORMAT, sex, patient.getDOB().longValue()/YEAR));
            tvPatientName.setText(patient.FullName);
            return convertView;
		}
	}

}
