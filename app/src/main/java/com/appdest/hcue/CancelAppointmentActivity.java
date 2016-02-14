package com.appdest.hcue;

import android.content.Intent;
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

import com.appdest.hcue.common.AppConstants;

import java.util.ArrayList;

/**
 * Created by shyamprasadg on 30/01/16.
 */
public class CancelAppointmentActivity extends BaseActivity
{
    private LinearLayout llCancelAppointment;
    private TextView tvHeading,tvPatientName;
    private GridView gvAppointments = null;
    private Button btnCancelAppointment;
    @Override
    public void initializeControls()
    {
        llCancelAppointment = (LinearLayout) inflater.inflate(R.layout.booked_appointments, null);

        llBody.addView(llCancelAppointment);

        tvHeading  				= (TextView)	llCancelAppointment.findViewById(R.id.tvHeading);
        tvPatientName  			= (TextView)	llCancelAppointment.findViewById(R.id.tvPatientName);

        btnCancelAppointment 		= (Button)		llCancelAppointment.findViewById(R.id.btnCancelAppointment);


        tvBack.setVisibility(View.GONE);
        tvHome.setVisibility(View.GONE);


        gvAppointments = (GridView) llCancelAppointment.findViewById(R.id.gvAppointments);

        setSpecificTypeFace(llCancelAppointment, AppConstants.WALSHEIM_MEDIUM);
        tvHeading.setTypeface(AppConstants.WALSHEIM_LIGHT);
        btnCancelAppointment.setTypeface(AppConstants.WALSHEIM_BOLD);


        tvTitle.setText("Cancel Your Appointment");

    }

    @Override
    public void bindControls()
    {
        prepareData();
        gvAppointments.setAdapter(new GridAdapter());

        gvAppointments.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) {

            }

        });

        btnCancelAppointment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CancelAppointmentActivity.this,ConfirmCancelationActivity.class);
                startActivity(intent);
            }
        });

    }

    private class GridAdapter extends BaseAdapter {
//        private ViewHolder holder;
        @Override
        public int getCount() {
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
//            if (view == null) {
                view = LayoutInflater.from(CancelAppointmentActivity.this).inflate(R.layout.appointments_history_cell,null);
//                holder = new ViewHolder();
                TextView tvDoctorName = (TextView) view.findViewById(R.id.tvDoctorName);
            TextView tvDateTime = (TextView) view.findViewById(R.id.tvDateTime);


            final ImageView ivCheck = (ImageView) view.findViewById(R.id.ivCheck);

                tvDateTime.setTypeface(AppConstants.WALSHEIM_LIGHT);
                tvDoctorName.setTypeface(AppConstants.WALSHEIM_LIGHT);


//                view.setTag(holder);
//            } else {
//                holder = (ViewHolder) view.getTag();
//            }

            AppointmentsData data = hospitalList.get(pos);
            tvDoctorName.setText(data.doctorName);
            tvDateTime.setText(data.time);

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
                        if(hospitalList.get(pos).isSelected) {
                            hospitalList.get(pos).isSelected = false;
//                            setData(pos, false);
                        } else {
                            hospitalList.get(pos).isSelected = true;
//                            setData(pos, true);
                        }
                        notifyDataSetChanged();
                    }
                });


            return view;
        }

    }

    /*static class ViewHolder {
        TextView tvDateTime,tvDoctorName;
        ImageView ivCheck;
    }*/

    //Temperory class for Holding data
    private class AppointmentsData {
        public boolean isSelected;
        public String doctorName = "Dr.P.VenkataKrishna";
        public String time = "14th Feb 2016 - 10:30 AM";
    }

    private ArrayList<AppointmentsData> hospitalList;
    private void prepareData() {
        hospitalList = new ArrayList<>();
        for(int i=0; i<24; i++) {
            AppointmentsData data = new AppointmentsData();
            data.doctorName = "Dr.P.VenkataKrishna";
            hospitalList.add(data);
        }
    }

    private void setData(int pos, boolean selection) {
        ArrayList<AppointmentsData> list = new ArrayList<>();
        for(int i=0; i<hospitalList.size(); i++) {
            AppointmentsData hospitalData = hospitalList.get(i);
            if(i==pos)
                hospitalData.isSelected = selection;
            else
//                hospitalData.isSelected = false;
                list.add(hospitalData);
        }
        hospitalList.clear();
        hospitalList.addAll(list);
    }
}
