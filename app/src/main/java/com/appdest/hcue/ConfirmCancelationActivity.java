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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private GetPatientAppointmentsResponse.AppointmentRow Appointment;
    private GetPatientResponse.PatientInfo patientInfo;
    private GetPatientResponse.Patient patient = null;
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
        btnConfirm.setTypeface(AppConstants.WALSHEIM_MEDIUM);

        tvTitle.setText("Confirm Cancellation");
    }

    @Override
    public void bindControls()
    {
        tvLogin.setEnabled(false);
        Intent i = getIntent();
        if(i.hasExtra("PatientInfo") && i.hasExtra("Appointment"))
        {
            Appointment = (GetPatientAppointmentsResponse.AppointmentRow) i.getSerializableExtra("Appointment");
            patientInfo = (GetPatientResponse.PatientInfo) i.getSerializableExtra("PatientInfo");
        }
        else{
            finish(); return;
        }
        long dayInstance = Appointment.appointmentDetails.ConsultationDt.longValue();
        long timeInstance = TimeUtils.getLongForHHMMSS(Appointment.appointmentDetails.StartTime + ":00");
        long totalInstance = dayInstance + timeInstance;
        StringBuilder sb = new StringBuilder();
        sb.append(DateUtils.isToday(totalInstance) ? ("Today") : (/*TimeUtils.getDay(totalInstance) + " - " +*/ TimeUtils.format2DateProper(totalInstance)));
        chosenTime = sb.toString();
        tvDoctorName.setText("Dr."+Appointment.doctorDetail.doctorFullName);
        tvAppointmentTime.setText(chosenTime+" - " +getFormattedTime(Appointment.appointmentDetails.StartTime));
        ArrayList<GetPatientResponse.Patient> patients = patientInfo.patients;

        if(patients != null)
        {
            for(int j = 0; j < patients.size(); j++ )
            {
                if(patients.get(j).getPatientID().longValue() == Appointment.appointmentDetails.PatientID.longValue())
                    patient = patients.get(j);
            }
        }
        tvPatientName.setText(patient != null ? patient.FullName : "NA");
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
         //       Toast.makeText(ConfirmCancelationActivity.this, responseString, Toast.LENGTH_SHORT).show();
                if ("-Success-".equalsIgnoreCase(responseString)) {
                    Intent intent = new Intent(ConfirmCancelationActivity.this, CancelationSummaryActivity.class);
                    intent.putExtra("PatientName", patient.FullName);
                    intent.putExtra("DoctorName", Appointment.doctorDetail.doctorFullName);
                    intent.putExtra("chosenTime", tvAppointmentTime.getText().toString().trim());
                    startActivity(intent);
                    finish();
                } else {
                    Log.i("Response", "" + response.getReason());
                }
            }
        });
    }

    private String getFormattedTime(String time)
    {
        String formattedTime = "";
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateObj = sdf.parse(time);
            formattedTime = new SimpleDateFormat("hh:mm a").format(dateObj).toUpperCase();
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return formattedTime;
    }


}
