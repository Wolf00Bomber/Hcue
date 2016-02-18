package com.appdest.hcue;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.GetDoctorsResponse;
import com.appdest.hcue.model.GetPatientAppointmentsResponse;
import com.appdest.hcue.model.GetPatientResponse;

/**
 * Created by shyamprasadg on 31/01/16.
 */
public class CancelationSummaryActivity extends BaseActivity implements View.OnClickListener
{
    private LinearLayout llCancel;
    private TextView tvPatientName,tvDateTime,tvDoctorName,tvHeading;
    private Button btnOk;
    private String PatientName, DoctorName, chosenTime;

    @Override
    public void initializeControls()
    {
        llCancel = (LinearLayout) inflater.inflate(R.layout.cancelation_summery, null);
        llBody.addView(llCancel);

        tvPatientName   =   (TextView)  llCancel.findViewById(R.id.tvPatientName);
        tvDoctorName    =   (TextView)  llCancel.findViewById(R.id.tvDoctorName);
        tvDateTime      =   (TextView)  llCancel.findViewById(R.id.tvDateTime);
        tvHeading       =   (TextView)  llCancel.findViewById(R.id.tvHeading);

        btnOk      =   (Button)    llCancel.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(this);
        setSpecificTypeFace(llCancel, AppConstants.WALSHEIM_LIGHT);
        tvHeading.setTypeface(AppConstants.WALSHEIM_MEDIUM);
        btnOk.setTypeface(AppConstants.WALSHEIM_BOLD);

        tvTitle.setText("Cancellation Summary");
    }

    @Override
    public void bindControls() {
        Intent i = getIntent();
        if(i.hasExtra("Appointment") && i.hasExtra("DoctorName") && i.hasExtra("chosenTime"))
        {
            PatientName = i.getStringExtra("Appointment");
            DoctorName = i.getStringExtra("DoctorName");
            chosenTime = i.getStringExtra("chosenTime");
        }
        else
        {
            finish();
            return;
        }
        tvDoctorName.setText(DoctorName);
        tvPatientName.setText(PatientName);
        tvDateTime.setText(chosenTime);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnOk:
                Intent intent   = new Intent(CancelationSummaryActivity.this,SelectDoctorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

    }
}
