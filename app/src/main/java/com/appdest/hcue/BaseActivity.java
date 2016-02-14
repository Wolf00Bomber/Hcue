package com.appdest.hcue;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appdest.hcue.common.AppConstants;

public abstract class BaseActivity extends Activity 
{
	
	public LinearLayout llTop, llBody, llBottom;
	public LayoutInflater inflater;
	public Context context;
	public LayoutParams layoutParams;
	public ProgressDialog progressdialog;
	public TextView tvBack,tvTitle,tvHome,tvLogin;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.base);
		initLocal();
		initializeControls();
		bindControls();

		tvHome.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(BaseActivity.this,SelectDoctorActivity.class);
				startActivity(intent);

			}
		});
		
		tvBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				
			}
		});

		tvLogin.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(BaseActivity.this,AdminLoginActivity.class);
				startActivity(intent);
			}
		});
	}

	private void initLocal() 
	{
		inflater 	= getLayoutInflater();
		layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		context = BaseActivity.this;
		
		llTop 		= 	(LinearLayout) 	findViewById(R.id.llTop);
		llBody 		= 	(LinearLayout) 	findViewById(R.id.llMiddle);
		llBottom 	= 	(LinearLayout) 	findViewById(R.id.llFooter);
		tvBack 		= 	(TextView) 		findViewById(R.id.tvBack);
		tvTitle 	= 	(TextView) 		findViewById(R.id.tvTitle);
		tvLogin		=	(TextView) 		findViewById(R.id.tvLogin);
		tvHome 		= 	(TextView) 		findViewById(R.id.tvHome);
		
//		tvTitle.setTypeface(AppConstants.WALSHEIM_MEDIUM);
//		tvBack.setTypeface(AppConstants.WALSHEIM_MEDIUM);
		setSpecificTypeFace(llBottom, AppConstants.WALSHEIM_MEDIUM);
		setSpecificTypeFace(llTop, AppConstants.WALSHEIM_MEDIUM);
		
	}
	
	/**Just Find Resource IDs in this method.*/
	public abstract void initializeControls();
	
	/**Do binding the controls(actual values) in this method.*/
	public abstract void bindControls();
	
	/**Method to show Toast messages*/
	public void showToast(String toast)
	{
		Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
	}
	
	public void showLoader(String str) {
		runOnUiThread(new RunShowLoader(str));
	}

	public void hideLoader()
	{
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(progressdialog != null && progressdialog.isShowing())
				{
					progressdialog.dismiss();
				}
			}
		});
	}
	
	/**
	 * Name:         RunShowLoader
	   Description:  This is to show the loading progress dialog when some other functionality is taking place.**/
	class RunShowLoader implements Runnable {
		private String strMsg;
		
		public RunShowLoader(String strMsg) {
			this.strMsg = strMsg;
		}
		
		@Override
		public void run() 
		{
			try {
				if(progressdialog == null ||(progressdialog != null && !progressdialog.isShowing())) {
					progressdialog = ProgressDialog.show(BaseActivity.this, "", strMsg);
//					progressdialog.setContentView(R.layout.progress_dialog);
					progressdialog.setCancelable(false);
				} else {
				}
			} catch(Exception e) {
				e.printStackTrace();
				progressdialog = null;
			}
		}
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
		if(progressdialog != null && progressdialog.isShowing())
		{
			progressdialog.dismiss();
		}
	}
	
	public static void setSpecificTypeFace(ViewGroup group, Typeface typeface)
    {
        int count = group.getChildCount();
        View v;
        for(int i = 0; i < count; i++) {
            v = group.getChildAt(i);
            if(v instanceof TextView || v instanceof Button || v instanceof EditText/*etc.*/)
                ((TextView)v).setTypeface(typeface);
            else if(v instanceof ViewGroup)
                setSpecificTypeFace((ViewGroup)v, typeface);
        }
    }

	/**This method validates the given email is in valid format or not?
	 * @param email : Email id in String format*/
	public boolean isValidEmail(String email) {
		if(email == null || email.isEmpty())
			return false;
		return Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	/**This method hides the soft keyboard*/
	public void hideKeyBoard(View view){
		if(view != null){
			InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
}
