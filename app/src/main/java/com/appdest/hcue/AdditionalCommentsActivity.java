package com.appdest.hcue;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdest.hcue.common.AppConstants;

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
    @Override
    public void initializeControls()
    {
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

        slide_up 	= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slide_down  = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

    }

    @Override
    public void bindControls()
    {
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
        switch (v.getId())
        {
            case R.id.btnSubmit:
                Intent intent = new Intent(AdditionalCommentsActivity.this,EnterMailActivity.class);
                startActivity(intent);
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

        }

    }
}
