package com.appdest.hcue;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.text.Spanned;
import android.text.TextUtils;
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
import com.appdest.hcue.model.AddPatientResponse;
import com.appdest.hcue.model.DoctorsAppointment;
import com.appdest.hcue.model.DoctorsAppointmentResponse;
import com.appdest.hcue.model.GetDoctorAppointmentRequest;
import com.appdest.hcue.model.GetDoctorAppointmentResponse;
import com.appdest.hcue.model.GetDoctorsResponse;
import com.appdest.hcue.model.GetPatientResponse;
import com.appdest.hcue.services.RestCallback;
import com.appdest.hcue.services.RestClient;
import com.appdest.hcue.services.RestError;
import com.appdest.hcue.utils.AppointmentTimeInterface;
import com.appdest.hcue.utils.Connectivity;
import com.appdest.hcue.utils.CustomCalendarView;
import com.appdest.hcue.utils.EventHandler;
import com.appdest.hcue.utils.TimeUtils;

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
    private TextView tvNoSlots, tvTime, tvDoctorName;
    private Button btnProvideDetails;
    private ImageView ivLeftTime, ivRightTime;
    private CustomAppointmentAdapter mCustomPagerAdapter;

    private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;
    private Number phNumber;
    private AddPatientResponse addPatientResponse;
    private GetPatientResponse.PatientInfo getPatientInfo;
    private Number patientId;

    boolean isActivityNeedsFinish = false;

    private Number getPatientId(AddPatientResponse addPatientResponse)
    {
        ArrayList<AddPatientResponse.PatientPhone> patientPhones = addPatientResponse.arrPatientPhone;
        if(patientPhones == null || patientPhones.size() == 0)
            return null;
        else
        {
            for(int j = 0; j < patientPhones.size(); j++) {
                AddPatientResponse.PatientPhone patientPhone = patientPhones.get(j);
                if (patientPhone.PhNumber.longValue() == phNumber.longValue()) {
                    return addPatientResponse.arrPatients.get(j).PatientID;
                }
            }
            return null;
        }
    }


    @Override
    public void initializeControls()
    {
        Intent i = getIntent();
        if(i.hasExtra("DoctorDetails") && i.hasExtra("PhoneNumber") && i.hasExtra("PatientInfo"))
        {
            selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail) i.getSerializableExtra("DoctorDetails");
            phNumber = (Number) i.getSerializableExtra("PhoneNumber");
            Object patientInfo = i.getSerializableExtra("PatientInfo");
            boolean isNoMobile = i.getBooleanExtra("isNoMobile", false);
            if(patientInfo instanceof AddPatientResponse)
            {
                addPatientResponse = (AddPatientResponse) patientInfo;
                patientId = getPatientId(addPatientResponse);
                if(isNoMobile)
                {
                    patientId = addPatientResponse.arrPatients.get(0).PatientID;
                }
            }
            else if(patientInfo instanceof GetPatientResponse.PatientInfo)
            {
                getPatientInfo = (GetPatientResponse.PatientInfo) patientInfo;
                patientId = ((GetPatientResponse.PatientInfo) patientInfo).patients.get(0).PatientID;
            }
        }
        else
        {
            isActivityNeedsFinish = true;
            finish();
            return;
        }

        llAppointment = (LinearLayout) inflater.inflate(R.layout.choose_date_time_of_appointment, null);
        llBody.addView(llAppointment);

        customCalendarView  = (CustomCalendarView) llAppointment.findViewById(R.id.calendar_view);
        mViewPager          = (ViewPager) llAppointment.findViewById(R.id.viewPager);
        tvNoSlots           = (TextView) llAppointment.findViewById(R.id.tvNoSlots);
        tvTime              = (TextView) llAppointment.findViewById(R.id.tvTime);
        tvDoctorName              = (TextView) llAppointment.findViewById(R.id.tvDoctorName);
        pBar                = (ProgressBar) llAppointment.findViewById(R.id.pBar);
        btnProvideDetails   = (Button) llAppointment.findViewById(R.id.btnProvideDetails);
        ivLeftTime          = (ImageView) llAppointment.findViewById(R.id.ivLeftTime);
        ivRightTime         = (ImageView) llAppointment.findViewById(R.id.ivRightTime);
        mCustomPagerAdapter = new CustomAppointmentAdapter(this);
        mCustomPagerAdapter.setViewPager(mViewPager);
        mCustomPagerAdapter.setAppointmentTimeInterface(new AppointmentTimeInterface() {
            @Override
            public void updateAppointmentText(Spanned appointmentString) {
                tvTime.setText(appointmentString);
            }
        });
        mViewPager.setAdapter(mCustomPagerAdapter);

        customCalendarView.updateCalendar();
        if (Connectivity.isConnected(ChooseAppointmentActivity.this)) {
            populateTimeSlots(new Date());
        } else {
            Toast.makeText(ChooseAppointmentActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        customCalendarView.setEventHandler(new EventHandler() {
            @Override
            public void onDayClicked(Date date) {
                DateFormat df = SimpleDateFormat.getDateInstance();
                if (Connectivity.isConnected(ChooseAppointmentActivity.this)) {
                    populateTimeSlots(date);
                } else {
                    Toast.makeText(ChooseAppointmentActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setSpecificTypeFace(llAppointment, AppConstants.WALSHEIM_MEDIUM);
        tvTitle.setText("Choose Date & Time of your Appointment");
        tvDoctorName.setText(selectedDoctorDetails.FullName);
        btnProvideDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Connectivity.isConnected(ChooseAppointmentActivity.this)) {
                    bookAppointment();
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

    private void bookAppointment()
    {
        int selectedPage = mViewPager.getCurrentItem();
        GetDoctorAppointmentResponse.AppointmentRow selectedPageItem = mCustomPagerAdapter.getSelectedPageItem(selectedPage);
        if(selectedPageItem == null)
        {
            Toast.makeText(this, "Please select an another date, where doctor appointment slots are present.", Toast.LENGTH_SHORT).show();
            return;
        }
        GetDoctorAppointmentResponse.TimeSlot selectedTimeSlot = mCustomPagerAdapter.getSelectedTimeSlot();
        if(selectedTimeSlot == null)
        {
            Toast.makeText(this, "Please select an Appointment Slot", Toast.LENGTH_SHORT).show();
            return;
        }

        DoctorsAppointment doctorsAppointment = new DoctorsAppointment();
        doctorsAppointment.setmDoctorIDId(selectedPageItem.getDoctorID()/*"487"*/);
        doctorsAppointment.setAddressConsultID(selectedPageItem.getAddressConsultID()/*341*/);
        doctorsAppointment.setDayCD(selectedPageItem.getDayCD()/*"TUE"*/);
        doctorsAppointment.setUSRType("KIOSK");
        doctorsAppointment.setVisitUserTypeID("OPATIENT");
        doctorsAppointment.setEndTime(TimeUtils.format2hhmm(selectedTimeSlot.getEndTime())/*"11:50"*/);
        doctorsAppointment.setConsultationDt(TimeUtils.format2Date(selectedPageItem.getConsultationDate().longValue())/*"2016-01-05"*/);
        doctorsAppointment.setAddressConsultID(selectedPageItem.getAddressConsultID()/*2106*/);
        doctorsAppointment.setPatientID(patientId/*9944208696001l*/);
        doctorsAppointment.setStartTime(TimeUtils.format2hhmm(selectedTimeSlot.getStartTime())/*"11:40"*/);
        doctorsAppointment.setAppointmentStatus("B");
        doctorsAppointment.setUSRId(0);
        doctorsAppointment.setDoctorVisitRsnID("ALLMRDRR");
        doctorsAppointment.setSendSMS("Y");
        String url = "http://dct4avjn1lfw.cloudfront.net";

        RestClient.getAPI(url).addDoctorsAppointment(doctorsAppointment, new RestCallback<DoctorsAppointmentResponse>() {
            @Override
            public void failure(RestError restError) {
                Log.e("Doctor Appointment", ""+restError.getErrorMessage());
                Toast.makeText(ChooseAppointmentActivity.this, "Couldn't book appointment for the selected slot.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(DoctorsAppointmentResponse doctorsAppointmentResponse, Response response) {
                if(doctorsAppointmentResponse == null)
                {
                    Toast.makeText(ChooseAppointmentActivity.this, "Booking Doctor Appointment Failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(doctorsAppointmentResponse.getExceptionType())) {
                    Intent i = new Intent(ChooseAppointmentActivity.this, ConfirmationSummaryActivity.class);
                    i.putExtra("BookingDetails", doctorsAppointmentResponse);
                    startActivity(i);
                } else {

                    if(!TextUtils.isEmpty(response.getReason()) && response.getStatus() != 200)
                    {
                        Log.i("Response", "" + response.getReason());
                        Toast.makeText(ChooseAppointmentActivity.this, "Appointment Failed : "+doctorsAppointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if(!TextUtils.isEmpty(doctorsAppointmentResponse.getExceptionType()))
                    {
                        Log.i("ExceptionType", "" + doctorsAppointmentResponse.getExceptionType());
                        Log.i("Message", "" + doctorsAppointmentResponse.getMessage());
                        Toast.makeText(ChooseAppointmentActivity.this, "Appointment Failed : "+doctorsAppointmentResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }


                }
            }
        });
    }

    private void populateTimeSlots(Date date)
    {
        tvNoSlots.setVisibility(View.GONE);
        pBar.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.INVISIBLE);

        GetDoctorAppointmentRequest doctorAppointmentRequest = new GetDoctorAppointmentRequest();
        doctorAppointmentRequest.setDoctorID(selectedDoctorDetails.DoctorID/*487*/);
        doctorAppointmentRequest.setFilterByDate(TimeUtils.format2Date(date)/*"2016-01-29"*/);
        doctorAppointmentRequest.setAddressID(selectedDoctorDetails.AddressID/*341*/);
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
