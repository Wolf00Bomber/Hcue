package com.appdest.hcue;

import android.media.Image;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appdest.hcue.adapters.CustomAppointmentAdapter;
import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.DoctorsAppointment;
import com.appdest.hcue.model.DoctorsAppointmentResponse;
import com.appdest.hcue.model.GetDoctorAppointmentRequest;
import com.appdest.hcue.model.GetDoctorAppointmentResponse;
import com.appdest.hcue.services.RestCallback;
import com.appdest.hcue.services.RestClient;
import com.appdest.hcue.services.RestError;
import com.appdest.hcue.utils.Connectivity;
import com.appdest.hcue.utils.CustomCalendarView;
import com.appdest.hcue.utils.EventHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit.client.Response;

public class ChooseAppointmentActivity extends BaseActivity {

    private LinearLayout llAppointment;
    private CustomCalendarView customCalendarView;
    private ViewPager mViewPager;
    private ProgressBar pBar;
    private TextView tvNoSlots;
    private Button btnProvideDetails;
    private ImageView ivLeftTime, ivRightTime;
    private CustomAppointmentAdapter mCustomPagerAdapter;


    @Override
    public void initializeControls() {
        llAppointment = (LinearLayout) inflater.inflate(R.layout.choose_date_time_of_appointment, null);
        llBody.addView(llAppointment);
        customCalendarView = (CustomCalendarView) llAppointment.findViewById(R.id.calendar_view);
        mViewPager = (ViewPager) llAppointment.findViewById(R.id.viewPager);
        tvNoSlots = (TextView) llAppointment.findViewById(R.id.tvNoSlots);
        pBar = (ProgressBar) llAppointment.findViewById(R.id.pBar);
        btnProvideDetails = (Button) llAppointment.findViewById(R.id.btnProvideDetails);
        ivLeftTime = (ImageView) llAppointment.findViewById(R.id.ivLeftTime);
        ivRightTime = (ImageView) llAppointment.findViewById(R.id.ivRightTime);
        mCustomPagerAdapter = new CustomAppointmentAdapter(this);
        mViewPager.setAdapter(mCustomPagerAdapter);

        customCalendarView.updateCalendar();
        customCalendarView.setEventHandler(new EventHandler() {
            @Override
            public void onDayClicked(Date date) {

                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(ChooseAppointmentActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
                if (Connectivity.isConnected(ChooseAppointmentActivity.this)) {
                    populateTimeSlots();
                } else {
                    Toast.makeText(ChooseAppointmentActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setSpecificTypeFace(llAppointment, AppConstants.WALSHEIM_MEDIUM);
        tvTitle.setText("Choose Date & Time of your Appointment");
        btnProvideDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Connectivity.isConnected(ChooseAppointmentActivity.this)) {
                    bookAppointement();
                } else {
                    Toast.makeText(ChooseAppointmentActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivLeftTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
            }
        });

        ivRightTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        });
    }

    private void bookAppointement()
    {
        DoctorsAppointment doctorsAppointment = new DoctorsAppointment();
        doctorsAppointment.setAddressConsultID(341);
        doctorsAppointment.setDayCD("TUE");
        doctorsAppointment.setUSRType("KIOSK");
        doctorsAppointment.setVisitUserTypeID("OPATIENT");
        doctorsAppointment.setEndTime("11:50");
        doctorsAppointment.setConsultationDt("2016-01-05");
        doctorsAppointment.setAddressConsultID(2106);
        doctorsAppointment.setPatientID(9944208696001l);
        doctorsAppointment.setStartTime("11:40");
        doctorsAppointment.setAppointmentStatus("B");
        doctorsAppointment.setUSRId(7);
        doctorsAppointment.setDoctorVisitRsnID("ALLMRDRR");
        String url = "http://dct4avjn1lfw.cloudfront.net";

        RestClient.getAPI(url).addDoctorsAppointment(doctorsAppointment, new RestCallback<DoctorsAppointmentResponse>() {
            @Override
            public void failure(RestError restError) {
                Toast.makeText(ChooseAppointmentActivity.this, restError.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(DoctorsAppointmentResponse doctorsAppointmentResponse, Response response) {
                if (doctorsAppointmentResponse != null) {

                } else {
                    Log.i("Response", "" + response.getReason());
                }
            }
        });
    }

    private void populateTimeSlots()
    {
        tvNoSlots.setVisibility(View.GONE);
        pBar.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.INVISIBLE);

        GetDoctorAppointmentRequest doctorAppointmentRequest = new GetDoctorAppointmentRequest();
        doctorAppointmentRequest.setDoctorID(487);
        doctorAppointmentRequest.setFilterByDate("2016-01-29");
        doctorAppointmentRequest.setAddressID(341);
        String url = "http://dct4avjn1lfw.cloudfront.net";
        RestClient.getAPI(url).getDoctorAppointment(doctorAppointmentRequest, new RestCallback<GetDoctorAppointmentResponse>() {
            @Override
            public void failure(RestError restError) {
                tvNoSlots.setVisibility(View.VISIBLE);
                pBar.setVisibility(View.GONE);
                mViewPager.setVisibility(View.INVISIBLE);
            }

            @Override
            public void success(GetDoctorAppointmentResponse getDoctorAppointmentResponse, Response response) {
                pBar.setVisibility(View.GONE);
                if(getDoctorAppointmentResponse != null && getDoctorAppointmentResponse.count > 0)
                {
                    mViewPager.setVisibility(View.VISIBLE);
                    tvNoSlots.setVisibility(View.GONE);
                    ArrayList<GetDoctorAppointmentResponse.AppointmentRow> appointmentRows = getDoctorAppointmentResponse.appointmentRows;
                    mCustomPagerAdapter.refresh(appointmentRows);
                }
                else
                {
                    mViewPager.setVisibility(View.INVISIBLE);
                    tvNoSlots.setVisibility(View.VISIBLE);
                    Log.i("Response", ""+response.getReason());
                }
            }
        });
    }

    @Override
    public void bindControls() {

    }
}
