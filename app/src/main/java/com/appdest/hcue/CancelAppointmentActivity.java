package com.appdest.hcue;

import android.content.Intent;
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
        ivHome.setVisibility(View.GONE);


        gvAppointments = (GridView) llCancelAppointment.findViewById(R.id.gvAppointments);

        setSpecificTypeFace(llCancelAppointment, AppConstants.WALSHEIM_MEDIUM);
        tvHeading.setTypeface(AppConstants.WALSHEIM_LIGHT);

    }

    @Override
    public void bindControls()
    {
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
            ViewHolder holder = null;
            if (view == null) {
                view = LayoutInflater.from(CancelAppointmentActivity.this).inflate(R.layout.appointments_history_cell,null);
                holder = new ViewHolder();
                holder.tvPatientName = (TextView) view.findViewById(R.id.tvPatientName);
                holder.tvDateTime = (TextView) view.findViewById(R.id.tvDateTime);


                holder.ivCheck = (ImageView) view.findViewById(R.id.ivCheck);

                holder.tvDateTime.setTypeface(AppConstants.WALSHEIM_MEDIUM);
                holder.tvPatientName.setTypeface(AppConstants.WALSHEIM_MEDIUM);


                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.tvPatientName.setText("Sri krishna");
            holder.tvDateTime.setText("Today - 10:30 AM");


            return view;
        }

    }

    static class ViewHolder {
        TextView tvDateTime,tvPatientName;
        ImageView ivCheck;
    }
}
