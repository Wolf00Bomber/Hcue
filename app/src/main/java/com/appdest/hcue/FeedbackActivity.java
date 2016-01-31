package com.appdest.hcue;

import com.appdest.hcue.common.AppConstants;

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
				break;
			case R.id.ivGood:
				break;
			case R.id.ivAverage:
				break;
			case R.id.ivPoor:
				break;
			case R.id.btnDone:
				break;
			case R.id.tvAdditionalComments:
				break;
		}
	}

}
