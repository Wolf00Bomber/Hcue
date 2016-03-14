package com.hCue.Kiosk;

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

import com.hCue.Kiosk.common.AppConstants;
import com.hCue.Kiosk.model.GetPatientAppointmentsRequest;
import com.hCue.Kiosk.model.GetPatientAppointmentsResponse;
import com.hCue.Kiosk.model.GetPatientResponse;
import com.hCue.Kiosk.services.RestCallback;
import com.hCue.Kiosk.services.RestClient;
import com.hCue.Kiosk.services.RestError;
import com.hCue.Kiosk.utils.Connectivity;
import com.hCue.Kiosk.utils.TimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit.client.Response;

/**
 * Created by shyamprasadg on 30/01/16.
 */
public class CancelAppointmentActivity extends BaseActivity
{
    private LinearLayout llCancelAppointment;
    private TextView tvHeading,tvPatientName, tvPatient;
    private GridView gvAppointments;
    private GridAdapter gridAdapter;
    private Button btnCancelAppointment;
    private GetPatientResponse.PatientInfo patientInfo;
    private GetPatientAppointmentsResponse.AppointmentRow selectedPatientAppointment;
    private int pageCount;

    @Override
    public void initializeControls()
    {
        llCancelAppointment = (LinearLayout) inflater.inflate(R.layout.booked_appointments, null);

        llBody.addView(llCancelAppointment);

        tvHeading  				= (TextView)	llCancelAppointment.findViewById(R.id.tvHeading);
        tvPatientName  			= (TextView)	llCancelAppointment.findViewById(R.id.tvPatientName);
        tvPatient               = (TextView)	llCancelAppointment.findViewById(R.id.tvPatient);
        btnCancelAppointment 	= (Button)		llCancelAppointment.findViewById(R.id.btnCancelAppointment);

        tvBack.setVisibility(View.VISIBLE);
        tvHome.setVisibility(View.GONE);


        gvAppointments = (GridView) llCancelAppointment.findViewById(R.id.gvAppointments);

//        setSpecificTypeFace(llCancelAppointment, AppConstants.WALSHEIM_MEDIUM);
        tvHeading.setTypeface(AppConstants.WALSHEIM_MEDIUM);
        btnCancelAppointment.setTypeface(AppConstants.WALSHEIM_MEDIUM);
        tvPatientName.setTypeface(AppConstants.WALSHEIM_LIGHT);
        tvPatient.setTypeface(AppConstants.WALSHEIM_LIGHT);

        tvTitle.setText("Cancel Your Appointment");

        tvHeading.setVisibility(View.GONE);
        btnCancelAppointment.setVisibility(View.GONE);

    }

    @Override
    public void bindControls()
    {
        tvLogin.setEnabled(false);
        Intent i = getIntent();
        if(i.hasExtra("PatientInfo"))
        {
            patientInfo = (GetPatientResponse.PatientInfo) i.getSerializableExtra("PatientInfo");
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

        btnCancelAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPatientAppointment == null) {
                    Toast.makeText(CancelAppointmentActivity.this,
                            "Select an appointment for Cancellation",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(CancelAppointmentActivity.this, ConfirmCancelationActivity.class);
                intent.putExtra("Appointment", selectedPatientAppointment);
                intent.putExtra("PatientInfo", patientInfo);
                startActivity(intent);
            }
        });

