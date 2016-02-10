package com.appdest.hcue;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.appdest.hcue.common.AppConstants;

/**
 * Created by shyamprasadg on 09/02/16.
 */
public class EnterAddressActivity extends BaseActivity implements View.OnClickListener
{
    private LinearLayout llEmail;
    private EditText edtEnterAddress,edtLandmark;
    private Button btnConfirm,btnSkip;


    @Override
    public void initializeControls()
    {
        llEmail = (LinearLayout) inflater.inflate(R.layout.mail_more_details, null);
        llBody.addView(llEmail);



        edtEnterAddress	=	(EditText)		llEmail.findViewById(R.id.edtEnterAddress);
        edtLandmark		=	(EditText)		llEmail.findViewById(R.id.edtLandmark);



        btnConfirm		=	(Button)		llEmail.findViewById(R.id.btnConfirm);
        btnSkip			=	(Button)		llEmail.findViewById(R.id.btnSkip);


        btnConfirm.setOnClickListener(this);
        btnSkip.setOnClickListener(this);

        setSpecificTypeFace(llEmail, AppConstants.WALSHEIM_LIGHT);
        btnConfirm.setTypeface(AppConstants.WALSHEIM_BOLD);
        btnSkip.setTypeface(AppConstants.WALSHEIM_BOLD);

        tvTitle.setText("Enter Address");



    }

    @Override
    public void bindControls()
    {

    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {

            case R.id.btnConfirm:
                Intent intent = new Intent(EnterAddressActivity.this,ConfirmationFullViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSkip:
                Intent skip = new Intent(EnterAddressActivity.this,SelectDoctorActivity.class);
                startActivity(skip);
                break;

        }
    }
}
