package com.appdest.hcue;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import android.widget.ToggleButton;

import com.appdest.hcue.common.AppConstants;
import com.pushwoosh.BasePushMessageReceiver;
import com.pushwoosh.BaseRegistrationReceiver;
import com.pushwoosh.PushManager;

public abstract class BaseActivity extends FragmentActivity
{
	
	public LinearLayout llTop, llBody, llBottom;
	public LayoutInflater inflater;
	public Context context;
	public LayoutParams layoutParams;
	public ProgressDialog progressdialog;
	public TextView tvBack,tvTitle,tvHome,tvLogin;
	public Bundle savedInstanceState;
    public LinearLayout lladditional_info ;
	public ToggleButton toggle_additional_info ;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		registerReceivers();

		PushManager pushManager = PushManager.getInstance(this);

		//Start push manager, this will count app open for Pushwoosh stats as well
		try {
			pushManager.onStartup(this);
		}
		catch(Exception e)
		{
			//push notifications are not available or AndroidManifest.xml is not configured properly
		}

		//Register for push!
		pushManager.registerForPushNotifications();

		checkMessage(getIntent());

		this.savedInstanceState = savedInstanceState;
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


	@Override
	protected void onResume() {
		super.onResume();
		registerReceivers();
	}

	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		setIntent(intent);

		checkMessage(intent);
	}

	private void checkMessage(Intent intent)
	{
		if (null != intent)
		{
			if (intent.hasExtra(PushManager.PUSH_RECEIVE_EVENT))
			{
				showMessage("push message is " + intent.getExtras().getString(PushManager.PUSH_RECEIVE_EVENT));
			}
			else if (intent.hasExtra(PushManager.REGISTER_EVENT))
			{
				showMessage("register");
			}
			else if (intent.hasExtra(PushManager.UNREGISTER_EVENT))
			{
				showMessage("unregister");
			}
			else if (intent.hasExtra(PushManager.REGISTER_ERROR_EVENT))
			{
				showMessage("register error");
			}
			else if (intent.hasExtra(PushManager.UNREGISTER_ERROR_EVENT))
			{
				showMessage("unregister error");
			}

			resetIntentValues();
		}
	}

	/**
	 * Will check main Activity intent and if it contains any PushWoosh data, will clear it
	 */
	private void resetIntentValues()
	{
		Intent mainAppIntent = getIntent();

		if (mainAppIntent.hasExtra(PushManager.PUSH_RECEIVE_EVENT))
		{
			mainAppIntent.removeExtra(PushManager.PUSH_RECEIVE_EVENT);
		}
		else if (mainAppIntent.hasExtra(PushManager.REGISTER_EVENT))
		{
			mainAppIntent.removeExtra(PushManager.REGISTER_EVENT);
		}
		else if (mainAppIntent.hasExtra(PushManager.UNREGISTER_EVENT))
		{
			mainAppIntent.removeExtra(PushManager.UNREGISTER_EVENT);
		}
		else if (mainAppIntent.hasExtra(PushManager.REGISTER_ERROR_EVENT))
		{
			mainAppIntent.removeExtra(PushManager.REGISTER_ERROR_EVENT);
		}
		else if (mainAppIntent.hasExtra(PushManager.UNREGISTER_ERROR_EVENT))
		{
			mainAppIntent.removeExtra(PushManager.UNREGISTER_ERROR_EVENT);
		}

		setIntent(mainAppIntent);
	}

	private void showMessage(String message)
	{
		//Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	//Registration receiver
	BroadcastReceiver mBroadcastReceiver = new BaseRegistrationReceiver()
	{
		@Override
		public void onRegisterActionReceive(Context context, Intent intent)
		{
			checkMessage(intent);
		}
	};

	//Push message receiver
	private BroadcastReceiver mReceiver = new BasePushMessageReceiver()
	{
		@Override
		protected void onMessageReceive(Intent intent)
		{
			//JSON_DATA_KEY contains JSON payload of push notification.
			//showMessage("push message is " + intent.getExtras().getString(JSON_DATA_KEY));
		}
	};

	//Registration of the receivers
	public void registerReceivers()
	{
		IntentFilter intentFilter = new IntentFilter(getPackageName() + ".action.PUSH_MESSAGE_RECEIVE");

		registerReceiver(mReceiver, intentFilter, getPackageName() +".permission.C2D_MESSAGE", null);

		registerReceiver(mBroadcastReceiver, new IntentFilter(getPackageName() + "." + PushManager.REGISTER_BROAD_CAST_ACTION));
	}

	public void unregisterReceivers()
	{
		//Unregister receivers on pause
		try
		{
			unregisterReceiver(mReceiver);
		}
		catch (Exception e)
		{
			// pass.
		}

		try
		{
			unregisterReceiver(mBroadcastReceiver);
		}
		catch (Exception e)
		{
			//pass through
		}
	}

	private void initLocal() 
	{
		inflater 	= getLayoutInflater();
		layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		context = BaseActivity.this;
		
		llTop 		= 	(LinearLayout) 	findViewById(R.id.llTop);
		llBody 		= 	(LinearLayout) 	findViewById(R.id.llMiddle);
		lladditional_info 		= 	(LinearLayout) 	findViewById(R.id.lladditional_info);
		llBottom 	= 	(LinearLayout) 	findViewById(R.id.llFooter);
		tvBack 		= 	(TextView) 		findViewById(R.id.tvBack);
		tvTitle 	= 	(TextView) 		findViewById(R.id.tvTitle);
		tvLogin		=	(TextView) 		findViewById(R.id.tvLogin);
		tvHome 		= 	(TextView) 		findViewById(R.id.tvHome);
		toggle_additional_info 		= 	(ToggleButton) 		findViewById(R.id.toggle_additional_info);


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
		unregisterReceivers();
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
