package com.appdest.hcue;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdest.hcue.common.AppConstants;


public class SelectDoctorActivity extends BaseActivity
{

	private LinearLayout llMain;
	private TextView tvHeading;
	private GridView gridView = null;
	private Button btnBookAppointment,btnCancelAppointment;
	private ImageView ivLeft,ivRight;
	
	@Override
	public void initializeControls() 
	{
		llMain = (LinearLayout) inflater.inflate(R.layout.select_doctor_grid, null);
		
		llBody.addView(llMain);
		
		tvHeading  				= (TextView)	llMain.findViewById(R.id.tvHeading);

		ivLeft  				= (ImageView)	llMain.findViewById(R.id.ivLeft);
		ivRight  				= (ImageView)	llMain.findViewById(R.id.ivRight);

		btnBookAppointment 		= (Button)		llMain.findViewById(R.id.btnBookAppointment);
		btnCancelAppointment 	= (Button)		llMain.findViewById(R.id.btnCancelAppointment);
		
		tvBack.setVisibility(View.GONE);
		ivLike.setVisibility(View.GONE);
		ivHome.setVisibility(View.GONE);
		
		
		gridView = (GridView) llMain.findViewById(R.id.gridView1);
		
		setSpecificTypeFace(llMain, AppConstants.WALSHEIM_MEDIUM);
		tvHeading.setTypeface(AppConstants.WALSHEIM_LIGHT);
		
		tvTitle.setText("Welcome to VHSL PhysioPoint");
		tvTitle.setTypeface(AppConstants.MYRAIDPRO_REGULAR);
	}

	@Override
	public void bindControls() 
	{
		gridView.setAdapter(new GridAdapter());
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) {

			}

		});

		btnBookAppointment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SelectDoctorActivity.this, EnterContactNumberActivity.class);
				startActivity(intent);
			}
		});
		btnCancelAppointment.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(SelectDoctorActivity.this,CancelAppointmentActivity.class);
				startActivity(intent);
			}
		});
		
	}

	
	
	private class GridAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return 12;
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
				view = LayoutInflater.from(SelectDoctorActivity.this).inflate(R.layout.grid_cell,null);
				holder = new ViewHolder();
				holder.tvDoctorName = (TextView) view.findViewById(R.id.tvDoctorName);
				holder.tvDesignation = (TextView) view.findViewById(R.id.tvDesignation);
				holder.tvSpecality = (TextView) view.findViewById(R.id.tvSpecality);
				holder.tvStatus		=	(TextView)view.findViewById(R.id.tvStatus);
				holder.iv = (ImageView) view.findViewById(R.id.imageView);
				
				holder.tvDoctorName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
				holder.tvDesignation.setTypeface(AppConstants.WALSHEIM_MEDIUM);
				holder.tvSpecality.setTypeface(AppConstants.WALSHEIM_MEDIUM);
				holder.tvStatus.setTypeface(AppConstants.WALSHEIM_MEDIUM);

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.tvDoctorName.setText("Dr.P. Venkatakrishna");
			holder.tvDesignation.setText("BPT");
			holder.tvSpecality.setText("Physiotherapist");

			return view;
		}

	}

	static class ViewHolder {
		TextView tvDoctorName, tvDesignation, tvSpecality,tvStatus;
		ImageView iv;
	}
	
}
