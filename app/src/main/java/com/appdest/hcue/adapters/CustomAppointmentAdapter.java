package com.appdest.hcue.adapters;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.appdest.hcue.R;
import com.appdest.hcue.model.GetDoctorAppointmentResponse;
import com.appdest.hcue.utils.AppointmentTimeInterface;
import com.appdest.hcue.utils.TimeUtils;

import java.util.ArrayList;

public class CustomAppointmentAdapter extends PagerAdapter {

    private Context mContext;
    LayoutInflater mLayoutInflater;
    ViewPager viewPager;
    private AppointmentTimeInterface appointmentTimeInterface;
    private ArrayList<GetDoctorAppointmentResponse.AppointmentRow> appointmentRows;

    private static final int heightGap = 25;
    private static final int widthGap = 25;
    private int gvWidth, gvHeight;
    private LinearLayout.LayoutParams llParams;
    private GetDoctorAppointmentResponse.TimeSlot selectedTimeSlot;

    public GetDoctorAppointmentResponse.TimeSlot getSelectedTimeSlot()
    {
        return selectedTimeSlot;
    }

    public GetDoctorAppointmentResponse.AppointmentRow getSelectedPageItem(int position)
    {
        if(appointmentRows != null && appointmentRows.size() != 0 && appointmentRows.size() >= position - 1)
            return appointmentRows.get(position);
        else
            return null;
    }

    public void setViewPager(ViewPager viewPager)
    {
        this.viewPager = viewPager;
    }

    public void setAppointmentTimeInterface(AppointmentTimeInterface appointmentTimeInterface)
    {
        this.appointmentTimeInterface = appointmentTimeInterface;
    }

    public CustomAppointmentAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        preCalculateGridDimensions(context);
        llParams = new LinearLayout.LayoutParams(gvWidth, gvHeight);
        llParams.gravity = Gravity.CENTER;
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
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LinearLayout itemView = (LinearLayout) mLayoutInflater.inflate(R.layout.pager_item, container, false);

        CustomTimeAdapter customTimeAdapter = new CustomTimeAdapter(mContext, appointmentRows.get(position));
        GridView gvTime = (GridView) itemView.findViewById(R.id.gvTime);
        gvTime.setSelector(R.drawable.appointment_time);
        gvTime.setAdapter(customTimeAdapter);
        gvTime.setLayoutParams(llParams);
        gvTime.setHorizontalSpacing(widthGap);
        gvTime.setVerticalSpacing(heightGap);
        gvTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTimeSlot = ((CustomTimeAdapter) parent.getAdapter()).getSelectedItem(position);
                if(!"Y".equalsIgnoreCase(selectedTimeSlot.Available))
                    return;

                view.setSelected(true);

                if(appointmentTimeInterface != null)
                {
                    GetDoctorAppointmentResponse.AppointmentRow selectedPageItem = getSelectedPageItem(viewPager.getCurrentItem());
                    long dayInstance = selectedPageItem.getConsultationDate().longValue();
                    long timeInstance = TimeUtils.getLongForHHMMSS(selectedTimeSlot.getStartTime());
                    long totalInstance = dayInstance + timeInstance;
                    StringBuilder sb = new StringBuilder();
                    sb.append(DateUtils.isToday(totalInstance) ? "Today" : TimeUtils.getDay(totalInstance))
                            .append(", ")
                            .append(TimeUtils.format2DateProper(totalInstance))
                            .append(" @ ")
                            .append("<font color=\"#48B09E\">" + TimeUtils.format2hhmm(selectedTimeSlot.getStartTime()) + " hrs</font>")

                    ;
                    appointmentTimeInterface.updateAppointmentText(Html.fromHtml(sb.toString()));
                }
            }
        });

        gvTime.setSelection(0);
        gvTime.requestFocusFromTouch();
        gvTime.setSelection(0);

        container.addView(itemView);

        return itemView;
    }

    private void preCalculateGridDimensions(Context context) {
        Drawable d = context.getResources().getDrawable(R.drawable.selected_time_col_bg);
        int gvCellWidth = d.getIntrinsicWidth();
        int gvCellHeight = d.getIntrinsicHeight();
        int xItems = 5;
        int yItems = 5;
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