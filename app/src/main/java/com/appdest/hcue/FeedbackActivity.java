package com.appdest.hcue;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.FeedbackRequest;
import com.appdest.hcue.model.GetDoctors;
import com.appdest.hcue.model.GetDoctorsResponse;
import com.appdest.hcue.model.Speciality;
import com.appdest.hcue.services.RestCallback;
import com.appdest.hcue.services.RestClient;
import com.appdest.hcue.services.RestError;
import com.appdest.hcue.utils.Connectivity;
import com.appdest.hcue.utils.Preference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit.client.Response;

public class FeedbackActivity extends BaseActivity implements OnClickListener
{
	private LinearLayout llFeedback;
	private ImageView ivImage,ivExcellent,ivGood,ivAverage,ivPoor;
	private TextView tvDoctorName, tvSpecality, tvAdditionalComments;
	private Button btnDone;
	private EditText edtFeedback;

    /** Data required for Feedback*/
    private FeedbackRequest feedbackRequest;
    private Number AppointmentID, PatientID, UserID;
    private int DoctorID;
    private String RatingComment;
    private float StarValue = 0f;
    private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;
    boolean isActivityNeedsFinish = false;
    private final float MAX_VALUE = 10f;
    private HashMap<String,Speciality> hmSpecialities;

	@Override
	public void initializeControls() 
	{
        Intent i = getIntent();
        if(i.hasExtra("FeedbackRequest") && i.hasExtra("DoctorDetails"))
        {
            feedbackRequest = (FeedbackRequest) i.getSerializableExtra("FeedbackRequest");
            selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail)i.getSerializableExtra("DoctorDetails");
        }
        else
        {
            isActivityNeedsFinish = true;
            finish();
            return;
        }

		llFeedback = (LinearLayout) inflater.inflate(R.layout.feedback, null);
		
		llBody.addView(llFeedback);
		
		
		tvBack.setText("Previous Page");
		
		ivImage 				= 	(ImageView)	llFeedback.findViewById(R.id.ivImage);
		ivExcellent 			= 	(ImageView)	llFeedback.findViewById(R.id.ivExcellent);
		ivGood 					= 	(ImageView)	llFeedback.findViewById(R.id.ivGood);
		ivAverage 				= 	(ImageView)	llFeedback.findViewById(R.id.ivAverage);
		ivPoor 					= 	(ImageView)	llFeedback.findViewById(R.id.ivPoor);

		tvDoctorName 					= 	(TextView) 	llFeedback.findViewById(R.id.tvDoctorName);
		tvSpecality				= 	(TextView) 	llFeedback.findViewById(R.id.tvSpecality);
		tvAdditionalComments	= 	(TextView) 	llFeedback.findViewById(R.id.tvAdditionalComments);

		btnDone 				= 	(Button) 	llFeedback.findViewById(R.id.btnDone);
		
		setSpecificTypeFace(llFeedback, AppConstants.WALSHEIM_LIGHT);
		
		tvDoctorName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		btnDone.setTypeface(AppConstants.WALSHEIM_MEDIUM);

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

    private void initMap()
    {
        Preference preference = new Preference(FeedbackActivity.this);
        String specialitiesInString = preference.getStringFromPreference(Preference.SPECIALITIES_MAP, "");
        if(!TextUtils.isEmpty(specialitiesInString))
        {
            Gson gson = new Gson();
            ArrayList<Speciality> list = gson.fromJson(specialitiesInString, new TypeToken<List<Speciality>>(){}.getType());
            Collections.sort(list);
            hmSpecialities = new HashMap<>();
            for (Speciality speciality : list) {
                hmSpecialities.put(speciality.DoctorSpecialityID, speciality);
            }
        }
    }

	@Override
	public void bindControls() 
	{
		tvLogin.setEnabled(false);
        if(isActivityNeedsFinish)
            return;
        initMap();
		tvDoctorName.setText(selectedDoctorDetails.FullName);
        if (hmSpecialities != null && hmSpecialities.size() > 0) {
            ArrayList<String> list = new ArrayList<>(selectedDoctorDetails.specialityCD.values());
            for (int i = 0; i < list.size(); i++) {
                list.set(i, hmSpecialities.get(list.get(i)).DoctorSpecialityDesc);
            }
            tvSpecality.setText(TextUtils.join(",", list) /*"Physiotherapist"*/);
        }
        Drawable d = getResources().getDrawable(R.drawable.profile_doctor_bg_male);
        int mHeight = d.getIntrinsicHeight();
        int mWidth = d.getIntrinsicWidth();
        if (!TextUtils.isEmpty(selectedDoctorDetails.ImageURL))
            Picasso.with(context)
                    .load(selectedDoctorDetails.ImageURL)
                    .resize(mWidth, mHeight)
                    .placeholder(R.drawable.profile_doctor_bg_male)
                    .centerInside()
                    .into(ivImage);
	}

