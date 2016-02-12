package com.appdest.hcue;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdest.hcue.common.AppConstants;

public class SelectDoctorForFeedbackActivity extends BaseActivity
{

	private LinearLayout llMain;
	private TextView tvHeading;
	private GridView gridView = null;
	
	@Override
	public void initializeControls() 
	{
		llMain = (LinearLayout) inflater.inflate(R.layout.select_doctor_grid, null);
		
		llBody.addView(llMain);
		
		tvHeading  = (TextView)llMain.findViewById(R.id.tvHeading);
		
		tvBack.setVisibility(View.GONE);
		tvHome.setVisibility(View.GONE);
		
		
		gridView = (GridView) findViewById(R.id.gridView1);
		
		setSpecificTypeFace(llMain, AppConstants.WALSHEIM_MEDIUM);
		tvHeading.setTypeface(AppConstants.WALSHEIM_LIGHT);
		
		tvTitle.setText("Thanks for using hCue");
		tvTitle.setTypeface(AppConstants.MYRAIDPRO_REGULAR);
	}

	@Override
	public void bindControls() 
	{
		gridView.setAdapter(new GridAdapter());
		
		gridView.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int pos,long arg3) 
			{
				Intent intent = new Intent(SelectDoctorForFeedbackActivity.this,FeedbackActivity.class);
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
				view = LayoutInflater.from(SelectDoctorForFeedbackActivity.this).inflate(R.layout.grid_cell,null);
				holder = new ViewHolder();
				holder.tvDoctorName = (TextView) view.findViewById(R.id.tvDoctorName);
//				holder.tvDesignation = (TextView) view.findViewById(R.id.tvDesignation);
				holder.tvSpecality = (TextView) view.findViewById(R.id.tvSpecality);
				holder.iv = (ImageView) view.findViewById(R.id.imageView);
				
				holder.tvDoctorName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
//				holder.tvDesignation.setTypeface(AppConstants.WALSHEIM_MEDIUM);
				holder.tvSpecality.setTypeface(AppConstants.WALSHEIM_MEDIUM);

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.tvDoctorName.setText("Dr.P. Venkatakrishna");
//			holder.tvDesignation.setText("BPT");
			holder.tvSpecality.setText("Physiotherapist");

			return view;
		}

	}

	static class ViewHolder {
		TextView tvDoctorName, /*tvDesignation,*/ tvSpecality;
		ImageView iv;
	}

}
