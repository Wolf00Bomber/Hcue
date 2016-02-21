//package com.appdest.hcue;
//
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnFocusChangeListener;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class MainActivity extends Activity implements OnClickListener{
//
//
//	EditText edtFirstName, edtLastName, edtAge;
//	Button btnConfirm;
////	TextView tvMale,tvFeMale;
//	LinearLayout llUserDetails, llKeyboard;
////	ImageView ivMale,ivFeMale;
//	View focusedView;
//	InputMethodManager im;
//	Handler h;
//	Typeface type,typeone,typetwo,typethree,typefour,typefive,typelato;
//
//	Animation slide_up, slide_down;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.user_details_activity);
//
//		type = Typeface.createFromAsset(getAssets(),"GT-Walsheim-Bold.ttf");
//		typeone = Typeface.createFromAsset(getAssets(),"GT-Walsheim-Bold-Oblique.ttf");
//		typetwo = Typeface.createFromAsset(getAssets(),"GT-Walsheim-Light-Oblique.ttf");
//		typethree = Typeface.createFromAsset(getAssets(),"GT-Walsheim-Light.ttf");
//		typefour = Typeface.createFromAsset(getAssets(),"GT-Walsheim-Medium-Oblique.ttf");
//		typefive = Typeface.createFromAsset(getAssets(),"GT-Walsheim-Medium.ttf");
//		typelato = Typeface.createFromAsset(getAssets(),"Lato-Regular.ttf");
//
//		llUserDetails	=	(LinearLayout)	findViewById(R.id.llUserDetails);
//		llKeyboard		=	(LinearLayout)	findViewById(R.id.llKeyBoard);
//		edtFirstName 	= 	(EditText) 		findViewById(R.id.edtFirstName);
//		edtLastName 	= 	(EditText) 		findViewById(R.id.edtLastName);
//
//
////		tvMale			=	(TextView)		findViewById(R.id.tvMale);
////		tvFeMale		=	(TextView)		findViewById(R.id.tvFeMale);
//		edtAge 			= 	(EditText) 		findViewById(R.id.edtAge);
//		btnConfirm 		= 	(Button) 		findViewById(R.id.btnConfirm);
////		ivMale 			= 	(ImageView) 	findViewById(R.id.ivMale);
////		ivFeMale 		= 	(ImageView) 	findViewById(R.id.ivFeMale);
//
//
//
//		slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
//		slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
//
//		llKeyboard.setVisibility(View.GONE);
//		overrideFonts(llUserDetails);
//		edtFirstName.setTypeface(typethree);
//		edtLastName.setTypeface(typethree);
//		btnConfirm.setTypeface(type);
//
//		edtAge.setOnClickListener(this);
//		edtFirstName.setOnClickListener(this);
//		edtLastName.setOnClickListener(this);
//
//		h = new Handler(Looper.getMainLooper());
//
//		edtFirstName.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(final View v, boolean hasFocus) {
//				hideKeyBoard(v);
//				h.postDelayed(new Runnable() {
//
//					@Override
//					public void run() {
//						hideKeyBoard(v);
//					}
//				}, 50);
//				if(hasFocus){
//
//
//
//					if(llKeyboard.getVisibility() == View.GONE)
//						llKeyboard.startAnimation(slide_up);
//
//					llKeyboard.setVisibility(View.VISIBLE);
//
//				}
//			}
//		});
//		edtLastName.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(final View v, boolean hasFocus) {
//				hideKeyBoard(v);
//				h.postDelayed(new Runnable() {
//
//					@Override
//					public void run() {
//						hideKeyBoard(v);
//					}
//				}, 50);
//				if(hasFocus){
//
//					if(llKeyboard.getVisibility() == View.GONE)
//						llKeyboard.startAnimation(slide_up);
//					llKeyboard.setVisibility(View.VISIBLE);
//				}
//			}
//		});
//		edtAge.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(final View v, boolean hasFocus) {
//				hideKeyBoard(v);
//				h.postDelayed(new Runnable() {
//
//					@Override
//					public void run() {
//						hideKeyBoard(v);
//					}
//				}, 50);
//				if(hasFocus){
//
//
//					if(llKeyboard.getVisibility() == View.GONE)
//						llKeyboard.startAnimation(slide_up);
//
//					llKeyboard.setVisibility(View.VISIBLE);
//				}
//			}
//		});
//
//		hideKeyBoard(edtAge);
//		hideKeyBoard(edtFirstName);
//		hideKeyBoard(edtLastName);
//
//		edtAge.clearFocus();
//		edtFirstName.clearFocus();
//		edtLastName.clearFocus();
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//
//		h.postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
//				llKeyboard.setVisibility(View.GONE);
//			}
//		}, 50);
//	}
//
//	public void hideKeyBoard(View view)
//	{
//		im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//		im.hideSoftInputFromWindow(view.getWindowToken(), 0);
//	}
//
//	public void keyboardClick(View v)
//	{
//		hideKeyBoard(v);
//		focusedView = getCurrentFocus();
//
//		Button button = (Button)v;
//
//		if(focusedView != null && focusedView instanceof EditText)
//		{
//			String str = ((EditText)focusedView).getText().toString();
//
//			if(button.getText().toString().equalsIgnoreCase("123"))
//			{
//				Toast.makeText(MainActivity.this, "Need To Implement.", Toast.LENGTH_SHORT).show();
//			}
//			else if(button.getText().toString().equalsIgnoreCase("Delete"))
//			{
//				if(str.length()>0)
//				{
//					str = str.substring(0, str.length()-1);
//					((EditText)focusedView).setText(str);
//					((EditText)focusedView).setSelection(((EditText)focusedView).length());
//				}
//			}
//			else if(button.getText().toString().equalsIgnoreCase(""))
//			{
//				str = str + " ";
//				((EditText)focusedView).setText(str);
//				((EditText)focusedView).setSelection(((EditText)focusedView).length());
//			}
//			else if(button.getText().toString().equalsIgnoreCase("Shift"))
//			{
//				Toast.makeText(MainActivity.this, "Need To Implement.", Toast.LENGTH_SHORT).show();
//			}
//			else if(button.getText().toString().equalsIgnoreCase("Next"))
//			{
//				if(edtFirstName.isFocused())
//					edtLastName.requestFocus();
//				else if(edtLastName.isFocused())
//				{
//					edtAge.requestFocus();
//					button.setText("Done");
//				}
//				else{
//
//					if(llKeyboard.getVisibility() == View.VISIBLE)
//						llKeyboard.startAnimation(slide_down);
//
//					llKeyboard.setVisibility(View.GONE);
//				}
//			}
//			else if(button.getText().toString().equalsIgnoreCase("Done"))
//			{
//				if(llKeyboard.getVisibility() == View.VISIBLE)
//					llKeyboard.startAnimation(slide_down);
//
//				llKeyboard.setVisibility(View.GONE);
//			}
//			else
//			{
//				str = str + button.getText().toString() ;
//				((EditText)focusedView).setText(str);
//				((EditText)focusedView).setSelection(((EditText)focusedView).length());
//			}
//		}
//
//	}
//
//	private void overrideFonts(final View v) {
//	    try {
//	        if (v instanceof ViewGroup) {
//	            ViewGroup vg = (ViewGroup) v;
//	            for (int i = 0; i < vg.getChildCount(); i++) {
//	                View child = vg.getChildAt(i);
//	                overrideFonts(child);
//	         }
//	        } else if (v instanceof TextView ) {
//	            ((TextView) v).setTypeface(Typeface.createFromAsset(getAssets(), "Lato-Regular.ttf"));
//	        }
//	    } catch (Exception e) {
//			e.printStackTrace();
//	 	}
//	 }
//
//	@Override
//	public void onClick(final View v)
//	{
//		switch (v.getId()) {
//		case R.id.edtFirstName:
//		case R.id.edtAge:
//		case R.id.edtLastName:
//			v.requestFocus();
//			hideKeyBoard(v);
//			h.postDelayed(new Runnable() {
//
//				@Override
//				public void run() {
//					hideKeyBoard(v);
//				}
//			}, 50);
//
//
//			if(llKeyboard.getVisibility() == View.GONE)
//				llKeyboard.startAnimation(slide_up);
//
//			llKeyboard.setVisibility(View.VISIBLE);
//			break;
//
//		default:
//			break;
//		}
//	}
//
//}