	private void callService(FeedbackRequest feedbackRequest)
	{
		if (Connectivity.isConnected(FeedbackActivity.this)) {
			sendFeedback(feedbackRequest);
		} else {
			Toast.makeText(FeedbackActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
		}
	}

	private void sendFeedback(FeedbackRequest feedbackRequest)
	{
        feedbackRequest.setRatingComments("N");
		String url = "http://d1lmwj8jm5d3bc.cloudfront.net";
		RestClient.getAPI(url).postFeedback(feedbackRequest, new RestCallback<String>() {
            @Override
            public void failure(RestError restError) {
                Toast.makeText(FeedbackActivity.this, "Couldn't post the feedback", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(String feedbackResponse, Response response) {
               // Toast.makeText(FeedbackActivity.this, String.valueOf(feedbackResponse), Toast.LENGTH_LONG).show();
                Intent FeedbackIntent = new Intent(FeedbackActivity.this, FeedbackConfirmationActivity.class);
                FeedbackIntent.putExtra("DoctorDetails", selectedDoctorDetails);
                startActivity(FeedbackIntent);
				finish();
            }
        });
    }



	@Override
	public void onClick(View v) 
	{
		switch (v.getId())
		{
			case R.id.ivExcellent:

				if("0".equals(ivExcellent.getTag()))
				{
					resetTags();
					ivExcellent.setTag("1");
					ivExcellent.setBackgroundResource(R.drawable.excellent_hov);
					ivAverage.setBackgroundResource(R.drawable.average);
					ivPoor.setBackgroundResource(R.drawable.poor);
					ivGood.setBackgroundResource(R.drawable.good);
                    StarValue = (1f) * MAX_VALUE;
				}
				else
				{
					ivExcellent.setTag("0");
					ivExcellent.setBackgroundResource(R.drawable.exalant_icon);

				}
				break;
			case R.id.ivGood:
				if("0".equals(ivGood.getTag()))
				{
					resetTags();
					ivGood.setTag("1");
					ivGood.setBackgroundResource(R.drawable.good_hov);
					ivAverage.setBackgroundResource(R.drawable.average);
					ivPoor.setBackgroundResource(R.drawable.poor);
					ivExcellent.setBackgroundResource(R.drawable.exalant_icon);
                    StarValue = (3f/4f) * MAX_VALUE;
				}
				else
				{
					ivGood.setTag("0");
					ivGood.setBackgroundResource(R.drawable.good);
				}
				break;
			case R.id.ivAverage:
				if("0".equals(ivAverage.getTag()))
				{
					resetTags();
					ivAverage.setTag("1");
					ivAverage.setBackgroundResource(R.drawable.average_hov);
					ivPoor.setBackgroundResource(R.drawable.poor);
					ivGood.setBackgroundResource(R.drawable.good);
					ivExcellent.setBackgroundResource(R.drawable.exalant_icon);
                    StarValue = (2f/4f) * MAX_VALUE;

				}
				else
				{
					ivAverage.setTag("0");
					ivAverage.setBackgroundResource(R.drawable.average);

				}
				break;
			case R.id.ivPoor:
				if("0".equals(ivPoor.getTag()))
				{
					resetTags();
					ivPoor.setTag("1");
					ivPoor.setBackgroundResource(R.drawable.poor_hov);
					ivAverage.setBackgroundResource(R.drawable.average);
					ivGood.setBackgroundResource(R.drawable.good);
					ivExcellent.setBackgroundResource(R.drawable.exalant_icon);
                    StarValue = (1f/4f) * MAX_VALUE;
				}
				else
				{
					ivPoor.setTag("0");
					ivPoor.setBackgroundResource(R.drawable.poor);

				}
				break;
			case R.id.btnDone:
                if(StarValue == 0f)
                {
                    Toast.makeText(context, "Please select a rating!", Toast.LENGTH_SHORT).show();
                    return;
                }
                feedbackRequest.setStarValue(StarValue);
                callService(feedbackRequest);
				break;
			case R.id.tvAdditionalComments:
                if(StarValue == 0f)
                {
                    Toast.makeText(context, "Please select a rating!", Toast.LENGTH_SHORT).show();
                    return;
                }
                feedbackRequest.setStarValue(StarValue);
				Intent ac = new Intent(FeedbackActivity.this,AdditionalCommentsActivity.class);
                ac.putExtra("DoctorDetails", selectedDoctorDetails);
                ac.putExtra("FeedbackRequest", feedbackRequest);
				startActivity(ac);
				break;
		}
	}

	private void resetTags()
	{
		ivExcellent.setTag("0");
		ivGood.setTag("0");
		ivAverage.setTag("0");
		ivPoor.setTag("0");
	}

}
