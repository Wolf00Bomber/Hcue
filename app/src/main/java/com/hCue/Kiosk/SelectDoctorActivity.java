package com.hCue.Kiosk;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hCue.Kiosk.common.AppConstants;
import com.hCue.Kiosk.model.ClinicResponse;
import com.hCue.Kiosk.model.GetDoctorRequest;
import com.hCue.Kiosk.model.GetDoctors;
import com.hCue.Kiosk.model.GetDoctorsResponse;
import com.hCue.Kiosk.model.Speciality;
import com.hCue.Kiosk.services.RestCallback;
import com.hCue.Kiosk.services.RestClient;
import com.hCue.Kiosk.services.RestError;
import com.hCue.Kiosk.utils.Connectivity;
import com.hCue.Kiosk.utils.Preference;
import com.hCue.Kiosk.utils.SpeechHelper;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit.client.Response;


public class SelectDoctorActivity extends BaseActivity {

    private LinearLayout llMain;
    private TextView tvHeading, tvRateYourVisit, tvTitle, tvNoDoctors;
    private ViewPager viewPager;
    private Button btnBookAppointment, btnCancelAppointment;
    private ImageView ivLeftTime, ivRightTime;
    private ImageButton ibLeft, ibRight;
    private GridAdapter gridAdapter;
    private int hospitalId;
    private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;
    public static final int PAGE_SIZE = 6;
    private int pageCount = 1;
    int maxDoctors;
    private DoctorsPagerAdapter doctorsPagerAdapter;
    private ArrayList<GetDoctorsResponse.DoctorDetail> listDoctors;
    private int selectedDoctorPos = -1; //Not selected yet
    private ArrayList<Boolean> listCalledPos;
    private List<Integer> listDoctorIDs;
    private boolean isClinic;
    private String clinic_name;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onResume() {
        super.onResume();
        SpeechHelper.getInstance(SelectDoctorActivity.this).initTextSpeaker();
    }

