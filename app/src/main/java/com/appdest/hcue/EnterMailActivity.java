package com.appdest.hcue;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.DoctorsAppointmentResponse;
import com.appdest.hcue.model.GetDoctorsResponse;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EnterMailActivity extends BaseActivity implements OnClickListener
{
	private LinearLayout llEmail,llKeyboard,llSpecilaKeyboard;
	private EditText edtEmail;
	private Button btnConfirm,btnSkip,btnDel;
	private InputMethodManager im;
	private Handler h;
	private Animation slide_up, slide_down;
	private View focusedView;
	boolean isActivityNeedsFinish = false;
    private DoctorsAppointmentResponse bookingDetails;
    private GetDoctorsResponse.DoctorDetail selectedDoctorDetails;


	@Override
	public void initializeControls() {
        llEmail = (LinearLayout) inflater.inflate(R.layout.enter_mail, null);
        llBody.addView(llEmail);
        edtEmail = (EditText) llEmail.findViewById(R.id.edtEmail);
        btnConfirm = (Button) llEmail.findViewById(R.id.btnConfirm);
        btnSkip = (Button) llEmail.findViewById(R.id.btnSkip);
        llKeyboard          = (LinearLayout) llEmail.findViewById(R.id.llKeyBoard);
        llSpecilaKeyboard   = (LinearLayout) llEmail.findViewById(R.id.llSpecialKeyBoard);
        btnDel				= (Button)       llEmail.findViewById(R.id.btnDel);

        llKeyboard.setVisibility(View.VISIBLE);
        llSpecilaKeyboard.setVisibility(View.GONE);

        btnConfirm.setOnClickListener(this);
        btnSkip.setOnClickListener(this);

        setSpecificTypeFace(llEmail, AppConstants.WALSHEIM_LIGHT);
        btnConfirm.setTypeface(AppConstants.WALSHEIM_MEDIUM);
        btnSkip.setTypeface(AppConstants.WALSHEIM_MEDIUM);

        tvTitle.setText("Enter Email Address");

        slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
    }
	public void bindControls()
	{
        Intent i = getIntent();
        if(!i.hasExtra("BookingDetails") || !i.hasExtra("DoctorDetails"))
        {
            isActivityNeedsFinish = true;
            finish();
            return;
        }
        selectedDoctorDetails = (GetDoctorsResponse.DoctorDetail) i.getSerializableExtra("DoctorDetails");
        bookingDetails = (DoctorsAppointmentResponse) i.getSerializableExtra("BookingDetails");
        tvLogin.setEnabled(false);
        if(isActivityNeedsFinish)
            return;
        h = new Handler(Looper.getMainLooper());

        edtEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(final View v, boolean hasFocus) {
                hideKeyBoard(v);
                h.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        hideKeyBoard(v);
                    }
                }, 50);
                if (hasFocus) {
                    if (llKeyboard.getVisibility() == View.GONE)
                        llSpecilaKeyboard.setVisibility(View.GONE);
                    llKeyboard.setVisibility(View.VISIBLE);
                }
            }
        });

        btnDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (edtEmail != null)
                    edtEmail.setText("");
                return false;
            }
        });

        hideKeyBoard(edtEmail);

        edtEmail.clearFocus();

        edtEmail.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                edtEmail.clearFocus();
                hideKeyBoard(v);
                return false;
            }
        });
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
                ((EditText)focusedView).requestFocusFromTouch();
               // ((EditText)focusedView).setSelection(((EditText)focusedView).requestFocusFromTouch());
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        h.postDelayed(new Runnable() {

            @Override
            public void run() {
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
		switch(v.getId())
		{
            case R.id.edtEmail:
                v.requestFocus();
                hideKeyBoard(v);
                h.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        hideKeyBoard(v);
                    }
                }, 50);


                if(llKeyboard.getVisibility() == View.GONE)
                    llSpecilaKeyboard.setVisibility(View.GONE);

                llKeyboard.setVisibility(View.VISIBLE);
                break;

			case R.id.btnConfirm:
                if(edtEmail.getText().toString().isEmpty())
                {
                    Toast.makeText(EnterMailActivity.this,"Email address should not be empty.",Toast.LENGTH_LONG).show();
                }else {
                    if (validateEmailAddress(edtEmail.getText().toString())) {
                        Intent intent = new Intent(EnterMailActivity.this, EnterAddressActivity.class);
                        intent.putExtra("BookingDetails", bookingDetails);
                        intent.putExtra("DoctorDetails", selectedDoctorDetails);
                        startActivity(intent);
                    } else
                    {
                        Toast.makeText(EnterMailActivity.this,"Please enter a valid Email address.",Toast.LENGTH_LONG).show();
                    }
                }
				break;
			case R.id.btnSkip:
				Intent skip = new Intent(EnterMailActivity.this,SelectDoctorActivity.class);
				skip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(skip);
				break;

		}
	}

    private boolean validateEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();

    }

}
