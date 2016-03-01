package com.appdest.hcue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.AdminLoginResponse;
import com.appdest.hcue.utils.Preference;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Vinsan on 2/12/2016.
 */
public class AdminChooseHospital extends BaseActivity implements View.OnClickListener{
    private LinearLayout layout;
    private PorterShapeImageView imageView;
    private TextView tvDoctorName, tvDesgAndSpeciality, tvEmail, tvMobile, tvHeading;
    private ImageView ivLeft, ivRight;
    private Button btnCancel, btnNext;
    private ViewPager viewPager;
    private HospitalPagerAdapter hospitalPagerAdapter;
    private List<AdminLoginResponse.DoctorAddress> hospitalList;
    private AdminLoginResponse.DoctorAddress hospitalData;
    private int doctorId;

    @Override
    public void initializeControls() {
        layout = (LinearLayout) inflater.inflate(R.layout.admin_choose_hospital_main, null);
        llBody.addView(layout);



        imageView   = (PorterShapeImageView) layout.findViewById(R.id.imageView);
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

        toggle_additional_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(toggle_additional_info.getText().toString().equalsIgnoreCase("ON"))
                {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked
                                    dialog.dismiss();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    toggle_additional_info.setChecked(false);
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want patient to enter additional info ?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            }
        });
    }

    @Override
    public void bindControls() {
        hospitalList = (ArrayList<AdminLoginResponse.DoctorAddress>)getIntent().getSerializableExtra("hospitals");

        Iterator<AdminLoginResponse.DoctorAddress> iterator =  hospitalList.iterator();
        while (iterator.hasNext())
        {
            if(!iterator.next().getActive().equalsIgnoreCase("Y"))
            {
                iterator.remove();
            }
        }
        doctorId = getIntent().getIntExtra("doctorId",0);
        AdminLoginResponse.Doctor doctor = (AdminLoginResponse.Doctor) getIntent().getSerializableExtra("doctor");
        tvHome.setVisibility(View.GONE);
        tvBack.setVisibility(View.GONE);
        lladditional_info.setVisibility(View.VISIBLE);
        tvLogin.setEnabled(false);
        tvBack.setText("Previous Page");
        tvTitle.setText("Choose Hospital / Clinic");
        tvDoctorName.setText("Dr . "+doctor.getFullName());
        tvDesgAndSpeciality.setText("");
        tvEmail.setText(doctor.getDoctorLoginID());
        tvMobile.setText(getIntent().getStringExtra("phone"));

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
                    ivRight.setAlpha(1.0f);
                    ivRight.setEnabled(true);
                } else if(position == hospitalList.size()/6+(hospitalList.size()%6 == 0 ?0:1)-1) {
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
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                break;
            case R.id.btnCancel :
                finish();
                break;
            case R.id.btnNext :
                if(this.hospitalData != null) {
                    //if hospital id is present then move to choose doctors activity
                    //or else
                    //it is a clinic and no need to go to choose doctors activity
                    //directly goto confirm and logout screen
                    Preference preference = new Preference(AdminChooseHospital.this);
                    if(toggle_additional_info.getText().toString().equalsIgnoreCase("ON"))
                    {
                       AppConstants.Is_AdditionalInfo_On = true ;

                        preference.saveBooleanInPreference(Preference.ADDITIONAL_INFO,true);
                    }else
                    {
                        AppConstants.Is_AdditionalInfo_On = false ;
                        preference.saveBooleanInPreference(Preference.ADDITIONAL_INFO,false);
                    }
                    preference.commitPreference();
                    if(this.hospitalData.getExtDetails().getHospitalID() == 0) { //clinic
                        Intent intent = new Intent(AdminChooseHospital.this, AdminConfirmation.class);
                        intent.putExtra("from", "AdminHospital");
                        intent.putExtra("hospitalData", this.hospitalData);
                        intent.putExtra("doctorId", doctorId);
                        startActivity(intent);
                    } else { //hospital
                        Intent intent = new Intent(AdminChooseHospital.this, AdminChooseDoctors.class);
                        intent.putExtra("hospitalData", this.hospitalData);
                        intent.putExtra("doctorId", doctorId);
                        startActivity(intent);
                    }
                } else {
                    showToast("Please select a hospital.");
                }
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
            int count = hospitalList.size()/6 + (hospitalList.size()%6==0 ? 0:1);
            Log.e("Count :", ""+count);
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
            LinearLayout itemView = (LinearLayout) LayoutInflater.from(AdminChooseHospital.this).inflate(R.layout.admin_pager_item, container, false);

            GridView gridView = (GridView) itemView.findViewById(R.id.gridView);
            GridAdapter gridAdapter = new GridAdapter(position);
            gridView.setAdapter(gridAdapter);
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

    //Custom Adapter for GridView
    private class GridAdapter extends BaseAdapter {
        private int pageNumber;
        public GridAdapter(final int pageNumber){
            this.pageNumber = pageNumber;
            Log.e("Page : ", ""+pageNumber);
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
            convertView = LayoutInflater.from(AdminChooseHospital.this).inflate(R.layout.admin_choose_hospital_grid_cell, parent, false);
            final ImageView ivCheck = (ImageView) convertView.findViewById(R.id.ivCheck);
            TextView tvHospital = (TextView) convertView.findViewById(R.id.tvHospital);
            TextView tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);

            int gridItemPos = pageNumber*6+position;
            ivCheck.setTag(R.id.ivCheck, gridItemPos);
            Log.e("GRID ITEM POSITION :", ""+gridItemPos);
            if(gridItemPos>=hospitalList.size())
                convertView.setVisibility(View.INVISIBLE);
            else {
                AdminLoginResponse.DoctorAddress hospitalData = hospitalList.get(gridItemPos);
                tvHospital.setText(hospitalData.getClinicName());
                tvLocation.setText(hospitalData.getAddress2());
                tvHospital.setTypeface(AppConstants.WALSHEIM_BOLD);
                tvLocation.setTypeface(AppConstants.WALSHEIM_LIGHT);
                if(hospitalData.isSelected){
                    ivCheck.setBackgroundResource(R.drawable.check_box_admin);
                } else {
                    ivCheck.setBackgroundResource(R.drawable.un_check_box_admin);
                }

                ivCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = (int) ivCheck.getTag(R.id.ivCheck);
                        Log.e("Radio Check at : ", ""+pos);
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

    private void setData(int pos, boolean selection) {
        ArrayList<AdminLoginResponse.DoctorAddress> list = new ArrayList<>();
        for(int i=0; i<hospitalList.size(); i++) {
            AdminLoginResponse.DoctorAddress hospitalData = hospitalList.get(i);
            if(i==pos) {
                hospitalData.isSelected = selection;
                if(selection)
                    this.hospitalData = hospitalData;
                else
                    this.hospitalData = null;
            }
            else
                hospitalData.isSelected = false;
            list.add(hospitalData);
        }
        hospitalList.clear();
        hospitalList.addAll(list);
    }
}
