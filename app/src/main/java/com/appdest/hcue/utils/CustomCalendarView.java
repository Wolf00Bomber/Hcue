package com.appdest.hcue.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appdest.hcue.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CustomCalendarView extends LinearLayout
{ // internal components
    private LinearLayout header , ll_calendar_next_button , ll_calendar_prev_button;
    private RelativeLayout rvDateTitle;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;
    private Calendar currentDate;
    private String dateFormat;

    private int gap =  10;
    private int calendarCellWidth = 0;
    private int calendarCellHeight = 0;

    private EventHandler eventHandler;

    private static final int  DAYS_COUNT = 42;

    //kvk
    private ImageView btnRefresh;
    private int currentMonthIndex = 0;
    private boolean isMonthUpdated;
    private int updatedMonth;
    private CalendarAdapter adapter;


    // seasons' rainbow
    int[] rainbow = new int[] {
            R.color.summer,
            R.color.fall,
            R.color.winter,
            R.color.spring
    };

    int[] monthSeason = new int[] {2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

    public CustomCalendarView(Context context)
    {
        super(context);
        initControl(context);
    }

    public CustomCalendarView(Context context, AttributeSet attrs)
    {
        super(context, attrs, 0);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarDateElement);
        dateFormat = ta.getString(R.styleable.CalendarDateElement_dateFormat);
        initControl(context);
    }

    public void setEventHandler(EventHandler eventHandler)
    {
        this.eventHandler = eventHandler;
    }

    public EventHandler getEventHandler()
    {
        return eventHandler;
    }

    public GridView getGrid()
    {
        return grid ;
    }

    /**
     * Load component XML layout
     */
    private void initControl(Context context)
    {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.control_calendar, this);

        // layout is inflated, assign local variables to components
        rvDateTitle = (RelativeLayout) findViewById(R.id.rvDateTitle);
        header = (LinearLayout)findViewById(R.id.calendar_header);
        ll_calendar_next_button = (LinearLayout)findViewById(R.id.ll_calendar_next_button);
        ll_calendar_prev_button = (LinearLayout)findViewById(R.id.ll_calendar_prev_button);
        btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView)findViewById(R.id.calendar_next_button);
        btnRefresh = (ImageView)findViewById(R.id.calendar_refresh);
        txtDate = (TextView)findViewById(R.id.calendar_date_display);
        grid = (GridView)findViewById(R.id.calendar_grid);

        currentDate = Calendar.getInstance();
        Drawable d = getResources().getDrawable(R.drawable.date_mnth_header);
        calendarCellWidth = d.getIntrinsicWidth()/7;
        calendarCellHeight = d.getIntrinsicHeight();
        adapter = new CalendarAdapter(null);
        adapter.setSelectedDate(new Date());
        grid.setAdapter(adapter);


        btnPrev.setBackgroundResource(R.drawable.right_arrow_calendar_un);
        rvDateTitle.setLayoutParams(new RelativeLayout.LayoutParams(d.getIntrinsicWidth(), d.getIntrinsicHeight()));


        ll_calendar_next_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNext.performClick();
            }
        });

        ll_calendar_prev_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPrev.performClick();
            }
        });
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentMonthIndex > 0) {// To avoid the selection of previous months than current month
                    currentMonthIndex--;
                    currentDate.add(Calendar.MONTH, -1);
                    isMonthUpdated = true;
                    updatedMonth = 0;
                    updateCalendar();
                    if(currentMonthIndex == 0)
                        btnPrev.setBackgroundResource(R.drawable.right_arrow_calendar_un);
                    else
                        btnPrev.setBackgroundResource(R.drawable.right_arrow_calendar);

                }else
                {
                    btnPrev.setBackgroundResource(R.drawable.right_arrow_calendar_un);
                }
            }
        });

        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMonthIndex++;
                currentDate.add(Calendar.MONTH, 1);
                isMonthUpdated= true;
                updatedMonth=0;
                updateCalendar();
                btnPrev.setBackgroundResource(R.drawable.right_arrow_calendar);
            }
        });

        btnRefresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    isMonthUpdated = false;
                    updatedMonth = 0;
                    currentMonthIndex = 0;
                    currentDate = null;
                    currentDate = Calendar.getInstance();
                    ((CalendarAdapter)grid.getAdapter()).setSelectedDate(new Date());
                    updateCalendar();
                    if(eventHandler != null)
                        eventHandler.onDayClicked(currentDate.getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (eventHandler == null)
                    return;

                if(view.isEnabled()) {
//					Toast.makeText(getContext(), "Enabled", Toast.LENGTH_SHORT).show();
                    Date date = (Date) parent.getItemAtPosition(position);
                    ((CalendarAdapter)grid.getAdapter()).setSelectedDate(date);
                    grid.invalidateViews();
                    eventHandler.onDayClicked(date);
                }
            }

        });
    }

    public CalendarAdapter getAdapter()

    {
        return adapter;
    }

    public void updateCalendar()
    {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar)currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells (42 days calendar as per our business logic)
        while (cells.size() < DAYS_COUNT)
        {

            Date d = calendar.getTime();
            cells.add(d);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        ((CalendarAdapter)grid.getAdapter()).updateData(cells);

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat != null ? dateFormat : "MMM yyyy");
        txtDate.setText(sdf.format(currentDate.getTime()));

    }

    public class CalendarAdapter extends BaseAdapter
    {
        private ArrayList<Date> cells;
        private GridView.LayoutParams cellParams;
        private Date selectedDate;

        public CalendarAdapter(ArrayList<Date> cells)
        {
            this.cells = cells;
//            cellParams = new ViewGroup.LayoutParams(calendarCellWidth - 2 * gap, calendarCellHeight - 2 * gap);
            cellParams = new GridView.LayoutParams((int)(calendarCellWidth * 0.8f), (int)(calendarCellHeight * 0.8f));
        }

        public void setSelectedDate(Date selectedDate)
        {
            this.selectedDate = selectedDate;
        }

        @Override
        public int getCount() {
            if(cells != null && cells.size() > 0)
                return cells.size();
            else
                return 0;
        }

        @Override
        public Date getItem(int position) {
            return cells.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            // day in question
            Date date = getItem(position);
            if(isMonthUpdated && currentMonthIndex>0) {
                if(date.getDate()==1){
                    isMonthUpdated = false;
                    updatedMonth = date.getMonth();
                }
            }

            // today
            Date today = new Date();

            // inflate item if it does not exist yet
            if (view == null)
            {
                view = LayoutInflater.from(CustomCalendarView.this.getContext()).inflate(R.layout.control_calendar_day, parent, false);
                view.setLayoutParams(cellParams);
            }

            TextView tvCell = (TextView) view.findViewById(R.id.tvCell);

            // clear styling
            tvCell.setTypeface(null, Typeface.NORMAL);
            if(currentMonthIndex <= 0){
                if((date.before(today)&& !DateUtils.isToday(date.getTime()))
                        || (date.getMonth() != today.getMonth() || date.getYear() != today.getYear())) {
                    // if this day is past Date, grey it out
                    view.setEnabled(false);
                    tvCell.setEnabled(false);
                    tvCell.setTextColor(getResources().getColor(R.color.greyed_out));
                    tvCell.setBackgroundResource(0);
                } else if(date.getDate() == selectedDate.getDate()){
                    view.setEnabled(true);
                    tvCell.setEnabled(true);
                    tvCell.setTextColor(getResources().getColor(R.color.white));
                    tvCell.setBackgroundResource(R.drawable.selected_date_icon);
                } else {
                    view.setEnabled(true);
                    tvCell.setEnabled(true);
                    tvCell.setTextColor(Color.BLACK);
                    tvCell.setBackgroundResource(0);
                    tvCell.setTypeface(null,Typeface.BOLD);
                }
            } else if(currentMonthIndex > 0) {
                if(Math.abs(date.getMonth() - updatedMonth)==0  && date.getDate()>0 && date.getDate()<=31) {
                    view.setEnabled(true);
                    tvCell.setEnabled(true);
                    tvCell.setTextColor(Color.BLACK);
                    tvCell.setBackgroundResource(0);
                    if((date.getDate() == selectedDate.getDate()) && (date.getMonth()== selectedDate.getMonth())){
                        tvCell.setTextColor(getResources().getColor(R.color.white));
                        tvCell.setBackgroundResource(R.drawable.selected_date_icon);
                    }
                } else {
                    view.setEnabled(false);
                    tvCell.setEnabled(false);
                    tvCell.setTextColor(getResources().getColor(R.color.greyed_out));
                    tvCell.setBackgroundResource(0);
                }
            }

            tvCell.setText(String.valueOf(date.getDate()));

            return view;
        }

        public void updateData(ArrayList<Date> cells)
        {
            this.cells = cells;
            notifyDataSetChanged();;
        }
    }

}