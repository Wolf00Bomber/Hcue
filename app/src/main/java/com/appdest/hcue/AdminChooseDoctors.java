package com.appdest.hcue;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.AdminGetDoctorsRequest;
import com.appdest.hcue.model.AdminGetDoctorsResponse;
import com.appdest.hcue.model.AdminLoginResponse;
import com.appdest.hcue.model.Speciality;
import com.appdest.hcue.services.RestCallback;
import com.appdest.hcue.services.RestClient;
import com.appdest.hcue.services.RestError;
import com.appdest.hcue.utils.Connectivity;
import com.appdest.hcue.utils.Preference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit.client.Response;

/**
 * Created by Vinsan on 2/12/2016.
 */
public class AdminChooseDoctors extends BaseActivity implements View.OnClickListener{
    private LinearLayout layout;
    private TextView tvHospitalName, tvAddress, tvHeading;
    private ImageView ivLeft, ivRight;
    private Button btnCancel, btnNext;
    private ViewPager viewPager;
    private DoctorsPagerAdapter doctorsPagerAdapter;
    private AdminLoginResponse.DoctorAddress hospitalData;
    private static final int PAGE_SIZE = 6;
    private int maxDoctors;
    private int selectedDoctorPos = -1; //Not selected yet
    private ArrayList<Boolean> listCalledPos;
    private ArrayList<AdminGetDoctorsResponse.DoctorDetails> listDoctors;
    private int doctorId;
    private int count = 0;
    private ArrayList<Integer> listSelectedDoctorIDs;

    @Override
    public void initializeControls() {
        layout = (LinearLayout) inflater.inflate(R.layout.admin_choose_doctors_main, null);
        llBody.addView(layout);

        tvHospitalName    = (TextView) layout.findViewById(R.id.tvHospitalName);
        tvAddress = (TextView) layout.findViewById(R.id.tvAddress);
        tvHeading   = (TextView) layout.findViewById(R.id.tvHeading);
        ivLeft    = (ImageView) layout.findViewById(R.id.ivLeft);
        ivRight    = (ImageView) layout.findViewById(R.id.ivRight);
        btnCancel    = (Button) layout.findViewById(R.id.btnCancel);
        btnNext    = (Button) layout.findViewById(R.id.btnNext);
        viewPager    = (ViewPager) layout.findViewById(R.id.viewPager);

        tvHospitalName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
        tvAddress.setTypeface(AppConstants.WALSHEIM_LIGHT);
        tvHeading.setTypeface(AppConstants.WALSHEIM_LIGHT);
        btnCancel.setTypeface(AppConstants.WALSHEIM_MEDIUM);
        btnNext.setTypeface(AppConstants.WALSHEIM_MEDIUM);

        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        tvBack.setOnClickListener(this);
    }

    @Override
    public void bindControls() {
//        prepareData();
        hospitalData = (AdminLoginResponse.DoctorAddress) getIntent().getSerializableExtra("hospitalData");
        doctorId = getIntent().getIntExtra("doctorId",0);
        tvLogin.setEnabled(false);
        tvHome.setVisibility(View.GONE);
        tvBack.setVisibility(View.GONE);
        tvBack.setText("Previous Page");
        tvTitle.setText("Choose Doctor(s)");

        tvHospitalName.setText(hospitalData.getClinicName());
        tvAddress.setText(hospitalData.getAddress2());
        listDoctors = new ArrayList<>();
        listCalledPos = new ArrayList<>();
        listSelectedDoctorIDs = new ArrayList<>();
        callService(PAGE_SIZE, 1, hospitalData.getExtDetails().getHospitalID());

        viewPager.setCurrentItem(0);
        ivLeft.setAlpha(0.25f);
        ivLeft.setEnabled(false);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    ivLeft.setAlpha(0.25f);
                    ivLeft.setEnabled(false);
                    ivRight.setAlpha(1.0f);
                    ivRight.setEnabled(true);
                } else if(position == listDoctors.size()/6+(listDoctors.size()%6==0?0:1)-1) {
                    ivRight.setAlpha(0.25f);
                    ivRight.setEnabled(false);
                    ivLeft.setAlpha(1.0f);
                    ivLeft.setEnabled(true);
                } else {
                    ivLeft.setAlpha(1.0f);
                    ivLeft.setEnabled(true);
                    ivRight.setAlpha(1.0f);
                    ivRight.setEnabled(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivLeft :
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
                break;
            case R.id.ivRight :
            {
                int page = viewPager.getCurrentItem();
                Log.e("ibRight : ","CurrentPage: "+page);
                if(!listCalledPos.get(page+1)) {
                    Log.e("Service called : ","PageNumber: "+page);
                    callService(PAGE_SIZE, page + 2, hospitalData.getExtDetails().getHospitalID());
                    listCalledPos.set(page+1, true);
                }

                viewPager.setCurrentItem(page + 1);
            }
            break;
            case R.id.btnCancel :
                finish();
                break;
            case R.id.btnNext :
                if(count > 0) {
                    Intent intent = new Intent(AdminChooseDoctors.this, AdminConfirmation.class);
                    intent.putExtra("hospitalData", this.hospitalData);
                    intent.putExtra("from", "AdminDoctor");
                    intent.putExtra("selectedDoctors", prepareSelectedDoctorsString());
                    intent.putExtra("doctorId", doctorId);
                    startActivity(intent);
                } else {
                    showToast("Please select at least one doctor.");
                }
                break;
            case R.id.tvBack :
                finish();
                break;
        }
    }

