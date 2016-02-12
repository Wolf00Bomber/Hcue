package com.appdest.hcue;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdest.hcue.adapters.CustomTimeAdapter;
import com.appdest.hcue.common.AppConstants;
import com.github.siyamed.shapeimageview.CircularImageView;

import java.util.ArrayList;

/**
 * Created by Vinsan on 2/12/2016.
 */
public class AdminChooseHospital extends BaseActivity implements View.OnClickListener{
    private LinearLayout layout;
    private CircularImageView imageView;
    private TextView tvDoctorName, tvDesgAndSpeciality, tvEmail, tvMobile, tvHeading;
    private ImageView ivLeft, ivRight;
    private Button btnCancel, btnNext;
    private ViewPager viewPager;
    private HospitalPagerAdapter hospitalPagerAdapter;

    @Override
    public void initializeControls() {
        layout = (LinearLayout) inflater.inflate(R.layout.admin_choose_hospital_main, null);
        llBody.addView(layout);

        imageView   = (CircularImageView) layout.findViewById(R.id.imageView);
        tvDoctorName    = (TextView) layout.findViewById(R.id.tvDoctorName);
        tvDesgAndSpeciality    = (TextView) layout.findViewById(R.id.tvDesgAndSpeciality);
        tvEmail    = (TextView) layout.findViewById(R.id.tvEmail);
        tvMobile    = (TextView) layout.findViewById(R.id.tvMobile);
        tvHeading   = (TextView) layout.findViewById(R.id.tvHeading);
        ivLeft    = (ImageView) layout.findViewById(R.id.ivLeft);
        ivRight    = (ImageView) layout.findViewById(R.id.ivRight);
        btnCancel    = (Button) layout.findViewById(R.id.btnCancel);
        btnNext    = (Button) layout.findViewById(R.id.btnNext);
        viewPager    = (ViewPager) layout.findViewById(R.id.viewPager);

        tvDoctorName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
        tvDesgAndSpeciality.setTypeface(AppConstants.WALSHEIM_LIGHT);
        tvEmail.setTypeface(AppConstants.WALSHEIM_LIGHT);
        tvMobile.setTypeface(AppConstants.WALSHEIM_MEDIUM);
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
        tvBack.setText("Previous Page");
        tvTitle.setText("Choose Hospital / Clinic");
        tvDoctorName.setText("");
        tvDesgAndSpeciality.setText("");
        tvEmail.setText("");
        tvMobile.setText("");

        hospitalPagerAdapter = new HospitalPagerAdapter();
        viewPager.setAdapter(hospitalPagerAdapter);
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
                } else if(position == hospitalList.size()) {
                    ivRight.setAlpha(0.25f);
                    ivRight.setEnabled(false);
                } else {
                    ivLeft.setAlpha(1.0f);
                    ivRight.setAlpha(1.0f);
                    ivLeft.setEnabled(true);
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
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            break;
        case R.id.btnCancel :
            break;
        case R.id.btnNext :
            Intent intent = new Intent(AdminChooseHospital.this, AdminChooseDoctors.class);
            startActivity(intent);
            break;
        case R.id.tvBack :
            finish();
            break;
    }
    }

    //Custom Pager Adapter with GridView as its page item
    private class HospitalPagerAdapter extends PagerAdapter {

        public void refreshPager() {
            this.notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return hospitalList.size()/6 + (hospitalList.size()%6==0 ? 0:1);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout itemView = (LinearLayout) LayoutInflater.from(AdminChooseHospital.this).inflate(R.layout.admin_pager_item, container, false);

            GridView gridView = (GridView) itemView.findViewById(R.id.gridView);
            GridAdapter gridAdapter = new GridAdapter(position);
            gridView.setAdapter(gridAdapter);
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
        private int pageNumber;
        public GridAdapter(final int pageNumber){
            this.pageNumber = pageNumber+1;
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
            convertView = (LinearLayout) LayoutInflater.from(AdminChooseHospital.this).inflate(R.layout.admin_choose_hospital_grid_cell, parent, false);
            final ImageView ivCheck = (ImageView) convertView.findViewById(R.id.ivCheck);
            TextView tvHospital = (TextView) convertView.findViewById(R.id.tvHospital);
            TextView tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);

            int gridItemPos = pageNumber*6+position-1;
            ivCheck.setTag(R.id.ivCheck, gridItemPos);
            if(gridItemPos>hospitalList.size())
                convertView.setVisibility(View.INVISIBLE);
            else {
                HospitalData hospitalData = hospitalList.get(gridItemPos);
                if(hospitalData.isSelected){
                    ivCheck.setBackgroundResource(R.drawable.check_box_admin);
                } else {
                    ivCheck.setBackgroundResource(R.drawable.un_check_box_admin);
                }

                ivCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) ivCheck.getTag(R.id.ivCheck);
                        if(hospitalList.get(pos).isSelected) {
//                            hospitalList.get(pos).isSelected = false;
                            setData(pos, false);
                        } else {
//                            hospitalList.get(pos).isSelected = true;
                            setData(pos, true);
                        }
                        hospitalPagerAdapter.refreshPager();
                    }
                });
            }

            return convertView;
        }
    }

    //Temperory class for Holding data
    private class HospitalData {
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
    }

    private void setData(int pos, boolean selection) {
        ArrayList<HospitalData> list = new ArrayList<>();
        for(int i=0; i<hospitalList.size(); i++) {
            HospitalData hospitalData = hospitalList.get(i);
            if(i==pos)
                hospitalData.isSelected = selection;
            else
                hospitalData.isSelected = false;
            list.add(hospitalData);
        }
        hospitalList.clear();
        hospitalList.addAll(list);
    }
}
