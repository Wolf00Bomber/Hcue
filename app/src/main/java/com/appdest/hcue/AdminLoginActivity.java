package com.appdest.hcue;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.AdminLoginRequest;
import com.appdest.hcue.model.AdminLoginResponse;
import com.appdest.hcue.services.RestCallback;
import com.appdest.hcue.services.RestClient;
import com.appdest.hcue.services.RestError;
import com.appdest.hcue.utils.Connectivity;
import com.appdest.hcue.utils.Preference;

import retrofit.client.Response;

/**
 * Created by shyamprasadg on 06/02/16.
 */
//kvk
public class AdminLoginActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout llLogin ;
    private EditText edtEnterEmailID,edtEnterPassword;
    private Button btnSubmit;
    private TextView tvFailureMessage;


    @Override
    public void initializeControls() {
        llLogin = (LinearLayout) inflater.inflate(R.layout.admin_login, null);
        llBody.addView(llLogin);

        edtEnterEmailID     = (EditText) llLogin.findViewById(R.id.edtEnterEmailID);
        edtEnterPassword    = (EditText) llLogin.findViewById(R.id.edtEnterPassword);
        btnSubmit           = (Button) llLogin.findViewById(R.id.btnSubmit);
        tvFailureMessage    = (TextView) llLogin.findViewById(R.id.tvFailureMessage);


        btnSubmit.setOnClickListener(this);

        setSpecificTypeFace(llLogin, AppConstants.WALSHEIM_MEDIUM);
        edtEnterEmailID.setTypeface(AppConstants.WALSHEIM_LIGHT);
        edtEnterPassword.setTypeface(AppConstants.WALSHEIM_LIGHT);
        tvFailureMessage.setTypeface(AppConstants.WALSHEIM_LIGHT);


    }

    @Override
    public void bindControls() {
        tvLogin.setEnabled(false);
        llTop.setVisibility(View.GONE);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                hideKeyBoard(btnSubmit);
                validateSignIn();
                break;
        }
    }

    private void validateSignIn() {
        String email = edtEnterEmailID.getText().toString().trim();
        String password = edtEnterPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            showToast("All fields are mandatory.");
        } else if(!isValidEmail(email)) {
            showToast("Please enter valid email ID.");
        } else {
            callService(email, password);
        }
    }

    private void callService(String email, String password) {
        if (Connectivity.isConnected(AdminLoginActivity.this)) {
            adminLogin(email, password);
        } else {
            Toast.makeText(AdminLoginActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void adminLogin(String email, String password) {
        showLoader("Loading...");

        final AdminLoginRequest adminLoginRequest = new AdminLoginRequest();
        adminLoginRequest.setDoctorLoginID(email);
        adminLoginRequest.setDoctorPassword(password);

        String url = "http://dct4avjn1lfw.cloudfront.net";
        RestClient.getAPI(url).adminLogin(adminLoginRequest, new RestCallback<AdminLoginResponse>() {
            @Override
            public void failure(RestError restError) {
                hideLoader();
                Log.e("Doctor Login", "" + restError.getErrorMessage());
                tvFailureMessage.setVisibility(View.VISIBLE);
            }

            @Override
            public void success(AdminLoginResponse adminLoginResponse, Response response) {
                hideLoader();
                if (adminLoginResponse != null) {
                    int loginDoctorID = adminLoginResponse.getArrDoctor().get(0).DoctorID;
                    String phone =  adminLoginResponse.getArrDoctorPhone().get(0).getPhNumber()+"";
                    Log.e("AdminLoginActivity", "loginDoctorID = "+loginDoctorID);
                    tvFailureMessage.setVisibility(View.GONE);
                    Intent intent = new Intent(AdminLoginActivity.this, AdminChooseHospital.class);
                    intent.putExtra("hospitals", adminLoginResponse.getArrDoctorAddress());
                    intent.putExtra("doctorId", loginDoctorID);
                    intent.putExtra("doctor", adminLoginResponse.getArrDoctor().get(0));
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                    finish();
                } else {
                    Log.i("Response", "" + response.getReason());
                }
            }
        });
    }
}
