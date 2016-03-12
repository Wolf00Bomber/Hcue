package com.hCue.Kiosk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hCue.Kiosk.common.AppConstants;
import com.hCue.Kiosk.model.AddPatientResponse;
import com.hCue.Kiosk.model.DoctorsAppointment;
import com.hCue.Kiosk.model.DoctorsAppointmentResponse;
import com.hCue.Kiosk.model.GetDoctorAppointmentRequest;
import com.hCue.Kiosk.model.GetDoctorAppointmentResponse;
import com.hCue.Kiosk.model.GetDoctorsResponse;
import com.hCue.Kiosk.model.GetPatientAppointmentsResponse;
import com.hCue.Kiosk.model.GetPatientResponse;
import com.hCue.Kiosk.model.Speciality;
import com.hCue.Kiosk.services.RestCallback;
import com.hCue.Kiosk.services.RestClient;
import com.hCue.Kiosk.services.RestError;
import com.hCue.Kiosk.utils.Connectivity;
import com.hCue.Kiosk.utils.CustomCalendarView;
import com.hCue.Kiosk.utils.EventHandler;
import com.hCue.Kiosk.utils.Preference;
import com.hCue.Kiosk.utils.TimeUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import retrofit.client.Response;

public class ChooseAppointmentActivityNew extends BaseActivity {

    private LinearLayout llAppointment;
    private CustomCalendarView customCalendarView;
    private ViewPager mViewPager;
    private ProgressBar pBar;
    private TextView tvNoSlots, tvTime, tv_youhave_selected , selected_pos;
    private Button btnProvideDetails;
    private ImageView ivLeftTime, ivRightTime;
    private CustomAppointmentAdapterNew mCustomPagerAdapter;
    private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;
    private Number phNumber;
    private AddPatientResponse addPatientResponse;
    private GetPatientResponse.PatientInfo getPatientInfo;
    private Number patientId;
    private boolean isActivityNeedsFinish = false;
    private Date selected_date;
    private ArrayList<GetDoctorAppointmentResponse.AppointmentRow> appointmentRows;
    private GetDoctorAppointmentResponse.TimeSlot selectedTimeSlot;
    private boolean provideMoredetails , isfromAdditionalinfo;

    private Number getPatientId(AddPatientResponse addPatientResponse) {
        ArrayList<AddPatientResponse.PatientPhone> patientPhones = addPatientResponse.arrPatientPhone;
        if (patientPhones == null || patientPhones.size() == 0)
            return null;
        else {
            for (int j = 0; j < patientPhones.size(); j++) {
                AddPatientResponse.PatientPhone patientPhone = patientPhones.get(j);
                if (patientPhone.PhNumber.longValue() == phNumber.longValue()) {
                    return addPatientResponse.arrPatients.get(j).PatientID;
                }
            }
            return null;
        }
    }

    private HashMap<String, Speciality> hmSpecialities;

    private void initMap() {
        Preference preference = new Preference(ChooseAppointmentActivityNew.this);
        String specialitiesInString = preference.getStringFromPreference(Preference.SPECIALITIES_MAP, "");
        if (!TextUtils.isEmpty(specialitiesInString)) {
            Gson gson = new Gson();
            ArrayList<Speciality> list = gson.fromJson(specialitiesInString, new TypeToken<List<Speciality>>() {
            }.getType());
            Collections.sort(list);
            hmSpecialities = new HashMap<>();
            for (Speciality speciality : list) {
                hmSpecialities.put(speciality.DoctorSpecialityID, speciality);
            }
        }
    }

    @Override
    public void initializeControls() {
        selected_date = new Date();
        selected_date.setTime(System.currentTimeMillis());
        Intent i = getIntent();
        if (i.hasExtra("DoctorDetails") && i.hasExtra("PhoneNumber") && i.hasExtra("PatientInfo")) {
            selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail) i.getSerializableExtra("DoctorDetails");
            phNumber = (Number) i.getSerializableExtra("PhoneNumber");
            Object patientInfo = i.getSerializableExtra("PatientInfo");
            boolean isNoMobile = i.getBooleanExtra("isNoMobile", false);
            provideMoredetails = i.getBooleanExtra("ShowProvideMoreDetails", false);

            isfromAdditionalinfo =  i.getBooleanExtra("isFromAdditionalInfo", false);


            if (patientInfo instanceof AddPatientResponse) {
                addPatientResponse = (AddPatientResponse) patientInfo;
                patientId = getPatientId(addPatientResponse);
                if (isNoMobile) {
                    patientId = addPatientResponse.arrPatients.get(0).PatientID;
                }
            } else if (patientInfo instanceof GetPatientResponse.PatientInfo) {
                getPatientInfo = (GetPatientResponse.PatientInfo) patientInfo;
                patientId = ((GetPatientResponse.PatientInfo) patientInfo).patients.get(0).PatientID;
            }
        } else {
            isActivityNeedsFinish = true;
            finish();
            return;
        }