    @Override
    public void initializeControls() {


        llMain = (LinearLayout) inflater.inflate(R.layout.select_doctor_grid, null);

        llBody.addView(llMain, layoutParams);

        tvHeading = (TextView) llMain.findViewById(R.id.tvHeading);
        tvRateYourVisit = (TextView) llMain.findViewById(R.id.tvRateYourVisit);
        tvTitle = (TextView) llMain.findViewById(R.id.tvTitle);
        tvNoDoctors = (TextView) llMain.findViewById(R.id.tvNoDoctors);
        ibLeft = (ImageButton) llMain.findViewById(R.id.ibLeft);
        ibRight = (ImageButton) llMain.findViewById(R.id.ibRight);

        btnBookAppointment = (Button) llMain.findViewById(R.id.btnBookAppointment);
        btnCancelAppointment = (Button) llMain.findViewById(R.id.btnCancelAppointment);
        viewPager = (ViewPager) llMain.findViewById(R.id.viewPager);

        tvBack.setVisibility(View.GONE);
        tvHome.setVisibility(View.GONE);

        llTop.setVisibility(View.GONE);

        setSpecificTypeFace(llMain, AppConstants.WALSHEIM_MEDIUM);
        tvHeading.setTypeface(AppConstants.WALSHEIM_MEDIUM);

        Preference preference = new Preference(SelectDoctorActivity.this);
        AppConstants.Is_AdditionalInfo_On = preference.getbooleanFromPreference(Preference.ADDITIONAL_INFO, false);

        tvTitle.setText("Welcome to VHSL PhysioPoint");
        tvTitle.setTypeface(AppConstants.MYRAIDPRO_REGULAR);

        listDoctors = new ArrayList<>();
        listCalledPos = new ArrayList<>();
        listDoctorIDs = new ArrayList<>();

        callService(pageCount);

        ibLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = viewPager.getCurrentItem();
                viewPager.setCurrentItem(page - 1);
            }
        });

        ibRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = viewPager.getCurrentItem();
                Log.e("ibRight : ", "CurrentPage: " + page);
                if (!listCalledPos.get(page + 1)) {
                    Log.e("Service called : ", "PageNumber: " + page);
                    callService(page + 2);
                    listCalledPos.set(page + 1, true);
                }

                viewPager.setCurrentItem(page + 1);
            }
        });

        //viewPager listener code


        viewPager.setCurrentItem(0);
        ibLeft.setAlpha(0.25f);
        ibLeft.setEnabled(false);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("onPageSelected", "" + position);
                if (position == 0) {
                    ibLeft.setAlpha(0.25f);
                    ibLeft.setEnabled(false);
                    ibRight.setAlpha(1.0f);
                    ibRight.setEnabled(true);
                } else if (position == maxDoctors / 6 + (maxDoctors % 6 == 0 ? 0 : 1) - 1) {
                    ibRight.setAlpha(0.25f);
                    ibRight.setEnabled(false);
                    ibLeft.setAlpha(1.0f);
                    ibLeft.setEnabled(true);
                    if (!listCalledPos.get(position)) {
                        callService(position + 1);
                        listCalledPos.set(position, true);
                    }

                } else {
                    ibLeft.setAlpha(1.0f);
                    ibRight.setAlpha(1.0f);
                    ibLeft.setEnabled(true);
                    ibRight.setEnabled(true);
                    if (!listCalledPos.get(position)) {
                        callService(position + 1);
                        listCalledPos.set(position, true);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void callService(int pageNumber) {
        int hospitalId;
        int loginDoctorId;
        int clinicAddressId;
//        boolean isClinic;
        String selectedDoctors;

        Preference preference = new Preference(SelectDoctorActivity.this);
        hospitalId = preference.getIntFromPreference(Preference.SELECTED_HOSPITAL_ID, 0);
        loginDoctorId = preference.getIntFromPreference(Preference.LOGIN_DOCTOR_ID, 0);
        clinicAddressId = preference.getIntFromPreference(Preference.SELECTED_CLINIC_ADDRESS_ID, 0);
        isClinic = preference.getbooleanFromPreference(Preference.IS_CLINIC, false);
        selectedDoctors = preference.getStringFromPreference(Preference.SELECTED_DOCTORS, "");
        clinic_name = preference.getStringFromPreference(Preference.CLINIC_NAME, "");

        if (Connectivity.isConnected(SelectDoctorActivity.this)) {
            if (isClinic) {
                GetDoctorRequest getDoctorRequest = new GetDoctorRequest();
                /*{
                 "DoctorID":[85],
                 "PageSize":6,
                 "PageNumber":1,
                 "AddressID":[269]
                 }*/
                List<Integer> doctorID = new ArrayList<>();
                doctorID.add(loginDoctorId);
                List<Integer> addressID = new ArrayList<>();
                addressID.add(clinicAddressId);
                getDoctorRequest.setDoctorID(doctorID);
                getDoctorRequest.setPageSize(PAGE_SIZE);
                getDoctorRequest.setPageNumber(pageNumber);
                getDoctorRequest.setAddressID(addressID);

                getDoctorClinics(getDoctorRequest);

            } else {
                final GetDoctors getDoctorsRequest = new GetDoctors();
                getDoctorsRequest.setHospitalID(hospitalId);
                getDoctorsRequest.setPageNumber(pageNumber);
                getDoctorsRequest.setPageSize(PAGE_SIZE);

                if (!TextUtils.isEmpty(selectedDoctors)) {
                    String[] ids = selectedDoctors.split(",");
                    for (int i = 0; i < ids.length; i++)
                        listDoctorIDs.add(Integer.parseInt(ids[i]));
                    getDoctorsRequest.setDoctorID(listDoctorIDs);
                }
                getHospitalDetails(getDoctorsRequest, pageNumber);
            }
        } else {
            Toast.makeText(SelectDoctorActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void getHospitalDetails(GetDoctors getDoctorsRequest, int pageNumber) {
        String url = "http://dct4avjn1lfw.cloudfront.net";
        RestClient.getAPI(url).getDoctors(getDoctorsRequest, new RestCallback<GetDoctorsResponse>() {
            @Override
            public void failure(RestError restError) {
                viewPager.setVisibility(View.GONE);

                ibRight.setAlpha(0.25f);
                ibRight.setEnabled(false);
                ibLeft.setAlpha(0.25f);
                ibLeft.setEnabled(false);
                Log.e("Doctor Appointement", "" + restError.getErrorMessage());
                Toast.makeText(SelectDoctorActivity.this, "Couldn't get the List of Doctors.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(GetDoctorsResponse listDoctorsRequest, Response response) {
                if (listDoctorsRequest != null) {
                    viewPager.setVisibility(View.VISIBLE);
                    pageCount++;
                    if (maxDoctors == 0) {
                        maxDoctors = listDoctorsRequest.DoctorCount;
                        Log.e("maxDoctors : ", "" + maxDoctors);
                        int pages = maxDoctors / 6 + (maxDoctors % 6 == 0 ? 0 : 1);
                        if (pages <= 1) {//No left right swipe

                            ibRight.setAlpha(0.25f);
                            ibRight.setEnabled(false);
                            ibLeft.setAlpha(0.25f);
                            ibLeft.setEnabled(false);
                        }
                        for (int i = 0; i < pages; i++) {
                            listCalledPos.add(false);
                        }

                        listCalledPos.set(0, true);
                    }
                    if (listDoctorsRequest.arrDoctorDetails != null)
                        listDoctors.addAll(listDoctorsRequest.arrDoctorDetails);
                    Log.e("List size : ", "" + listDoctors.size());
                    if (doctorsPagerAdapter == null) {
                        doctorsPagerAdapter = new DoctorsPagerAdapter();
                        viewPager.setAdapter(doctorsPagerAdapter);
                    } else {
                        doctorsPagerAdapter.refreshPager();
                    }

                    tvTitle.setText("Welcome to " + listDoctorsRequest.hospitalInfo.hospitalDetails.HospitalName);
                } else {
                    Log.i("Response", "" + response.getReason());
                    viewPager.setVisibility(View.GONE);
                    ibRight.setAlpha(0.25f);
                    ibRight.setEnabled(false);
                    ibLeft.setAlpha(0.25f);
                    ibLeft.setEnabled(false);
                }
            }
        });
    }

    private void getDoctorClinics(GetDoctorRequest getDoctorRequest) {
        String url = "http://dct4avjn1lfw.cloudfront.net";
        RestClient.getAPI(url).getDoctor(getDoctorRequest, new RestCallback<ClinicResponse>() {
            @Override
            public void failure(RestError restError) {
                viewPager.setVisibility(View.GONE);

                ibRight.setAlpha(0.25f);
                ibRight.setEnabled(false);
                ibLeft.setAlpha(0.25f);
                ibLeft.setEnabled(false);
                Log.e("Doctor Appointement", "" + restError.getErrorMessage());
                Toast.makeText(SelectDoctorActivity.this, "Couldn't get the List of Doctors.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(ClinicResponse clinicResponse, Response response) {
                if (clinicResponse != null) {
                    viewPager.setVisibility(View.VISIBLE);
                    pageCount++;
                    if (maxDoctors == 0) {
                        maxDoctors = clinicResponse.getCount();
                        Log.e("maxDoctors : ", "" + maxDoctors);
                        int pages = maxDoctors / 6 + (maxDoctors % 6 == 0 ? 0 : 1);
                        if (pages <= 1) {//No left right swipe

                            ibRight.setAlpha(0.25f);
                            ibRight.setEnabled(false);
                            ibLeft.setAlpha(0.25f);
                            ibLeft.setEnabled(false);
                        }
                        for (int i = 0; i < pages; i++) {
                            listCalledPos.add(false);
                        }
                        if (pages > 0)
                            listCalledPos.set(0, true);
                    }
                    if (clinicResponse.listDoctorDetails != null)
                        listDoctors.addAll(clinicResponse.listDoctorDetails);
                    Log.e("List size : ", "" + listDoctors.size());
                    if (doctorsPagerAdapter == null) {
                        doctorsPagerAdapter = new DoctorsPagerAdapter();
                        viewPager.setAdapter(doctorsPagerAdapter);
                    } else {
                        doctorsPagerAdapter.refreshPager();
                    }

                    String hospitalName = "NA";
                    try {
//                        hospitalName = clinicResponse.XXXXXXXXXXXXXXXX;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (isClinic)
                        tvTitle.setText("Welcome to " + clinic_name);
                    else
                        tvTitle.setText("Welcome to " + hospitalName);
                } else {
                    Log.i("Response", "" + response.getReason());
                    viewPager.setVisibility(View.GONE);
                    ibRight.setAlpha(0.25f);
                    ibRight.setEnabled(false);
                    ibLeft.setAlpha(0.25f);
                    ibLeft.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void bindControls() {
        btnBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDoctorDetails == null) {
                    Toast.makeText(context, "Please select a doctor!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SelectDoctorActivity.this, EnterContactNumberActivity.class);
                intent.putExtra("DoctorDetails", selectedDoctorDetails);
                startActivity(intent);
            }
        });
        btnCancelAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectDoctorActivity.this, EnterContactNumberActivity.class);
                intent.putExtra("From", "CancelAppointment");
                startActivity(intent);
            }
        });
        tvRateYourVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDoctorDetails == null) {
                    Toast.makeText(context, "Please select a doctor!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SelectDoctorActivity.this, EnterContactNumberActivity.class);
                intent.putExtra("From", "Feedback");
                intent.putExtra("DoctorDetails", selectedDoctorDetails);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SelectDoctor Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.hCue.Kiosk/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SelectDoctor Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.hCue.Kiosk/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    private class GridAdapter extends BaseAdapter {

        private int mHeight, mWidth;
        private ArrayList<GetDoctorsResponse.DoctorDetail> doctorDetails;
        private HashMap<String, Speciality> hmSpecialities;
        private int pageNumber;

        public GridAdapter(final int pageNumber) {
            this.pageNumber = pageNumber;
            Drawable d = getResources().getDrawable(R.drawable.profile_doctor_bg_male);
            mHeight = d.getIntrinsicHeight();
            mWidth = d.getIntrinsicWidth();
            initMap();
        }

        private void initMap() {
            Preference preference = new Preference(SelectDoctorActivity.this);
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

        public void refresh(ArrayList<GetDoctorsResponse.DoctorDetail> doctorDetails) {
            this.doctorDetails = doctorDetails;
            notifyDataSetChanged();
            Drawable d = getResources().getDrawable(R.drawable.profile_doctor_bg_male);
            mHeight = d.getIntrinsicHeight();
            mWidth = d.getIntrinsicWidth();
        }

        @Override
        public int getCount() {
           /* if(doctorDetails != null && doctorDetails.size() > 0 && doctorDetails.size()<=6)
                return doctorDetails.size();*/
            /*if(maxDoctors == 1 || isClinic)
                return 1;*/
            return 6;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int pos, View view, ViewGroup parent) {
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(SelectDoctorActivity.this).inflate(R.layout.grid_cell, null);
                holder = new ViewHolder();
                holder.tvDoctorName = (TextView) view.findViewById(R.id.tvDoctorName);
                holder.tvSpecality = (TextView) view.findViewById(R.id.tvSpecality);
                holder.tvStatus = (TextView) view.findViewById(R.id.tvStatus);
                holder.iv = (PorterShapeImageView) view.findViewById(R.id.imageView);

                holder.tvDoctorName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
                holder.tvSpecality.setTypeface(AppConstants.WALSHEIM_LIGHT);
                holder.tvStatus.setTypeface(AppConstants.WALSHEIM_MEDIUM);

                holder.tvDoctorName.setTextColor(context.getResources().getColorStateList(R.color.text_pressed_doctors));
                holder.tvSpecality.setTextColor(context.getResources().getColorStateList(R.color.text_pressed_doctors));

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            int gridItemPos = pageNumber * 6 + pos;
            Log.e("GRID ITEM POSITION :", "" + gridItemPos);
            if (gridItemPos >= maxDoctors)
                view.setVisibility(isClinic ? View.GONE : View.INVISIBLE);
            else {

                GetDoctorsResponse.DoctorDetail doctorDetail = null;
                try {
                    doctorDetail = listDoctors.get(gridItemPos);
                } catch (Exception e) {
                    doctorDetail = listDoctors.get(pos);
                }
                holder.tvStatus.setTextColor(context.getResources().getColorStateList(R.color.text_pressed_doctors_availability));

                if ("Y".equalsIgnoreCase(doctorDetail.Avaialble)) {
                    view.setEnabled(true);
                    holder.tvStatus.setEnabled(true);
                    holder.tvDoctorName.setEnabled(true);
                    holder.tvSpecality.setEnabled(true);
                    holder.tvStatus.setText("Available");
                } else {
                    view.setEnabled(false);
                    holder.tvStatus.setEnabled(false);
                    holder.tvDoctorName.setEnabled(false);
                    holder.tvSpecality.setEnabled(false);
                    holder.tvStatus.setText("Not available");
                }

                view.setTag(R.id.tvStatus, doctorDetail);
                holder.tvDoctorName.setText("Dr. " + doctorDetail.FullName/*"Dr.P. Venkatakrishna"*/);
                if (hmSpecialities != null && hmSpecialities.size() > 0) {
                    ArrayList<String> list = new ArrayList<>(doctorDetail.specialityCD.values());
                    for (int i = 0; i < list.size(); i++) {
                        list.set(i, hmSpecialities.get(list.get(i)).DoctorSpecialityDesc);
                    }
                    holder.tvSpecality.setText(TextUtils.join(",", list) /*"Physiotherapist"*/);
                }

                if (!TextUtils.isEmpty(doctorDetail.ImageURL))
                    Picasso.with(context)
                            .load(doctorDetail.ImageURL)
                            .resize(mWidth, mHeight)
                            .placeholder(R.drawable.profile_doctor_bg_male)
                            .centerInside()
                            .into(holder.iv);
            }
            return view;
        }

    }

    static class ViewHolder {
        TextView tvDoctorName, /*tvDesignation,*/
                tvSpecality, tvStatus;
        PorterShapeImageView iv;
    }

    //Custom Pager Adapter with GridView as its page item
    private class DoctorsPagerAdapter extends PagerAdapter {

        public void refreshPager() {
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            int count = maxDoctors / 6 + (maxDoctors % 6 == 0 ? 0 : 1);
            Log.e("Count :", "" + count);
            return count;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout itemView = (LinearLayout) LayoutInflater.from(SelectDoctorActivity.this).inflate(R.layout.select_doctor_pager_item, container, false);

            GridView gridView = (GridView) itemView.findViewById(R.id.gridView1);
            if (isClinic || maxDoctors == 1) {
                gridView.setNumColumns(1);
                ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
                layoutParams.height = 150; //this is in pixels
                gridView.setLayoutParams(layoutParams);
                gridView.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_MOVE) {
                            return true;
                        }
                        return false;
                    }

                });
            }
            final GridAdapter gridAdapter = new GridAdapter(position);
            gridView.setAdapter(gridAdapter);
            gridView.setSelector(R.drawable.doctor);
            gridView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) {
                    selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail) v.getTag(R.id.tvStatus);
                    if (!"Y".equalsIgnoreCase(selectedDoctorDetails.Avaialble)) {
                        selectedDoctorDetails = null;
                        return;
                    }
                    selectedDoctorPos = pos;
                    v.setSelected(true);

                }

            });
            container.addView(itemView);

            return itemView;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

}
