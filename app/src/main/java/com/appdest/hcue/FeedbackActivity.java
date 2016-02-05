package com.appdest.hcue;

import com.appdest.hcue.common.AppConstants;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FeedbackActivity extends BaseActivity implements OnClickListener
{
	private LinearLayout llFeedback;
	private ImageView ivImage,ivExcellent,ivGood,ivAverage,ivPoor;
	private TextView tvName,tvSpecality,tvAdditionalComments;
	private Button btnDone;
	private EditText edtFeedback;

	@Override
	public void initializeControls() 
	{
		llFeedback = (LinearLayout) inflater.inflate(R.layout.feedback, null);
		
		llBody.addView(llFeedback);
		
		
		tvBack.setText("Previous Page");
		
		ivImage 				= 	(ImageView)	llFeedback.findViewById(R.id.ivImage);
		ivExcellent 			= 	(ImageView)	llFeedback.findViewById(R.id.ivExcellent);
		ivGood 					= 	(ImageView)	llFeedback.findViewById(R.id.ivGood);
		ivAverage 				= 	(ImageView)	llFeedback.findViewById(R.id.ivAverage);
		ivPoor 					= 	(ImageView)	llFeedback.findViewById(R.id.ivPoor);

		tvName 					= 	(TextView) 	llFeedback.findViewById(R.id.tvName);
		tvSpecality				= 	(TextView) 	llFeedback.findViewById(R.id.tvSpecality);
		tvAdditionalComments	= 	(TextView) 	llFeedback.findViewById(R.id.tvAdditionalComments);

		btnDone 				= 	(Button) 	llFeedback.findViewById(R.id.btnDone);
		
		setSpecificTypeFace(llFeedback, AppConstants.WALSHEIM_LIGHT);
		
		tvName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		btnDone.setTypeface(AppConstants.WALSHEIM_BOLD);
		tvName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		
		tvTitle.setText("Thanks for using hCue");

		ivExcellent.setOnClickListener(this);
		ivGood.setOnClickListener(this);
		ivAverage.setOnClickListener(this);
		ivPoor.setOnClickListener(this);
		btnDone.setOnClickListener(this);
		tvAdditionalComments.setOnClickListener(this);

		ivExcellent.setTag("0");
		ivGood.setTag("0");
		ivAverage.setTag("0");
		ivPoor.setTag("0");
	}

	@Override
	public void bindControls() 
	{
		
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId())
		{
			case R.id.ivExcellent:
				if(ivExcellent.getTag().equals("0"))
				{
					ivExcellent.setTag("1");
					ivExcellent.setBackgroundResource(R.drawable.excellent_hov);
					ivAverage.setBackgroundResource(R.drawable.average);
					ivPoor.setBackgroundResource(R.drawable.poor);
					ivGood.setBackgroundResource(R.drawable.good);


				}
				else
				{
					ivExcellent.setTag("0");
					ivExcellent.setBackgroundResource(R.drawable.exalant_icon);

				}
				break;
			case R.id.ivGood:
				if(ivGood.getTag().equals("0"))
				{
					ivGood.setTag("1");
					ivGood.setBackgroundResource(R.drawable.good_hov);
					ivAverage.setBackgroundResource(R.drawable.average);
					ivPoor.setBackgroundResource(R.drawable.poor);
					ivExcellent.setBackgroundResource(R.drawable.exalant_icon);

				}
				else
				{
					ivGood.setTag("0");
					ivGood.setBackgroundResource(R.drawable.good);

				}
				break;
			case R.id.ivAverage:
				if(ivAverage.getTag().equals("0"))
				{
					ivAverage.setTag("1");
					ivAverage.setBackgroundResource(R.drawable.average_hov);
					ivPoor.setBackgroundResource(R.drawable.poor);
					ivGood.setBackgroundResource(R.drawable.good);
					ivExcellent.setBackgroundResource(R.drawable.exalant_icon);

				}
				else
				{
					ivAverage.setTag("0");
					ivAverage.setBackgroundResource(R.drawable.average);

				}
				break;
			case R.id.ivPoor:
				if(ivPoor.getTag().equals("0"))
				{
					ivPoor.setTag("1");
					ivPoor.setBackgroundResource(R.drawable.poor_hov);
					ivAverage.setBackgroundResource(R.drawable.average);
					ivGood.setBackgroundResource(R.drawable.good);
					ivExcellent.setBackgroundResource(R.drawable.exalant_icon);

				}
				else
				{
					ivPoor.setTag("0");
					ivPoor.setBackgroundResource(R.drawable.poor);

				}
				break;
			case R.id.btnDone:
				Intent intent = new Intent(FeedbackActivity.this,SelectDoctorActivity.class);
				startActivity(intent);
				break;
			case R.id.tvAdditionalComments:
				Intent ac = new Intent(FeedbackActivity.this,AdditionalCommentsActivity.class);
				startActivity(ac);
				break;
		}
	}

}
