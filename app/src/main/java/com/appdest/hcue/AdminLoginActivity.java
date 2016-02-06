package com.appdest.hcue;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.appdest.hcue.common.AppConstants;

/**
 * Created by shyamprasadg on 06/02/16.
 */
public class AdminLoginActivity extends BaseActivity implements View.OnClickListener
{
    private LinearLayout llLogin;
    private EditText edtENterEmailID,edtEnterPassword;
    private Button btnSubmit;


    @Override
    public void initializeControls()
    {
        llLogin = (LinearLayout) inflater.inflate(R.layout.admin_login, null);
        llBody.addView(llLogin);

        edtENterEmailID     =   (EditText)  llLogin.findViewById(R.id.edtENterEmailID);
        edtEnterPassword    =   (EditText)  llLogin.findViewById(R.id.edtEnterPassword);

        btnSubmit           =   (Button)    llLogin.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(this);

        setSpecificTypeFace(llLogin, AppConstants.WALSHEIM_MEDIUM);
        edtENterEmailID.setTypeface(AppConstants.WALSHEIM_LIGHT);
        edtEnterPassword.setTypeface(AppConstants.WALSHEIM_LIGHT);

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
                Intent intent = new Intent(AdminLoginActivity.this,SelectDoctorActivity.class);
                startActivity(intent);
                break;
        }
    }
}
