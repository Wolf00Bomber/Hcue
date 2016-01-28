package com.appdest.hcue;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appdest.hcue.adapters.CustomTimeAdapter;
import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.GetDoctorAppointmentRequest;
import com.appdest.hcue.model.GetDoctorAppointmentResponse;
import com.appdest.hcue.model.TimeObject;
import com.appdest.hcue.services.RestCallback;
import com.appdest.hcue.services.RestClient;
import com.appdest.hcue.services.RestError;
import com.appdest.hcue.utils.CustomCalendarView;
import com.appdest.hcue.utils.EventHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.client.Response;

/**
 * Created by cvlhyd on 25-01-2016.
 */
public class ChooseAppointmentActivity extends BaseActivity {

    private LinearLayout llAppointment;
    private CustomCalendarView customCalendarView;
    private GridView gvTime;

    
    @Override
    public void initializeControls() {
        llAppointment = (LinearLayout) inflater.inflate(R.layout.choose_date_time_of_appointment, null);
        llBody.addView(llAppointment);
        customCalendarView = (CustomCalendarView) llAppointment.findViewById(R.id.calendar_view);
        gvTime = (GridView) llAppointment.findViewById(R.id.gvTime);

        customCalendarView.updateCalendar();
        customCalendarView.setEventHandler(new EventHandler() {
            @Override
            public void onDayClicked(Date date) {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(ChooseAppointmentActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
                GetDoctorAppointmentRequest doctorAppointmentRequest = new GetDoctorAppointmentRequest();
                doctorAppointmentRequest.setDoctorID(487);
                doctorAppointmentRequest.setFilterByDate("2016-01-29");
                doctorAppointmentRequest.setAddressID(331);
                String url = "http://dct4avjn1lfw.cloudfront.net";
                RestClient.getAPI(url).getDoctorAppointment(doctorAppointmentRequest, new RestCallback<GetDoctorAppointmentResponse>() {
                    @Override
                    public void failure(RestError restError) {

                    }

                    @Override
                    public void success(GetDoctorAppointmentResponse getDoctorAppointmentResponse, Response response) {
                        if(response != null)
                            Log.i("Response", ""+response);
                    }
                });
            }
        });

        Date now = new Date();
        long interval = 15 * 60* 1000;
        Date startDate = new Date(now.getTime() + 5 * interval);
        Date endDate = new Date(now.getTime() + 15 * interval);

        Drawable d = getResources().getDrawable(R.drawable.selected_time_col_bg);
        int gvCellWidth = d.getIntrinsicWidth();
        int gvCellHeight = d.getIntrinsicHeight();
        int heightGap = 25;
        int widthGap = 25;
        int xItems = 5;
        int yItems = 3;
        int gvWidth = xItems * gvCellWidth + (xItems - 1) * widthGap + 1;
        int gvHeight = yItems * gvCellHeight + (yItems - 1) * heightGap + 1;
        gvTime.setAdapter(new CustomTimeAdapter(this, populateTimeObjects(interval, startDate, endDate)));
        gvTime.setLayoutParams(new LinearLayout.LayoutParams(gvWidth, gvHeight));
        gvTime.setHorizontalSpacing(widthGap);
        gvTime.setVerticalSpacing(heightGap);
        gvTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        setSpecificTypeFace(llAppointment, AppConstants.WALSHEIM_MEDIUM);
        tvTitle.setText("Choose Date & Time of your Appointment");
    }

    private ArrayList<TimeObject> populateTimeObjects(long period, Date startDate, Date endDate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        ArrayList<TimeObject> timeObjectArrayList = new ArrayList<>();
        // By Default
        Date now = new Date();
        int i = 0;
        while (true)
        {

            if(now.getTime() < startDate.getTime())
            {
                timeObjectArrayList.add(new TimeObject("NORMAL", sdf.format(now)));
            }
            else if(now.getTime() >= startDate.getTime()
                    && now.getTime() < endDate.getTime())
            {
                if(i < 2)
                {
                    timeObjectArrayList.add(new TimeObject("UNAVAILABLE", sdf.format(now)));
                }
                else
                {
                    timeObjectArrayList.add(new TimeObject("AVAILABLE", sdf.format(now)));
                }
                i++;
            }
            else
            {
                break;
            }
            now = new Date(now.getTime() + period);
        }

        return timeObjectArrayList;
    }

    @Override
    public void bindControls() {

    }
}