    //Custom Pager Adapter with GridView as its page item
    private class DoctorsPagerAdapter extends PagerAdapter {

        public void refreshPager() {
            this.notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            int count = listDoctors.size()/6 + (listDoctors.size()%6==0 ? 0:1);
            if(count<=1) {
                ivRight.setAlpha(0.25f);
                ivRight.setEnabled(false);
                ivLeft.setAlpha(0.25f);
                ivLeft.setEnabled(false);
            }
            return count;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout itemView = (LinearLayout) LayoutInflater.from(AdminChooseDoctors.this).inflate(R.layout.admin_pager_item, container, false);

            GridView gridView = (GridView) itemView.findViewById(R.id.gridView);
            GridAdapter gridAdapter = new GridAdapter(position);
            gridView.setAdapter(gridAdapter);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public int getItemPosition(Object object){
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    //Custom Adapter for GridView
    private class GridAdapter extends BaseAdapter {

        private HashMap<String,Speciality> hmSpecialities;

        private void initMap()
        {
            Preference preference = new Preference(AdminChooseDoctors.this);
            String specialitiesInString = preference.getStringFromPreference(Preference.SPECIALITIES_MAP, "");
            if(!TextUtils.isEmpty(specialitiesInString))
            {
                Gson gson = new Gson();
                ArrayList<Speciality> list = gson.fromJson(specialitiesInString, new TypeToken<List<Speciality>>(){}.getType());
                Collections.sort(list);
                hmSpecialities = new HashMap<>();
                for (Speciality speciality : list) {
                    hmSpecialities.put(speciality.DoctorSpecialityID, speciality);
                }
            }
        }

        private int pageNumber;
        public GridAdapter(final int pageNumber){
            this.pageNumber = pageNumber;
            initMap();
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(AdminChooseDoctors.this).inflate(R.layout.admin_choose_doctors_grid_cell, parent, false);
            final ImageView ivCheck = (ImageView) convertView.findViewById(R.id.ivCheck);
            TextView tvDoctor = (TextView) convertView.findViewById(R.id.tvDoctor);
            TextView tvDesignation = (TextView) convertView.findViewById(R.id.tvDesignation);
            TextView tvSpeciality = (TextView) convertView.findViewById(R.id.tvSpeciality);
            TextView tvYou = (TextView) convertView.findViewById(R.id.tvYou);



            int gridItemPos = pageNumber*6+position;
            ivCheck.setTag(R.id.ivCheck, gridItemPos);
            if(gridItemPos>=listDoctors.size())
                convertView.setVisibility(View.INVISIBLE);
            else {
                AdminGetDoctorsResponse.DoctorDetails doctorData = listDoctors.get(gridItemPos);
                ArrayList<String> list = new ArrayList<>(doctorData.getMapSpecialityCD().values());
                tvDoctor.setText(doctorData.getFullName());
                tvDesignation.setText(TextUtils.join(",", list));
                tvDoctor.setTypeface(AppConstants.WALSHEIM_BOLD);
                tvDesignation.setTypeface(AppConstants.WALSHEIM_LIGHT);
                tvSpeciality.setTypeface(AppConstants.WALSHEIM_LIGHT);
                if (hmSpecialities != null && hmSpecialities.size() > 0) {

                    for (int i = 0; i < list.size(); i++) {
                        list.set(i, hmSpecialities.get(list.get(i)).DoctorSpecialityDesc);
                    }
                    tvSpeciality.setText(TextUtils.join(",", list) /*"Physiotherapist"*/);
                }

                tvDoctor.setText(doctorData.getFullName()+",");

                if(doctorData.getDoctorID() == doctorId) {
                    tvYou.setVisibility(View.VISIBLE);
                } else {
                    tvYou.setVisibility(View.GONE);
                }

                if(doctorData.isSelected){
                    ivCheck.setBackgroundResource(R.drawable.check_box_sq_admin);
                } else {
                    ivCheck.setBackgroundResource(R.drawable.un_check_admin);
                }

                ivCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) ivCheck.getTag(R.id.ivCheck);
                        if(listDoctors.get(pos).isSelected) {
                            listDoctors.get(pos).isSelected = false;
                            count--;
                            try {
                                listSelectedDoctorIDs.remove((Integer)listDoctors.get(pos).getDoctorID());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                            setData(pos, false);
                        } else {
                            listDoctors.get(pos).isSelected = true;
                            count++;
                            listSelectedDoctorIDs.add((Integer) listDoctors.get(pos).getDoctorID());
//                            setData(pos, true);
                        }
                        doctorsPagerAdapter.refreshPager();
                    }
                });
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ivCheck.performClick();
                    }
                });
            }

            return convertView;
        }
    }

    //Temperory class for Holding data
    /*private class HospitalData {
        public boolean isSelected;
        public String name = "BHS Hospital";
        public String location = "T Nagar, Chennai";
    }

    private ArrayList<HospitalData> hospitalList;
    private void prepareData() {
        hospitalList = new ArrayList<>();
        for(int i=0; i<24; i++) {
            HospitalData hospitalData = new HospitalData();
            hospitalList.add(hospitalData);
        }
    }*/

    /*private void setData(int pos, boolean selection) {
        ArrayList<HospitalData> list = new ArrayList<>();
        for(int i=0; i<hospitalList.size(); i++) {
            HospitalData hospitalData = hospitalList.get(i);
            if(i==pos)
                hospitalData.isSelected = selection;
            else
//                hospitalData.isSelected = false;
            list.add(hospitalData);
        }
        hospitalList.clear();
        hospitalList.addAll(list);
    }*/

    private void setData(int pos, boolean selection) {
        ArrayList<AdminGetDoctorsResponse.DoctorDetails> list = new ArrayList<>();
        for(int i=0; i<listDoctors.size(); i++) {
            AdminGetDoctorsResponse.DoctorDetails doctorData = listDoctors.get(i);
            if(i==pos)
                doctorData.isSelected = selection;
            else
//                hospitalData.isSelected = false;
                list.add(doctorData);
        }
        listDoctors.clear();
        listDoctors.addAll(list);
    }

    private void callService(int pageSize, int pageNumber, int hospitalId) {
        if (Connectivity.isConnected(AdminChooseDoctors.this)) {
            getDoctors(pageSize, pageNumber, hospitalId);
        } else {
            Toast.makeText(AdminChooseDoctors.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDoctors(int pageSize, int pageNumber, int hospitalId) {
        final AdminGetDoctorsRequest adminGetDoctorsRequest = new AdminGetDoctorsRequest();
        adminGetDoctorsRequest.setPageSize(pageSize);
        adminGetDoctorsRequest.setPageNumber(pageNumber);
        adminGetDoctorsRequest.setHospitalID(hospitalId);
//        adminGetDoctorsRequest.setDoctorID(new ArrayList<Integer>());

        String url = "http://dct4avjn1lfw.cloudfront.net";
        RestClient.getAPI(url).getDoctors(adminGetDoctorsRequest, new RestCallback<AdminGetDoctorsResponse>() {
            @Override
            public void failure(RestError restError) {
                Log.e("Doctor Login", "" + restError.getErrorMessage());
                ivRight.setAlpha(0.25f);
                ivRight.setEnabled(false);
                ivLeft.setAlpha(0.25f);
                ivLeft.setEnabled(false);
            }

            @Override
            public void success(AdminGetDoctorsResponse adminGetDoctorsResponse, Response response) {
                if (adminGetDoctorsResponse != null) {

                    if(maxDoctors == 0) {
                        maxDoctors = adminGetDoctorsResponse.DoctorCount;
                        Log.e("maxDoctors : ",""+maxDoctors);
                        int pages = maxDoctors/6+(maxDoctors%6==0?0:1);
                        if(pages<=1) {//No left right swipe

                            ivRight.setAlpha(0.25f);
                            ivRight.setEnabled(false);
                            ivLeft.setAlpha(0.25f);
                            ivLeft.setEnabled(false);
                        }
                        for (int i=0; i<pages; i++) {
                            listCalledPos.add(false);
                        }
                        if(pages>0)
                        listCalledPos.set(0, true);
                    }

                    listDoctors.addAll(adminGetDoctorsResponse.getListDoctorDetails());
                    Log.e("List size : ", "" + listDoctors.size());
                    if(doctorsPagerAdapter==null) {
                        doctorsPagerAdapter = new DoctorsPagerAdapter();
                        viewPager.setAdapter(doctorsPagerAdapter);
                    } else {
                        doctorsPagerAdapter.refreshPager();
                    }
                } else {
                    Log.i("Response", "" + response.getReason());
                    ivRight.setAlpha(0.25f);
                    ivRight.setEnabled(false);
                    ivLeft.setAlpha(0.25f);
                    ivLeft.setEnabled(false);
                }
            }
        });
    }

    private String prepareSelectedDoctorsString() {
        String result = "";
        if(listSelectedDoctorIDs != null) {
            for(int i=0; i<listSelectedDoctorIDs.size(); i++)
                result += ","+listSelectedDoctorIDs.get(i);
            result = result.replaceFirst(",", "");
        }
        Log.e("Selected Doctors >>", result);
        return result;
    }
}
