package com.appdest.hcue.adapters;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.appdest.hcue.R;
import com.appdest.hcue.model.GetDoctorAppointmentResponse;

import java.util.ArrayList;

public class CustomAppointmentAdapter extends PagerAdapter {

    private Context mContext;
    LayoutInflater mLayoutInflater;
    private ArrayList<GetDoctorAppointmentResponse.AppointmentRow> appointmentRows;

    private static final int heightGap = 25;
    private static final int widthGap = 25;
    int gvWidth, gvHeight;

    public CustomAppointmentAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        preCalculateGridDimensions(context);
    }

    public void refresh(ArrayList<GetDoctorAppointmentResponse.AppointmentRow> appointmentRows) {
        this.appointmentRows = appointmentRows;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (appointmentRows != null
                && appointmentRows.size() > 0)
            return appointmentRows.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout itemView = (LinearLayout) mLayoutInflater.inflate(R.layout.pager_item, container, false);

        GridView gvTime = (GridView) itemView.findViewById(R.id.gvTime);
        gvTime.setAdapter(new CustomTimeAdapter(mContext, appointmentRows.get(position)));
        gvTime.setLayoutParams(new LinearLayout.LayoutParams(gvWidth, gvHeight));
        gvTime.setHorizontalSpacing(widthGap);
        gvTime.setVerticalSpacing(heightGap);
        gvTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        container.addView(itemView);

        return itemView;
    }

    private void preCalculateGridDimensions(Context context) {
        Drawable d = context.getResources().getDrawable(R.drawable.selected_time_col_bg);
        int gvCellWidth = d.getIntrinsicWidth();
        int gvCellHeight = d.getIntrinsicHeight();
        int xItems = 5;
        int yItems = 3;
        gvWidth = xItems * gvCellWidth + (xItems - 1) * widthGap + 1;
        gvHeight = yItems * gvCellHeight + (yItems - 1) * heightGap + 1;
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