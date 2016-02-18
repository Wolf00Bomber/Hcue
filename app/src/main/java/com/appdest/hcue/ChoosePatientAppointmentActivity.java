package com.appdest.hcue;

import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.AddPatientResponse;
import com.appdest.hcue.model.FeedbackRequest;
import com.appdest.hcue.model.GetDoctors;
import com.appdest.hcue.model.GetDoctorsResponse;
import com.appdest.hcue.model.GetPatientAppointmentsRequest;
import com.appdest.hcue.model.GetPatientAppointmentsResponse;
import com.appdest.hcue.model.GetPatientDetailsResponse;
import com.appdest.hcue.model.GetPatientRequest;
import com.appdest.hcue.model.GetPatientResponse;
import com.appdest.hcue.services.RestCallback;
import com.appdest.hcue.services.RestClient;
import com.appdest.hcue.services.RestError;
import com.appdest.hcue.utils.Connectivity;
import com.appdest.hcue.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;

import retrofit.client.Response;

/**
 * Created by Sri Krishna on 18/02/16.
 */
public class ChoosePatientAppointmentActivity extends BaseActivity
{
    private LinearLayout llFeedbackAppointment;
    private TextView tvHeading,tvPatientName;
    private GridView gvAppointments;
    private GridAdapter gridAdapter;
    private Button btnFeedbackAppointment;
    private GetPatientResponse.PatientInfo patientInfo;
    private GetPatientAppointmentsResponse.AppointmentRow selectedPatientAppointment;
    private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;
    private int pageCount;
    private String fromActivity;

    @Override
    public void initializeControls()
    {
        llFeedbackAppointment = (LinearLayout) inflater.inflate(R.layout.booked_patient_appointments_for_feedback, null);

        llBody.addView(llFeedbackAppointment);

        tvHeading  				= (TextView)	llFeedbackAppointment.findViewById(R.id.tvHeading);
        tvPatientName  			= (TextView)	llFeedbackAppointment.findViewById(R.id.tvPatientName);

        btnFeedbackAppointment 		= (Button)		llFeedbackAppointment.findViewById(R.id.btnFeedbackAppointment);

        tvBack.setVisibility(View.GONE);
        tvHome.setVisibility(View.GONE);

        gvAppointments = (GridView) llFeedbackAppointment.findViewById(R.id.gvAppointments);

        setSpecificTypeFace(llFeedbackAppointment, AppConstants.WALSHEIM_MEDIUM);
        tvHeading.setTypeface(AppConstants.WALSHEIM_LIGHT);
        btnFeedbackAppointment.setTypeface(AppConstants.WALSHEIM_BOLD);
        tvTitle.setText("Choose appointment to give feedback");
    }

    @Override
    public void bindControls()
    {
        Intent i = getIntent();
        if(i.hasExtra("PatientInfo") && i.hasExtra("DoctorDetails"))
        {
            patientInfo = (GetPatientResponse.PatientInfo) i.getSerializableExtra("PatientInfo");
            selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail)i.getSerializableExtra("DoctorDetails");
            fromActivity = i.hasExtra("From") ? i.getStringExtra("From") : "";
        }
        if(patientInfo == null)
        {
            finish();
            return;
        }

