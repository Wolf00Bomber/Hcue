package com.appdest.hcue;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.appdest.hcue.common.AppConstants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

public class EnterAddressActivity extends BaseActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String TAG = EnterAddressActivity.class.getSimpleName();
    private LinearLayout llEmail, llKeyboard, llSpecilaKeyboard;
    private EditText edtEnterAddress, edtLandmark;
    private Button btnConfirm, btnSkip;
    private InputMethodManager im;
    private Handler h;
    private Animation slide_up, slide_down;
    private View focusedView;
    boolean isActivityNeedsFinish = false;
    private GoogleApiClient mGoogleApiClient;
    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    private static final String STATE_RESOLVING_ERROR = "resolving_error";
    private static final int  PLACE_AUTOCOMPLETE_REQUEST_CODE_FOR_FULL_ADDRESS = 1000;
    private static final int  PLACE_AUTOCOMPLETE_REQUEST_CODE_FOR_LANDMARK = 1001;



    @Override
    public void initializeControls() {
        mResolvingError = savedInstanceState != null
                && savedInstanceState.getBoolean(STATE_RESOLVING_ERROR, false);

        llEmail = (LinearLayout) inflater.inflate(R.layout.mail_more_details, null);
        llBody.addView(llEmail);


        edtEnterAddress = (EditText) llEmail.findViewById(R.id.edtEnterAddress);
        edtLandmark = (EditText) llEmail.findViewById(R.id.edtLandmark);


        btnConfirm = (Button) llEmail.findViewById(R.id.btnConfirm);
        btnSkip = (Button) llEmail.findViewById(R.id.btnSkip);

        llKeyboard = (LinearLayout) llEmail.findViewById(R.id.llKeyBoard);
        llSpecilaKeyboard = (LinearLayout) llEmail.findViewById(R.id.llSpecialKeyBoard);

        llKeyboard.setVisibility(View.VISIBLE);
        llSpecilaKeyboard.setVisibility(View.GONE);

        btnConfirm.setOnClickListener(this);
        btnSkip.setOnClickListener(this);

        setSpecificTypeFace(llEmail, AppConstants.WALSHEIM_LIGHT);
        btnConfirm.setTypeface(AppConstants.WALSHEIM_BOLD);
        btnSkip.setTypeface(AppConstants.WALSHEIM_BOLD);

        tvTitle.setText("Enter Address");

        slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mResolvingError) {  // more about this later
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    public void findPlaceFullAddress(View view) {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                    .build();

            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(typeFilter)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_FOR_FULL_ADDRESS);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    public void findPlaceLandmark(View view) {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                    .build();

            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(typeFilter)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_FOR_LANDMARK);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_FOR_FULL_ADDRESS || resultCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_FOR_LANDMARK) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());
                if(requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_FOR_LANDMARK)
                {
                    edtLandmark.setText(place.getName());
                }
                else
                {
                    edtEnterAddress.setText(place.getName());
                }

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        else if (requestCode == REQUEST_RESOLVE_ERROR) {
            mResolvingError = false;
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mGoogleApiClient.isConnecting() &&
                        !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_RESOLVING_ERROR, mResolvingError);
    }

    @Override
    public void bindControls() {
        tvLogin.setEnabled(false);
        if (isActivityNeedsFinish)
            return;
        h = new Handler(Looper.getMainLooper());

        edtEnterAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {

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
        edtLandmark.setOnFocusChangeListener(new View.OnFocusChangeListener() {

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

        hideKeyBoard(edtEnterAddress);
        hideKeyBoard(edtLandmark);

        edtEnterAddress.clearFocus();
        edtLandmark.clearFocus();

    }

    public void keyboardClick(View v) {
        hideKeyBoard(v);
        focusedView = getCurrentFocus();

        Button button = (Button) v;

        if (focusedView != null && focusedView instanceof EditText) {
            String str = ((EditText) focusedView).getText().toString();

            if (button.getText().toString().equalsIgnoreCase("123")) {
                if (llSpecilaKeyboard.getVisibility() == View.GONE) {
                    llKeyboard.startAnimation(slide_down);
                    llKeyboard.setVisibility(View.GONE);
                    llSpecilaKeyboard.setVisibility(View.VISIBLE);
                    llSpecilaKeyboard.startAnimation(slide_up);
                }
            } else if (button.getText().toString().equalsIgnoreCase("ABC")) {
                if (llKeyboard.getVisibility() == View.GONE) {
                    llSpecilaKeyboard.startAnimation(slide_down);
                    llSpecilaKeyboard.setVisibility(View.GONE);
                    llKeyboard.setVisibility(View.VISIBLE);
                    llKeyboard.startAnimation(slide_up);
                }
            } else if (button.getText().toString().equalsIgnoreCase("DEL")) {
                if (str.length() > 0) {
                    str = str.substring(0, str.length() - 1);
                    ((EditText) focusedView).setText(str);
                    ((EditText) focusedView).setSelection(((EditText) focusedView).length());
                }
            } else if (button.getText().toString().equalsIgnoreCase("SPACE")) {
                str = str + " ";
                ((EditText) focusedView).setText(str);
                ((EditText) focusedView).setSelection(((EditText) focusedView).length());
            } else if (button.getText().toString().equalsIgnoreCase("Done")) {
                if (llKeyboard.getVisibility() == View.VISIBLE)
                    llKeyboard.startAnimation(slide_down);

                llKeyboard.setVisibility(View.GONE);
            } else {
                str = str + button.getText().toString();
                ((EditText) focusedView).setText(str);
                ((EditText) focusedView).setSelection(((EditText) focusedView).length());
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

    public void hideKeyBoard(View view) {
        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.edtEnterAddress:
            case R.id.edtLandmark:
                v.requestFocus();
                hideKeyBoard(v);
                h.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        hideKeyBoard(v);
                    }
                }, 50);


                if (llKeyboard.getVisibility() == View.GONE)
                    llSpecilaKeyboard.setVisibility(View.GONE);

                llKeyboard.setVisibility(View.VISIBLE);
                break;

            case R.id.btnConfirm:
                Intent intent = new Intent(EnterAddressActivity.this, ConfirmationFullViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSkip:
                Intent skip = new Intent(EnterAddressActivity.this, SelectDoctorActivity.class);
                skip.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(skip);
                break;

        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // Connected to Google Play services!
        // The good stuff goes here.
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                mGoogleApiClient.connect();
            }
        } else {
            // Show dialog using GoogleApiAvailability.getErrorDialog()
            showErrorDialog(result.getErrorCode());
            mResolvingError = true;
        }
    }

    // The rest of this code is all about building the error dialog

    /* Creates a dialog for an error message */
    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "errordialog");
    }

    /* Called from ErrorDialogFragment when the dialog is dismissed. */
    public void onDialogDismissed() {
        mResolvingError = false;
    }

    /* A fragment to display an error dialog */
    public static class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() { }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            ((EnterAddressActivity) getActivity()).onDialogDismissed();
        }
    }


}
