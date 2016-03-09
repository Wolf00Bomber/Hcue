package com.hCue.Kiosk;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hCue.Kiosk.common.AppConstants;
import com.hCue.Kiosk.model.FeedbackRequest;
import com.hCue.Kiosk.model.GetDoctorsResponse;
import com.hCue.Kiosk.services.RestCallback;
import com.hCue.Kiosk.services.RestClient;
import com.hCue.Kiosk.services.RestError;
import com.hCue.Kiosk.utils.Connectivity;

import retrofit.client.Response;

/**
 * Created by shyamprasadg on 02/02/16.
 */
public class AdditionalCommentsActivity extends BaseActivity implements View.OnClickListener
{
    private LinearLayout llComments,llKeyboard,llSpecilaKeyboard;
    private EditText edtEnterComments;
    private Button btnSubmit,btnCancel;
    private TextView tvDoctorName;
    private View focusedView;
    private InputMethodManager im;
    private Handler h;
    private Animation slide_up, slide_down;
    boolean isActivityNeedsFinish = false;

    private FeedbackRequest feedbackRequest;
    private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;
    private float Starvalue = 2.5f;

    @Override
    public void initializeControls()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent i = getIntent();
        if(i.hasExtra("FeedbackRequest") && i.hasExtra("DoctorDetails"))
        {
            feedbackRequest = (FeedbackRequest) i.getSerializableExtra("FeedbackRequest");
            selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail)i.getSerializableExtra("DoctorDetails");
            Starvalue =  i.getFloatExtra("StarValue",2.5f);
        }
        else
        {
            isActivityNeedsFinish = true;
            finish();
            return;
        }

        llComments = (LinearLayout) inflater.inflate(R.layout.additional_comments, null);

        llBody.addView(llComments);

        edtEnterComments    =   (EditText)  llComments.findViewById(R.id.edtEnterComments);

        btnSubmit           =   (Button)    llComments.findViewById(R.id.btnSubmit);
        btnCancel           =   (Button)    llComments.findViewById(R.id.btnCancel);

        tvDoctorName        =   (TextView)  llComments.findViewById(R.id.tvDoctorName);

        llKeyboard          =   (LinearLayout) llComments.findViewById(R.id.llKeyBoard);
        llSpecilaKeyboard   =   (LinearLayout) llComments.findViewById(R.id.llSpecialKeyBoard);

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        llKeyboard.setVisibility(View.GONE);
        llSpecilaKeyboard.setVisibility(View.GONE);
        setSpecificTypeFace(llComments, AppConstants.WALSHEIM_LIGHT);
        tvDoctorName.setTypeface(AppConstants.WALSHEIM_MEDIUM);
        btnSubmit.setTypeface(AppConstants.WALSHEIM_MEDIUM);
        btnCancel.setTypeface(AppConstants.WALSHEIM_MEDIUM);

        tvTitle.setText("Thanks for using hCue");
        tvDoctorName.setText("Dr. "+selectedDoctorDetails.FullName);

        slide_up 	= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slide_down  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);


    }

    @Override
    public void bindControls()
    {
        tvLogin.setEnabled(false);
        if(isActivityNeedsFinish)
            return;
        h = new Handler(Looper.getMainLooper());

        edtEnterComments.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(final View v, boolean hasFocus) {
                hideKeyBoard(v);

                if (hasFocus) {

                    if (llKeyboard.getVisibility() == View.GONE)
                        llSpecilaKeyboard.setVisibility(View.GONE);

                    llKeyboard.setVisibility(View.VISIBLE);

                }
                h.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        hideKeyBoard(v);
                    }
                }, 50);
            }
        });
        edtEnterComments.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyBoard(v);
                return false;
            }
        });

        hideKeyBoard(edtEnterComments);

        edtEnterComments.clearFocus();

    }
    public void keyboardClick(View v)
    {
        hideKeyBoard(v);
        focusedView = getCurrentFocus();

        Button button = (Button)v;

        if(focusedView != null && focusedView instanceof EditText)
        {
            String str = ((EditText)focusedView).getText().toString();

            if(button.getText().toString().equalsIgnoreCase("123"))
            {
                if(llSpecilaKeyboard.getVisibility() == View.GONE)
                {
                    llKeyboard.startAnimation(slide_down);
                    llKeyboard.setVisibility(View.GONE);
                    llSpecilaKeyboard.setVisibility(View.VISIBLE);
                    llSpecilaKeyboard.startAnimation(slide_up);
                }
            }
            else if(button.getText().toString().equalsIgnoreCase("ABC"))
            {
                if(llKeyboard.getVisibility() == View.GONE)
                {
                    llSpecilaKeyboard.startAnimation(slide_down);
                    llSpecilaKeyboard.setVisibility(View.GONE);
                    llKeyboard.setVisibility(View.VISIBLE);
                    llKeyboard.startAnimation(slide_up);
                }
            }
            else if(button.getText().toString().equalsIgnoreCase("DEL"))
            {
                if(str.length()>0)
                {
                    str = str.substring(0, str.length()-1);
                    ((EditText)focusedView).setText(str);
                    ((EditText)focusedView).setSelection(((EditText)focusedView).length());
                }
            }
            else if(button.getText().toString().equalsIgnoreCase("SPACE"))
            {
                str = str + " ";
                ((EditText)focusedView).setText(str);
                ((EditText)focusedView).setSelection(((EditText) focusedView).length());
            }

            else if(button.getText().toString().equalsIgnoreCase("Done"))
            {
                if(llKeyboard.getVisibility() == View.VISIBLE)
                    llKeyboard.startAnimation(slide_down);

                llKeyboard.setVisibility(View.GONE);
            }
            else
            {
                str = str + button.getText().toString() ;
                ((EditText)focusedView).setText(str);
                ((EditText)focusedView).setSelection(((EditText)focusedView).length());
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideKeyBoard(edtEnterComments);
        h.postDelayed(new Runnable() {

            @Override
            public void run() {
                hideKeyBoard(edtEnterComments);
                llKeyboard.setVisibility(View.VISIBLE);
                llSpecilaKeyboard.setVisibility(View.GONE);
            }
        }, 50);
    }

    public void hideKeyBoard(View view)
    {
        im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onClick(final View v)
    {
        switch (v.getId())
        {
            case R.id.btnSubmit:
                if(TextUtils.isEmpty(edtEnterComments.getText().toString().trim()))
                {
                    Toast.makeText(AdditionalCommentsActivity.this, "Please enter a comment !", Toast.LENGTH_SHORT).show();
                    return;
                }
                callService(feedbackRequest);
                break;

            case R.id.edtEnterComments:
                v.requestFocus();
                hideKeyBoard(v);

                if(llKeyboard.getVisibility() == View.GONE)
                    llSpecilaKeyboard.setVisibility(View.GONE);
                    llKeyboard.setVisibility(View.VISIBLE);

                h.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        hideKeyBoard(v);
                    }
                }, 30);

                break;
            case R.id.btnCancel:
                finish();
                break;
        }
    }

    private void callService(FeedbackRequest feedbackRequest)
    {
        if (Connectivity.isConnected(AdditionalCommentsActivity.this)) {
            sendFeedback(feedbackRequest);
        } else {
            Toast.makeText(AdditionalCommentsActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendFeedback(FeedbackRequest feedbackRequest)
    {
        feedbackRequest.setRatingComments(edtEnterComments.getText().toString());
        feedbackRequest.setStarValue(Starvalue);
        String url = "http://d1lmwj8jm5d3bc.cloudfront.net";
        RestClient.getAPI(url).postFeedback(feedbackRequest, new RestCallback<String>() {
            @Override
            public void failure(RestError restError) {
                Toast.makeText(AdditionalCommentsActivity.this, "Couldn't post the feedback", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void success(String feedbackResponse, Response response) {
               // Toast.makeText(AdditionalCommentsActivity.this, String.valueOf(feedbackResponse), Toast.LENGTH_LONG).show();
                Intent FeedbackIntent = new Intent(AdditionalCommentsActivity.this, FeedbackConfirmationActivity.class);
                startActivity(FeedbackIntent);
                finish();
            }
        });
    }
}
