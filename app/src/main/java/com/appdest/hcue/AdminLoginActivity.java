package com.appdest.hcue;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdest.hcue.common.AppConstants;

/**
 * Created by shyamprasadg on 06/02/16.
 */
public class AdminLoginActivity extends BaseActivity implements View.OnClickListener
{
    private LinearLayout llLogin;
    private EditText edtEnterEmailID,edtEnterPassword;
    private Button btnSubmit;
    private TextView tvFailureMessage;

    @Override
    public void initializeControls()
    {
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

        llTop.setVisibility(View.GONE);
    }

    @Override
    public void bindControls()
    {

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnSubmit:
                hideKeyBoard(btnSubmit);
                validateSignIn();
                break;
        }
    }

    private void validateSignIn() {
        String email = edtEnterEmailID.getText().toString().trim();
        String password = edtEnterPassword.getText().toString().trim();

        if(email == null || password == null || email.isEmpty() || password.isEmpty()) {
            showToast("All fields are mandatory.");
        } else if(!isValidEmail(email)) {
            showToast("Please enter valid email ID.");
        } else
        {
            //need to call Web Service
            showToast("Login success.");
            Intent intent = new Intent(AdminLoginActivity.this,AdminChooseHospital.class);
            startActivity(intent);
        }
    }
}
