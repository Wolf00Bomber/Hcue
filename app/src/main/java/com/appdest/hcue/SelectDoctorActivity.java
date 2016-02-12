package com.appdest.hcue;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
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
import android.widget.Toast;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.GetHospitalsRequest;
import com.appdest.hcue.model.GetHospitalsResponse;
import com.appdest.hcue.services.RestCallback;
import com.appdest.hcue.services.RestClient;
import com.appdest.hcue.services.RestError;
import com.appdest.hcue.utils.Connectivity;
import com.appdest.hcue.utils.TimeUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit.client.Response;


public class SelectDoctorActivity extends BaseActivity
{

	private LinearLayout llMain;
	private TextView tvHeading,tvRateYourVisit;
    private ViewPager mViewPager;
	private GridView gridView;
	private Button btnBookAppointment,btnCancelAppointment;
	private ImageView ivLeftTime,ivRightTime, ivLeft,ivRight;
	private GridAdapter gridAdapter;
    private int hospitalId;
    private GetHospitalsResponse.DoctorDetail selectedDoctorDetails;

	@Override
	public void initializeControls() 
	{
		llMain = (LinearLayout) inflater.inflate(R.layout.select_doctor_grid, null);
		
		llBody.addView(llMain);
		
		tvHeading  				= (TextView)	llMain.findViewById(R.id.tvHeading);
		tvRateYourVisit  		= (TextView)	llMain.findViewById(R.id.tvRateYourVisit);

        ivLeft  				= (ImageView)	llMain.findViewById(R.id.ivLeft);
        ivRight  				= (ImageView)	llMain.findViewById(R.id.ivRight);

		btnBookAppointment 		= (Button)		llMain.findViewById(R.id.btnBookAppointment);
		btnCancelAppointment 	= (Button)		llMain.findViewById(R.id.btnCancelAppointment);
		
		tvBack.setVisibility(View.GONE);
		tvHome.setVisibility(View.GONE);

		gridView = (GridView) llMain.findViewById(R.id.gridView1);
		setSpecificTypeFace(llMain, AppConstants.WALSHEIM_MEDIUM);
		tvHeading.setTypeface(AppConstants.WALSHEIM_LIGHT);
		
		tvTitle.setText("Welcome to VHSL PhysioPoint");
		tvTitle.setTypeface(AppConstants.MYRAIDPRO_REGULAR);

		if (Connectivity.isConnected(SelectDoctorActivity.this)) {
			getHospitalDetails();
		} else {
			Toast.makeText(SelectDoctorActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
		}
	}

	private void getHospitalDetails()
	{
		final GetHospitalsRequest getHospitalsRequest = new GetHospitalsRequest();
		getHospitalsRequest.setHospitalID(19);
		getHospitalsRequest.setPageNumber(1);
		getHospitalsRequest.setPageSize(6);

		String url = "http://d318m5cseah7np.cloudfront.net";

		RestClient.getAPI(url).getHospitalDetails(getHospitalsRequest, new RestCallback<GetHospitalsResponse>() {
			@Override
			public void failure(RestError restError) {
				Log.e("Doctor Appointement", "" + restError.getErrorMessage());
				Toast.makeText(SelectDoctorActivity.this, "Couldn't get the List of Doctors.", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void success(GetHospitalsResponse getHospitalsResponse, Response response) {
				if (getHospitalsResponse != null) {
					gridAdapter.refresh(getHospitalsResponse.hospitalInfo.arrDoctorDetails);
					tvTitle.setText("Welcome to " + getHospitalsResponse.hospitalInfo.hospitalInfoInner.hospitalDetails.HospitalName);
					hospitalId = getHospitalsResponse.hospitalInfo.hospitalInfoInner.hospitalDetails.HospitalID;
				} else {
					Log.i("Response", "" + response.getReason());
				}
			}
		});
	}

	@Override
	public void bindControls() 
	{
		gridAdapter = new GridAdapter();
		gridView.setAdapter(gridAdapter);
		gridView.setSelector(R.drawable.doctor);
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int pos, long arg3) {
                v.setSelected(true);
                selectedDoctorDetails = (GetHospitalsResponse.DoctorDetail) gridAdapter.getItem(pos);
            }

        });
		btnBookAppointment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                if(selectedDoctorDetails == null)
                {
                    Toast.makeText(context, "Please select a doctor!", Toast.LENGTH_SHORT).show();
                    return;
                }
				Intent intent = new Intent(SelectDoctorActivity.this, EnterContactNumberActivity.class);
                intent.putExtra("DoctorDetails", selectedDoctorDetails);
				startActivity(intent);
			}
		});
		btnCancelAppointment.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(selectedDoctorDetails == null)
				{
					Toast.makeText(context, "Please select a doctor!", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent(SelectDoctorActivity.this,CancelAppointmentActivity.class);
				intent.putExtra("DoctorDetails", selectedDoctorDetails);
				startActivity(intent);
			}
		});
		tvRateYourVisit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(SelectDoctorActivity.this,FeedbackActivity.class);
				startActivity(intent);
			}
		});
		
	}

	
	
	private class GridAdapter extends BaseAdapter {

        private int mHeight, mWidth;
		private ArrayList<GetHospitalsResponse.DoctorDetail> doctorDetails;

		public void refresh(ArrayList<GetHospitalsResponse.DoctorDetail> doctorDetails)
		{
			this.doctorDetails = doctorDetails;
			notifyDataSetChanged();
            Drawable d = getResources().getDrawable(R.drawable.doctor_img_bg);
            mHeight = d.getIntrinsicHeight();
            mWidth = d.getIntrinsicWidth();
		}

		@Override
		public int getCount() {
			if(doctorDetails != null && doctorDetails.size() > 0)
				return doctorDetails.size();
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			return doctorDetails.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int pos, View view, ViewGroup parent) {
			ViewHolder holder;
			if (view == null) {
				view = LayoutInflater.from(SelectDoctorActivity.this).inflate(R.layout.grid_cell,null);
				holder = new ViewHolder();
				holder.tvDoctorName = (TextView) view.findViewById(R.id.tvDoctorName);
				holder.tvDesignation = (TextView) view.findViewById(R.id.tvDesignation);
				holder.tvSpecality = (TextView) view.findViewById(R.id.tvSpecality);
				holder.tvStatus		=	(TextView)view.findViewById(R.id.tvStatus);
				holder.iv = (ImageView) view.findViewById(R.id.imageView);
				
				holder.tvDoctorName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
				holder.tvDesignation.setTypeface(AppConstants.WALSHEIM_LIGHT);
				holder.tvSpecality.setTypeface(AppConstants.WALSHEIM_LIGHT);
				holder.tvStatus.setTypeface(AppConstants.WALSHEIM_LIGHT);

                holder.tvDoctorName.setTextColor(context.getResources().getColorStateList(R.color.text_pressed_doctors));
//                holder.tvDesignation.setTextColor(context.getResources().getColorStateList(R.color.text_pressed_doctors));
//                holder.tvSpecality.setTextColor(context.getResources().getColorStateList(R.color.text_pressed_doctors));
                holder.tvStatus.setTextColor(context.getResources().getColorStateList(R.color.green));

				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			GetHospitalsResponse.DoctorDetail doctorDetail = doctorDetails.get(pos);
            view.setTag(R.string.app_name, doctorDetail);
			holder.tvDoctorName.setText(doctorDetail.FullName/*"Dr.P. Venkatakrishna"*/);
			holder.tvDesignation.setText("Doctor Id : "+doctorDetail.DoctorID/*"BPT"*/);
			holder.tvSpecality.setText(TextUtils.join(",", doctorDetail.specialityCD.values()) /*"Physiotherapist"*/);
            if(!TextUtils.isEmpty(doctorDetail.ImageURL))
            Picasso.with(context)
                    .load(doctorDetail.ImageURL)
                    .resize(mWidth, mHeight)
                    .placeholder(R.drawable.doctor_img_bg)
                    .centerInside()
                    .into(holder.iv)
                    ;

            return view;
		}

	}

	static class ViewHolder {
		TextView tvDoctorName, tvDesignation, tvSpecality,tvStatus;
		ImageView iv;
	}
	
}
