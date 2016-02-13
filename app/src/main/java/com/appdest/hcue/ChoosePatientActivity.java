package com.appdest.hcue;

import com.appdest.hcue.common.AppConstants;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChoosePatientActivity extends BaseActivity implements OnClickListener
{
	private LinearLayout llPatient,llAddPatient,llPatientDetails;
	private TextView tvAdd,tvChoosePatient;
	private GridView gridView;
	private GridAdapter adapter;

	@Override
	public void initializeControls() 
	{
		llPatient = (LinearLayout) inflater.inflate(R.layout.choose_patitent, null);
		llBody.addView(llPatient);

		tvAdd			 =	(TextView)	llPatient.findViewById(R.id.tvAdd);
		tvChoosePatient	 =	(TextView)	llPatient.findViewById(R.id.tvChoosePatient);
		
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
		adapter = new GridAdapter();
		gridView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		 	case R.id.llAddPatient:
		 		Intent intent = new Intent(ChoosePatientActivity.this,RegistrationActivity.class);
		 		startActivity(intent);
			 break;

		}
	}

	//Custom Adapter for GridView
	private class GridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 4;
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
			convertView = (LinearLayout) LayoutInflater.from(ChoosePatientActivity.this).inflate(R.layout.choose_patient_grid_cell, parent, false);
			TextView tvPatientName = (TextView) convertView.findViewById(R.id.tvPatientName);
			TextView tvGenderAndAge = (TextView) convertView.findViewById(R.id.tvGenderAndAge);
			LinearLayout llPatientDetails =	(LinearLayout) convertView.findViewById(R.id.llPatientDetails);

			if(position%2==0){
				tvGenderAndAge.setText("Male, 27 years");
				llPatientDetails.setBackgroundResource(R.drawable.added_patient_male);
			} else{
				tvGenderAndAge.setText("Female, 23 years");
				llPatientDetails.setBackgroundResource(R.drawable.added_patient_female);
			}

			return convertView;
		}
	}

}
