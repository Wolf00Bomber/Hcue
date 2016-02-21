package com.appdest.hcue;

import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appdest.hcue.BaseActivity;
import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.ConfirmCancellationRequest;
import com.appdest.hcue.model.GetDoctorsResponse;
import com.appdest.hcue.model.GetPatientAppointmentsRequest;
import com.appdest.hcue.model.GetPatientAppointmentsResponse;
import com.appdest.hcue.model.GetPatientResponse;
import com.appdest.hcue.services.RestCallback;
import com.appdest.hcue.services.RestClient;
import com.appdest.hcue.services.RestError;
import com.appdest.hcue.utils.Connectivity;
import com.appdest.hcue.utils.TimeUtils;

import java.util.Date;

import retrofit.client.Response;

/**
 * Created by shyamprasadg on 31/01/16.
 */
public class ConfirmCancelationActivity extends BaseActivity implements View.OnClickListener
{
    private LinearLayout llCancel;
    private TextView tvPatientName,tvAppointmentTime,tvDoctorName,tvHeading;
    private Button btnConfirm;
    private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;
    private GetPatientAppointmentsResponse.AppointmentRow Appointment;
    private GetPatientResponse.PatientInfo patientInfo;

    private String chosenTime;

    @Override
    public void initializeControls()
    {
        llCancel = (LinearLayout) inflater.inflate(R.layout.confirm_cancelation, null);
        llBody.addView(llCancel);

        tvPatientName   =   (TextView)  llCancel.findViewById(R.id.tvPatientName);
        tvDoctorName    =   (TextView)  llCancel.findViewById(R.id.tvDoctorName);
        tvAppointmentTime      =   (TextView)  llCancel.findViewById(R.id.tvAppointmentTime);
        tvHeading       =   (TextView)  llCancel.findViewById(R.id.tvHeading);

        btnConfirm      =   (Button)    llCancel.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);

        setSpecificTypeFace(llCancel, AppConstants.WALSHEIM_LIGHT);
        tvHeading.setTypeface(AppConstants.WALSHEIM_MEDIUM);
        btnConfirm.setTypeface(AppConstants.WALSHEIM_BOLD);

        tvTitle.setText("Confirm Cancellation");
    }

    @Override
    public void bindControls()
    {
        tvLogin.setEnabled(false);
        Intent i = getIntent();
        if(i.hasExtra("PatientInfo") && i.hasExtra("DoctorDetails") && i.hasExtra("PatientInfo"))
        {
            Appointment = (GetPatientAppointmentsResponse.AppointmentRow) i.getSerializableExtra("Appointment");
            selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail)i.getSerializableExtra("DoctorDetails");
            patientInfo = (GetPatientResponse.PatientInfo) i.getSerializableExtra("PatientInfo");
        }
        else
        {
            finish();
            return;
        }

        long dayInstance = Appointment.appointmentDetails.ConsultationDt.longValue();
        long timeInstance = TimeUtils.getLongForHHMMSS(Appointment.appointmentDetails.StartTime + ":00");
        long totalInstance = dayInstance + timeInstance;
        StringBuilder sb = new StringBuilder();
        sb.append(DateUtils.isToday(totalInstance) ? "Today" : TimeUtils.getDay(totalInstance))
                .append(" - ")
                .append(TimeUtils.format2DateProper(totalInstance));
        chosenTime = sb.toString();
        tvDoctorName.setText(selectedDoctorDetails.FullName);
        tvAppointmentTime.setText(chosenTime);
        tvPatientName.setText(patientInfo.patients.get(0).FullName);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnConfirm:
                callService();
                break;
        }
    }

    private void callService()
    {
        if (Connectivity.isConnected(ConfirmCancelationActivity.this)) {
            confirmCancellation();
        } else {
            Toast.makeText(ConfirmCancelationActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmCancellation()
    {
        ConfirmCancellationRequest confirmCancellationRequest = new ConfirmCancellationRequest();
        confirmCancellationRequest.setAppointmentID(Appointment.appointmentDetails.AppointmentID);
        confirmCancellationRequest.setUSRType("KIOSK");
        confirmCancellationRequest.setAppointmentStatus("C");
        confirmCancellationRequest.setUSRId(0);

        String url = "http://dct4avjn1lfw.cloudfront.net";
        RestClient.getAPI(url).confirmAppointmentCancellation(confirmCancellationRequest, new RestCallback<String>() {
            @Override
            public void failure(RestError restError) {
                Log.e("Cancellation", "" + restError.getErrorMessage());
                Toast.makeText(ConfirmCancelationActivity.this, "Couldn't cancel the Appointment", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(String responseString, Response response) {
                Toast.makeText(ConfirmCancelationActivity.this, responseString, Toast.LENGTH_SHORT).show();
                if ("-Success-".equalsIgnoreCase(responseString)) {
                    Intent intent = new Intent(ConfirmCancelationActivity.this, CancelationSummaryActivity.class);
                    intent.putExtra("PatientName", patientInfo.patients.get(0).FullName);
                    intent.putExtra("DoctorName", selectedDoctorDetails.FullName);
                    intent.putExtra("chosenTime", chosenTime);
                    startActivity(intent);
                } else {
                    Log.i("Response", "" + response.getReason());
                }
            }
        });
    }


}