        callService();

    }

    private void callService()
    {
        if (Connectivity.isConnected(CancelAppointmentActivity.this)) {
            getPatientAppointments(pageCount);
        } else {
            Toast.makeText(CancelAppointmentActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPatientAppointments(int pageNumber)
    {
        final GetPatientAppointmentsRequest getPatientAppointmentsRequest = new GetPatientAppointmentsRequest();
        getPatientAppointmentsRequest.setFamilyHdID(patientInfo.patients.get(0).getFamilyHdID());
        getPatientAppointmentsRequest.setPageNumber(pageNumber);
        getPatientAppointmentsRequest.setPageSize(30);
        getPatientAppointmentsRequest.setPatientID(patientInfo.patients.get(0).PatientID/*selectedPatientAppointment.appointmentDetails.PatientID*/);
        getPatientAppointmentsRequest.setSort("asc");
        String baseDate = TimeUtils.format2Date(new Date()/*selectedPatientAppointment.appointmentDetails.ConsultationDt.longValue()*/);
        getPatientAppointmentsRequest.setBaseDate(baseDate);
        getPatientAppointmentsRequest.setCount(0);
        getPatientAppointmentsRequest.setIndicator("F");

        String url = "http://d1lmwj8jm5d3bc.cloudfront.net";
        RestClient.getAPI(url).getPatientAppointment(getPatientAppointmentsRequest, new RestCallback<GetPatientAppointmentsResponse>() {
            @Override
            public void failure(RestError restError) {
                Log.e("Doctor Appointement", "" + restError.getErrorMessage());
                Toast.makeText(CancelAppointmentActivity.this, "Couldn't get the List of Appointments.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(GetPatientAppointmentsResponse getPatientAppointmentsResponse, Response response) {
                if (getPatientAppointmentsResponse != null && getPatientAppointmentsResponse.count > 0) {
                    tvHeading.setText("Choose appointment(s) to cancel");
                    tvHeading.setVisibility(View.VISIBLE);
                    btnCancelAppointment.setVisibility(View.VISIBLE);
                    pageCount++;
                    gridAdapter.refresh(getPatientAppointmentsResponse.rows);
                } else {
                    btnCancelAppointment.setVisibility(View.GONE);
                    tvHeading.setText("You don't have any appointments to cancel");
                    tvHeading.setVisibility(View.VISIBLE);
                    Log.i("Response", "" + response.getReason());
                    Toast.makeText(CancelAppointmentActivity.this, "No Appointments Found", Toast.LENGTH_SHORT).show();
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
            return arg0;
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
            if(view == null)
            view = LayoutInflater.from(CancelAppointmentActivity.this).inflate(R.layout.appointments_history_cell,null);
            TextView tvDoctorName = (TextView) view.findViewById(R.id.tvDoctorName);
            TextView tvDateTime = (TextView) view.findViewById(R.id.tvDateTime);
            TextView tvTime = (TextView) view.findViewById(R.id.tvTime);


            final ImageView ivCheck = (ImageView) view.findViewById(R.id.ivCheck);

            tvDateTime.setTypeface(AppConstants.WALSHEIM_LIGHT);
            tvDoctorName.setTypeface(AppConstants.WALSHEIM_LIGHT);
            tvTime.setTypeface(AppConstants.WALSHEIM_MEDIUM);

            final GetPatientAppointmentsResponse.AppointmentRow data = patientAppointments.get(pos);

            long dayInstance = data.appointmentDetails.ConsultationDt.longValue();
            long timeInstance = TimeUtils.getLongForHHMMSS(data.appointmentDetails.StartTime+":00");
            long totalInstance = dayInstance + timeInstance;
            StringBuilder sb = new StringBuilder();
            sb.append(DateUtils.isToday(totalInstance) ? ("Today - ") : (/*TimeUtils.getDay(totalInstance) +*/ TimeUtils.format2DateProper(totalInstance)+" - " ));

            StringBuilder dname = new StringBuilder();
            dname.append("Dr ."+" "+data.doctorDetail.doctorFullName );
            tvDoctorName.setText(dname.toString());
            tvDateTime.setText(sb.toString());
            tvTime.setText(" "+data.appointmentDetails.StartTime+"Hrs");
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

        private String getFormattedTime(String time) {
            String formattedTime = "";
            try {
                final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                final Date dateObj = sdf.parse(time);
                formattedTime = new SimpleDateFormat("hh:mm").format(dateObj).toUpperCase();
            } catch (final ParseException e) {
                e.printStackTrace();
            }
            return formattedTime;
        }
    }
}
