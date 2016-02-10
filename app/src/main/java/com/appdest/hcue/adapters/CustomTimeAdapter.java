package com.appdest.hcue.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.appdest.hcue.BaseActivity;
import com.appdest.hcue.R;
import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.GetDoctorAppointmentResponse;
import com.appdest.hcue.utils.TimeUtils;

public class CustomTimeAdapter extends BaseAdapter
{
    private Context context;
    private GetDoctorAppointmentResponse.AppointmentRow appointmentRow;
    private GridView.LayoutParams cellParams;

    public GetDoctorAppointmentResponse.TimeSlot getSelectedItem(int selectedItem){
        return appointmentRow.getTimeSlots().get(selectedItem);
    }

    public CustomTimeAdapter(Context context, GetDoctorAppointmentResponse.AppointmentRow appointmentRow)
    {
        this.context = context;
        this.appointmentRow = appointmentRow;
        init(context);
    }

    private void init(Context context)
    {
        Drawable d = context.getResources().getDrawable(R.drawable.selected_time_col_bg);
        int calendarCellWidth = d.getIntrinsicWidth();
        int calendarCellHeight = d.getIntrinsicHeight();
        cellParams = new GridView.LayoutParams(calendarCellWidth, calendarCellHeight);

    }

    @Override
    public int getCount() {
        if(appointmentRow != null
            && appointmentRow.getTimeSlots() != null
            && appointmentRow.getTimeSlots().size() > 0)
            return appointmentRow.getTimeSlots().size();
        return 0;
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
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.control_calendar_day, parent, false);
            view.setLayoutParams(cellParams);
        }

        GetDoctorAppointmentResponse.TimeSlot timeSlot = appointmentRow.getTimeSlots().get(position);

        TextView tvCell = (TextView) view.findViewById(R.id.tvCell);
        tvCell.setTypeface(null, Typeface.NORMAL);

        if("Y".equalsIgnoreCase(timeSlot.Available))
        {
            view.setEnabled(true);
            tvCell.setEnabled(true);
        }
        else
        {
            view.setEnabled(false);
            tvCell.setEnabled(false);
        }
        view.setBackgroundResource(R.drawable.appointment_time);
        tvCell.setTextColor(context.getResources().getColorStateList(R.color.text_pressed));

        ((BaseActivity)context).setSpecificTypeFace((ViewGroup) view, AppConstants.WALSHEIM_MEDIUM);

        // set text
        tvCell.setText(TimeUtils.format2hhmm(timeSlot.getStartTime()));

        return view;
    }

}