        llAppointment = (LinearLayout) inflater.inflate(R.layout.choose_date_time_of_appointment, null);
        llBody.addView(llAppointment);

        customCalendarView = (CustomCalendarView) llAppointment.findViewById(R.id.calendar_view);
        mViewPager = (ViewPager) llAppointment.findViewById(R.id.viewPager);
        tvNoSlots = (TextView) llAppointment.findViewById(R.id.tvNoSlots);
        tvTime = (TextView) llAppointment.findViewById(R.id.tvTime);
        tv_youhave_selected = (TextView) llAppointment.findViewById(R.id.tv_youhave_selected);
        selected_pos = (TextView) llAppointment.findViewById(R.id.selected_pos);
        pBar = (ProgressBar) llAppointment.findViewById(R.id.pBar);
        btnProvideDetails = (Button) llAppointment.findViewById(R.id.btnProvideDetails);
        ivLeftTime = (ImageView) llAppointment.findViewById(R.id.ivLeftTime);
        ivRightTime = (ImageView) llAppointment.findViewById(R.id.ivRightTime);
        mCustomPagerAdapter = new CustomAppointmentAdapterNew(this);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position == 0)
                {
                        ivLeftTime.setImageResource(R.drawable.left_arrow_time_un);

                    if(mViewPager.getChildCount() == 1)
                        ivRightTime.setImageResource(R.drawable.right_arrow_time_un);
                    else
                        ivRightTime.setImageResource(R.drawable.right_arrow_time);
                }else
                {
                    ivLeftTime.setImageResource(R.drawable.left_arrow_time);
                    if(appointmentRows != null && (((int)mViewPager.getTag(R.id.tvCell))-1) > position)
                        ivRightTime.setImageResource(R.drawable.right_arrow_time);
                    else
                        ivRightTime.setImageResource(R.drawable.right_arrow_time_un);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tv_youhave_selected.setTypeface(AppConstants.WALSHEIM_LIGHT);

        customCalendarView.updateCalendar();
        if (Connectivity.isConnected(ChooseAppointmentActivityNew.this)) {
            populateTimeSlots(new Date());
        } else {
            Toast.makeText(ChooseAppointmentActivityNew.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        customCalendarView.setEventHandler(new EventHandler() {
            @Override
            public void onDayClicked(Date date) {
                DateFormat df = SimpleDateFormat.getDateInstance();
                if (Connectivity.isConnected(ChooseAppointmentActivityNew.this)) {
                    populateTimeSlots(date);
                } else {
                    Toast.makeText(ChooseAppointmentActivityNew.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        setSpecificTypeFace(llAppointment, AppConstants.WALSHEIM_MEDIUM);
        initMap();
        String TitleFormat = "%s, %s";
        ArrayList<String> list = new ArrayList<>(selectedDoctorDetails.specialityCD.values());
        for (int j = 0; j < list.size(); j++) {
            list.set(j, hmSpecialities.get(list.get(j)).DoctorSpecialityDesc);
        }
        String specializationDesc = (list != null && list.size() > 0) ? list.get(0)/*TextUtils.join(",", list)*/ : "NA";
        String Title = String.format(Locale.US, TitleFormat, selectedDoctorDetails.FullName, specializationDesc);
        tvTitle.setText(Title);
        btnProvideDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Connectivity.isConnected(ChooseAppointmentActivityNew.this)) {
                    bookAppointment();
                } else {
                    Toast.makeText(ChooseAppointmentActivityNew.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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

    private void bookAppointment() {
        int selectedPage = mViewPager.getCurrentItem();
        GetDoctorAppointmentResponse.AppointmentRow selectedPageItem = appointmentRows.get(0); //mCustomPagerAdapter.getSelectedPageItem(selectedPage);
        if (selectedPageItem == null) {
            Toast.makeText(this, "Please select an another date, where doctor appointment slots are present.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedTimeSlot == null) {
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
                Log.e("Doctor Appointment", "" + restError.getErrorMessage());
                Toast.makeText(ChooseAppointmentActivityNew.this, "Couldn't book appointment for the selected slot.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(DoctorsAppointmentResponse doctorsAppointmentResponse, Response response) {
                if (doctorsAppointmentResponse == null) {
                    Toast.makeText(ChooseAppointmentActivityNew.this, "Booking Doctor Appointment Failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(doctorsAppointmentResponse.getExceptionType())) {
                    Intent i = new Intent(ChooseAppointmentActivityNew.this, ConfirmationSummaryActivity.class);
                    i.putExtra("BookingDetails", doctorsAppointmentResponse);
                    i.putExtra("DoctorDetails", selectedDoctorDetails);
                    i.putExtra("ProvideMoredetails",provideMoredetails);
                    i.putExtra("PatientInfo",getPatientInfo);
                    startActivity(i);
                    finish();
                } else {

                    if (!TextUtils.isEmpty(response.getReason()) && response.getStatus() != 200) {
                        Log.i("Response", "" + response.getReason());
                        Toast.makeText(ChooseAppointmentActivityNew.this, "Appointment Failed : " + doctorsAppointmentResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if (!TextUtils.isEmpty(doctorsAppointmentResponse.getExceptionType())) {
                        Log.i("ExceptionType", "" + doctorsAppointmentResponse.getExceptionType());
                        Log.i("Message", "" + doctorsAppointmentResponse.getMessage());
                        Toast.makeText(ChooseAppointmentActivityNew.this, "Appointment Failed : " + doctorsAppointmentResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }


                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isfromAdditionalinfo)
        {
            Intent intent=new Intent(ChooseAppointmentActivityNew.this,RegistrationActivity.class);
            intent.putExtra("isFromAdditionalInfo", true);
            startActivity(intent);
        }
        finish();
    }

    private void populateTimeSlots(Date date) {
        selectedTimeSlot = null;
        selected_date = date;
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
                if (getDoctorAppointmentResponse != null && getDoctorAppointmentResponse.count > 0) {
                    int pages = 0;
                    mViewPager.setVisibility(View.VISIBLE);
                    tvNoSlots.setVisibility(View.GONE);
                    appointmentRows = new ArrayList<>();
                    appointmentRows = getDoctorAppointmentResponse.appointmentRows;
                    if (appointmentRows != null
                            && appointmentRows.size() > 0) {
                        for (int i = 0; i < appointmentRows.size(); i++) {
                            if(i!=0)
                            appointmentRows.get(0).getTimeSlots().addAll(appointmentRows.get(i).getTimeSlots());
                        }
                        /*Iterator<GetDoctorAppointmentResponse.AppointmentRow> iterator = appointmentRows.iterator();
                        int i=0;
                        while (iterator.hasNext()) {
                            if(i != 0)
                                appointmentRows.get(0).getTimeSlots().addAll(iterator.next().getTimeSlots());
                            i++;
                        }*/
                        pages = appointmentRows.get(0).getTimeSlots().size() / 25 + (appointmentRows.get(0).getTimeSlots().size() % 25 == 0 ? 0 : 1);
                    }
                    if (appointmentRows == null || pages == 0) {
                        ivLeftTime.setVisibility(View.GONE);
                        ivRightTime.setVisibility(View.GONE);
                        ivLeftTime.setImageResource(R.drawable.left_arrow_time_un);
                        ivRightTime.setImageResource(R.drawable.right_arrow_time_un);
                    } else if (appointmentRows != null && pages == 1) {
                        ivLeftTime.setImageResource(R.drawable.left_arrow_time_un);
                        ivRightTime.setImageResource(R.drawable.right_arrow_time_un);
                        ivLeftTime.setVisibility(View.GONE);
                        ivRightTime.setVisibility(View.GONE);
                    } else {
                        ivLeftTime.setVisibility(View.VISIBLE);
                        ivRightTime.setVisibility(View.VISIBLE);
                        ivLeftTime.setImageResource(R.drawable.left_arrow_time_un);
                        ivRightTime.setImageResource(R.drawable.right_arrow_time);
                    }
                    mCustomPagerAdapter.refresh();
                } else {
                    /*mViewPager.setVisibility(View.INVISIBLE);
                    tvNoSlots.setVisibility(View.VISIBLE);*/
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(selected_date);
                    cal.add(Calendar.DATE, 1);
                    // populateTimeSlots(cal.getTime());
                    //;
                    customCalendarView.getAdapter().setSelectedDate(cal.getTime());
                    customCalendarView.getGrid().invalidateViews();
                    customCalendarView.getEventHandler().onDayClicked(cal.getTime());
                    Log.i("Response", "" + response.getReason());
                }
            }
        });
    }

    @Override
    public void bindControls() {
        tvLogin.setEnabled(false);
        if (isActivityNeedsFinish)
            return;
    }

    private void resetData(int pageNum, int item, boolean selection) {
        for (int i = 0; i < appointmentRows.size(); i++) {
            GetDoctorAppointmentResponse.AppointmentRow row = appointmentRows.get(i);
            for (int j = 0; j < row.getTimeSlots().size(); j++) {
                if (pageNum == i && item == j) {
                    row.getTimeSlots().get(j).isSelected = selection;
                    if (selection) {
                        this.selectedTimeSlot = row.getTimeSlots().get(j);
                    } else {
                        this.selectedTimeSlot = null;
                    }
                } else {
                    row.getTimeSlots().get(j).isSelected = false;
                }
            }
        }
    }


    /////Custom Pager Adapter
    private class CustomAppointmentAdapterNew extends PagerAdapter {
        private CustomAppointmentAdapterNew instance;
        private Context mContext;
        LayoutInflater mLayoutInflater;
        private static final int heightGap = 20;
        private static final int widthGap = 20;
        private int gvWidth, gvHeight;
        private LinearLayout.LayoutParams llParams;

        public CustomAppointmentAdapterNew(Context context) {
            instance = this;
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            preCalculateGridDimensions(context);
            llParams = new LinearLayout.LayoutParams(gvWidth, gvHeight);
            llParams.gravity = Gravity.CENTER;
        }

        public void refresh() {
            notifyDataSetChanged();

        }

        @Override
        public int getCount() {
            /*if (appointmentRows != null
                    && appointmentRows.size() > 0)
                return appointmentRows.size();
            return 0;*/

            if (appointmentRows != null
                    && appointmentRows.size() > 0) {
                int pages = appointmentRows.get(0).getTimeSlots().size() / 25 + (appointmentRows.get(0).getTimeSlots().size() % 25 == 0 ? 0 : 1);
                mViewPager.setTag(R.id.tvCell,pages);
                return pages;
            }
            else return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            LinearLayout itemView = (LinearLayout) mLayoutInflater.inflate(R.layout.pager_item, container, false);

            CustomTimeAdapterNew customTimeAdapter = new CustomTimeAdapterNew(mContext, appointmentRows.get(0), instance, position);
            final GridView gvTime = (GridView) itemView.findViewById(R.id.gvTime);
            gvTime.setAdapter(customTimeAdapter);
            gvTime.setLayoutParams(llParams);
            gvTime.setHorizontalSpacing(widthGap);
            gvTime.setVerticalSpacing(heightGap);

            container.addView(itemView);
            if(selected_pos!=null && !selected_pos.getText().toString().isEmpty())
            {
                gvTime.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       /* String[] vals = selected_pos.getText().toString().split(",");
                        int x =  Integer.parseInt(vals[0]);
                        int y =  Integer.parseInt(vals[1]);
                        gvTime.scrollBy(x, y);*/
                        gvTime.setSelection( Integer.parseInt(selected_pos.getText().toString()));
                    }
                }, 200);

            }else
            {
                if(mViewPager.getTag() == null)
                {
                    mViewPager.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int pagenumber = 0;
                            outerloop:
                            for (int i=0;i<appointmentRows.get(0).getTimeSlots().size();i++)
                            {
                               GetDoctorAppointmentResponse.TimeSlot timeSlot =  appointmentRows.get(0).getTimeSlots().get(i);
                                if ("Y".equalsIgnoreCase(timeSlot.Available)) {
                                    Calendar now = Calendar.getInstance();
                                    int hour = now.get(Calendar.HOUR_OF_DAY);
                                    int minute = now.get(Calendar.MINUTE);

                                    Date currentDate = new Date();
                                    currentDate.setTime(System.currentTimeMillis());
                                    if (currentDate.before(selected_date)) { //27 before 29(true)

                                        if (timeSlot.isSelected) {

                                        } else {

                                        }
                                    } else { //same day or 27 before 26 (false). We need to check for current elapsed time
                                        String vals[] = TimeUtils.format2hhmm(timeSlot.getStartTime()).toString().split(":");
                                        if (hour > Integer.parseInt(vals[0])) {

                                        } else {
                                            if (hour == Integer.parseInt(vals[0]) && minute > Integer.parseInt(vals[1])) {

                                            } else {

                                                if (timeSlot.isSelected) {

                                                } else {

                                                    pagenumber = i/25;
                                                    break outerloop;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            mViewPager.setTag(new Intent());
                            mViewPager.setCurrentItem(pagenumber);
                        }
                    },100);

                }
            }

            return itemView;
        }

        private void preCalculateGridDimensions(Context context) {
            Drawable d = context.getResources().getDrawable(R.drawable.selected_time_col_bg);
            int gvCellWidth = d.getIntrinsicWidth()+1;
            int gvCellHeight = d.getIntrinsicHeight()+1;
            int xItems = 5;
            int yItems = 5;
            gvWidth = xItems * gvCellWidth + (xItems - 1) * widthGap + 1;
            gvHeight = yItems * gvCellHeight + (yItems - 1) * heightGap + 1;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }


        ///Custom grid adapter
        private class CustomTimeAdapterNew extends BaseAdapter {
            private Context context;
            private GetDoctorAppointmentResponse.AppointmentRow appointmentRow;
            private GridView.LayoutParams cellParams;
            private int pageNumber;

            public GetDoctorAppointmentResponse.TimeSlot getSelectedItem(int selectedItem) {
                return appointmentRow.getTimeSlots().get(selectedItem);
            }

            public CustomTimeAdapterNew(Context context, GetDoctorAppointmentResponse.AppointmentRow appointmentRow,
                                        CustomAppointmentAdapterNew pagerAdapter,
                                        int pageNumber) {
                this.context = context;
                this.appointmentRow = appointmentRow;
                this.pageNumber = pageNumber;
                init(context);
            }

            private void init(Context context) {
                Drawable d = context.getResources().getDrawable(R.drawable.selected_time_col_bg);
                int calendarCellWidth = d.getIntrinsicWidth();
                int calendarCellHeight = d.getIntrinsicHeight();
                cellParams = new GridView.LayoutParams(calendarCellWidth, calendarCellHeight);
            }

            @Override
            public int getCount() {
                /*if (appointmentRow != null
                        && appointmentRow.getTimeSlots() != null
                        && appointmentRow.getTimeSlots().size() > 0)
                    return appointmentRow.getTimeSlots().size();*/
                Log.e("Page number , size ",pageNumber+" , "+appointmentRow.getTimeSlots().size());
                if (appointmentRow != null
                        && appointmentRow.getTimeSlots() != null
                        && appointmentRow.getTimeSlots().size() > 0)
                {
                    if(appointmentRow.getTimeSlots().size() - ((pageNumber) *25) < 25) {
                        Log.e("return value ", (appointmentRow.getTimeSlots().size() - ((pageNumber) * 25)) + "");
                        return (appointmentRow.getTimeSlots().size() - ((pageNumber) * 25));
                    }
                    else {
                        Log.e("return value ", 25+"");
                        return 25;
                    }
                }
                return 0;
            }

            private void refresh(GetDoctorAppointmentResponse.AppointmentRow appointmentRow) {
                this.appointmentRow = appointmentRow;
                this.notifyDataSetChanged();
            }

            @Override
            public Object getItem(int position) {
                return appointmentRow.getTimeSlots().get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int pos, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(context).inflate(R.layout.control_calendar_day, parent, false);
                final LinearLayout llCalCell = (LinearLayout) convertView.findViewById(R.id.llCalCell);
                final TextView tvCell = (TextView) convertView.findViewById(R.id.tvCell);
                convertView.setLayoutParams(cellParams);
                tvCell.setTypeface(null, Typeface.NORMAL);
                convertView.setBackgroundResource(R.drawable.appointment_time);
                tvCell.setTextColor(context.getResources().getColorStateList(R.color.text_pressed));
                ((BaseActivity) context).setSpecificTypeFace((ViewGroup) llCalCell, AppConstants.WALSHEIM_MEDIUM);

                final int position = (pageNumber) * 25 + pos;
                Log.e("Position",position+"");

                final GetDoctorAppointmentResponse.TimeSlot timeSlot = appointmentRow.getTimeSlots().get(position);
                tvCell.setText(TimeUtils.format2hhmm(timeSlot.getStartTime()));

                if ("Y".equalsIgnoreCase(timeSlot.Available)) {
                    Calendar now = Calendar.getInstance();
                    int hour = now.get(Calendar.HOUR_OF_DAY);
                    int minute = now.get(Calendar.MINUTE);

                    Date currentDate = new Date();
                    currentDate.setTime(System.currentTimeMillis());
                    if (currentDate.before(selected_date)) { //27 before 29(true)

                        if (timeSlot.isSelected) {
                            Log.e("getView :", "selection at " + position);
                            convertView.setSelected(true);
                            convertView.setBackgroundResource(R.drawable.selected_time_gcol_bg);
                            tvCell.setTextColor(Color.WHITE);
                        } else {
                            Log.e("getView :", "no selection at " + position);
                            convertView.setSelected(false);
                            convertView.setBackgroundResource(R.drawable.selected_time_col_bg);
                            tvCell.setTextColor(Color.LTGRAY);
                        }
                    } else { //same day or 27 before 26 (false). We need to check for current elapsed time
                        convertView.setSelected(false);
                        String vals[] = tvCell.getText().toString().split(":");
                        if (hour > Integer.parseInt(vals[0])) {
                            convertView.setEnabled(false);
                            tvCell.setEnabled(false);
                            tvCell.setTypeface(AppConstants.WALSHEIM_BOLD);
                            tvCell.setPaintFlags(tvCell.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        } else {
                            if (hour == Integer.parseInt(vals[0]) && minute > Integer.parseInt(vals[1])) {
                                convertView.setEnabled(false);
                                tvCell.setEnabled(false);
                                tvCell.setTypeface(AppConstants.WALSHEIM_BOLD);
                                tvCell.setPaintFlags(tvCell.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            } else {
                                convertView.setEnabled(true);
                                tvCell.setEnabled(true);
                                if (timeSlot.isSelected) {
                                    convertView.setSelected(true);
                                    convertView.setBackgroundResource(R.drawable.selected_time_gcol_bg);
                                    tvCell.setTextColor(Color.WHITE);
                                } else {

                                    convertView.setSelected(false);
                                    convertView.setBackgroundResource(R.drawable.selected_time_col_bg);
                                    tvCell.setTextColor(Color.LTGRAY);
                                }
                            }
                        }
                    }
                } else {
                    convertView.setEnabled(false);
                    tvCell.setEnabled(false);
                    tvCell.setTypeface(AppConstants.WALSHEIM_BOLD);
                    tvCell.setPaintFlags(tvCell.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                final View finalConvertView = convertView;
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!"Y".equalsIgnoreCase(timeSlot.Available)) //perform no click action
                            return;

                        if (timeSlot.isSelected) {
                            Log.e("convertView click :", "deselect at " + position);
                            resetData(pageNumber, position, false);
                            tvTime.setText(Html.fromHtml(""));
                        } else {
                            Log.e("convertView click :", "select at " + position);
                            tv_youhave_selected.setVisibility(View.VISIBLE);
                            resetData(pageNumber, position, true);
                            long dayInstance = appointmentRow.getConsultationDate().longValue();
                            long timeInstance = TimeUtils.getLongForHHMMSS(timeSlot.getStartTime());
                            long totalInstance = dayInstance + timeInstance;
                            finalConvertView.setSelected(true);
                            finalConvertView.setBackgroundResource(R.drawable.selected_time_gcol_bg);
                            tvCell.setTextColor(Color.WHITE);
                            StringBuilder sb = new StringBuilder();
                            sb.append(DateUtils.isToday(totalInstance) ? "Today" : TimeUtils.getDay(totalInstance))
                                    .append(", ")
                                    .append(TimeUtils.format2DateProper(totalInstance))
                                    .append("<font color=\"##A2A2A2\"> @ </font>")
                                    .append("<font color=\"#48B09E\">" + TimeUtils.format2hhmm(timeSlot.getStartTime()) + " hrs</font>");
                            tvTime.setText(Html.fromHtml(sb.toString()));
                            timeSlot.isSelected = true ;
                            selectedTimeSlot = timeSlot;
                        }
                      // selected_pos.setText(tvCell.getLeft()+","+tvCell.getRight());
                        selected_pos.setText(position+"");
                      //  selected_pos.setTag(tvCell);
                       mCustomPagerAdapter.refresh();


                    }
                });
                return convertView;
            }
        }
    }
}