        tvPatientName.setText(patientInfo.patients.get(0).FullName);
        gridAdapter = new GridAdapter();
        gvAppointments.setAdapter(gridAdapter);
        gvAppointments.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) {
                selectedPatientAppointment = (GetPatientAppointmentsResponse.AppointmentRow) gridAdapter.getItem(pos);
            }

        });

        btnFeedbackAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPatientAppointment == null) {
                    Toast.makeText(ChoosePatientAppointmentActivity.this,
                            "Select an appointment for Feedback",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                FeedbackRequest feedbackRequest = new FeedbackRequest();
                feedbackRequest.setPatientID(selectedPatientAppointment.appointmentDetails.PatientID);
                feedbackRequest.setDoctorID(selectedPatientAppointment.appointmentDetails.DoctorID);
                feedbackRequest.setUSRType("PATIENT");
                feedbackRequest.setUSRId(0);
                feedbackRequest.setAppointmentID(selectedPatientAppointment.appointmentDetails.AppointmentID);

                Intent intent = new Intent(ChoosePatientAppointmentActivity.this, FeedbackActivity.class);
                intent.putExtra("FeedbackRequest", feedbackRequest);
                intent.putExtra("DoctorDetails", selectedDoctorDetails);
                startActivity(intent);
            }
        });
        callService();

    }

    private void callService()
    {
        if (Connectivity.isConnected(ChoosePatientAppointmentActivity.this)) {
            getPatientAppointments(pageCount);
        } else {
            Toast.makeText(ChoosePatientAppointmentActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPatientAppointments(int pageNumber)
    {
        final GetPatientAppointmentsRequest getPatientAppointmentsRequest = new GetPatientAppointmentsRequest();
        getPatientAppointmentsRequest.setFamilyHdID(patientInfo.patients.get(0).getFamilyHdID());
        getPatientAppointmentsRequest.setPageNumber(pageNumber);
        getPatientAppointmentsRequest.setPageSize(4);
        getPatientAppointmentsRequest.setPatientID(patientInfo.patients.get(0).PatientID/*selectedPatientAppointment.appointmentDetails.PatientID*/);
        getPatientAppointmentsRequest.setSort("asc");
        String baseDate = TimeUtils.format2Date(new Date()/*selectedPatientAppointment.appointmentDetails.ConsultationDt.longValue()*/);
        getPatientAppointmentsRequest.setBaseDate(baseDate);
        getPatientAppointmentsRequest.setCount(0);
        getPatientAppointmentsRequest.setIndicator("P");

        String url = "http://d1lmwj8jm5d3bc.cloudfront.net";
        RestClient.getAPI(url).getPatientAppointment(getPatientAppointmentsRequest, new RestCallback<GetPatientAppointmentsResponse>() {
            @Override
            public void failure(RestError restError) {
                Log.e("Doctor Appointement", "" + restError.getErrorMessage());
                Toast.makeText(ChoosePatientAppointmentActivity.this, "Couldn't get the List of Appointments", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(GetPatientAppointmentsResponse getPatientAppointmentsResponse, Response response) {
                if (getPatientAppointmentsResponse != null && getPatientAppointmentsResponse.count > 0) {
                    pageCount++;
                    gridAdapter.refresh(getPatientAppointmentsResponse.rows);
                } else {
                    Log.i("Response", "" + response.getReason());
                    Toast.makeText(ChoosePatientAppointmentActivity.this, "No Appointments Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private class GridAdapter extends BaseAdapter {

        private ArrayList<GetPatientAppointmentsResponse.AppointmentRow> patientAppointments;

        @Override
        public int getCount() {
            if(patientAppointments != null && patientAppointments.size() > 0)
                return patientAppointments.size();
            return 0;
        }

        @Override
        public Object getItem(int arg0) {
            return patientAppointments.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        public void refresh(ArrayList<GetPatientAppointmentsResponse.AppointmentRow> patientAppointments)
        {
            this.patientAppointments = patientAppointments;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int pos, View view, ViewGroup parent) {
            view = LayoutInflater.from(ChoosePatientAppointmentActivity.this).inflate(R.layout.appointments_history_cell,null);
            TextView tvDoctorName = (TextView) view.findViewById(R.id.tvDoctorName);
            TextView tvDateTime = (TextView) view.findViewById(R.id.tvDateTime);


            final ImageView ivCheck = (ImageView) view.findViewById(R.id.ivCheck);

                tvDateTime.setTypeface(AppConstants.WALSHEIM_LIGHT);
                tvDoctorName.setTypeface(AppConstants.WALSHEIM_LIGHT);

            final GetPatientAppointmentsResponse.AppointmentRow data = patientAppointments.get(pos);

            long dayInstance = data.appointmentDetails.ConsultationDt.longValue();
            long timeInstance = TimeUtils.getLongForHHMMSS(data.appointmentDetails.StartTime+":00");
            long totalInstance = dayInstance + timeInstance;
            StringBuilder sb = new StringBuilder();
            sb.append(DateUtils.isToday(totalInstance) ? "Today" : TimeUtils.getDay(totalInstance))
                    .append(" - ")
                    .append(TimeUtils.format2DateProper(totalInstance));


            tvDoctorName.setText(data.doctorDetail.doctorFullName);
            tvDateTime.setText(sb.toString());

            ivCheck.setTag(R.id.ivCheck, pos);

                if(data.isSelected){
                    ivCheck.setBackgroundResource(R.drawable.check_box_sq_admin);
                } else {
                    ivCheck.setBackgroundResource(R.drawable.un_check_admin);
                }

            ivCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) ivCheck.getTag(R.id.ivCheck);
                        Log.e("clicked at : ",""+pos);
                        for(GetPatientAppointmentsResponse.AppointmentRow data : patientAppointments)
                            data.isSelected = false;
                        if(patientAppointments.get(pos).isSelected) {
                            patientAppointments.get(pos).isSelected = false;
                        } else {
                            patientAppointments.get(pos).isSelected = true;
                        }
                        selectedPatientAppointment = data;
                        notifyDataSetChanged();
                    }
                });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivCheck.performClick();
                }
            });

            return view;
        }

    }
}
