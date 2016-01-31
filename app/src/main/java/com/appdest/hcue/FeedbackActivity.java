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
	private ImageView ivImage;
	private TextView tvName,tvSpecality;
	private Button btnSubmit;
	private EditText edtFeedback;

	@Override
	public void initializeControls() 
	{
		llFeedback = (LinearLayout) inflater.inflate(R.layout.feedback, null);
		
		llBody.addView(llFeedback);
		
		
		tvBack.setVisibility(View.GONE);

		ivHome.setVisibility(View.GONE);
		
		ivImage 	= 	(ImageView)	llFeedback.findViewById(R.id.ivImage);

		tvName 		= 	(TextView) 	llFeedback.findViewById(R.id.tvName);
		tvSpecality	= 	(TextView) 	llFeedback.findViewById(R.id.tvSpecality);
		btnSubmit 	= 	(Button) 	llFeedback.findViewById(R.id.btnSubmit);
		
		setSpecificTypeFace(llFeedback, AppConstants.WALSHEIM_LIGHT);
		
		tvName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		btnSubmit.setTypeface(AppConstants.WALSHEIM_BOLD);
		tvName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		
		tvTitle.setText("Thanks for using hCue");
	}

	@Override
	public void bindControls() 
	{
		
	}

	@Override
	public void onClick(View v) 
	{
		
	}

}
